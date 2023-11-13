package az.company.mssubscription.mapper;

import az.company.mssubscription.dao.entity.SubscriptionEntity;
import az.company.mssubscription.model.queue.SubscriptionQueueDto;
import az.company.mssubscription.model.request.CreateSubscriptionRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static az.company.mssubscription.enums.SubscriptionStatus.ACTIVE;

public class SubscriptionMapper {

    public static SubscriptionEntity buildSubscriptionEntity(CreateSubscriptionRequest createSubscriptionRequest) {
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

    public static SubscriptionQueueDto buildSubscriptionQueueDto(SubscriptionEntity subscriptionEntity) {
        return SubscriptionQueueDto.builder()
                .userId(subscriptionEntity.getUserId())
                .productId(subscriptionEntity.getProductId())
                .cardId(subscriptionEntity.getCardId())
                .subscriptionStatus(subscriptionEntity.getStatus())
                .subscriptionType(subscriptionEntity.getSubscriptionType())
                .build();
    }

    public static List<SubscriptionQueueDto> buildSubscriptionQueueDto(List<SubscriptionEntity> subscriptions) {
        return subscriptions.stream().map(SubscriptionMapper::buildSubscriptionQueueDto).collect(Collectors.toList());
    }
}