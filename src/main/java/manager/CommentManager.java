package manager;

import db.DBCollectionprovider;
import lombok.SneakyThrows;
import model.Comment;
import model.Task;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CommentManager {

    private Connection connection = DBCollectionprovider.getInstance().getConnection();

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    UserManager userManager = new UserManager();
    TaskManager taskManager = new TaskManager();


    public void addComment(Comment comment) throws SQLException {

        String sql = "INSERT INTO comment(`userId`,`taskId`,`comment`) VALUES(?,?,?)";


        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, comment.getUserId());
        statement.setInt(2, comment.getTaskId());
        statement.setString(3, comment.getComment());



        statement.executeUpdate();

        ResultSet rs = statement.getGeneratedKeys();
        if (rs.next()) {
            int id = rs.getInt(1);
            comment.setId(id);
        }


    }


    public void deleteComment(int commentId) {
        String sql = "DELETE FROM comment WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, commentId);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @SneakyThrows
    public List<Comment> getAllComments(int id) {
        String sql = "SELECT * FROM comment WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        List<Comment> comment = new ArrayList<>();
        while (resultSet.next()) {
            Comment comments = new Comment();
            comments.setId(resultSet.getInt("id"));
            comments.setUserId(resultSet.getInt("userId"));
            comments.setTaskId(resultSet.getInt("taskId"));
            comments.setComment(resultSet.getString("comment"));
        comments.setDate(sdf.parse(resultSet.getString("date")));
            comments.setUser(userManager.getUserById(comments.getId()));
            comments.setTask((Task) taskManager.getAllTasksByUserId(comments.getUserId()));
            comment.add(comments);

        }
        return comment;
    }

    public List<Comment> getAllComments() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `comment`");
            List<Comment> comments = new ArrayList<>();
            while (resultSet.next()) {
                Comment comment = new Comment();
                comment.setId(resultSet.getInt("id"));
                comment.setUserId(resultSet.getInt("userId"));
                comment.setTaskId(resultSet.getInt("taskId"));
                comment.setComment(resultSet.getString("comment"));
               // comment.setDate(sdf.parse(resultSet.getString("date")));
                comment.setUser(userManager.getUserById(comment.getUserId()));
                comment.setTask(taskManager.getAllTasksById(comment.getUserId()));
                comments.add(comment);
            }
            return comments;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

