package manager;

import db.DBCollectionprovider;
import lombok.SneakyThrows;
import model.Task;
import model.TaskStatus;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;


public class TaskManager {
    private manager.UserManager userManager = new manager.UserManager();
    private Connection connection = DBCollectionprovider.getInstance().getConnection();

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    public boolean create(Task task) {
        String sql = "INSERT INTO task (name,description,deadline,status,user_id) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, task.getName());
            statement.setString(2, task.getDescription());
            if (task.getDeadline() != null) {
                statement.setString(3, sdf.format(task.getDeadline()));
            } else {
                statement.setString(3, null);
            }

            statement.setString(4, task.getStatus().name());
            statement.setInt(5, task.getUserId());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()) {
                task.setId(rs.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public List<Task> getAllTasksByUserId(int userID) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM task WHERE user_id = ?");
        statement.setInt(1, userID);
        ResultSet resultSet = statement.executeQuery();
        List<Task> tasks = new LinkedList<Task>();
        while (resultSet.next()) {
            Task task = new Task();
            task.setId(resultSet.getInt("id"));
            task.setName(resultSet.getString("name"));
            task.setDescription(resultSet.getString("description"));
            try {
                task.setDeadline(sdf.parse(resultSet.getString("deadline")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            task.setStatus(TaskStatus.valueOf(resultSet.getString("status")));
            task.setUserId(resultSet.getInt("user_id"));
            task.setUser(userManager.getUserById(task.getUserId()));
            tasks.add(task);
        }
        return tasks;

    }


    public void update(int taskId, String newStatus) {

        String sql = "UPDATE task SET status = ? WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
         statement.setString(1,newStatus);
         statement.setInt(2,taskId);
          statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    public boolean delete(long id) {
        String sql = "DELETE FROM task WHERE id =" + id;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;
    }

    public List<Task> getAllTaskByUserAndStatus(int userId, TaskStatus status) {
        List<Task> task = new LinkedList<Task>();
        String sql = "SELECT * FROM task WHERE user_id = ? And status = ? ";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setString(1, status.name());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                task.add(getTaskFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return task;
    }


    @SneakyThrows
    public List<Task> getAllTasks() {
        String sql = "SELECT * FROM task";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        List<Task> tasks = new LinkedList<Task>();
        while (resultSet.next()) {
            Task task = new Task();
            task.setId(resultSet.getInt("id"));
            task.setName(resultSet.getString("name"));
            task.setDescription(resultSet.getString("description"));
            task.setDeadline(sdf.parse(resultSet.getString("deadline")));
            task.setStatus(TaskStatus.valueOf(resultSet.getString("status").toUpperCase()));
            task.setUserId(resultSet.getInt("user_id"));
            task.setUser(userManager.getUserById(resultSet.getInt(6)));
            tasks.add(task);

        }
        return tasks;
    }

    private Task getTaskFromResultSet(ResultSet resultSet) {
        try {
            return Task.builder()
                    .id(resultSet.getInt(1))
                    .name(resultSet.getString(2))
                    .description(resultSet.getNString(3))
                    .deadline(sdf.parse(resultSet.getString(4)))
                    .status(TaskStatus.valueOf(resultSet.getString(5)))
                    .userId(resultSet.getInt(6))
                    .build();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
