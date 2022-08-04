package task.test.tasks.clients.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import task.test.tasks.clients.api.KafkaClient;
import task.test.tasks.data.Task;


@Component
@RequiredArgsConstructor
public class KafkaClientImpl implements KafkaClient {
    private static final String TOPIC = "tasks";

    private final KafkaTemplate<Long, Task> template;

    @Override
    public void send(Task task) {
//        template.send(TOPIC, task);
    }
}
