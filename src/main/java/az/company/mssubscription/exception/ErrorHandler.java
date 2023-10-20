package az.company.mssubscription.exception;

import az.company.mssubscription.model.constants.ErrorMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(Exception ex, HttpServletRequest request) {
        log.error("Exception: ", ex);

        return ErrorResponse.builder()
                .path(request.getRequestURI())
                .code(String.valueOf(ErrorMessages.UNEXPECTED_ERROR))
                .message(ErrorMessages.UNEXPECTED_ERROR.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handle(NotFoundException ex, HttpServletRequest request) {
        log.error("NotFoundException: ", ex);
        return ErrorResponse.builder()
                .path(request.getRequestURI())
                .code(ex.getCode())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

}
