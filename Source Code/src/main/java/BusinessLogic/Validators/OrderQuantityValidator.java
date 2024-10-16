package BusinessLogic.Validators;

import DataAccess.AbstractDAO;
import DataAccess.ProductDAO;
import Model.Orders;
import Model.Product;
import Presentation.OrderPageController;

/**
 * The class implements the interface Validator. It checks id the selected quantity for the product is smaller or equal
 * than the existing stock.
 */
public class OrderQuantityValidator implements Validator<Orders> {
    private static final int min = 1;
    OrderPageController orderPageController;

    public void validate(Orders t) {
        AbstractDAO<Product> productAbstractDAO = new ProductDAO();
        int productID = t.getProduct_id();
        Product p = productAbstractDAO.findById(productID,"product_id");

        if (t.getQunatity() < min) {
            throw new IllegalArgumentException("There must be at least 1 piece!");
        }
        if (t.getQunatity() > p.getProduct_stock()) {
            printMessage();
            throw new IllegalArgumentException("The selected quantity is not available!");
        }

        // Update the stock value in memory
        int newValue = p.getProduct_stock() - t.getQunatity();
        p.setProduct_stock(newValue);

        // Persist the updated stock value back to the database
        productAbstractDAO.editById(productID, p,"product_id");
    }

    public void setOrderPageController(OrderPageController orderPageController) {
        this.orderPageController = orderPageController;
    }

    public void printMessage() {
        orderPageController.errorLabels.setText("The quantity for this product is not available!");
    }
}
