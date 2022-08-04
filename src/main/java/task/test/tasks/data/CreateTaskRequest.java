package task.test.tasks.data;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class CreateTaskRequest {
    private String text;
    @NotBlank
    private String number;
    @NotNull
    private Integer userId;
    @NotNull
    private Integer groupId;
}
