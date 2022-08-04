package task.test.tasks.data;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Task {
    private Integer id;
    private String text;
    private String number;
    private Integer userId;
    private Integer groupId;
}
