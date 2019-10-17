package py.com.global.spm.GUISERVICE.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import py.com.global.spm.GUISERVICE.dto.ErrorsDTO;
import py.com.global.spm.GUISERVICE.dto.ResponseDto;
import py.com.global.spm.GUISERVICE.exceptions.AuthorizationException;
import py.com.global.spm.GUISERVICE.exceptions.DraftAmountException;
import py.com.global.spm.GUISERVICE.exceptions.ObjectNotFoundException;
import py.com.global.spm.GUISERVICE.utils.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
/**
 * @author christiandelgado on 06/08/18
 * @project GOP
 */
@RestControllerAdvice
@Controller
public abstract class BaseController<T, K extends Serializable> implements IBaseController {

    @Autowired
    private ResponseUtil responseUtil;

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseDto handleValidationException(MethodArgumentNotValidException ex) {
        List<String> replaceText = new ArrayList<>();
        replaceText.add(ex.getBindingResult().getFieldError().getField());
        String code = ex.getBindingResult().getFieldError().getDefaultMessage();
        return responseUtil.buildErrorResponseDTO(code,replaceText);
    }

    @Override
    @ExceptionHandler(value = { ConstraintViolationException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseDto handleResourceNotFoundException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        List<String> replaceText = new ArrayList<>();
        replaceText.add(violation.getPropertyPath().toString());
        String code = violation.getMessage();
        return responseUtil.buildErrorResponseDTO(code,replaceText);
    }

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseDto handleArgumentTypeException(MethodArgumentTypeMismatchException ex) {
        List<String> replaceText = new ArrayList<>();
        replaceText.add(ex.getName());
        replaceText.add(ex.getParameter().getParameterType().getSimpleName());
        String code = ErrorsDTO.CODE.INVALIDPARAMFORMAT.getValue();
        return responseUtil.buildErrorResponseDTO(code,replaceText);
    }

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseDto handleNoRedeableException(HttpMessageNotReadableException ex) {
        List<String> replaceText = new ArrayList<>();
        replaceText.add(ex.getMostSpecificCause().getMessage());
        String code = ErrorsDTO.CODE.PARSEINDATAERROR.getValue();
        return responseUtil.buildErrorResponseDTO(code,replaceText);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseDto objectNotFoundException(ObjectNotFoundException e){
        return responseUtil.buildErrorResponseDTO(e.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AuthorizationException.class)
    public ResponseDto authorizationException(AuthorizationException e){
        return responseUtil.buildErrorResponseDTO(e.getMessage(), null);
    }
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseDto accessDeniedException(AccessDeniedException e){
        return responseUtil.buildErrorResponseDTO(ErrorsDTO.CODE.NOTAUTHORIZED.getValue(), null);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(DraftAmountException.class)
    public ResponseDto draftAmountException(DraftAmountException e){
        return responseUtil.buildErrorResponseDTO(e.getCode(), e.getToReplace());
    }


}
