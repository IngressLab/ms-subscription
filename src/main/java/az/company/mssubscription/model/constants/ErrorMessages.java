package az.company.mssubscription.model.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorMessages {

    SUBSCRIPTION_NOT_FOUND("Subscription not found with this ID: %s "),

    UNEXPECTED_ERROR("Something went wrong!");

    private final String message;
}