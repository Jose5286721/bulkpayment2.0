package py.com.global.spm.GUISERVICE.dto;

import com.fasterxml.jackson.annotation.JsonView;
import py.com.global.spm.GUISERVICE.utils.Messages;
import py.com.global.spm.domain.utils.ScopeViews;

/**
 * Created by global on 3/14/18.
 */
@JsonView({ScopeViews.Basics.class, ScopeViews.Details.class})
public class ErrorsDTO {

    public enum CODE {
        NOCOMPANY("0001"), NOTAUTHORIZED("0002"), NOEXISTSPECIFICMASC("0003"), NOEXISTSPECIFICFEM("0004"),
        INVALIDPARAMFORMAT("0005"), PARSEINDATAERROR("0006"), NONELISTMASC("0007"), NONELISTFEM("0008"),
        INVALIDPHONE("0009"),MTSUSRINVALID("0011"),INVALIDPASSWORD("0015"), EXIST("0016"),
        USERNOCOMPANYASOC("0017"), INVALIDEXTENSION("0018"), INVALIDDATEFORMAT("0021"), NOROLASOC("0022"),
        OBLIGATORIESFIELDS("0023"), NOEXISTGRALMASC("0024"), NOEXISTGRALFEM("0025"), REQUIREDFIELD("0030"),
        ERRORCORE("0034"),CONNECTION("0035"),NOWORKFLOW("0036"),DRAFTAMOUNTREQUIRED("0031"),MAXLENGTH("0037"),
        PASSMATCH("0039"), NOCOMPANYASOC("0040"), ASOCOMPANYWORKFLOW("0041"), INVALIDFIELDLENGTH("0042")
         ,WALLETNOTMATCH("0043"), ROWPARSINGERROR("0045"), REPEATEDPHONENUMBERCSV("0044"), NOCOMPANYLOGGEDIN("0046"),
        DENIEDACCES("0047"), ASOUSERAPPROVAL("0048"), MINAMOUNT("0049"), MAXAMOUNT("0050"),PHONUMBERPARSECSV("0051"),
        AMOUNTINVALIDCSV("0052"),COLUMNSINVALIDCSV("0053"), REQUIREDPERM("0056"), CIPARSECSV("0057"), REPEATEDCICSV("0058"),
        INVALIDPIN("0059"), USERBLOCKED("0060"), EXPIREDPINDATE("0061"),EMAILPARSECSV("0062"), AMOUNTERRORCSV("0063"),
        BADCREDENTIALS("0064"),INVALIDCAPTCHA("0065")
        ,UNKNOWNERROR("9999");


        private final String rule;

        CODE(String rule) { this.rule = rule; }

        public String getValue() { return rule; }
    }

    private String errorCode;

    private Messages message;

    public ErrorsDTO(String errorCode, Messages message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public ErrorsDTO(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Messages getMessage() {
        return message;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setMessage(Messages message) {
        this.message = message;
    }

}

