package az.company.mssubscription.scheduler

import az.company.mssubscription.service.SubscriptionService
import spock.lang.Specification

class SubscriptionSchedulerTest extends Specification{
     SubscriptionService subscriptionService;
     SubscriptionScheduler scheduler;

    def setup() {
        subscriptionService = Mock()
        scheduler = new SubscriptionScheduler(subscriptionService)
    }

    def "TestSubscriptionScheduler"() {
        when:
        scheduler.subscriptionStatusCheck()

        then:
        1 * subscriptionService.subscriptionStatusCheck()
    }
}
