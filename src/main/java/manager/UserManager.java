package manager;

import db.DBCollectionprovider;
import model.User;
import model.UserType;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserManager {
    private Connection connection;
    public UserManager(){
        connection = DBCollectionprovider.getInstance().getConnection();
    }


    public boolean addUser(User user) {
        String sql = "INSERT INTO user(name,surname,email,password,type,picture_url) VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getUserType().name());
            statement.setString(6, user.getPictureUrl());

            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()) {
                user.setId(rs.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
    public User getUserById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE id=? ");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return getUserFromResultSet(resultSet);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public User getByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM user WHERE email = ? AND password = ? ";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,email);
            statement.setString(2,password);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
              return getUserFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return getUserFromResultSet(resultSet);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getAllUser() {
        List<User> users = new LinkedList<User>();
        String sql = "SELECT * FROM user ";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                users.add(getUserFromResultSet(resultSet));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    private User getUserFromResultSet(ResultSet resultSet) {
        try {
            return User.builder()
                    .id(resultSet.getInt(1))
                    .name(resultSet.getString(2))
                    .surName(resultSet.getString(3))
                    .email(resultSet.getString(4))
                    .password(resultSet.getString(5))
                    .userType(UserType.valueOf(resultSet.getString(6)))
                    .pictureUrl(resultSet.getString(7))
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
