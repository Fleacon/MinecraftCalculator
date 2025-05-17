package DB;

import java.sql.*;
import java.nio.file.Paths;

/**
 * Verwaltet die Datenbankverbindung für die Minecraft-Anwendung.
 * Stellt eine Singleton-Verbindung zur SQLite-Datenbank her.
 */
public class DatabaseManager {
    private static final String DB_URL;

    static {
        String basePath = Paths.get("").toAbsolutePath().toString();  // current working directory
        DB_URL = "jdbc:sqlite:" + basePath + "/minecraftDB.sqlite";
    }

    private static Connection connection;

    /**
     * Gibt eine Verbindung zur Datenbank zurück. Falls keine aktive Verbindung
     * existiert, wird eine neue erstellt.
     *
     * @return Eine aktive Datenbankverbindung
     * @throws SQLException Wenn ein Fehler beim Verbindungsaufbau auftritt
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
        }
        return connection;
    }
}
