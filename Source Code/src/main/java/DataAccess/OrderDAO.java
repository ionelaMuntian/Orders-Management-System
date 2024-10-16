package DataAccess;

import Model.Bill;
import DataAccess.BillDAO;
import Model.Orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Connection.DatabaseConnection;
/**
 * The OrderDAO extends the AbstractDAO class, which implements all the CRUD operations.
 * The methods from OrderDAO class are called from the corresponding BLL class.
 */
public class OrderDAO extends AbstractDAO<Orders> {

    public OrderDAO() {
        super();
    }
    public Orders findById(int order_id) {
        return super.findById(order_id, "order_id");
    }
    public Orders insert(Orders order) {
        // Create a BillDAO instance
        BillDAO billDAO = new BillDAO();
        // Create a new Bill object
        Bill newBill = new Bill(order.getClient_id(), order.getProduct_id(), order.getQunatity(),  java.time.LocalDateTime.now());
        try {
            // Insert the new Bill into the log table
            billDAO.insertIntoLog(newBill);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return super.insert(order);
    }
    public boolean deleteById(int order_id) {
        return super.deleteById(order_id, "order_id");
    }
    public void editById(int order_id, Orders newOrder, String field_name) {
        super.editById(order_id, newOrder, field_name);
    }
    public List<Orders> findAll() {
        return super.findAll();
    }
}
