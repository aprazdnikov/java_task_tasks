package task.test.tasks.clients.api;


import task.test.tasks.data.Task;

public interface KafkaClient {
    void send(Task task);
}
