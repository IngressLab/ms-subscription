package az.company.mssubscription.exception;

import az.company.mssubscription.model.enums.ErrorMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MethodNotAllowedException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

import static az.company.mssubscription.model.enums.ErrorMessages.METHOD_NOT_ALLOWED;
import static az.company.mssubscription.model.enums.ErrorMessages.UNEXPECTED_ERROR;
import static org.springframework.http.HttpStatus.*;

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
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorResponse handle(MethodNotAllowedException ex) {
        log.error("MethodNotAllowedException: ", ex);
        return ErrorResponse.builder()
                .message(METHOD_NOT_ALLOWED.getMessage())
                .build();
    }
}
