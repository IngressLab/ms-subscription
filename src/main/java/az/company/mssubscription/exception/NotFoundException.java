package az.company.mssubscription.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotFoundException extends RuntimeException {

    String code;

    public NotFoundException(String code, String message) {
        super(message);
        this.code = code;
    }
}