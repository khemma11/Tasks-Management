package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBCollectionprovider {
    public static DBCollectionprovider instance = new DBCollectionprovider();

private Connection connection;

private final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
private final String DB_URL="jdbc:mysql://localhost:3306/task_manegement? useUnicode=true&characterEncoding=utf8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
private final String DB_USERNAME = "root";
private final String DB_PASSWORD = "root";

    public DBCollectionprovider() {
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        }

    }

    public static DBCollectionprovider getInstance(){
        return instance;
    }
public Connection getConnection(){
    try {
        if (connection==null||connection.isClosed()){
            connection = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
        }
    } catch (SQLException e) {
        e.printStackTrace();


    }
    return connection;
}
}
