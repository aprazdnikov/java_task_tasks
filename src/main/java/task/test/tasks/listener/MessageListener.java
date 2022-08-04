package task.test.tasks.listener;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import task.test.tasks.data.Message;
import task.test.tasks.services.api.KafkaService;


@Profile("!test")
@Component
@RequiredArgsConstructor
public class MessageListener {
    private final KafkaService service;

    @KafkaListener(id = "Starship", topics = {"messages"}, containerFactory = "singleFactory")
    public void consume(Message message) {
        service.handleMessage(message);
    }
}
