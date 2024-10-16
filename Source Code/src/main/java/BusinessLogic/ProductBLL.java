package BusinessLogic;
import BusinessLogic.Validators.*;
import DataAccess.AbstractDAO;
import DataAccess.ProductDAO;
import Model.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Business Logic Layer (BLL) class for managing Product entities.
 * This class provides methods to manage CRUD (Create, Read, Update, Delete) operations on Product entities,
 * along with validation for client data.
 */

public class ProductBLL{
    private static List<Validator<Product>> validators;
    /**
     * Constructs a ProductBLL object and initializes validators.
     */
    public ProductBLL() {
        validators = new ArrayList<Validator<Product>>();
        validators.add(new ProductPriceValidator());
        validators.add(new ProductStockValidator());
    }
    /**
     * Finds a product by its ID.
     *
     * @param product_id The ID of the product to find.
     * @return The Product object if found.
     * @throws NoSuchElementException if the product with the specified ID is not found.
     */
    public Product findById(int product_id) {
        AbstractDAO<Product> productDAO = new ProductDAO();
        Product product = productDAO.findById(product_id,"product_id");
        if (product == null) {
            throw new NoSuchElementException("The student with id =" + product_id + " was not found!");
        }
        return product;
    }
    /**
     * Inserts a new product into the database.
     *
     * @param product The Product object to insert.
     */
    public void insertProduct(Product product) {
        for (Validator<Product> v : validators) {
            v.validate(product);
        }
        AbstractDAO<Product> productDAO = new ProductDAO();
        productDAO.insert(product);
    }
    /**
     * Deletes a product by its ID.
     *
     * @param id The ID of the product to delete.
     * @throws NoSuchElementException if the product with the specified ID does not exist.
     */
    public void deleteProductById(int id) {
        // Check if the client exists
        AbstractDAO<Product> productDAO = new ProductDAO();
        Product product = findById(id);

        if (product == null) {
            throw new NoSuchElementException("The client with id = " + id + " does not exist!");
        }
        // If the client exists, proceed with deletion
        productDAO.deleteById(id,"product_id");
    }
    /**
     * Edits a product's information by its ID.
     *
     * @param id      The ID of the product to edit.
     * @param product The updated Product object.
     * @throws NoSuchElementException if the product with the specified ID does not exist.
     */
    public void editProductById(int id, Product product) {
        // Check if the client exists
        AbstractDAO<Product> productDAO = new ProductDAO();
        Product originalProduct = productDAO.findById(id,"product_id");
        if (originalProduct == null) {
            throw new NoSuchElementException("The client with id = " + id + " does not exist!");
        }
        // If the client exists, proceed with deletion
        productDAO.editById(id,product,"product_id");
    }
    /**
     * Retrieves all products from the database.
     *
     * @return A list of all Product objects.
     */
    public List<Product> findAll() {
        AbstractDAO<Product> productDAO = new ProductDAO();
        return productDAO.findAll();
    }
}
