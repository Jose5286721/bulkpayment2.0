package py.com.global.spm.GUISERVICE.controller;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import py.com.global.spm.GUISERVICE.dto.ResponseDto;


import javax.validation.ConstraintViolationException;
import java.io.Serializable;

/**
 * @author christiandelgado on 06/08/18
 * @project GOP
 */
public interface IBaseController<T, K extends Serializable>{

    /**
     * Maneja excepciones de validacion de argumentos no válidos.
     *
     * @param ex
     * @return {@link ResponseDto}
     */
    ResponseDto handleValidationException(MethodArgumentNotValidException ex);

    /**
     * Maneja excepciones de restricciones en campos del cuerpo del request.
     *
     * @param e
     * @return {@link ResponseDto}
     */
    ResponseDto handleResourceNotFoundException(ConstraintViolationException e);

    /**
     * Maneja excepciones de errores de tipo de parámetros no válidos.
     *
     * @param ex
     * @return {@link ResponseDto}
     */
    ResponseDto handleArgumentTypeException(MethodArgumentTypeMismatchException ex);


    /**
     * Maneja excepciones de mal formación del json de entrada.
     *
     * @param ex
     * @return {@link ResponseDto}
     */
    ResponseDto handleNoRedeableException(HttpMessageNotReadableException ex);
}
