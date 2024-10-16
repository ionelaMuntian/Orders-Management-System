package DataAccess;

import Model.Product;

import java.util.List;
/**
 * The ProductDAO extends the AbstractDAO class, which implements all the CRUD operations.
 * The methods from ProductDAO class are called from the corresponding BLL class.
 */
public class ProductDAO extends AbstractDAO<Product> {

    public ProductDAO() {
        super();
    }

    public Product findById(int product_id) {
        return super.findById(product_id,"product_id");
    }

    public Product insert(Product product) {
        return super.insert(product);
    }

    public boolean deleteById(int product_id) {
        return super.deleteById(product_id, "product_id");
    }

    public void editById(int product_id, Product newProduct, String field_name) {
        super.editById(product_id, newProduct,field_name);
    }

    public List<Product> findAll() {
        return super.findAll();
    }
}
