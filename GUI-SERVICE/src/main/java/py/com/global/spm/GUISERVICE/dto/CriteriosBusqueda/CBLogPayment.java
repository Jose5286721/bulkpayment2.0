package py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda;

public class CBLogPayment {
     private Long idpaymentNum;
     private Long idpaymentorderPk;
     private Long idcompanyPk;
     private String stateChr;
     private String sinceDate;
     private String toDate;

     public Long getIdpaymentNum() {
          return idpaymentNum;
     }

     public Long getIdpaymentorderPk() {
          return idpaymentorderPk;
     }

     public Long getIdcompanyPk() {
          return idcompanyPk;
     }

     public String getStateChr() {
          return stateChr;
     }

     public String getSinceDate() {
          return sinceDate;
     }

     public String getToDate() {
          return toDate;
     }

     @Override
     public String toString() {
          return "CBLogPayment{" +
                  "idpaymentNum=" + idpaymentNum +
                  ", idpaymentorderPk=" + idpaymentorderPk +
                  ", idcompanyPk=" + idcompanyPk +
                  ", stateChr='" + stateChr + '\'' +
                  ", sinceDate='" + sinceDate + '\'' +
                  ", toDate='" + toDate + '\'' +
                  '}';
     }
}
