package az.company.mssubscription.service;

import az.company.mssubscription.dao.entity.SubscriptionEntity;
import az.company.mssubscription.dao.repository.SubscriptionRepository;
import az.company.mssubscription.enums.SubscriptionStatus;
import az.company.mssubscription.exception.NotFoundException;
import az.company.mssubscription.model.request.CreateSubscriptionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static az.company.mssubscription.enums.SubscriptionStatus.EXPIRED;
import static az.company.mssubscription.mapper.SubscriptionMapper.buildSubscriptionEntity;
import static az.company.mssubscription.model.constants.ErrorMessages.*;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public void createSubscription(CreateSubscriptionRequest createSubscriptionRequest) {
        var subscriptionEntity = buildSubscriptionEntity(createSubscriptionRequest);
        subscriptionRepository.save(subscriptionEntity);
    }

    public void cancelSubscription(Long id) {
        var subscription = fetchIfExist(id);
        subscription.setStatus(EXPIRED);
        subscriptionRepository.save(subscription);
    }

    public void subscriptionStatusCheck() {
        var expiredSubscriptions = subscriptionRepository.findByExpiredSubscriptions().orElseThrow(
                () -> new NotFoundException(String.valueOf(SUBSCRIPTION_NOT_FOUND),
                        SUBSCRIPTION_NOT_FOUND.getMessage()));
        expiredSubscriptions.forEach(subscription -> subscription.setStatus(SubscriptionStatus.EXPIRED));
        subscriptionRepository.saveAll(expiredSubscriptions);
    }

    private SubscriptionEntity fetchIfExist(Long id) {
        return subscriptionRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.valueOf(SUBSCRIPTION_NOT_FOUND),
                        String.format(SUBSCRIPTION_NOT_FOUND.getMessage(), id)));

    }
}
