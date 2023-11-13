package az.company.mssubscription.scheduler;

import az.company.mssubscription.service.SubscriptionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubscriptionScheduler {
    SubscriptionService subscriptionService;

    @Scheduled(fixedDelayString = "PT1H")
    @SchedulerLock(name = "subscriptionStatusCheck", lockAtLeastFor = "PT1M", lockAtMostFor = "PT5M")
    public void subscriptionStatusCheck() {
        subscriptionService.subscriptionStatusCheck();
    }
}