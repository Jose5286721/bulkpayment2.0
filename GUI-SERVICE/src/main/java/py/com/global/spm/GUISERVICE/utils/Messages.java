package py.com.global.spm.GUISERVICE.utils;

import com.fasterxml.jackson.annotation.JsonView;
import py.com.global.spm.domain.utils.ScopeViews;

/**
 * Created by global on 3/14/18.
 */
@JsonView({ScopeViews.Basics.class, ScopeViews.Details.class})
public class Messages {

    private String message;
    private String detail;

    public Messages() {
        // Do nothing
    }

    public Messages(String message) {

        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
