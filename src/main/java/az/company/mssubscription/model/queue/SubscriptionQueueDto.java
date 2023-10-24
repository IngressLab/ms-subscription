package az.company.mssubscription.model.queue;

import az.company.mssubscription.enums.SubscriptionStatus;
import az.company.mssubscription.enums.SubscriptionType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubscriptionQueueDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 6546124315671L;

    Long userId;
    Long productId;
    Long cardId;
    SubscriptionType subscriptionType;
    SubscriptionStatus subscriptionStatus;
}
