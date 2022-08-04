package task.test.tasks.repositories.api;

import task.test.tasks.data.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    /**
     * Создание задачи пользователя
     */
    Optional<Task> createTask(String text, String number, Integer userId, Integer groupId);

    /**
     * Обновление задачи пользователя
     */
    void updateTask(Task task);

    /**
     * Получение задачи
     */
    Optional<Task> getTask(String number);
    List<Task> getTask(Integer userId);

    /**
     * Удаление задачи
     */
    void deleteTask(Integer id);
}
