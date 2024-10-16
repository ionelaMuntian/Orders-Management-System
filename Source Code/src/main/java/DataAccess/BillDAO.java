package DataAccess;
import Connection.DatabaseConnection;
import Model.Bill;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {
    public void insertIntoLog(Bill bill) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO log (client_id, product_id, quantity, date_time) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, bill.clientId());
                statement.setInt(2, bill.productId());
                statement.setInt(3, bill.quantity());
                statement.setTimestamp(4, Timestamp.valueOf(bill.dateTime()));
                statement.executeUpdate();
            }
        }
    }

    /**
     * Reads Bill records from the Log table in the database.
     * @throws SQLException if a database access error occurs.
     */
    public static List<Bill> readFromLog() throws SQLException {
        List<Bill> bills = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM log";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    int clientId = resultSet.getInt("client_id");
                    int productId = resultSet.getInt("product_id");
                    int quantity = resultSet.getInt("quantity");
                    int orderId = resultSet.getInt("order_id");
                    LocalDateTime dateTime = resultSet.getTimestamp("date_time").toLocalDateTime();

                    // Create and add Bill object to the list
                    Bill bill = new Bill(clientId, productId, quantity, dateTime);
                    bills.add(bill);
                }
            }
        }
        return bills;
    }
}
