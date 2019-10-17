package py.com.global.spm.GUISERVICE.dto.CodigoEventos;

import py.com.global.spm.GUISERVICE.dto.SystemParameters.ProcesoDto;

public class CodigoEventosDto {
    private Long idprocessPk;
    private Long ideventcodeNum;
    private String descriptionChr;
    private ProcesoDto proceso;

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

    public String getDescriptionChr() {
        return descriptionChr;
    }

    public void setDescriptionChr(String descriptionChr) {
        this.descriptionChr = descriptionChr;
    }

    public ProcesoDto getProceso() {
        return proceso;
    }

    public void setProceso(ProcesoDto proceso) {
        this.proceso = proceso;
    }

    @Override
    public String toString() {
        return "CodigoEventosDto{" +
                "idprocessPk=" + idprocessPk +
                ", ideventcodeNum=" + ideventcodeNum +
                ", descriptionChr='" + descriptionChr + '\'' +
                ", proceso=" + proceso +
                '}';
    }
}
