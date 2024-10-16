package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Utility class for managing database connections and resources.
 */
public class DatabaseConnection {
    public static Connection databaseLink;
    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());
    /**
     * Establishes a connection to the database.
     *
     * @return The Connection object representing the database connection.
     */
    public static Connection getConnection() {
        String databaseName = "OrdersManagement";
        String databaseUser = "postgres";
        String databasePassword = "MiculPrint2004!";
        String url = "jdbc:postgresql://localhost:5432/OrdersManagement";

        try {
            Class.forName("org.postgresql.Driver");
            databaseLink = DriverManager.getConnection(url,databaseUser,databasePassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return databaseLink;
    }
    /**
     * Closes the database connection.
     *
     * @param connection The Connection object to close.
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the connection");
            }
        }
    }
    /**
     * Closes the database statement.
     *
     * @param statement The Statement object to close.
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the statement");
            }
        }
    }
    /**
     * Closes the database result set.
     *
     * @param resultSet The ResultSet object to close.
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the ResultSet");
            }
        }
    }
}