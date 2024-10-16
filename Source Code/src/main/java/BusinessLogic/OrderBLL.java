package BusinessLogic;

import BusinessLogic.Validators.OrderQuantityValidator;
import DataAccess.AbstractDAO;
import DataAccess.OrderDAO;
import Model.Orders;
import Presentation.OrderPageController;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Business Logic Layer (BLL) class for managing Orders entities.
 * This class provides methods to manage CRUD (Create, Read, Update, Delete) operations on Orders entities,
 * along with validation for client data.
 */
public class OrderBLL {
    private static OrderQuantityValidator validator;
    static OrderPageController orderPageController;

    /**
     * Constructs an OrderBLL object and initializes the order quantity validator.
     */

    public OrderBLL() {
        validator = new OrderQuantityValidator();
    }

    /**
     * Sets the order page controller.
     *
     * @param orderPageController The OrderPageController instance to set.
     */

    public void setOrderPageController(OrderPageController orderPageController) {
        OrderBLL.orderPageController = orderPageController;
    }

    /**
     * Finds an order by its ID.
     *
     * @param id The ID of the order to find.
     * @return The Orders object if found.
     * @throws NoSuchElementException if the order with the specified ID is not found.
     */
    public Orders findById(int id) {
        AbstractDAO<Orders> orderDAOAbstractDAO = new OrderDAO();
        Orders st = orderDAOAbstractDAO.findById(id, "order_id");
        if (st == null) {
            throw new NoSuchElementException("The order with id =" + id + " was not found!");
        }
        return st;
    }

    /**
     * Inserts a new order into the database, after checking the conditions
     *
     * @param order The Orders object to insert.
     */
    public void insertOrder(Orders order) {
        validator.setOrderPageController(orderPageController);
        validator.validate(order);
        AbstractDAO<Orders> orderDAOAbstractDAO = new OrderDAO();
        orderDAOAbstractDAO.insert(order);
    }

    /**
     * Deletes an order by its ID.
     *
     * @param id The ID of the order to delete.
     * @throws NoSuchElementException if the order with the specified ID does not exist.
     */
    public void deleteOrderById(int id) {
        // Check if the client exists
        Orders order = findById(id);
        if (order == null) {
            throw new NoSuchElementException("The order with id = " + id + " does not exist!");
        }
        // If the client exists, proceed with deletion
        AbstractDAO<Orders> orderDAOAbstractDAO = new OrderDAO();
        orderDAOAbstractDAO.deleteById(id, "order_id");
    }

    /**
     * Edits an order's information by its ID.
     *
     * @param id    The ID of the order to edit.
     * @param order The updated Orders object.
     * @throws NoSuchElementException if the order with the specified ID does not exist.
     */

    public void editOrderById(int id, Orders order) {
        AbstractDAO<Orders> orderDAOAbstractDAO = new OrderDAO();
        Orders originalOrder = findById(id);
        if (originalOrder == null) {
            throw new NoSuchElementException("The order with id = " + id + " does not exist!");
        }
        validator.setOrderPageController(orderPageController);
        validator.validate(order);

        // If the client exists, proceed with deletion
        orderDAOAbstractDAO.editById(id, order, "order_id");
    }

    /**
     * Retrieves all orders from the database.
     *
     * @return A list of all Orders objects.
     */
    public List<Orders> findAll() {
        AbstractDAO<Orders> ordersAbstractDAO = new OrderDAO();
        return ordersAbstractDAO.findAll();
    }
}
