package liming.emsp.ats.configs;

import liming.emsp.ats.domains.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // 422
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorInfo handleValidationException(MethodArgumentNotValidException ex) {
        return new ErrorInfo(ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).reduce((x, y)->x+"; "+y).orElse(""));
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorInfo handleException(MethodArgumentTypeMismatchException ex) {
        return new ErrorInfo(ex.getMessage());
    }
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorInfo handleException(IllegalArgumentException ex) {
        return new ErrorInfo(ex.getMessage());
    }
}

