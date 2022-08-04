package task.test.tasks.clients.impl;

import org.springframework.stereotype.Component;
import task.test.tasks.clients.api.ExternalApiClient;
import task.test.tasks.data.Task;

@Component
public class ExternalApiClientImpl implements ExternalApiClient {
    @Override
    public Boolean sendInfo(Task task) {
        try {
            Thread.sleep(5000);
            return true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
