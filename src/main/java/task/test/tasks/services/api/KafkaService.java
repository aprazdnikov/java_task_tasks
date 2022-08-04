package task.test.tasks.services.api;

import task.test.tasks.data.Message;

public interface KafkaService {
    /**
     * Обработка сообщений
     */
    void handleMessage(Message message);
}
