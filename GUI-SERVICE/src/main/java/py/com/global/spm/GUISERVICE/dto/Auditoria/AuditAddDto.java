package py.com.global.spm.GUISERVICE.dto.Auditoria;


public class AuditAddDto {
    private String accesstypeChr;
    private String descriptionChr;
    private String paramsChr;
    private String ipChr;

    public String getAccesstypeChr() {
        return accesstypeChr;
    }

    public void setAccesstypeChr(String accesstypeChr) {
        this.accesstypeChr = accesstypeChr;
    }

    public String getDescriptionChr() {
        return descriptionChr;
    }

    public void setDescriptionChr(String descriptionChr) {
        this.descriptionChr = descriptionChr;
    }

    public String getParamsChr() {
        return paramsChr;
    }

    public void setParamsChr(String paramsChr) {
        this.paramsChr = paramsChr;
    }

    public String getIpChr() {
        return ipChr;
    }

    public void setIpChr(String ipChr) {
        this.ipChr = ipChr;
    }

    @Override
    public String toString() {
        return "AuditAddDto{" +
                "accesstypeChr='" + accesstypeChr + '\'' +
                ", descriptionChr='" + descriptionChr + '\'' +
                ", paramsChr='" + paramsChr + '\'' +
                ", ipChr='" + ipChr + '\'' +
                '}';
    }


}
