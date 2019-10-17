package py.com.global.spm.GUISERVICE.dto;

import com.fasterxml.jackson.annotation.JsonView;
import py.com.global.spm.GUISERVICE.utils.Messages;
import py.com.global.spm.domain.utils.ScopeViews;

import java.util.Map;

/**
 * Created by global on 3/14/18.
 */
@JsonView({ScopeViews.Basics.class, ScopeViews.Details.class})
public class DataDTO {

    public enum CODE {
        OK("01"), OKWF("02"), OKNODATA("03"),OKNODATAMIGRATE("04"),DATAMIGRACION("05"), OKNOTMAT("06"),
        CREATEEXITO("001"),LISTEXITO("003"),GETFEM("004"),GETMASC("005"),NOTELEMENTPAGE("017"),EDITEXITOMASC("018"),
        EDITEXITOFEM("019"),CREATEEXITOMASC("012"),DELETEEXITOMASC("07"), DELETEEXITOMASCBODY("020"),OKSENDEMAIL("024");

        private final String rule;

        CODE(String rule) { this.rule = rule; }

        public String getValue() { return rule; }
    }
    private String dataCode;
    private Messages message;
    private Map<String,Object> body;

    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    public Messages getMessage() {
        return message;
    }

    public void setMessage(Messages message) {
        this.message = message;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }

}
