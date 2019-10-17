package py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda;

import java.util.Date;

public class CBAudit {

    private String accesstypeChr;
    private String usernameChr;
    private String ipChr;
    private Boolean allByPage;
    private String sinceDate;
    private String toDate;

    public String getSinceDate() {
        return sinceDate;
    }

    public void setSinceDate(String sinceDate) {
        this.sinceDate = sinceDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getAccesstypeChr() {
        return accesstypeChr;
    }

    public void setAccesstypeChr(String accesstypeChr) {
        this.accesstypeChr = accesstypeChr;
    }

    public String getUsernameChr() {
        return usernameChr;
    }

    public void setUsernameChr(String usernameChr) {
        this.usernameChr = usernameChr;
    }

    public void setAllByPage(Boolean allByPage) { this.allByPage = allByPage; }

    public String getIpChr() {
        return ipChr;
    }

    public void setIpChr(String ipChr) {
        this.ipChr = ipChr;
    }

    public Boolean getAllByPage() { return allByPage; }

    @Override
    public String toString() {
        return "CBAudit{" +
                "accesstypeChr='" + accesstypeChr + '\'' +
                ", usernameChr='" + usernameChr + '\'' +
                ", ipChr='" + ipChr + '\'' +
                ", allByPage=" + allByPage +
                ", sinceDate=" + sinceDate +
                ", toDate=" + toDate +
                '}';
    }
}
