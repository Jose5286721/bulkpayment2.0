package py.com.global.spm.GUISERVICE.dto.Report;

import com.google.common.hash.Hashing;
import py.com.global.spm.GUISERVICE.services.UtilService;
import py.com.global.spm.GUISERVICE.utils.NumberToLetter;
import py.com.global.spm.domain.entity.Logmts;
import py.com.global.spm.domain.entity.Paymentorder;
import py.com.global.spm.domain.entity.Paymentorderdetail;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public class PaymentVoucherBodyDto {

    private String name;

    private String conceptPayment;

    private String accreditedAmount;
    private String accreditedRefNumber;
    private String accreditedRefNumberHash;

    private Date emittedDate;

    private String receptorCiNumber;
    private String amountChr;
    private String accreditedLineChr;

    public PaymentVoucherBodyDto(Paymentorderdetail paymentorderdetail){
        Paymentorder paymentorder = paymentorderdetail.getPaymentorder();
        Long poPk = paymentorder.getIdpaymentorderPk();
        Long paymentPk  = paymentorderdetail.getLogPayment().getIdpaymentNum();
        Set<Logmts> logmts = paymentorderdetail.getLogmtses();
        name = paymentorder.getCompany().getCompanynameChr();
        conceptPayment = paymentorder.getDescriptionChr();
        accreditedAmount = UtilService.toNumberFormat(paymentorderdetail.getAmountNum().toPlainString());
        accreditedRefNumber = poPk +"-"+paymentPk +"-"+logmts.stream().filter(logmts1 -> logmts1.getIdtrxmtsChr()!=null).collect(Collectors.toSet()).iterator().next().getIdtrxmtsChr()
                .concat((logmts.size()>1)? "-NO_PAGE_FOR_IDS":"" );
        accreditedRefNumberHash = Hashing.sha256()
                .hashString(poPk +"-"+paymentPk +
                "-"+logmts.stream().map(Logmts::getIdtrxmtsChr).collect(Collectors.joining("-")), StandardCharsets.UTF_8)
                .toString();
        emittedDate = paymentorder.getUpdatedateTim();
        receptorCiNumber = paymentorderdetail.getBeneficiary().getSubscriberciChr();
        amountChr = NumberToLetter.toLetter(paymentorderdetail.getAmountChr());
        accreditedLineChr = paymentorderdetail.getBeneficiary().getPhonenumberChr();
    }

    public PaymentVoucherBodyDto(String name, String conceptPayment, String accreditedAmount,
                                 String accreditedRefNumber, Date emittedDate, String receptorCiNumber,
                                 String amountChr, String accreditedLineChr) {
        this.name = name;
        this.conceptPayment = conceptPayment;
        this.accreditedAmount = accreditedAmount;
        this.accreditedRefNumber = accreditedRefNumber;
        this.emittedDate = emittedDate;
        this.receptorCiNumber = receptorCiNumber;
        this.amountChr = amountChr;
        this.accreditedLineChr = accreditedLineChr;
    }




    public String getName() {
        return name;
    }

    public String getConceptPayment() {
        return conceptPayment;
    }

    public String getAccreditedAmount() {
        return accreditedAmount;
    }

    public String getAccreditedRefNumberHash() {
        return accreditedRefNumberHash;
    }

    public String getAccreditedRefNumber() {
        return accreditedRefNumber;
    }

    public Date getEmittedDate() {
        return emittedDate;
    }

    public String getReceptorCiNumber() {
        return receptorCiNumber;
    }

    public String getAmountChr() {
        return amountChr;
    }

    public String getAccreditedLineChr() {
        return accreditedLineChr;
    }

    @Override
    public String toString() {
        return "PaymentVoucherBodyDto{" +
                "name='" + name + '\'' +
                ", conceptPayment='" + conceptPayment + '\'' +
                ", accreditedAmount='" + accreditedAmount + '\'' +
                ", accreditedRefNumber='" + accreditedRefNumber + '\'' +
                ", emittedDate=" + String.valueOf(emittedDate) +
                ", receptorCiNumber='" + receptorCiNumber + '\'' +
                ", amountChr='" + amountChr + '\'' +
                ", accreditedLineChr='" + accreditedLineChr + '\'' +
                '}';
    }
}
