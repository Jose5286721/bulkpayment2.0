package py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by global on 3/16/18.
 */
public class CBCompany {
    @Size(max = 30, message = "0037")
    private String companynameChr;
    @NotNull(message = "0030")
    private String stateChr;
    @Size(max=30, message = "0037")
    private  String mtsfield5Chr;




    public String getCompanynameChr() {
        return companynameChr;
    }

    public void setCompanynameChr(String companynameChr) {
        this.companynameChr = companynameChr;
    }

    public String getMtsfield5Chr() {
        return mtsfield5Chr;
    }

    public void setMtsfield5Chr(String mtsfield5Chr) {
        this.mtsfield5Chr = mtsfield5Chr;
    }

    public String getStateChr() {
        return stateChr;
    }

    public void setStateChr(String stateChr) {
        this.stateChr = stateChr;
    }



}
