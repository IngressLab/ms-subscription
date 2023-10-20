package az.company.mssubscription.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public enum SubscriptionType {
    WEEKLY(7,BigDecimal.valueOf(5)),
    MONTHLY(31,BigDecimal.valueOf(30));

    private final Integer duration;
    private final BigDecimal price;
}
