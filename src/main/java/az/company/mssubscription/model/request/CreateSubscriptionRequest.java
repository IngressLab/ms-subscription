package az.company.mssubscription.model.request;

import az.company.mssubscription.enums.SubscriptionType;
import lombok.Builder;

@Builder
public record CreateSubscriptionRequest(Long userId, Long productId, Long cardId, SubscriptionType subscriptionType) {
}