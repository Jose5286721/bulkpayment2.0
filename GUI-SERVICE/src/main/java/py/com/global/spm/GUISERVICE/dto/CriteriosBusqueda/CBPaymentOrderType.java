package py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda;

import javax.validation.constraints.Size;

/**
 * @author christiandelgado
 */
public class CBPaymentOrderType {
    @Size(max = 30, message = "0037")
    private String nameChr;
    @Size(max = 100, message = "0037")
    private String descriptionChr;

    public CBPaymentOrderType() {
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

    @Override
    public String toString() {
        return "CBPaymentOrderType{" +
                "nameChr='" + nameChr + '\'' +
                ", descriptionChr='" + descriptionChr + '\'' +
                '}';
    }
}
