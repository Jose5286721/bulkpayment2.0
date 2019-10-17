package py.com.global.spm.GUISERVICE.dto.OrdenDePago;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonView;
import py.com.global.spm.GUISERVICE.dto.Firmantes.FirmanteDto;
import py.com.global.spm.domain.utils.ScopeViews;

import java.util.List;


@JsonAutoDetect
public class PaymentOrderDto {

    @JsonView({ScopeViews.Basics.class})
    private Long idpaymentorderPk;
    @JsonView({ScopeViews.Basics.class})
    private Long idDraft;
    @JsonView({ScopeViews.Basics.class})
    private Long idWorkflow;
    @JsonView({ScopeViews.Basics.class})
    private String nombreWorkflow;
    @JsonView({ScopeViews.Basics.class})
    private Long idCompany;
    @JsonView({ScopeViews.Basics.class})
    private String nombreEmpresa;
    @JsonView({ScopeViews.Basics.class})
    private Long idPaymentordertype;
    @JsonView({ScopeViews.Basics.class})
    private String nombreTipoOrdenPago;
    @JsonView({ScopeViews.Basics.class})
    private String stateChr;
    @JsonView({ScopeViews.Basics.class})
    private String descripcion;
    @JsonView({ScopeViews.Basics.class})
    private String montoTotal;
    @JsonView({ScopeViews.Basics.class})
    private String creationdateTim;
    @JsonView({ScopeViews.Details.class})
    private String creationhour;
    @JsonView({ScopeViews.Basics.class})
    private String updatedateTim;
    @JsonView({ScopeViews.Basics.class})
    private String updatedatehour;
    @JsonView({ScopeViews.Details.class})
    private String amountpaidChr;
    @JsonView({ScopeViews.Details.class})
    private Long totalpaymentNum;
    @JsonView({ScopeViews.Details.class})
    private Long paymentsuccessNum;
    @JsonView({ScopeViews.Details.class})
    private Long paymenterrorNum;
    @JsonView({ScopeViews.Details.class})
    private Long paymentpartsucNum;
    @JsonView({ScopeViews.Details.class})
    private Integer beneficiarieslength;
    @JsonView({ScopeViews.Details.class})
    private Integer signerslength;
    @JsonView({ScopeViews.Details.class})
    private List<FirmanteDto> firmantes;
    @JsonView({ScopeViews.Details.class})
    private Boolean isCurrentUserTurn;
    @JsonView({ScopeViews.Details.class})
    private Boolean isSigner;

    public Long getIdpaymentorderPk() {
        return idpaymentorderPk;
    }

    public void setIdpaymentorderPk(Long idpaymentorderPk) {
        this.idpaymentorderPk = idpaymentorderPk;
    }

    public Long getIdDraft() {
        return idDraft;
    }

    public void setIdDraft(Long idDraft) {
        this.idDraft = idDraft;
    }

    public Long getIdWorkflow() {
        return idWorkflow;
    }

    public void setIdWorkflow(Long idWorkflow) {
        this.idWorkflow = idWorkflow;
    }

    public String getNombreWorkflow() {
        return nombreWorkflow;
    }

    public void setNombreWorkflow(String nombreWorkflow) {
        this.nombreWorkflow = nombreWorkflow;
    }

    public Long getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(Long idCompany) {
        this.idCompany = idCompany;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public Long getIdPaymentordertype() {
        return idPaymentordertype;
    }

    public void setIdPaymentordertype(Long idPaymentordertype) {
        this.idPaymentordertype = idPaymentordertype;
    }

    public String getNombreTipoOrdenPago() {
        return nombreTipoOrdenPago;
    }

    public void setNombreTipoOrdenPago(String nombreTipoOrdenPago) {
        this.nombreTipoOrdenPago = nombreTipoOrdenPago;
    }

    public String getStateChr() {
        return stateChr;
    }

    public void setStateChr(String stateChr) {
        this.stateChr = stateChr;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(String montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getCreationdateTim() {
        return creationdateTim;
    }

    public void setCreationdateTim(String creationdateTim) {
        this.creationdateTim = creationdateTim;
    }

    public String getUpdatedateTim() {
        return updatedateTim;
    }

    public void setUpdatedateTim(String updatedateTim) {
        this.updatedateTim = updatedateTim;
    }

    public String getCreationhour() {
        return creationhour;
    }

    public void setCreationhour(String creationhour) {
        this.creationhour = creationhour;
    }

    public String getUpdatedatehour() {
        return updatedatehour;
    }

    public void setUpdatedatehour(String updatedatehour) {
        this.updatedatehour = updatedatehour;
    }

    public String getAmountpaidChr() {
        return amountpaidChr;
    }

    public void setAmountpaidChr(String amountpaidChr) {
        this.amountpaidChr = amountpaidChr;
    }

    public Long getTotalpaymentNum() {
        return totalpaymentNum;
    }

    public void setTotalpaymentNum(Long totalpaymentNum) {
        this.totalpaymentNum = totalpaymentNum;
    }

    public Long getPaymentsuccessNum() {
        return paymentsuccessNum;
    }

    public void setPaymentsuccessNum(Long paymentsuccessNum) {
        this.paymentsuccessNum = paymentsuccessNum;
    }

    public Long getPaymenterrorNum() {
        return paymenterrorNum;
    }

    public void setPaymenterrorNum(Long paymenterrorNum) {
        this.paymenterrorNum = paymenterrorNum;
    }

    public Long getPaymentpartsucNum() {
        return paymentpartsucNum;
    }

    public void setPaymentpartsucNum(Long paymentpartsucNum) {
        this.paymentpartsucNum = paymentpartsucNum;
    }

    public Integer getBeneficiarieslength() {
        return beneficiarieslength;
    }

    public void setBeneficiarieslength(Integer beneficiarieslength) {
        this.beneficiarieslength = beneficiarieslength;
    }

    public Integer getSignerslength() {
        return signerslength;
    }

    public void setSignerslength(Integer signerslength) {
        this.signerslength = signerslength;
    }

    public List<FirmanteDto> getFirmantes() {
        return firmantes;
    }

    public void setFirmantes(List<FirmanteDto> firmantes) {
        this.firmantes = firmantes;
    }

    public Boolean getCurrentUserTurn() {
        return isCurrentUserTurn;
    }

    public void setCurrentUserTurn(Boolean currentUserTurn) {
        isCurrentUserTurn = currentUserTurn;
    }

    public Boolean getSigner() {
        return isSigner;
    }

    public void setSigner(Boolean signer) {
        isSigner = signer;
    }

    @Override
    public String toString() {
        return "PaymentOrderDto{" +
                "idpaymentorderPk=" + idpaymentorderPk +
                ", idDraft=" + idDraft +
                ", idWorkflow=" + idWorkflow +
                ", nombreWorkflow='" + nombreWorkflow + '\'' +
                ", idCompany=" + idCompany +
                ", nombreEmpresa='" + nombreEmpresa + '\'' +
                ", idPaymentordertype=" + idPaymentordertype +
                ", nombreTipoOrdenPago='" + nombreTipoOrdenPago + '\'' +
                ", stateChr='" + stateChr + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", descriptionChr='" + montoTotal + '\'' +
                ", creationdateTim=" + creationdateTim +
                ", updatedateTim=" + updatedateTim +
                '}';
    }
}
