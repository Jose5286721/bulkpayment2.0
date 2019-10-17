package py.com.global.spm.GUISERVICE.utils;

import org.springframework.stereotype.Component;
import py.com.global.spm.GUISERVICE.dto.DataDTO;
import py.com.global.spm.GUISERVICE.dto.ErrorsDTO;
import py.com.global.spm.GUISERVICE.dto.MetaDTO;
import py.com.global.spm.GUISERVICE.dto.ResponseDto;

import java.util.Arrays;

@Component
public class PrintResponses
{
    private static final String INDENT_UNIT = "\t";
    private static final String LINE_UNIT =    ", \n";
    public String printResponseDto(ResponseDto response) {
        StringBuilder sb = new StringBuilder();
        sb.append("{ \n");
        if(response!=null) {
            sb.append(INDENT_UNIT).append("Data DTO: ").append(printDataDto(response.getData())).append(LINE_UNIT);
            sb.append(INDENT_UNIT).append("Errors DTO: ").append(printErrorsDto(response.getErrors())).append(LINE_UNIT);
            sb.append(INDENT_UNIT).append("Meta DTO: ").append(printMetaDto(response.getMeta())).append(LINE_UNIT);
            sb.append("}");
        }
        sb.append("}");
        return sb.toString();
    }

    private String printDataDto(DataDTO data){
        StringBuilder sb = new StringBuilder();
        sb.append("{ \n");
        if(data!=null) {
            if(data.getMessage()!=null) {
                sb.append(INDENT_UNIT).append("Message: ").append(data.getMessage().getMessage()).append(LINE_UNIT);
                sb.append(INDENT_UNIT).append("Detail: ").append(data.getMessage().getDetail()).append(LINE_UNIT);
            }
            sb.append(INDENT_UNIT).append("Code: ").append(data.getDataCode()).append(LINE_UNIT);

            if(data.getBody()!=null)
                sb.append(INDENT_UNIT).append("Detail: ").append(Arrays.asList(data.getBody())).append(LINE_UNIT);
        }
        sb.append("}");
        return sb.toString();
    }

    private String printErrorsDto(ErrorsDTO errorsDTO) {
        StringBuilder sb = new StringBuilder();
        sb.append("{ \n");
        if(errorsDTO!=null) {
            sb.append(INDENT_UNIT).append("Error Code: ").append(errorsDTO.getErrorCode()).append(LINE_UNIT);
            if(errorsDTO.getMessage()!=null) {
                sb.append(INDENT_UNIT).append("Message: ").append(errorsDTO.getMessage().getMessage()).append(LINE_UNIT);
                sb.append(INDENT_UNIT).append("Detail: ").append(errorsDTO.getMessage().getDetail()).append(LINE_UNIT);
            }
        }
        sb.append("}");
        return sb.toString();
    }

    private String printMetaDto(MetaDTO metaDTO) {
        StringBuilder sb = new StringBuilder();
        sb.append("{ \n");
        if(metaDTO!=null){
            sb.append(INDENT_UNIT).append("Page Size: ").append(metaDTO.getPageSize()).append(LINE_UNIT);
            sb.append(INDENT_UNIT).append("Total Count: ").append(metaDTO.getTotalCount()).append(LINE_UNIT);
            sb.append(INDENT_UNIT).append("Total Pages: ").append(metaDTO.getTotalPages()).append(LINE_UNIT);
        }
        sb.append("}");
        return sb.toString();
    }

}
