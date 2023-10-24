package az.company.mssubscription.config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class RabbitMQConfig {
    String subscriptionQ;
    String subscriptionDLQ;
    String subscriptionQExchange;
    String subscriptionDLQExchange;
    String subscriptionQKey;
    String subscriptionDLQKey;

    public RabbitMQConfig(@Value("${rabbitmq.queue.subscription}") String subscriptionQ,
                          @Value("${rabbitmq.queue.subscription-dlq}") String subscriptionDLQ) {
        this.subscriptionQ = subscriptionQ;
        this.subscriptionDLQ = subscriptionDLQ;
        this.subscriptionQExchange = subscriptionQ + "_Exchange";
        this.subscriptionDLQExchange = subscriptionDLQ + "_Exchange";
        this.subscriptionQKey = subscriptionQ + "_Key";
        this.subscriptionDLQKey = subscriptionDLQ + "_Key";
    }

    @Bean
    DirectExchange subscriptionDLQExchange() {
        return new DirectExchange(subscriptionDLQExchange);
    }

    @Bean
    DirectExchange subscriptionQExchange() {
        return new DirectExchange(subscriptionQExchange);
    }

    @Bean
    Queue subscriptionDLQ() {
        return QueueBuilder.durable(subscriptionDLQ).build();
    }

    @Bean
    Queue subscriptionQ() {
        return QueueBuilder.durable(subscriptionQ)
                .withArgument("x-dead-letter-exchange", subscriptionDLQExchange)
                .withArgument("x-dead-letter-routing-key", subscriptionDLQKey)
                .build();
    }

    @Bean
    Binding subscriptionDLQBinding() {
        return BindingBuilder.bind(subscriptionDLQ())
                .to(subscriptionQExchange()).with(subscriptionDLQKey);
    }

    @Bean
    Binding subscriptionQBinding() {
        return BindingBuilder.bind(subscriptionQ())
                .to(subscriptionQExchange()).with(subscriptionQKey);
    }
}
