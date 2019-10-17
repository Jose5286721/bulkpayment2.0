package py.com.global.spm.GUISERVICE.exceptions;

import java.util.ArrayList;
import java.util.List;

public class DraftAmountException extends RuntimeException {
    private static final long serialVersionUID=1L;

    private List<String> toReplace = new ArrayList<>();
    private String code;

    public DraftAmountException(String msg){
        super(msg);
    }

    public DraftAmountException(String msg, String code, String msgToReplace){
        super(msg);
        toReplace.add(msgToReplace);
        this.code = code;
    }

    public DraftAmountException(String msg, Throwable cause){
        super(msg, cause);
    }

    public List<String> getToReplace() {
        return toReplace;
    }

    public String getCode() {
        return code;
    }
}
