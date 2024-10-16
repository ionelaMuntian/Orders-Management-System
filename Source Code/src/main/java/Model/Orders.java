package Model;
/**
 * Represents an Order entity containing order details.
 */
public class Orders {
    private int order_id;
    private int client_id;
    private int product_id;
    private int qunatity;
    /**
     * Default constructor for the Orders class.
     */
    public Orders() {
    }
    /**
     * Constructs an Orders object with specified attributes.
     *
     * @param client_id The ID of the client associated with the order.
     * @param product_id The ID of the product in the order.
     * @param qunatity The quantity of the product in the order.
     */
    public Orders(int client_id, int product_id, int qunatity) {
        this.client_id = client_id;
        this.product_id = product_id;
        this.qunatity = qunatity;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getClient_id() {
        return client_id;
    }
    /**
     * Sets the client's ID associated with the order.
     *
     * @param client_id The ID to set.
     */
    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getProduct_id() {
        return this.product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQunatity() {
        return qunatity;
    }

    public void setQunatity(int qunatity) {
        this.qunatity = qunatity;
    }
}
