package az.company.mssubscription.exception;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponse(String message) {
}
