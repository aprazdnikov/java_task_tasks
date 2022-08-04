package task.test.tasks.controllers;

import org.hamcrest.core.IsNull;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import task.test.tasks.data.CreateTaskRequest;
import task.test.tasks.data.Task;
import task.test.tasks.repositories.api.TaskRepository;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class TasksControllerTest extends AbstractWebMvcTest {
    private static final String URL_TASK = "/task/";
    private static final String URL_BY_USER_ID = URL_TASK + "%s";
    private static final String URL_BY_NUMBER = URL_TASK + "by-number/%s";

    @Autowired
    private TaskRepository repository;

    @Test
    public void testCreateTask() throws Exception {
        CreateTaskRequest request = new CreateTaskRequest()
            .setUserId(1)
            .setText("test")
            .setNumber("num_test")
            .setGroupId(1);

        mockMvc.perform(post(URL_TASK)
            .contentType(APPLICATION_JSON)
            .content(toJson(request)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is(IsNull.notNullValue())))
            .andExpect(jsonPath("$.text", is(request.getText())))
            .andExpect(jsonPath("$.number", is(request.getNumber())))
            .andExpect(jsonPath("$.userId", is(request.getUserId())))
            .andExpect(jsonPath("$.groupId", is(request.getGroupId())))
        ;
    }

    @Test
    public void testCreateTaskBadRequest() throws Exception {
        mockMvc.perform(post(URL_TASK)
                .contentType(APPLICATION_JSON)
                .content(toJson(new CreateTaskRequest())))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.number", is("must not be blank")))
            .andExpect(jsonPath("$.userId", is("must not be null")))
            .andExpect(jsonPath("$.groupId", is("must not be null")))
        ;
    }

    @Test
    public void testGetTaskByUserId() throws Exception {
        mockMvc.perform(get(format(URL_BY_USER_ID, 1))
                .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.size()", is(2)))
        ;
    }

    @Test
    public void testGetTaskByUserIdNotFound() throws Exception {
        mockMvc.perform(get(format(URL_BY_USER_ID, 10))
                .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.size()", is(0)))
        ;
    }

    @Test
    public void testGetTaskByNumber() throws Exception {
        mockMvc.perform(get(format(URL_BY_NUMBER, "number1"))
                .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.text", is("text1")))
            .andExpect(jsonPath("$.number", is("number1")))
            .andExpect(jsonPath("$.userId", is(1)))
            .andExpect(jsonPath("$.groupId", is(1)))
        ;
    }

    @Test
    public void testGetTaskByNumberNotFound() throws Exception {
        mockMvc.perform(get(format(URL_BY_NUMBER, 10))
                .contentType(APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.error", is("Task number 10 not found")))
        ;
    }

    @Test
    public void testUpdateTask() throws Exception {
        Task task = new Task()
            .setId(1)
            .setUserId(1)
            .setText("test")
            .setNumber("num_test")
            .setGroupId(1);

        mockMvc.perform(patch(URL_TASK)
                .contentType(APPLICATION_JSON)
                .content(toJson(task)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.result", is("success")))
        ;

        Task taskDb = repository.getTask("num_test").get();
        assertThat(taskDb.getNumber()).isEqualTo(task.getNumber());
        assertThat(taskDb.getText()).isEqualTo(task.getText());
    }
}
