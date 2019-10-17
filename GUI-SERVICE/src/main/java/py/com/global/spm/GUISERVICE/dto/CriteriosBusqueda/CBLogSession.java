package py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda;


public class CBLogSession {
    private String userNameChr;
    private String sinceDate;
    private String toDate;

    public String getUserNameChr() {
        return userNameChr;
    }

    public String getSinceDate() {
        return sinceDate;
    }

    public String getToDate() {
        return toDate;
    }

    @Override
    public String toString() {
        return "CBLogSession{" +
                "userNameChr='" + userNameChr + '\'' +
                ", sinceDate='" + sinceDate + '\'' +
                ", toDate='" + toDate + '\'' +
                '}';
    }
}
