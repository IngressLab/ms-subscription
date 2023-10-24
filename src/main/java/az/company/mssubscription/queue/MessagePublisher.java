package az.company.mssubscription.queue;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessagePublisher {

    private final AmqpTemplate template;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public <T> void publishMessage(String queue, T data) {
        template.convertAndSend(queue, objectMapper.writeValueAsString(data));
    }
}
