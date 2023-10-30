package az.company.mssubscription.service

import az.company.mssubscription.dao.entity.SubscriptionEntity
import az.company.mssubscription.dao.repository.SubscriptionRepository
import az.company.mssubscription.exception.NotFoundException
import az.company.mssubscription.model.queue.SubscriptionQueueDto
import az.company.mssubscription.model.request.CreateSubscriptionRequest
import az.company.mssubscription.queue.MessagePublisher
import io.github.benas.randombeans.EnhancedRandomBuilder
import org.springframework.beans.factory.annotation.Value
import spock.lang.Specification

import java.time.LocalDateTime

import static az.company.mssubscription.enums.SubscriptionStatus.DELETED
import static az.company.mssubscription.enums.SubscriptionStatus.EXPIRED
import static az.company.mssubscription.enums.SubscriptionType.MONTHLY
import static az.company.mssubscription.mapper.SubscriptionMapper.buildSubscriptionEntity
import static az.company.mssubscription.mapper.SubscriptionMapper.buildSubscriptionQueueDto

class SubscriptionServiceTest extends Specification {
    SubscriptionService subscriptionService
    SubscriptionRepository subscriptionRepository
    MessagePublisher messagePublisher
    def random = EnhancedRandomBuilder.aNewEnhancedRandom()

    @Value('${rabbitmq.subscription.queue}')
    String queueName

    def setup() {
        subscriptionRepository = Mock()
        messagePublisher = Mock()
        subscriptionService = new SubscriptionService(subscriptionRepository, messagePublisher)
    }

    def "TestCreateSubscription"() {
        given:
        def request = CreateSubscriptionRequest
                .builder()
                .userId(1L)
                .productId(1L)
                .cardId(1L)
                .subscriptionType(MONTHLY)
                .build()

        def entity = buildSubscriptionEntity(request)
        def subscriptionQueueDto = buildSubscriptionQueueDto(entity)

        when:
        subscriptionService.createSubscription(request)

        then:
        1 * subscriptionRepository.save(entity) >> entity
        1 * messagePublisher.publishMessage(queueName, subscriptionQueueDto)
    }

    def "TestCancelSubscription with valid subscription"() {
        given:
        def id = random.nextLong()
        def subscriptionEntity = random.nextObject(SubscriptionEntity)
        subscriptionEntity.status = DELETED
        def subscriptionQueueDto = buildSubscriptionQueueDto(subscriptionEntity)

        when:
        subscriptionService.cancelSubscription(id)

        then:
        1 * subscriptionRepository.findById(id) >> Optional.of(subscriptionEntity)
        1 * subscriptionRepository.save(subscriptionEntity) >> subscriptionEntity
        1 * messagePublisher.publishMessage(queueName, subscriptionQueueDto)
    }

    def "TestCancelSubscription with non-existing subscription should throw NotFoundException"() {
        given:
        def id = random.nextLong()
        def message = "Subscription not found with this ID: %s ".formatted(id)

        when:
        subscriptionService.cancelSubscription(id)

        then:
        1 * subscriptionRepository.findById(id) >> Optional.empty()
        0 * subscriptionRepository.save(_)
        0 * messagePublisher.publishMessage(_, _)

        NotFoundException ex = thrown()
        ex.message == message
    }

    def "TestSubscriptionStatusCheck with expired subscriptions"() {
        given:
        def currentDate = LocalDateTime.now()
        def entity =
                SubscriptionEntity.builder()
                        .id(1L)
                        .userId(5L)
                        .cardId(2L)
                        .productId(3L)
                        .subscriptionType(MONTHLY)
                        .status(EXPIRED)
                        .build()

        def subscriptionQueueDto = SubscriptionQueueDto.builder()

                .userId(5L)
                .cardId(2L)
                .productId(3L)
                .subscriptionType(MONTHLY)
                .status(EXPIRED)
                .build()

        def expiredSubscriptions = List.of(entity)
        def subscriptionQueueDtoList = List.of(subscriptionQueueDto)

        when:
        subscriptionService.subscriptionStatusCheck()

        then:
        1 * subscriptionRepository.findByExpireDateLessThanEqual(currentDate) >> expiredSubscriptions
        1 * subscriptionRepository.saveAll(expiredSubscriptions) >> expiredSubscriptions
        1 * messagePublisher.publishMessage(queueName, subscriptionQueueDtoList)
    }

    def "TestFetchIfExist with valid id"() {
        given:
        def id = random.nextLong()
        def subscriptionEntity = random.nextObject(SubscriptionEntity)

        when:
        subscriptionService.fetchIfExist(id)

        then:
        1 * subscriptionRepository.findById(id) >> Optional.of(subscriptionEntity)
    }

    def "TestFetchIfExist with invalid id"() {
        given:
        def id = random.nextLong()
        def message = "Subscription not found with this ID: %s ".formatted(id)

        when:
        subscriptionService.fetchIfExist(id)

        then:
        1 * subscriptionRepository.findById(id) >> Optional.empty()

        NotFoundException ex = thrown()
        ex.message == message
    }
}

