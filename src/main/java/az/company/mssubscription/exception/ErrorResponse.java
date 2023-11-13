package az.company.mssubscription.exception;

import lombok.Builder;

@Builder
public record ErrorResponse(String message) {
}