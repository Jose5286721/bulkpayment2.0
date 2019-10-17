package py.com.global.spm.model.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.global.spm.domain.entity.Approval;
import py.com.global.spm.domain.entity.Company;
import py.com.global.spm.domain.entity.Paymentorder;
import py.com.global.spm.model.dto.redis.LogPaymentCache;
import py.com.global.spm.model.eventcodes.NotificationEventEnum;
import py.com.global.spm.model.message.NotificationRequestMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UpdaterService {
    private static final Logger log = LoggerFactory
            .getLogger(UpdaterService.class);

    private final
    ApprovalService approvalService;

    private final
    CompanyService companyService;

    @Autowired
    public UpdaterService(ApprovalService approvalService, CompanyService companyService) {
        this.approvalService = approvalService;
        this.companyService = companyService;
    }

    public NotificationRequestMessage getSMSRequest(Paymentorder paymentorder,
                                                    NotificationEventEnum event, Long destinatario) {
        NotificationRequestMessage notificationRequest = new NotificationRequestMessage();
        notificationRequest.setEvent(event);
        notificationRequest.setOp(paymentorder);
        Long[] d = { destinatario };
        notificationRequest.setIdDestinatarios(d);
        return notificationRequest;
    }
    public NotificationRequestMessage getSMSRequest(Paymentorder paymentorder,
                                                    NotificationEventEnum event, Long[] destinatarios) {
        NotificationRequestMessage notificationRequest = new NotificationRequestMessage();
        notificationRequest.setEvent(event);
        notificationRequest.setOp(paymentorder);
        notificationRequest.setIdDestinatarios(destinatarios);
        return notificationRequest;
    }
    public NotificationRequestMessage getSMSRequest(Paymentorder paymentorder,
                                                    LogPaymentCache logpayment, NotificationEventEnum event,
                                                    Long destinatario) {
        NotificationRequestMessage notificationRequest = new NotificationRequestMessage();
        notificationRequest.setEvent(event);
        notificationRequest.setOp(paymentorder);
        notificationRequest.setPago(logpayment);
        Long[] d = { destinatario };
        notificationRequest.setIdDestinatarios(d);
        return notificationRequest;
    }
    public Long[] getSignersWithoutIdDraftCreator(Paymentorder paymentorder,
                                                  Long idDraftcreator) {
        List<Approval> approvalList = approvalService.getApprovalsByIdPaymentorder(paymentorder
                        .getIdpaymentorderPk());
        if (approvalList != null && idDraftcreator!=null) {
            List<Long> signers = new ArrayList<>();
            for (Approval l : approvalList) {
                Long idUser = l.getWorkflowdet().getUser().getIduserPk();
                if (!idDraftcreator.equals(idUser)) {
                    signers.add(idUser);
                }
            }
            Long[] arraySegunArturo = new Long[0];
            return signers.toArray(arraySegunArturo);
        }
        return null;
    }
    public boolean updateCompany(Company company, long trxCount,
                                 Date lastopTim) {
        try {
            if(company!=null) {
                company.setTrxdaycountNum(company.getTrxdaycountNum() + trxCount);
                company.setTrxmonthcountNum(company.getTrxmonthcountNum() + trxCount);
                company.setTrxtotalcountNum(company.getTrxtotalcountNum() + trxCount);
                company.setLastopdateTim(lastopTim);
                companyService.saveOrUpdate(company);
                return true;
            }
        }catch (Exception e){
            log.error("Fallo al actualuzar contadores de Compañia -> "+company.getIdcompanyPk());
        }
        return false;
    }
}
