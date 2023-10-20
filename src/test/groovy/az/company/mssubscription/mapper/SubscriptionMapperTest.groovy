package az.company.mssubscription.mapper

import az.company.mssubscription.enums.SubscriptionType
import az.company.mssubscription.model.request.CreateSubscriptionRequest
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import org.mockito.Mock
import spock.lang.Specification

import static az.company.mssubscription.enums.SubscriptionStatus.ACTIVE
import static az.company.mssubscription.mapper.SubscriptionMapper.buildSubscriptionEntity

class SubscriptionMapperTest extends Specification {
    private EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()

    def "TestMapSubscriptionRequestToSubscriptionEntity"() {
        given:
        //def request = random.nextObject(CreateSubscriptionRequest)  can't create

        when:
        def actual = buildSubscriptionEntity(request)
        request.getUserId() >> userId
        request.getProductId() >> productId
        request.getCardId() >> cardId
        request.getSubscriptionType() >> subscriptionType

        then:
        actual.userId == request.userId()
        actual.productId == request.productId()
        actual.cardId == request.cardId()
        actual.subscriptionType == request.subscriptionType()
        actual.status == ACTIVE
    }

}

