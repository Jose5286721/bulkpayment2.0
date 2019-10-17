package py.com.global.spm.GUISERVICE.dto.CodigoEventos;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CodigoEventoRequestDto {
    @NotNull(message = "0030") @Digits(integer = 5, fraction = 0)
    private Long idprocessPk;
    @NotNull(message = "0030") @Digits(integer = 10, fraction = 0)
    private Long ideventcodeNum;
    @Size(max = 100, message = "0037")
    private String descriptionChr;

    public Long getIdprocessPk() {
        return idprocessPk;
    }

    public void setIdprocessPk(Long idprocessPk) {
        this.idprocessPk = idprocessPk;
    }

    public Long getIdeventcodeNum() {
        return ideventcodeNum;
    }

    public void setIdeventcodeNum(Long ideventcodeNum) {
        this.ideventcodeNum = ideventcodeNum;
    }

    public String    getDescriptionChr() {
        return descriptionChr;
    }

    public void setDescriptionChr(String descriptionChr) {
        this.descriptionChr = descriptionChr;
    }

    @Override
    public String toString() {
        return "CodigoEventoRequestDto{" +
                "idprocessPk=" + idprocessPk +
                ", ideventcodeNum=" + ideventcodeNum +
                ", descriptionChr='" + descriptionChr + '\'' +
                '}';
    }
}
