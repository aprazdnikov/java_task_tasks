package task.test.tasks.data;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SuccessResponse {
    private final String result = "success";
}
