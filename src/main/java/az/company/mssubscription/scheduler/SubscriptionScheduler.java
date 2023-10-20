package az.company.mssubscription.scheduler;

import az.company.mssubscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SubscriptionScheduler {

    private final SubscriptionService subscriptionService;

    @Scheduled(fixedDelayString = "PT1H")
    @SchedulerLock(name = "subscriptionStatusCheck", lockAtLeastFor = "PT1M", lockAtMostFor = "PT5M")
    public void subscriptionStatusCheck() {
        log.info("Subscription status checking...");
        subscriptionService.subscriptionStatusCheck();
    }
}
