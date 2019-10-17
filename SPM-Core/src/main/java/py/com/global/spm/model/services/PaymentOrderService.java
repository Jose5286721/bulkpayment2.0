package py.com.global.spm.model.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.global.spm.domain.entity.*;
import py.com.global.spm.model.dto.CancelPaymentOrderDto;
import py.com.global.spm.model.dto.ResponseDto;
import py.com.global.spm.model.dto.RetryPaymentOrder;
import py.com.global.spm.model.dto.SignRequestDto;
import py.com.global.spm.model.eventcodes.EstadosPaymentorder;
import py.com.global.spm.model.eventcodes.OPManagerEventCodes;
import py.com.global.spm.model.exceptions.PaymentOrderRequiredValuesException;
import py.com.global.spm.model.message.SPMGUIToFlowManagerMessage;
import py.com.global.spm.model.message.SPMGUIToTransferProcessMessage;
import py.com.global.spm.model.repository.IPaymentOrderDao;
import py.com.global.spm.model.systemparameters.OPManagerParameters;
import py.com.global.spm.model.util.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PaymentOrderService {

    private final Logger logger = LoggerFactory.getLogger(PaymentOrderService.class);

    @Autowired
    private IPaymentOrderDao paymentOrderDao;
    @Autowired
    private PaymentOrderDetailService paymentOrderDetailService;
    @Autowired
    private DraftService draftService;
    @Autowired
    private EventCodeService eventCodeService;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private SystemParameterService systemParameterService;
    @Autowired
    private FlowService flowService;
    @Autowired
    private UserService userService;
    @Autowired
    private ApprovalService approvalService;


    public Paymentorder saveOrUpdate(Paymentorder paymentorder) {
        try {
            return paymentOrderDao.saveAndFlush(paymentorder);
        } catch (Exception e) {
            logger.error("Error al Guardar/Actualizar PaymentOrder", e);
            throw e;
        }
    }

    public void createPaymentorder(Paymentorder paymentorder, List<Paymentorderdetail> paymentorderdetails) throws PaymentOrderRequiredValuesException {

        try {
            logger.debug("Creating paymentorder --> \n" + paymentorder + ", "
                    + SpmUtil.paymentorderdetailListToString(paymentorderdetails));
                saveOrUpdate(paymentorder);
                for (Paymentorderdetail pod : paymentorderdetails) {
                    pod.getId().setIdpaymentorderPk(paymentorder.getIdpaymentorderPk());
                    paymentOrderDetailService.saveOrUpdate(pod);
                }
        } catch (Exception e) {
            logger.error("Creating paymentorder --> " + paymentorder, e);
            throw e;
        }

    }



    public Paymentorder getPaymentorder(long idpaymentorder) {
        try {
            return paymentOrderDao.findByIdpaymentorderPk(idpaymentorder);
        } catch (Exception e) {
            logger.error("Finding paymentorder --> idpaymentorder=" + idpaymentorder, e);
            throw e;
        }
    }

    public void createPaymentorderAndInactivateDraft(Draft draft, Paymentorder paymentorder, List<Paymentorderdetail> paymentorderdetails)
            throws PaymentOrderRequiredValuesException {
        try {
            logger.debug("Creating paymentorder --> \n" + paymentorder
                    + ", " + SpmUtil.paymentorderdetailListToString(paymentorderdetails));

            // validar campos requeridos
                saveOrUpdate(paymentorder);
                long date = System.currentTimeMillis();
                logger.info("Inicio de creacion de paymentOrderDetails");
                Long idPo = paymentorder.getIdpaymentorderPk();
                List<Paymentorderdetail> paymentorderdetailList = Collections.synchronizedList(new ArrayList<>());
               paymentorderdetails.parallelStream().forEach(pod ->{
                    pod.getId().setIdpaymentorderPk(idPo);
                    paymentorderdetailList.add(pod);
               });
               paymentOrderDetailService.saveAll(paymentorderdetailList);
                logger.info("Finalizacion de creacion de PoD --> {}",(System.currentTimeMillis()-date));
                draft.setStateChr(DraftStates.INACTIVE);
                draftService.saveOrUpdate(draft);
        } catch (Exception e) {
            logger.error("Creating paymentorder --> " + paymentorder, e);
            throw e;
        }

    }

    public Paymentorder generatePaymentorder(Draft draft, boolean programado) throws Exception {
        try {
            List<Draftdetail> draftdetailList =  new ArrayList<>(draft.getDraftdetails());
            logger.debug("Generating Paymentorder --> " + draft + ", detail count=" + draftdetailList.size());
            // validar empresa
            OPManagerEventCodes validateCompany = isValidCompany(draft.getCompany(), draftdetailList.size());

            if (!(OPManagerEventCodes.SUCCESS.getIdEventCode().equals(validateCompany.getIdEventCode()))) {
                this.createLogdraftOP(draft, validateCompany, null);
                logger.error("Error generando orden de pago  --> " + draft);
                throw new Exception();
            }

            String globalAmountByPayment = systemParameterService.getParameterValue(
                    OPManagerParameters.GLOBAL_AMOUNT_BY_PAYMENT.getIdProcess(),
                    OPManagerParameters.GLOBAL_AMOUNT_BY_PAYMENT.getParameterName(),
                    OPManagerParameters.GLOBAL_AMOUNT_BY_PAYMENT.getDefaultValue());

            BigDecimal totalAmount = new BigDecimal(0);
            for (Draftdetail draftdetail : draftdetailList) {

                // validar monto por pago
                // validar limite de monto global de orden de pago
                if (draftdetail.getAmountNum().compareTo(new BigDecimal(globalAmountByPayment)) > 0) {
                    logger.error("Monto maximo por pago excedido --> " + draft
                            + ", " + draftdetail + ", globalAmountByPayment=" + globalAmountByPayment);
                    this.createLogdraftOP(draft, OPManagerEventCodes.GLOBAL_AMOUNT_BY_PAYMENT_EXCEEDED, null);
                    throw new Exception();
                }
                //monto total de la orden de pago
                totalAmount = totalAmount.add(draftdetail.getAmountNum());
            }

            // validar limite de monto global de orden de pago
            String globalAmountByPaymentOrder = systemParameterService.getParameterValue(
                    OPManagerParameters.GLOBAL_AMOUNT_BY_PAYMENT_ORDER.getIdProcess(),
                    OPManagerParameters.GLOBAL_AMOUNT_BY_PAYMENT_ORDER.getParameterName(),
                    OPManagerParameters.GLOBAL_AMOUNT_BY_PAYMENT_ORDER.getDefaultValue());

            if (totalAmount.compareTo(new BigDecimal(globalAmountByPaymentOrder)) > 0) {
                logger.error("Monto total maximo por orden de pago excedido --> " + draft + ", globalAmountByPaymentOrder="
                        + globalAmountByPaymentOrder + ", monto=" + totalAmount);
                this.createLogdraftOP(draft, OPManagerEventCodes.GLOBAL_AMOUNT_BY_PAYMENT_ORDER_EXCEEDED, null);

                throw new Exception();
            }

            // todo ok, generar orden de pago
            Paymentorder paymentorder = this.createPaymentOrder(draft, totalAmount,draftdetailList.size());

            List<Paymentorderdetail> paymentorderdetailList = this
                    .createPaymentorderDetail(paymentorder, draftdetailList);

            try {
                if (!programado) {
                    createPaymentorder(paymentorder, paymentorderdetailList);
                    draft.setLastGenerationDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    draftService.saveOrUpdate(draft);
                } else {
                    createPaymentorderAndInactivateDraft(draft, paymentorder, paymentorderdetailList);
                }
            } catch (Exception e) {
                logger.error("Error persistiendo orden de pago --> " + draft, e);
                this.createLogdraftOP(draft, OPManagerEventCodes.ERROR_PERSISTING_PAYMENT_ORDER, null);
                throw new Exception();
            }

            // generar flujo de aprobacion de orden de pago
            if (flowService.generateApprovalFlow(paymentorder) == null) {
                this.createLogdraftOP(draft, OPManagerEventCodes.ERROR_GENERATE_APPROVAL_FLOW_PO, paymentorder);
                throw new Exception();
            }

            this.createLogdraftOP(draft, OPManagerEventCodes.SUCCESS, paymentorder);
            logger.info("Paymentorder created! \n" + paymentorder + SpmUtil.paymentorderdetailListToString(paymentorderdetailList));

            return paymentorder;
        } catch (Exception e) {
            logger.error("Error desconocido generando orden de pago", e);
            this.createLogdraftOP(draft, OPManagerEventCodes.ERROR_GENERATE_PAYMENT_ORDER, null);
            throw e;
        }
    }

    private Paymentorder createPaymentOrder(Draft draft, BigDecimal totalAmount, Integer cantBeneficiaries) {
        Paymentorder paymentorder = new Paymentorder();
        paymentorder.setDraft(draft);
        paymentorder.setCreationdateTim(new Timestamp(System.currentTimeMillis()));
        paymentorder.setDescriptionChr(draft.getDescriptionChr());
        paymentorder.setCompany(draft.getCompany());
        paymentorder.setPaymentordertype(draft.getPaymentordertype());
        paymentorder.setWorkflow(draft.getWorkflow());
        paymentorder.setStateChr(PaymentOrderStates.FIRMA_EN_PROCESO);
        paymentorder.setUpdatedateTim(new Timestamp(System.currentTimeMillis()));
        paymentorder.setNotifybenficiaryNum(draft.isNotifybenficiaryNum());
        paymentorder.setNotifycancelNum(draft.isNotifycancelNum());
        paymentorder.setNotifygenNum(draft.isNotifygenNum());
        paymentorder.setNotifysignerNum(draft.isNotifysignerNum());
        paymentorder.setAmountNum(totalAmount);
        paymentorder.setAmountpaidNum(new BigDecimal(0));
        paymentorder.setIdprocessPk(SpmProcesses.PO_MANAGER);
        paymentorder.setIdeventcodeNum(OPManagerEventCodes.SUCCESS.getIdEventCode());
        paymentorder.setTotalpaymentNum(0L);
        paymentorder.setPaymentsuccessNum(0L);
        paymentorder.setPaymentpartsucNum(0L);
        paymentorder.setPaymenterrorNum(0L);
        paymentorder.setCantBeneficiaries(cantBeneficiaries);
        return paymentorder;
    }

    private List<Paymentorderdetail> createPaymentorderDetail(
            Paymentorder paymentorder, List<Draftdetail> draftdetailList) {

        List<Paymentorderdetail> paymentorderdetailList = new ArrayList<Paymentorderdetail>();

        for (Draftdetail draftdetail : draftdetailList) {
            Paymentorderdetail pod = new Paymentorderdetail();
            PaymentorderdetailId paymentorderdetailId = new PaymentorderdetailId();
            paymentorderdetailId.setIdpaymentorderPk(paymentorder.getIdpaymentorderPk());
            paymentorderdetailId.setIdbeneficiaryPk(draftdetail.getBeneficiary().getIdbeneficiaryPk());
            pod.setId(paymentorderdetailId);
            pod.setBeneficiary(draftdetail.getBeneficiary());
            pod.setPaymentorder(paymentorder);
            pod.setAmountNum(draftdetail.getAmountNum());
            paymentorderdetailList.add(pod);
        }
        return paymentorderdetailList;
    }


    private void createLogdraftOP(Draft draft, OPManagerEventCodes event, Paymentorder paymentorder) {
        // Crear logdraftOP y enviar al updater para que persista
        Logdraftop logdraftop = new Logdraftop();
        logdraftop.setCreationdateTim(new Timestamp(System.currentTimeMillis()));
        logdraftop.setCompany(draft.getCompany());
        logdraftop.setDraft(draft);
        Eventcode eventcode = eventCodeService.getEventByIdAndProcess(event.getIdEventCode(), SpmProcesses.PO_MANAGER);
        logdraftop.setEventcode(eventcode);
        if (event.getIdEventCode().equals(OPManagerEventCodes.SUCCESS.getIdEventCode())) {
            logdraftop.setPaymentorder(paymentorder);
        }
        Company c = draft.getCompany();
        int jmsPriority = (c.getPriority() != null) ?
                c.getPriority() : 4;
        jmsTemplate.setPriority(jmsPriority);
        jmsTemplate.convertAndSend(ConstantsQueue.ASYNC_UPDATER_QUEUE_LOGDRAFTTOP, logdraftop);
    }

    private OPManagerEventCodes isValidCompany(Company company, int paidCount) {
        if (CompanyStates.ACTIVE.compareToIgnoreCase(company.getStateChr()) != 0) {
            logger.warn("Company not active --> " + company);
            return OPManagerEventCodes.INACTIVE_COMPANY;
        }

        // validar cantidad de transacciones total
        if (company.getTrxtotallimitNum() > 0
                && (company.getTrxtotalcountNum() + paidCount > company.getTrxtotallimitNum())) {
            logger.warn("Limite de transacciones total superado --> " + company + ", trx=" + paidCount);
            return OPManagerEventCodes.TRX_TOTAL_EXCEEDED;
        }

        // validar cantidad de transacciones diario
        if (company.getTrxdaylimitNum() > 0
                && (company.getTrxdaycountNum() + paidCount > company.getTrxdaylimitNum())) {
            logger.warn("Limite de transacciones diario superado --> " + company + ", trx=" + paidCount);
            return OPManagerEventCodes.TRX_DIARY_EXCEEDED;
        }

        // validar cantidad de transacciones mensual
        if (company.getTrxmonthlimitNum() > 0
                && (company.getTrxmonthcountNum() + paidCount > company.getTrxmonthlimitNum())) {
            logger.warn("Limite de transacciones mensual superado --> " + company + ", trx=" + paidCount);
            return OPManagerEventCodes.TRX_MONTHLY_EXCEEDED;
        }
        // todo OK
        return OPManagerEventCodes.SUCCESS;
    }


    public ResponseDto sign(SignRequestDto requestDto) {
        try {
            if (requestDto == null) {
                logger.info("PaymentOrder Request Invalid");
                return ResponseDto.getDtoEmpty();
            }
            User user = userService.getUserById(requestDto.getIdUser());
            if (user == null) {
                logger.info("Usuario no Encontrado: " + requestDto.getIdUser());
                return ResponseDto.getDtoEmpty();
            }
            Paymentorder paymentorder = getPaymentorder(requestDto.getIdPaymentOrder());
            if (paymentorder == null || paymentorder.getStateChr().isEmpty()) {
                logger.info("Orden de Pago no Encontrada: " + requestDto.getIdPaymentOrder());
                return ResponseDto.getDtoEmpty();
            }
            if (!(EstadosPaymentorder.FIRMA_EN_PROCESO.getCodigo0().equals(paymentorder.getStateChr().trim()))) {
                logger.info("Estado Invalido de la Orden de Pago: " + paymentorder.getIdpaymentorderPk());
                return ResponseDto.getDtoPaymentOrderStateSignError();
            }

            Approval approval = approvalService.getUserApproval(user, paymentorder);
            if (approval == null) {
                logger.info("Error Obteniendo Approval, idUser: " + requestDto.getIdUser() + "idPaymentOrder: " + requestDto.getIdPaymentOrder());
                return ResponseDto.getDtoErrorFirmaNoAutorizada();
            }

            if (!approvalService.isCurrentUserTurn(user, paymentorder)) {
                logger.info("Turno de Firma Incorrecto IPaymentOrder: " + paymentorder.getIdpaymentorderPk());
                return ResponseDto.getDtoPaymentOrderUserTurnError();

            }

            approval.setSignedNum(true);
            approval.setSigndateTim(new Timestamp(new Date().getTime()));
            approvalService.saveorUpdate(approval);
            if (approvalService.isFullySigned(paymentorder)) {
                paymentorder.setStateChr(EstadosPaymentorder.PAGO_PENDIENTE.getCodigo0().trim());
                saveOrUpdate(paymentorder);
            }

            //Envio del Mensaje al Core
            SPMGUIToFlowManagerMessage msg = new SPMGUIToFlowManagerMessage();
            msg.setEvent(SPMGUIToFlowManagerMessage.FIRMA_ORDENDEPAGO);
            msg.setLastSigner(paymentorder.getStateChr().equals(EstadosPaymentorder.PAGO_PENDIENTE.getCodigo0()));
            msg.setIdApproval(approval.getIdapprovalPk());
            msg.setIduser(requestDto.getIdUser());
            msg.setIdpaymentorder(paymentorder.getIdpaymentorderPk());
            int jmsPriority = (paymentorder.getCompany().getPriority() != null) ?
                    paymentorder.getCompany().getPriority() : 4;

            jmsTemplate.setPriority(jmsPriority);
            jmsTemplate.convertAndSend(ConstantsQueue.FLOW_MANAGER_QUEUE_SPMGUI, msg);

            logger.info("Firma Enviada Correctamente a FLOW_MANAGER_QUEUE, ID PaymentOrder:" + requestDto.getIdPaymentOrder());
            return ResponseDto.getDtoOK();

        } catch (Exception e) {
            logger.error("Error al firmar la orden de Pago: " + requestDto, e);
            return ResponseDto.getDtoERROR();
        }
    }


    public ResponseDto cancel(CancelPaymentOrderDto request) {
        try {
            if (request == null) {
                logger.info("PaymentOrder Request Invalid: " + request);
                return ResponseDto.getDtoEmpty();
            }
            Paymentorder paymentorder = getPaymentorder(request.getIdPaymentOrder());
            if (paymentorder == null || paymentorder.getStateChr() == null || paymentorder.getStateChr().isEmpty()) {
                logger.info("Invalid PaymentOrder " + paymentorder);
                return ResponseDto.getDtoEmpty();
            }
            if (!EstadosPaymentorder.stateIsChildOf(EstadosPaymentorder.getStateForCode(paymentorder.getStateChr().trim()),
                    EstadosPaymentorder.CANCELADA)) {
                logger.info("PaymentOrder State Invalid, IdPaymentOrder: " + request.getIdPaymentOrder());
                return ResponseDto.getDtoPaymentOrderStateCancelError();
            }
            User user = userService.getUserById(request.getIdUser());
            if (user == null) {
                logger.info("Usuario no Encontrado: " + request.getIdUser());
                return ResponseDto.getDtoEmpty();
            }
            Approval approval = approvalService.getUserApproval(user, paymentorder);
            if (approval == null) {
                logger.info("Error Obteniendo Approval, idUser: " + request.getIdUser() + "idPaymentOrder: " +
                        request.getIdPaymentOrder());
                return ResponseDto.getDtoErrorFirmaNoAutorizada();
            }

            paymentorder.setStateChr(EstadosPaymentorder.CANCELADA.getCodigo0().trim());
            saveOrUpdate(paymentorder);

            SPMGUIToFlowManagerMessage msg = new SPMGUIToFlowManagerMessage();
            msg.setEvent(SPMGUIToFlowManagerMessage.CANCELACION_ORDENDEPAGO);
            msg.setIdpaymentorder(paymentorder.getIdpaymentorderPk());
            msg.setIduser(request.getIdUser());
            msg.setIdApproval(approval.getIdapprovalPk());
            int jmsPriority = (paymentorder.getCompany().getPriority() != null) ?
                    paymentorder.getCompany().getPriority() : 4;

            jmsTemplate.setPriority(jmsPriority);
            jmsTemplate.convertAndSend(ConstantsQueue.FLOW_MANAGER_QUEUE_SPMGUI, msg);

            logger.info("Cancelación de Orden de pago Enviada a FLOW_MANAGER_QUEUE, ID: " + request.getIdPaymentOrder());
            return ResponseDto.getDtoOK();

        } catch (Exception e) {
            logger.error("Error al firmar la orden de Pago: " + request, e);
            return ResponseDto.getDtoERROR();
        }
    }

    public ResponseDto reintentar(RetryPaymentOrder request){
        try {
            Paymentorder paymentorder=getPaymentorder(request.getIdPaymentOrder());
            if(paymentorder==null ||paymentorder.getStateChr().isEmpty()){
                logger.info("Invalid PaymentOrder, IdPaymentOrder: "+request.getIdPaymentOrder());
                return ResponseDto.getDtoEmpty();
            }
            if(!EstadosPaymentorder.stateIsChildOf(EstadosPaymentorder.getStateForCode(paymentorder.getStateChr().trim()),
                    EstadosPaymentorder.PAGO_EN_PROCESO)){
                logger.info("PaymentOrder State Invalid, IdPaymentOrder: "+request.getIdPaymentOrder());
                return ResponseDto.getDtoPaymentOrderStateRetryError();
            }
            User user=userService.getUserById(request.getIdUser());
            if(user==null) {
                logger.info("Usuario no Encontrado: "+request.getIdUser());
                return ResponseDto.getDtoEmpty();
            }
            Approval approval = approvalService.getUserApproval(user, paymentorder);
            if(approval==null) {
                logger.info("Error Obteniendo Approval, idUser: "+request.getIdUser()+ "idPaymentOrder: "+request.getIdPaymentOrder());
                return ResponseDto.getDtoErrorFirmaNoAutorizada();
            }

            SPMGUIToTransferProcessMessage msg = new SPMGUIToTransferProcessMessage();
            msg.setIdpaymentorderPk(request.getIdPaymentOrder());
            int jmsPriority = (paymentorder.getCompany().getPriority() != null) ?
                    paymentorder.getCompany().getPriority() : 4;
            jmsTemplate.setPriority(jmsPriority);
            jmsTemplate.convertAndSend(ConstantsQueue.SPMGUI_TRANSFER_PROCESS_QUEUE, msg);

            logger.info("Reintento de Orden de pago OK, ID: "+ request.getIdPaymentOrder());
            return ResponseDto.getDtoOK();


        }catch (Exception e){
            logger.error("Error al Reintentar orden de Pago: "+request ,e);
            return ResponseDto.getDtoERROR();

        }
    }


    public boolean updatePaymentorderState(Paymentorder paymentorder, String state) {
        if (paymentorder == null) {
            logger.error("UpdatingPaymentorderState --> paymentorder = null");
            return false;
        } else if (state == null) {
            logger.error("UpdatingPaymentorderState --> " + paymentorder + ", state=null");
            return false;
        }
        paymentorder.setStateChr(state);
        saveOrUpdate(paymentorder);
        return true;
    }

    public List<Paymentorder> findPaymentOrderByState(String stateChr){
        return paymentOrderDao.findByStateChr(stateChr);
    }

}
