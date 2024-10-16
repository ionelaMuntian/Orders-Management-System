package Presentation;

import BusinessLogic.ClientBLL;
import BusinessLogic.ProductBLL;
import DataAccess.AbstractDAO;
import DataAccess.ClientDAO;
import DataAccess.ProductDAO;
import DataAccess.AbstractDAO;
import Model.Client;
import Model.Product;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
/**
 * Controller class for the product page. It manages all the operation related to the product though the insert, edit,
 * delete and show tables buttons. It also receives the information from the fields.
 */
public class ProductPageController {

    @FXML
    private TableView<Product> ProductTable;

    @FXML
    private Button addClient;

    @FXML
    private Button clientPage;

    @FXML
    private Button deleteClient;

    @FXML
    private Button editClient;

    @FXML
    private Label errorLabels;

    @FXML
    private TextField idEdit;

    @FXML
    private Label idEditLabel;

    @FXML
    private Label idLabel;

    @FXML
    private TextField idTextField;

    @FXML
    private Label nameLabel;

    @FXML
    private TextField nameTextField;

    @FXML
    private Button order;

    @FXML
    private Label priceLabel;

    @FXML
    private TextField priceTextField;

    @FXML
    private Button productPage;

    @FXML
    private TableColumn<?, ?> product_id;

    @FXML
    private TableColumn<?, ?> product_name;

    @FXML
    private TableColumn<?, ?> product_price;

    @FXML
    private TableColumn<?, ?> product_stock;

    @FXML
    private Button save;

    @FXML
    private TextField stockTextField;

    @FXML
    private Label stocklLabel;

    @FXML
    private Button tableContentButton;
    private boolean addProductPressed = false;
    private boolean deleteProductPressed = false;
    private boolean editProductPressed = false;
    private HelloApplication application;

    public void setApplication(HelloApplication application) {
        this.application = application;
    }

    /**
     * Handles the action when the "Client Page" button is clicked.
     *
     * @param event The event triggered by clicking the button.
     * @throws IOException If an I/O error occurs.
     */
    @FXML
    void clientPage_onAction(ActionEvent event) throws IOException {
        if (application != null) {
            Stage stage = (Stage) addClient.getScene().getWindow();
            application.startMainPage(stage);
        } else {
            System.err.println("Error: HelloApplication instance not set.");
        }
    }
    /**
     * Handles the action when the "Order Page" button is clicked.
     *
     * @param event The event triggered by clicking the button.
     * @throws IOException If an I/O error occurs.
     */

    @FXML
    void order_onAction(ActionEvent event) throws IOException {
        if (application != null) {
            Stage stage = (Stage) addClient.getScene().getWindow();
            application.startOrderPage(stage);
        } else {
            System.err.println("Error: HelloApplication instance not set.");
        }
    }
    /**
     * Handles the action when the "Product Page" button is clicked.
     *
     * @param event The event triggered by clicking the button.
     * @throws IOException If an I/O error occurs.
     */
    @FXML
    void productPage_onAction(ActionEvent event) throws IOException {
        if (application != null) {
            Stage stage = (Stage) addClient.getScene().getWindow();
            application.startProductPage(stage);
        } else {
            System.err.println("Error: HelloApplication instance not set.");
        }
    }
    /**
     * Handles the action when the "Add Product" button is clicked.
     *
     * @param event The event triggered by clicking the button.
     */
    @FXML
    void addProduct_onAction(ActionEvent event) {
        addProductPressed = true;
        deleteProductPressed = false;
        editProductPressed = false;

        // Set labels visible
        priceLabel.setVisible(true);
        stocklLabel.setVisible(true);
        nameLabel.setVisible(true);
        nameTextField.setVisible(true);
        stockTextField.setVisible(true);
        priceTextField.setVisible(true);

        //set invisible
        idLabel.setVisible(false);
        idTextField.setVisible(false);
        idEdit.setVisible(false);
        idEditLabel.setVisible(false);
        save.setVisible(true);
    }
    /**
     * Handles the action when the "Delete Product" button is clicked.
     *
     * @param event The event triggered by clicking the button.
     */
    @FXML
    void deleteProduct_onAction(ActionEvent event) {
        addProductPressed = false;
        deleteProductPressed = true;
        editProductPressed = false;

        // Set labels invisible
        priceLabel.setVisible(false);
        stocklLabel.setVisible(false);
        nameLabel.setVisible(false);
        nameTextField.setVisible(false);
        stockTextField.setVisible(false);
        priceTextField.setVisible(false);

        //set visible
        idLabel.setVisible(true);
        idTextField.setVisible(true);
        idEdit.setVisible(false);
        idEditLabel.setVisible(false);
        save.setVisible(true);
    }
    /**
     * Handles the action when the "Edit Product" button is clicked.
     *
     * @param event The event triggered by clicking the button.
     */
    @FXML
    void editProduct_onAction(ActionEvent event) {
        addProductPressed = false;
        deleteProductPressed = false;
        editProductPressed = true;

        // Set labels visible
        priceLabel.setVisible(true);
        stocklLabel.setVisible(true);
        nameLabel.setVisible(true);
        nameTextField.setVisible(true);
        stockTextField.setVisible(true);
        priceTextField.setVisible(true);
        idEdit.setVisible(true);
        idEditLabel.setVisible(true);

        //set invisible
        idLabel.setVisible(false);
        idTextField.setVisible(false);
        save.setVisible(true);
    }
    /**
     * Handles the action when the "Save" button is clicked.
     *
     * @param event The event triggered by clicking the button.
     */
    @FXML
    void save_onAction(ActionEvent event) {
        if (addProductPressed) {
            String name = nameTextField.getText();
            String priceText = priceTextField.getText();
            String stockText = stockTextField.getText();

            if (!name.isEmpty() && !priceText.isEmpty() && !stockText.isEmpty()) {
                int price = Integer.parseInt(priceText);
                int stock = Integer.parseInt(stockText);
                insert(name, price, stock);
            } else {
                errorLabels.setText("All fields must be completed!");
            }
        }
        if (editProductPressed) {
            String name = nameTextField.getText();
            String priceText = priceTextField.getText();
            String stockText = stockTextField.getText();
            String idText = idEdit.getText();

            if (!name.isEmpty() && !priceText.isEmpty() && !stockText.isEmpty() && !idText.isEmpty()) {
                int age = Integer.parseInt(priceText);
                int id = Integer.parseInt(idText);
                int stock = Integer.parseInt(stockText);
                edit(id, name, age, stock);
            } else {
                errorLabels.setText("All fields must be completed!");
            }
        }

        if (deleteProductPressed) {
            String idText = idTextField.getText();

            if (!idText.isEmpty()) {
                int id = Integer.parseInt(idText);
                delete(id);
            } else {
                errorLabels.setText("All fields must be completed!");
            }

        }
    }
    /**
     * Handles the action when the "Table Content" button is clicked.
     *
     * @param event The event triggered by clicking the button.
     */
    @FXML
    void tableContentButton_onAction(ActionEvent event) {
        ProductBLL productBLL = new ProductBLL();
        List<Product> products = productBLL.findAll();
        populateTable(products, ProductTable);
    }

    void insert(String name, int price, int stock) {
        Product product = new Product(name, price, stock);
        ProductBLL productBLL = new ProductBLL();
        productBLL.insertProduct(product);

        //update table
        AbstractDAO<Product> productDAO = new ProductDAO();
        List<Product> products = productDAO.findAll();
        populateTable(products, ProductTable);
    }

    void delete(int id) {
        ProductBLL productBLL = new ProductBLL();
        productBLL.deleteProductById(id);

        //update table
        AbstractDAO<Product> productDAO = new ProductDAO();
        List<Product> products = productDAO.findAll();
        populateTable(products, ProductTable);
    }

    void edit(int id, String name, int price, int stock) {
        Product product = new Product(name, price, stock);
        ProductBLL productBLL = new ProductBLL();
        productBLL.editProductById(id, product);

        //update table
        AbstractDAO<Product> productDAO = new ProductDAO();
        List<Product> products = productDAO.findAll();
        populateTable(products, ProductTable);
    }

    public static <T> void populateTable(List<T> objects, TableView<T> tableView) {
        if (objects.isEmpty()) {
            return;
        }
        Class<?> objectClass = objects.get(0).getClass();
        tableView.getColumns().clear();

        // Iterate through the fields of the class using reflection to create table columns
        for (Field field : objectClass.getDeclaredFields()) {
            TableColumn<T, Object> column = new TableColumn<>(field.getName());
            column.setCellValueFactory(data -> {
                try {
                    field.setAccessible(true);
                    return new SimpleObjectProperty<>(field.get(data.getValue()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return null;
                }
            });
            tableView.getColumns().add(column);
        }
        tableView.setItems(FXCollections.observableArrayList(objects));
    }
}
