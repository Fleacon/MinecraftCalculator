package DB;

import java.sql.*;
import java.nio.file.Paths;

public class DatabaseManager {
    private static final String DB_URL;

    static {
        String basePath = Paths.get("").toAbsolutePath().toString();  // current working directory
        DB_URL = "jdbc:sqlite:" + basePath + "/minecraftDB.sqlite";
    }

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
        }
        return connection;
    }
}
