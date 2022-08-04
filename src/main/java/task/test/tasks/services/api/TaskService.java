package task.test.tasks.services.api;

import task.test.tasks.data.CreateTaskRequest;
import task.test.tasks.data.Task;

import java.util.List;

public interface TaskService {
    /**
     * Создание задачи пользователя
     */
    Task createTask(CreateTaskRequest request);

    /**
     * Обновление задачи пользователя
     */
    void updateTask(Task task);

    /**
     * Получение задачи
     */
    Task getTask(String number);
    List<Task> getTask(Integer userId);

    /**
     * Удаление задачи
     */
    void deleteTask(Integer id);
}
