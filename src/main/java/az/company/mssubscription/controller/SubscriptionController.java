package az.company.mssubscription.controller;

import az.company.mssubscription.model.request.CreateSubscriptionRequest;
import az.company.mssubscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    @ResponseStatus(CREATED)
    public void createSubscription(@RequestBody CreateSubscriptionRequest createSubscriptionRequest) {
        subscriptionService.createSubscription(createSubscriptionRequest);
    }

    @DeleteMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelSubscription(@PathVariable Long id) {
        subscriptionService.cancelSubscription(id);
    }
}