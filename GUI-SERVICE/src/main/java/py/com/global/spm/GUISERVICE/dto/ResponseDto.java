package py.com.global.spm.GUISERVICE.dto;

import com.fasterxml.jackson.annotation.JsonView;
import py.com.global.spm.domain.utils.ScopeViews;

/**
 * Created by global on 3/14/18.
 */
@JsonView({ScopeViews.Basics.class, ScopeViews.Details.class})
public class ResponseDto {

    private DataDTO data;
    private ErrorsDTO errors;
    private MetaDTO meta;

    public ErrorsDTO getErrors() {
        return errors;
    }

    public void setErrors(ErrorsDTO errors) {
        this.errors = errors;
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public MetaDTO getMeta() {
        return meta;
    }

    public void setMeta(MetaDTO meta) {
        this.meta = meta;
    }

	@Override
	public String toString() {
		return "ResponseDto [data=" + data + ", errors=" + errors + ", meta=" + meta + "]";
	}
}
