package az.company.mssubscription.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum SubscriptionType {
    WEEKLY(7, BigDecimal.valueOf(5)),
    MONTHLY(31, BigDecimal.valueOf(30));

    Integer duration;
    BigDecimal price;
}