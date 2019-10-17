package py.com.global.spm.GUISERVICE.dto.TipoOrdenPago;

import javax.validation.constraints.Size;

/**
 * @author christiandelgado on 06/04/18
 * @project GOP
 */
public class TipoOrdenPagoAddDto {
    @Size(max = 30, message = "0037")
    private String nameChr;
    @Size(max = 100, message = "0037")
    private String descriptionChr;

    public TipoOrdenPagoAddDto() {
    }

    public TipoOrdenPagoAddDto(String nameChr, String descriptionChr) {
        this.nameChr = nameChr;
        this.descriptionChr = descriptionChr;
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
