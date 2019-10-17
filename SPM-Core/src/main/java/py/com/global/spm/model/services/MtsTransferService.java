package py.com.global.spm.model.services;


import com.global.spm.drivertransaction.RequestForTransferTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.global.spm.domain.entity.*;
import py.com.global.spm.model.eventcodes.MTSTransferProcessEventCodes;
import py.com.global.spm.model.exceptions.MTSInterfaceException;
import py.com.global.spm.model.mts.MtsService;
import py.com.global.spm.model.mts.SalaryPaymentResponse;
import py.com.global.spm.model.services.redis.LogPaymentCacheService;
import py.com.global.spm.model.systemparameters.MtsInterfaceParameters;
import py.com.global.spm.model.util.ConstantsQueue;
import py.com.global.spm.model.util.PaymentStates;
import py.com.global.spm.model.util.SpmConstants;
import py.com.global.spm.model.util.SpmProcesses;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
public class MtsTransferService {
    private final Logger log = LoggerFactory.getLogger(MtsTransferService.class);

    private final SystemParameterService systemParameterService;
    private final CompanyService companyService;
    private final LogPaymentService logPaymentService;
    private final MtsService mtsService;
    private final JmsTemplate jmsTemplate;
    private final LogMtsService logMtsService;


    public static final String LIMITED_AMOUNT = "10000";

    @Autowired
    public MtsTransferService(SystemParameterService systemParameterService, CompanyService companyService, LogPaymentService logPaymentService, MtsService mtsService, JmsTemplate jmsTemplate, LogMtsService logMtsService) {
        this.systemParameterService = systemParameterService;
        this.companyService = companyService;
        this.logPaymentService = logPaymentService;
        this.mtsService = mtsService;
        this.jmsTemplate = jmsTemplate;
        this.logMtsService = logMtsService;
    }

    public void processPayment(Logpayment payment, String walletChr, Long idpaymentOrderPk) {
        // 0.1 Verificar el estado.
        if (PaymentStates.PAGO_PENDIENTE.equals(payment.getStateChr())) {
            // 1- Obtener de systemParameters monto para particionar el pago en transacciones.
            String montoLimite = systemParameterService.getParameterValue(
                    MtsInterfaceParameters.LIMITED_AMOUNT.getIdProcess(),
                    MtsInterfaceParameters.LIMITED_AMOUNT.getParameterName(), LIMITED_AMOUNT);

            BigDecimal amountDouble = new BigDecimal(montoLimite);
            BigDecimal[] montos = this.getAmounts(payment.getAmountNum(), amountDouble);

            // 2- Generar los registros para LogMts.
            BigDecimal montoPagado = BigDecimal.ZERO;
            Long sessionId = payment.getIdpaymentNum();
            //try {
                Company company = companyService.getCompanyById(payment.getCompany().getIdcompanyPk());
                int jmsPriority = (company.getPriority()!=null)?company.getPriority():4;
                for (int i = 0; i < montos.length; i++) {

                    // 4- Persistir cada registro
                    Beneficiary beneficiario = payment.getPaymentorderdetail().getBeneficiary();
                    Logmts logmts = new Logmts();
                    logmts.setAmountNum(montos[i]);// Monto dividido.
                    logmts.setCompany(payment.getCompany());
                    logmts.setIdeventcodeNum(0L);// Estado 0 es que esta bien.
                    logmts.setEventcode(new Eventcode(new EventcodeId(SpmProcesses.MTS_TRANSFER_INTERFACE,0L)));
                    logmts.setLogpayment(payment);
                    logmts.setPaymentorderdetail(payment.getPaymentorderdetail());
                    logmts.setIdprocessPk(SpmProcesses.MTS_TRANSFER_INTERFACE);
                    logmts.setTrxtypeChr(SpmConstants.MTS_OPERATION_PAYMENT);
                    logmts.setRequestdateTim(new Timestamp(System.currentTimeMillis()));
                    logmts.setIdsessionmtsChr(String.valueOf(sessionId));
                    logmts.setEventcode(payment.getEventcode());

                    // Cambio hakure. Obtener Rol,wallet y currency de la tabla beneficiary.
                    logmts.setRequestChr("idSession=" + sessionId + ",PhoneNumber=" + payment.getPhonenumberChr()
                            + ",Rol=" + beneficiario.getRolspChr() + ",Monto=" + montos[i] + ",Currency="
                            + beneficiario.getCurrencyChr() + ",Band=" + company.getMtsfield1Chr() + ",Wallet="
                            + beneficiario.getWalletChr());
                    logmts.setMethodChr(SpmConstants.MTS_SALARYPAYMENT);

                    // 5- Ejecutar cada transaccion.
                    try {
                        // 1- Obtener datos para conexion.

                        RequestForTransferTransaction request = new RequestForTransferTransaction();
                        request.setAccount(beneficiario.getPhonenumberChr());
                        request.setSessionId(sessionId);
                        request.setAmount(montos[i]);
                        request.setRpmTransactionId(idpaymentOrderPk);
                        request.setCollectAccount(walletChr);
                        request.setCollectAccountPin(company.getMtspasswordChr());

                        SalaryPaymentResponse response = mtsService.transfer(request);

                        // 6- Enviar actualizacion de registro al AsyncUpdater.
                        logmts.setResponsedateTim(new Timestamp(System.currentTimeMillis()));
                        logmts.setIdtrxmtsChr(response.getNroTransaction().toString());
                        logmts.setResponseChr(response.getResponseStr());
                        logmts.setStateChr(SpmConstants.SUCCESS);

                    } catch (MTSInterfaceException e) {
                        logmts.setResponsedateTim(new Timestamp(System.currentTimeMillis()));
                        logmts.setStateChr(SpmConstants.ERROR);
                        logmts.setResponseChr(e.getMessage());
                        logmts.setIdeventcodeNum(e.getIdEvent());
                        log.error("Error mts:IdEvent-> {} ,msg= {}",e.getIdEvent(), e.getMessage());
                    }finally {

                        try {
                            //7-Enviar a AsyncUpdater
                            jmsTemplate.setPriority(jmsPriority);
                            jmsTemplate.convertAndSend(ConstantsQueue.ASYNC_UPDATER_QUEUE_LOGMTS, logmts);
                        }catch (Exception e){
                            log.error("Al enviar el LogMTS a la cola {}",ConstantsQueue.ASYNC_UPDATER_QUEUE_LOGMTS,e);
                        }
                    }
                    // 8-Contabilizamos el monto pagado.
                    if (logmts.getStateChr().equals(SpmConstants.SUCCESS)) {
                        montoPagado = montoPagado.add(montos[i]);
                    }
                }

                // 9- Actualizar logpayment con datos actualizados.
                // 9.1- Verificar si el pago se queda con estado parcial.
                String estadoOp = PaymentStates.SATISFACTORIO;
                if (montoPagado.compareTo(BigDecimal.ZERO) == 0) {
                    estadoOp = PaymentStates.ERROR;
                } else if (montoPagado.compareTo(payment.getAmountNum()) < 0) {
                    estadoOp = PaymentStates.PARCIALMENTE_PAGADA;
                }
                // 9.2- Actualizar el monto pagado y el estado.
                payment.setStateChr(estadoOp);
                payment.setAmountpaidChr(String.valueOf(montoPagado));
                payment.setIdprocessPk(SpmProcesses.MTS_TRANSFER_INTERFACE);
                try {
                    logPaymentService.saveOrUpdate(payment);
                }catch (Exception e){
                    log.error("Persistiendo LogPayment:",e);
                }

        } else if (PaymentStates.ERROR.equals(payment.getStateChr())
                || PaymentStates.PARCIALMENTE_PAGADA.equals(payment.getStateChr())) {
            // Esta parte es para el reintento de pago.
            // 1- Buscar transacciones mts del pago con estado error.
            List<Logmts> retryLogmts = logMtsService.findLogmts(payment.getIdpaymentNum(),SpmConstants.ERROR);
            // 2- Efectuar transaccion MTS.
            // 2.1-Inicio sesion para ejecutar pagos.
            Long sessionId = payment.getIdpaymentNum();
            try {
                if (retryLogmts != null && !retryLogmts.isEmpty()) {
                    BigDecimal montoPagado = payment.getAmountpaidNum();
                    Company company = payment.getCompany();
                    for (Logmts l : retryLogmts) {
                        Beneficiary beneficiario = l.getPaymentorderdetail().getBeneficiary();

                        Logmts log2 = new Logmts();
                        log2.setIdParent(l.getIdlogmtsPk());
                        log2.setAmountNum(l.getAmountNum());// Monto dividido.
                        log2.setCompany(l.getCompany());
                        log2.setIdeventcodeNum(0L);// Estado 0 es que esta bien.
                        log2.setLogpayment(l.getLogpayment());
                        log2.setPaymentorderdetail(l.getPaymentorderdetail());
                        log2.setIdprocessPk(SpmProcesses.MTS_TRANSFER_INTERFACE);
                        log2.setTrxtypeChr(SpmConstants.MTS_OPERATION_PAYMENT);// Para
                        // evitar
                        // pagos
                        // repetidos.
                        log2.setRequestdateTim(new Timestamp(System.currentTimeMillis()));
                        log2.setIdsessionmtsChr(sessionId.toString());
                        log2.setEventcode(new Eventcode(new EventcodeId(SpmProcesses.MTS_TRANSFER_INTERFACE,0L)));

                        // Cambio hakure. Obtener Rol,wallet y currency de la
                        // tabla
                        // beneficiary.
                        log2.setRequestChr("idSession=" + sessionId
                                + ",PhoneNumber=" + payment.getPhonenumberChr() + ",Rol=" + beneficiario.getRolspChr()
                                + ",Monto=" + l.getAmountNum() + ",Currency=" + beneficiario.getCurrencyChr() + ",Band="
                                + company.getMtsfield1Chr() + ",Wallet=" + beneficiario.getWalletChr());
                        log2.setMethodChr(SpmConstants.MTS_SALARYPAYMENT);

                        // 3- Ejecutar cada transaccion.
                        try {
                            // 3.1- Obtener datos para conexion.
                            RequestForTransferTransaction request = new RequestForTransferTransaction();
                            request.setAccount(beneficiario.getPhonenumberChr());
                            request.setSessionId(sessionId);
                            request.setAmount(l.getAmountNum());
                            request.setRpmTransactionId(idpaymentOrderPk);
                            request.setCollectAccount(walletChr);
                            request.setCollectAccountPin(company.getMtspasswordChr());

                            SalaryPaymentResponse response = mtsService.transfer(request);

                            // 3.2- Enviar actualizacion de registro al
                            // AsyncUpdater.
                            log2.setResponsedateTim(new Timestamp(System.currentTimeMillis()));
                            log2.setIdtrxmtsChr(response.getNroTransaction().toString());
                            log2.setResponseChr(response.getResponseStr());
                            log2.setStateChr(SpmConstants.SUCCESS);

                        } catch (MTSInterfaceException e) {
                            log2.setResponsedateTim(new Timestamp(System.currentTimeMillis()));
                            log2.setStateChr(SpmConstants.ERROR);
                            log2.setResponseChr(e.getMessage());
                            log2.setIdeventcodeNum(e.getIdEvent());
                            log.error("Error mts:IdEvent->" + e.getIdEvent() + ",msg=" + e.getMessage());
                        }
                        // 4-Contabilizamos el monto pagado.
                        if (log2.getStateChr().equals(SpmConstants.SUCCESS)) {
                            montoPagado = montoPagado.add(l.getAmountNum());
                        }
                        // 5-Enviar a AsyncUpdater
                        jmsTemplate.convertAndSend(ConstantsQueue.ASYNC_UPDATER_QUEUE_LOGMTS, log2);

                        // 6- Cambiar operacion que se aplico al registro.
                        l.setTrxtypeChr(SpmConstants.MTS_OPERATION_PAYMENT_RETRY);
                        jmsTemplate.convertAndSend(ConstantsQueue.ASYNC_UPDATER_QUEUE_LOGMTS, l);

                    }// Fin For_LogRetry

                    // 6- Actualizar logpayment con datos actualizados.
                    // 6.1- Verificar si el pago se queda con estado parcial.
                    String estadoOp = PaymentStates.SATISFACTORIO;
                    if (montoPagado.compareTo(payment.getAmountpaidNum()) == 0) {
                        estadoOp = payment.getStateChr();
                    } else if (montoPagado.compareTo(payment.getAmountNum()) < 0) {
                        estadoOp = PaymentStates.PARCIALMENTE_PAGADA;
                    }

                    // 6.2- Actualizar el monto pagado y el estado.
                    payment.setStateChr(estadoOp);
                    payment.setAmountpaidNum(montoPagado);
                    payment.setIdprocessPk(SpmProcesses.MTS_TRANSFER_INTERFACE);
                    logPaymentService.saveOrUpdate(payment);

                } else {
                    log.warn("No hay registros de logmts para reintentar el pago->{}", payment);
                }
            } catch (Exception e1) {
                log.error(
                        "Error al ejecutar reintento de pago en MTS->" + e1.getMessage(), e1);

            }
        } else {
            log.warn("Estado de pago invalido -->{}", payment);
        }


    }


    /**
     * Calcula los montos para las solicitudes.
     *
     * @param montoTotal
     *            Monto total a dividir.
     * @param montoLimite
     *            Monto maximo por transaccion.
     * @return Array con montos para transacciones.
     */
    private BigDecimal[] getAmounts(BigDecimal montoTotal, BigDecimal montoLimite) {
        BigDecimal[] montos;
        if (montoTotal.compareTo(montoLimite) > 0) {
            int cantAmount =montoTotal.divide(montoLimite,0,BigDecimal.ROUND_HALF_UP).intValue();
            BigDecimal resto = (montoTotal.remainder(montoLimite));
            cantAmount = (resto.compareTo(new BigDecimal(0)) != 0 ) ? (cantAmount + 1) : cantAmount;
            montos = new BigDecimal[cantAmount];
            for (int i = 0; i < montos.length - 1; i++) {
                montos[i] = montoLimite;
            }

            montos[montos.length - 1] = (resto.compareTo(new BigDecimal(0)) != 0 ) ? resto : montoLimite;

        } else {
            montos = new BigDecimal[1];
            montos[0] = montoTotal;
        }
        return montos;
    }


    public boolean isCompanyReposicionGuardaSaldo(Long idcompanyPk) {

        String idCompanyForReposicionGuardaSaldo = systemParameterService
                .getParameterValue(MtsInterfaceParameters.ID_COMPANY_FOR_REPOSICION_GUARDA_SALDO.getIdProcess(),
                        MtsInterfaceParameters.ID_COMPANY_FOR_REPOSICION_GUARDA_SALDO.getParameterName());
        log.info("idEmpresaActual --> " + idcompanyPk
                + "  idCompanyForReposicionGuardaSaldo --> " + idCompanyForReposicionGuardaSaldo);
        boolean res = false;

        String idcompany = Long.toString(idcompanyPk);
        String[] idCompanies = idCompanyForReposicionGuardaSaldo.split(",");
        for (String id : idCompanies) {
            if (id.equals(idcompany)) {
                res = true;
                log.info("[COMPANY REPOSICION GUARDA SALDO: " + res + "]");
                break;
            }
        }

        return res;
    }
}
