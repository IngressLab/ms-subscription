package az.company.mssubscription.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorMessages {

    SUBSCRIPTION_NOT_FOUND("Subscription not found with this ID: %s "),

    UNEXPECTED_ERROR("Something went wrong!"),

    METHOD_NOT_ALLOWED("Method not allowed exception!");

    private final String message;
}