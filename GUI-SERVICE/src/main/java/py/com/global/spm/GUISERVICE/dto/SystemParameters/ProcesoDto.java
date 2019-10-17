package py.com.global.spm.GUISERVICE.dto.SystemParameters;



import javax.validation.constraints.NotNull;
import java.util.List;

public class ProcesoDto {
    @NotNull(message = "0030")
    private Long idprocessPk;
    @NotNull(message = "0030")
    private String processnameChr;
    private String descriptionChr;
    private List<Long> systemparameter;
    private List<Long> processcontrols;
    private List<Long> processperformances;

    public Long getIdprocessPk() {
        return idprocessPk;
    }

    public void setIdprocessPk(Long idprocessPk) {
        this.idprocessPk = idprocessPk;
    }

    public String getProcessnameChr() {
        return processnameChr;
    }

    public void setProcessnameChr(String processnameChr) {
        this.processnameChr = processnameChr;
    }

    public String getDescriptionChr() {
        return descriptionChr;
    }

    public void setDescriptionChr(String descriptionChr) {
        this.descriptionChr = descriptionChr;
    }

    public List<Long> getSystemparameter() {
        return systemparameter;
    }

    public void setSystemparameter(List<Long> systemparameter) {
        this.systemparameter = systemparameter;
    }

    public List<Long> getProcesscontrols() {
        return processcontrols;
    }

    public void setProcesscontrols(List<Long> processcontrols) {
        this.processcontrols = processcontrols;
    }

    public List<Long> getProcessperformances() {
        return processperformances;
    }

    public void setProcessperformances(List<Long> processperformances) {
        this.processperformances = processperformances;
    }
    @Override
    public String toString() {
        return "ProcesoDto{" +
                "idprocessPk=" + idprocessPk +
                ", processnameChr='" + processnameChr + '\'' +
                ", descriptionChr='" + descriptionChr + '\'' +
                ", systemparameter=" + systemparameter +
                ", processcontrols=" + processcontrols +
                ", processperformances=" + processperformances +
                '}';
    }
}
