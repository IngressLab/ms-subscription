package az.company.mssubscription.controller

import az.company.mssubscription.exception.ErrorHandler
import az.company.mssubscription.model.request.CreateSubscriptionRequest
import az.company.mssubscription.service.SubscriptionService
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static az.company.mssubscription.enums.SubscriptionType.MONTHLY
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class SubscriptionControllerTest extends Specification {
    MockMvc mockMvc
    SubscriptionController subscriptionController
    SubscriptionService subscriptionService

    void setup() {
        subscriptionService = Mock()
        subscriptionController = new SubscriptionController(subscriptionService)
        mockMvc = MockMvcBuilders.standaloneSetup(subscriptionController)
                .setControllerAdvice(new ErrorHandler())
                .build()
    }

    def "TestCreateSubscription"() {
        given:
        def url = "/v1/subscriptions"
        def request = CreateSubscriptionRequest.builder()
                .productId(1L)
                .userId(2L)
                .cardId(3L)
                .subscriptionType(MONTHLY)
                .build()


        def requestBody = '''
        {
          "productId": "1",
          "userId": "2",
          "cardId": "3",
          "subscriptionType": "MONTHLY"
        }
        '''

        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(APPLICATION_JSON)
                .content(requestBody))

        then:
        1 * subscriptionService.createSubscription(request)
        result.andExpectAll(
                status().isCreated()
        )
    }

    def "TestCancelSubscription"() {
        given:
        def id = 1L
        def url = "/v1/subscriptions/$id/cancel"

        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.delete(url)
                .contentType(APPLICATION_JSON))

        then:
        1 * subscriptionService.cancelSubscription(id)
        result.andExpectAll(
                status().isNoContent()
        )
    }
}
