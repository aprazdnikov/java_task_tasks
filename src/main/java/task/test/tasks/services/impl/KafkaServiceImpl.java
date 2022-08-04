package task.test.tasks.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import task.test.tasks.data.Message;
import task.test.tasks.data.Task;
import task.test.tasks.repositories.api.TaskRepository;
import task.test.tasks.services.api.KafkaService;

@Service
@RequiredArgsConstructor
public class KafkaServiceImpl implements KafkaService {
    private final TaskRepository taskRepository;

    @Override
    public void handleMessage(Message message) {
        taskRepository.getTask(message.getFromUser())
            .forEach(task -> updateTask(task, message.getToUser()));
    }

    private void updateTask(Task task, Integer userId) {
        task.setUserId(userId);
        taskRepository.updateTask(task);
    }
}
