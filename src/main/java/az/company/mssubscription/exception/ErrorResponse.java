package az.company.mssubscription.exception;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponse(String path, String code, String message , LocalDateTime timestamp) {
}
