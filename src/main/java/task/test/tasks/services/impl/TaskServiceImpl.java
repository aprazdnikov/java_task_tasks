package task.test.tasks.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import task.test.tasks.clients.api.ExternalApiClient;
import task.test.tasks.clients.api.KafkaClient;
import task.test.tasks.data.CreateTaskRequest;
import task.test.tasks.data.Message;
import task.test.tasks.data.Task;
import task.test.tasks.exceptions.BadRequestException;
import task.test.tasks.exceptions.NotFoundException;
import task.test.tasks.repositories.api.TaskRepository;
import task.test.tasks.services.api.TaskService;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    public static final String FAILED_TO_CREATE_TASK = "failed to create task number %s";
    public static final String TASK_NUMBER_NOT_FOUND = "Task number %s not found";

    private final ExternalApiClient externalApiClient;
    private final KafkaClient kafkaClient;
    private final TaskRepository repository;

    @Override
    @Transactional
    public Task createTask(CreateTaskRequest request) {
        Task task;

        Optional<Task> optionalTask = repository.getTask(request.getNumber());
        if (optionalTask.isEmpty()) {
            task = repository.createTask(request.getText(), request.getNumber(), request.getUserId(), request.getGroupId())
                .orElseThrow(() -> new BadRequestException(format(FAILED_TO_CREATE_TASK, request.getNumber())));
        } else {
            task = optionalTask.get();
        }

        externalApiClient.sendInfo(task);
        kafkaClient.send(task);

        return task;
    }

    @Override
    public void updateTask(Task task) {
        repository.updateTask(task);
    }

    @Override
    @Transactional(readOnly = true)
    public Task getTask(String number) {
        return repository.getTask(number)
            .orElseThrow(() -> new NotFoundException(format(TASK_NUMBER_NOT_FOUND, number)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getTask(Integer userId) {
        return repository.getTask(userId);
    }

    @Override
    @Transactional
    public void deleteTask(Integer id) {
        repository.deleteTask(id);
    }
}
