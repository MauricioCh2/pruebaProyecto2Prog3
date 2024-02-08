package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConn {

    String userName = "root";
    String password = "AngeolicaM$55";
    String DBName = "correcion";

    public Connection connection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DBName, userName, password);

        return connection;
    }

}
