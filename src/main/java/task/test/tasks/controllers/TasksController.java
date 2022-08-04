package task.test.tasks.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import task.test.tasks.data.CreateTaskRequest;
import task.test.tasks.data.SuccessResponse;
import task.test.tasks.data.Task;
import task.test.tasks.services.api.TaskService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;

@Validated
@RestController
@RequestMapping("/task/")
@RequiredArgsConstructor
public class TasksController {
    private final TaskService service;
    private final SuccessResponse successResponse = new SuccessResponse();

    @PostMapping
    @ApiOperation(value = "Create task")
    public HttpEntity<Task> createTask(@Valid @RequestBody CreateTaskRequest request) {
        Task task = service.createTask(request);

        return status(CREATED).body(task);
    }

    @PatchMapping
    @ApiOperation(value = "Update task")
    public HttpEntity<SuccessResponse> createTask(@Valid @RequestBody Task task) {
        service.updateTask(task);

        return status(OK).body(successResponse);
    }

    @GetMapping(value = "/{userId}")
    @ApiOperation(value = "Get tasks by userId")
    public List<Task> getTasksByUserId(@PathVariable Integer userId) {
        return service.getTask(userId);
    }

    @GetMapping(value = "by-number/{number}")
    @ApiOperation(value = "Get task by number")
    public Task getTaskByNumber(@PathVariable String number) {
        return service.getTask(number);
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete task")
    public HttpEntity<Object> deleteTask(@PathVariable Integer id) {
        service.deleteTask(id);

        return status(NO_CONTENT).build();
    }
}