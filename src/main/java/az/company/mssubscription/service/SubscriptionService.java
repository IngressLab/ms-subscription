package az.company.mssubscription.service;

import az.company.mssubscription.annotation.Log;
import az.company.mssubscription.dao.entity.SubscriptionEntity;
import az.company.mssubscription.dao.repository.SubscriptionRepository;
import az.company.mssubscription.exception.NotFoundException;
import az.company.mssubscription.model.request.CreateSubscriptionRequest;
import az.company.mssubscription.queue.MessagePublisher;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static az.company.mssubscription.enums.SubscriptionStatus.DELETED;
import static az.company.mssubscription.enums.SubscriptionStatus.EXPIRED;
import static az.company.mssubscription.mapper.SubscriptionMapper.buildSubscriptionEntity;
import static az.company.mssubscription.mapper.SubscriptionMapper.buildSubscriptionQueueDto;
import static az.company.mssubscription.model.enums.ErrorMessages.SUBSCRIPTION_NOT_FOUND_ERROR;

@Log
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubscriptionService {

    final SubscriptionRepository subscriptionRepository;
    final MessagePublisher messagePublisher;

    @Value("${rabbitmq.queue.subscription}")
    String queue;

    @Transactional
    public void createSubscription(CreateSubscriptionRequest createSubscriptionRequest) {
        var subscriptionEntity = buildSubscriptionEntity(createSubscriptionRequest);
        subscriptionRepository.save(subscriptionEntity);
        var subscriptionQueueDto = buildSubscriptionQueueDto(subscriptionEntity);
        messagePublisher.publishMessage(queue, subscriptionQueueDto);
    }

    @Transactional
    public void cancelSubscription(Long id) {
        var subscription = fetchIfExist(id);
        subscription.setStatus(DELETED);
        subscriptionRepository.save(subscription);
        var subscriptionQueueDto = buildSubscriptionQueueDto(subscription);
        messagePublisher.publishMessage(queue, subscriptionQueueDto);
    }

    @Transactional
    public void subscriptionStatusCheck() {
        var currentDate = LocalDateTime.now();
        var expiredSubscriptions = subscriptionRepository.findByExpireDateLessThanEqual(currentDate);
        expiredSubscriptions.forEach(subscription -> subscription.setStatus(EXPIRED));
        subscriptionRepository.saveAll(expiredSubscriptions);
        var subscriptionQueueDtoList = buildSubscriptionQueueDto(expiredSubscriptions);
        messagePublisher.publishMessage(queue, subscriptionQueueDtoList);
    }

    public SubscriptionEntity fetchIfExist(Long id) {
        return subscriptionRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.valueOf(SUBSCRIPTION_NOT_FOUND_ERROR),
                        String.format(SUBSCRIPTION_NOT_FOUND_ERROR.getMessage(), id)));
    }
}