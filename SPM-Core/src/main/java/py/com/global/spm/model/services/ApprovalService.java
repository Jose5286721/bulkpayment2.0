package py.com.global.spm.model.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.global.spm.domain.entity.Approval;
import py.com.global.spm.domain.entity.Paymentorder;
import py.com.global.spm.domain.entity.User;
import py.com.global.spm.model.repository.IApprovalDao;

import java.util.List;

@Service
public class ApprovalService {
    private final Logger log = LoggerFactory.getLogger(ApprovalService.class);

    private final IApprovalDao dao;

    @Autowired
    public ApprovalService(IApprovalDao dao) {
        this.dao = dao;
    }

    public List<Approval> getApprovalsByIdPaymentorder(long idpaymentorder) {
        try {
            return dao.findByPaymentorderIdpaymentorderPk(idpaymentorder);
        } catch (Exception e) {
            log.error("Error al obtener approvals con idPaymentorder --> "
                    + idpaymentorder, e);
            throw e;
        }
    }

    public boolean saveorUpdate(Approval approval) {
        try {
            dao.save(approval);
            return true;
        } catch (Exception e) {
            log.error("Error al saveOrUpdate approval con id --> " + approval.getIdapprovalPk(), e);
            throw e;
        }
    }

    public List<Approval> saveApprovals(List<Approval> approvalList) {
        try {
            return dao.saveAll(approvalList);
        } catch (Exception e) {
            log.error("Inserting approval", e);
            throw e;
        }
    }


    public Approval getUserApproval(User u, Paymentorder po) {
        try {
            return dao.findByPaymentorderIdpaymentorderPkAndWorkflowdetUserIduserPk(po.getIdpaymentorderPk(), u.getIduserPk());
        } catch (Exception e) {
            log.error("Error al obtener aproval, iduser: " + u.getIduserPk() + " idPo: " + po.getIdpaymentorderPk());
            throw e;
        }
    }

    public boolean isCurrentUserTurn(User user, Paymentorder paymentorder) {
        if (isAlreadySignedByUser(user, paymentorder))
            return false;

        Long currenyUserStep = getStepFor(user, paymentorder);
        Long nextSignerStep = getMinNotSigned(paymentorder);
        if (currenyUserStep == null || nextSignerStep == null)
            return false;

        return currenyUserStep == nextSignerStep.intValue();
    }

    private boolean isAlreadySignedByUser(User u, Paymentorder po) {
        Approval approval = getUserApproval(u, po);
        if (approval != null && approval.isSignedNum()) {
            return true;
        } else
            return false;
    }

    private Long getStepFor(User u, Paymentorder po) {
        try {
            Approval approval = getUserApproval(u, po);

            if(approval == null)
                return null;
            else
                return approval.getStepPk();

        } catch (Exception e) {
            log.error("Firma Orden de Pago, getStepFor",e);
            throw e;
        }
    }


    private Long getMinNotSigned(Paymentorder po) {

        try {
           Approval approval = dao.findFirstByPaymentorderIdpaymentorderPkAndSignedNumOrderByStepPkAsc
                   (po.getIdpaymentorderPk(), false);

            if(approval == null)
                return null;
            else
                return approval.getStepPk();

        }catch(Exception e){
            log.error("Error al obtener GetMinNotSigned: ", e);
            throw e;
        }
    }


    public boolean isFullySigned(Paymentorder po) {

        try{
            Integer c = dao.countByPaymentorderIdpaymentorderPkAndSignedNum(po.getIdpaymentorderPk(), false);

            if(c != null)
                return c == 0;
            else
                return false;

        }catch (Exception e){
            log.error("Error al obtener count(ap)");
            throw e;
        }
    }
}
