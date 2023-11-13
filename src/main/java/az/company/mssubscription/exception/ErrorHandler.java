package az.company.mssubscription.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MethodNotAllowedException;

import static az.company.mssubscription.model.enums.ErrorMessages.UNEXPECTED_ERROR;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(Exception ex) {
        log.error("Exception: ", ex);
        return ErrorResponse.builder()
                .message(UNEXPECTED_ERROR.getMessage())
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handle(NotFoundException ex) {
        log.error("NotFoundException: ", ex);
        return ErrorResponse.builder()
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(METHOD_NOT_ALLOWED)
    public ErrorResponse handle(MethodNotAllowedException ex) {
        log.error("MethodNotAllowedException: ", ex);
        return ErrorResponse.builder()
                .message(ex.getMessage())
                .build();
    }
}