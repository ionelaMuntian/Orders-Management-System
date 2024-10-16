package BusinessLogic.Validators;

import Model.Product;

/**
 * The class implements the interface Validator. It checks id the selected quantity for the product is the correct interval
 */
public class ProductStockValidator implements Validator<Product>{
    private static final int MIN = 1;
    private static final int MAX = 1000;
    public void validate(Product t) {

        if (t.getProduct_stock() < MIN || t.getProduct_stock() > MAX) {
            throw new IllegalArgumentException("The Stock limit is not respected!");
        }
    }
}
