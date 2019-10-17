package py.com.global.spm.GUISERVICE.dto.Beneficiarios;


import py.com.global.spm.GUISERVICE.csvimporter.CSVRecord;
import py.com.global.spm.domain.entity.Beneficiary;
import py.com.global.spm.domain.entity.Draftdetail;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author christiandelgado on 17/07/18
 * @project GOP
 */
public class BeneficiaryDraftDetailDto implements Serializable {

    private static final long serialVersionUID = 6320646325098087542L;

    private Long idbeneficiaryPk;
    @NotNull(message = "0030")
    private String phonenumberChr;
    @NotNull(message = "0030")
    private BigDecimal amount;
    private String emailChr;
    private String subscriberciChr;
    private String beneficiarynameChr;
    private String beneficiarylastnameChr;
    private String currency;
    private String rolspChr;
    private String walletChr;

    public BeneficiaryDraftDetailDto() {
    }

    public BeneficiaryDraftDetailDto(Draftdetail draftdetail) {
        Beneficiary beneficiary = draftdetail.getBeneficiary();
        this.idbeneficiaryPk = beneficiary.getIdbeneficiaryPk();
        this.phonenumberChr = beneficiary.getPhonenumberChr();
        this.amount = draftdetail.getAmountNum();
    }

    public BeneficiaryDraftDetailDto(CSVRecord csvRecord){
        this.phonenumberChr = csvRecord.getPhonenumber();
        this.amount = csvRecord.getAmount();
        this.subscriberciChr = csvRecord.getCi();
        this.beneficiarynameChr = csvRecord.getName();
        this.beneficiarylastnameChr = csvRecord.getLastname();
        this.currency = csvRecord.getCurrency();
        this.rolspChr = csvRecord.getRole();
        this.walletChr = csvRecord.getWallet();

    }

    public Long getIdbeneficiaryPk() {
        return idbeneficiaryPk;
    }

    public void setIdbeneficiaryPk(Long idbeneficiaryPk) {
        this.idbeneficiaryPk = idbeneficiaryPk;
    }

    public String getPhonenumberChr() {
        return phonenumberChr;
    }

    public void setPhonenumberChr(String phonenumberChr) {
        this.phonenumberChr = phonenumberChr;
    }

    public String getBeneficiarynameChr() {
        return beneficiarynameChr;
    }

    public void setBeneficiarynameChr(String beneficiarynameChr) {
        this.beneficiarynameChr = beneficiarynameChr;
    }

    public String getBeneficiarylastnameChr() {
        return beneficiarylastnameChr;
    }

    public void setBeneficiarylastnameChr(String beneficiarylastnameChr) {
        this.beneficiarylastnameChr = beneficiarylastnameChr;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getEmailChr() {
        return emailChr;
    }

    public void setEmailChr(String emailChr) {
        this.emailChr = emailChr;
    }

    public String getSubscriberciChr() {
        return subscriberciChr;
    }

    public void setSubscriberciChr(String subscriberciChr) {
        this.subscriberciChr = subscriberciChr;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRolspChr() {
        return rolspChr;
    }

    public void setRolspChr(String rolspChr) {
        this.rolspChr = rolspChr;
    }

    public String getWalletChr() {
        return walletChr;
    }

    public void setWalletChr(String walletChr) {
        this.walletChr = walletChr;
    }
}
