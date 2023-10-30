package az.company.mssubscription.mapper

import az.company.mssubscription.dao.entity.SubscriptionEntity
import az.company.mssubscription.model.request.CreateSubscriptionRequest
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

import static az.company.mssubscription.enums.SubscriptionStatus.ACTIVE
import static az.company.mssubscription.enums.SubscriptionType.WEEKLY
import static az.company.mssubscription.mapper.SubscriptionMapper.buildSubscriptionEntity
import static az.company.mssubscription.mapper.SubscriptionMapper.buildSubscriptionQueueDto

class SubscriptionMapperTest extends Specification {
    EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()

    def "TestBuildSubscriptionEntity"() {
        given:
        def request = CreateSubscriptionRequest.builder()
                .userId(1L)
                .productId(1L)
                .cardId(1L)
                .subscriptionType(WEEKLY)
                .build()

        when:
        def actual = buildSubscriptionEntity(request)

        then:
        actual.userId == request.userId()
        actual.productId == request.productId()
        actual.cardId == request.cardId()
        actual.subscriptionType == request.subscriptionType()
        actual.status == ACTIVE
    }

    def "TestBuildSubscriptionQueueDto"() {
        given:
        def subscriptionEntity = random.nextObject(SubscriptionEntity)

        when:
        def actual = buildSubscriptionQueueDto(subscriptionEntity)

        then:
        actual.userId == subscriptionEntity.userId
        actual.productId == subscriptionEntity.productId
        actual.cardId == subscriptionEntity.cardId
        actual.subscriptionType == subscriptionEntity.subscriptionType
        actual.subscriptionStatus == subscriptionEntity.status
    }
}