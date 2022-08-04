package task.test.tasks.repositories.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import task.test.tasks.data.Task;
import task.test.tasks.repositories.api.TaskRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {
    private final NamedParameterJdbcTemplate template;

    private static final String SQL_INSERT =
        "INSERT INTO tasks (text, number, user_id, group_id) VALUES (:TEXT, :NUMBER, :USER_ID, :GROUP_ID) " +
            "RETURNING id, text, number, user_id, group_id";
    private static final String SQL_UPDATE =
        "UPDATE tasks SET text = :TEXT, number = :NUMBER, user_id = :USER_ID, group_id = :GROUP_ID " +
            "WHERE id = :ID";
    private static final String SQL_QUERY =
        "SELECT id, text, number, user_id, group_id FROM tasks ";
    private static final String BY_USER_ID =
        "WHERE user_id = :USER_ID";
    private static final String BY_NUMBER =
        "WHERE number = :NUMBER";
    private static final String SQL_DELETE =
        "DELETE FROM tasks WHERE id = :ID";

    @Override
    public Optional<Task> createTask(String text, String number, Integer userId, Integer groupId) {
        MapSqlParameterSource sqlParameters = new MapSqlParameterSource()
            .addValue("TEXT", text)
            .addValue("NUMBER", number)
            .addValue("USER_ID", userId)
            .addValue("GROUP_ID", groupId);

        return template.query(SQL_INSERT, sqlParameters, getResultDtoRowMapper())
            .stream()
            .findAny();
    }

    @Override
    public void updateTask(Task task) {
        MapSqlParameterSource sqlParameters = new MapSqlParameterSource()
            .addValue("ID", task.getId())
            .addValue("TEXT", task.getText())
            .addValue("NUMBER", task.getNumber())
            .addValue("USER_ID", task.getUserId())
            .addValue("GROUP_ID", task.getGroupId());

        template.update(SQL_UPDATE, sqlParameters);
    }

    @Override
    public Optional<Task> getTask(String number) {
        MapSqlParameterSource sqlParameters = new MapSqlParameterSource()
            .addValue("NUMBER", number);

        return template.query(SQL_QUERY + BY_NUMBER, sqlParameters, getResultDtoRowMapper())
            .stream()
            .findAny();
    }

    @Override
    public List<Task> getTask(Integer userId) {
        MapSqlParameterSource sqlParameters = new MapSqlParameterSource()
            .addValue("USER_ID", userId);

        return template.query(SQL_QUERY + BY_USER_ID, sqlParameters, getResultDtoRowMapper());
    }

    @Override
    public void deleteTask(Integer id) {
        MapSqlParameterSource sqlParameters = new MapSqlParameterSource()
            .addValue("ID", id);

        template.update(SQL_DELETE, sqlParameters);
    }

    private RowMapper<Task> getResultDtoRowMapper() {
        return (resultSet, i) -> toDto(resultSet);
    }

    private Task toDto(ResultSet resultSet) throws SQLException {
        return new Task()
            .setId(resultSet.getInt("id"))
            .setText(resultSet.getString("text"))
            .setNumber(resultSet.getString("number"))
            .setUserId(resultSet.getInt("user_id"))
            .setGroupId(resultSet.getInt("group_id"));
    }
}
