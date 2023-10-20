package az.company.mssubscription.mapper;

import az.company.mssubscription.dao.entity.SubscriptionEntity;
import az.company.mssubscription.model.request.CreateSubscriptionRequest;
import java.time.LocalDateTime;

import static az.company.mssubscription.enums.SubscriptionStatus.ACTIVE;
public class SubscriptionMapper {

    public static SubscriptionEntity buildSubscriptionEntity(CreateSubscriptionRequest createSubscriptionRequest){
        return SubscriptionEntity.builder()
                .userId(createSubscriptionRequest.userId())
                .productId(createSubscriptionRequest.productId())
                .cardId(createSubscriptionRequest.cardId())
                .subscriptionType(createSubscriptionRequest.subscriptionType())
                .status(ACTIVE)
                .expireDate(LocalDateTime.now().plusDays(createSubscriptionRequest.subscriptionType().getDuration()))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

}
