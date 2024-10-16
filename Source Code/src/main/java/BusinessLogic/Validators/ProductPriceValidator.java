package BusinessLogic.Validators;

import DataAccess.ProductDAO;
import Model.Client;
import Model.Product;

/**The class implements the interface Validator. It checks id the selected price for the product is in the right interval.
 */
public class ProductPriceValidator implements Validator<Product>{
    private static final int MIN_PRICE = 1;
    private static final int MAX_PRICE = 500;
    public void validate(Product t) {

        if (t.getProduct_price() < MIN_PRICE || t.getProduct_price() > MAX_PRICE) {
            throw new IllegalArgumentException("The Product Price limit is not respected!");
        }
    }
}
