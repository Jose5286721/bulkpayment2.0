package py.com.global.spm.GUISERVICE.dto.TipoOrdenPago;

import com.fasterxml.jackson.annotation.JsonView;
import py.com.global.spm.domain.utils.ScopeViews;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

/**
 * @author christiandelgado on 06/04/18
 * @project GOP
 */
@JsonView(ScopeViews.Basics.class)
public class TipoOrdenPagoDto {
    @Digits(integer = 19, fraction = 0)
    private Long idorderpaymenttypePk;
    @Size(max = 30, message = "0037")
    private String nameChr;
    @Size(max = 100, message = "0037")
    private String descriptionChr;

    public TipoOrdenPagoDto() {
    }

    public TipoOrdenPagoDto(Long idorderpaymenttypePk, String nameChr, String descriptionChr) {
        this.idorderpaymenttypePk = idorderpaymenttypePk;
        this.nameChr = nameChr;
        this.descriptionChr = descriptionChr;
    }

    public Long getIdorderpaymenttypePk() {
        return idorderpaymenttypePk;
    }

    public void setIdorderpaymenttypePk(Long idorderpaymenttypePk) {
        this.idorderpaymenttypePk = idorderpaymenttypePk;
    }

    public String getNameChr() {
        return nameChr;
    }

    public void setNameChr(String nameChr) {
        this.nameChr = nameChr;
    }

    public String getDescriptionChr() {
        return descriptionChr;
    }

    public void setDescriptionChr(String descriptionChr) {
        this.descriptionChr = descriptionChr;
    }
}
