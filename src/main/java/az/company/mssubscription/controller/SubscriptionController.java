package az.company.mssubscription.controller;

import az.company.mssubscription.model.request.CreateSubscriptionRequest;
import az.company.mssubscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    @ResponseStatus(CREATED)
    public void createSubscription(@RequestBody CreateSubscriptionRequest createSubscriptionRequest){
        subscriptionService.createSubscription(createSubscriptionRequest);
    }

    @PutMapping("/cancel-subscription/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelSubscription(@PathVariable Long id){
       subscriptionService.cancelSubscription(id);
    }

}
