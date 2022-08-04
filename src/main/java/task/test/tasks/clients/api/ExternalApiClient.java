package task.test.tasks.clients.api;

import task.test.tasks.data.Task;

public interface ExternalApiClient {
    Boolean sendInfo(Task task);
}
