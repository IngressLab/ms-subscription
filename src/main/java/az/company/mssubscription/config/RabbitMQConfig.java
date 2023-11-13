package az.company.mssubscription.config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RabbitMQConfig {
    String subscriptionQ;
    String subscriptionQExchange;
    String subscriptionQKey;

    public RabbitMQConfig(@Value("${rabbitmq.queue.subscription}") String subscriptionQ) {
        this.subscriptionQ = subscriptionQ;
        this.subscriptionQExchange = subscriptionQ + "_Exchange";
        this.subscriptionQKey = subscriptionQ + "_Key";
    }

    @Bean
    DirectExchange subscriptionQExchange() {
        return new DirectExchange(subscriptionQExchange);
    }

    @Bean
    Queue subscriptionQ() {
        return QueueBuilder.durable(subscriptionQ)
                .build();
    }

    @Bean
    Binding subscriptionQBinding() {
        return BindingBuilder.bind(subscriptionQ())
                .to(subscriptionQExchange()).with(subscriptionQKey);
    }
}