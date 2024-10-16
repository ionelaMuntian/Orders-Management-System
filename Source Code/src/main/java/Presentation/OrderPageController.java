package Presentation;

import BusinessLogic.ClientBLL;
import BusinessLogic.OrderBLL;
import BusinessLogic.ProductBLL;
import DataAccess.ClientDAO;
import DataAccess.OrderDAO;
import Model.Client;
import Model.Orders;
import Model.Product;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
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
import java.util.List;
/**
 * Controller class for the order page. It manages all the operation related to the order though the insert, edit,
 * delete and show tables buttons. It also receives the information from the fields.
 */
public class OrderPageController {
    @FXML
    private Label clientIDLabel;

    @FXML
    private TextField clientID_TextFiled;
    @FXML
    private Label productIDLabel;

    @FXML
    private TextField productID_TextField;
    @FXML
    private Label quantityLabel;

    @FXML
    private TextField quantity_TextField;


    @FXML
    private TableColumn<?, ?> Client_Age;

    @FXML
    private TableColumn<?, ?> Client_Email;

    @FXML
    private TableColumn<?, ?> Client_ID;

    @FXML
    private TableColumn<?, ?> Client_Name;

    @FXML
    private TableView<Orders> OrderTable;

    @FXML
    private Button addOrder;


    @FXML
    private Button clientPage;

    @FXML
    private Button deleteOrder;

    @FXML
    private Button editOrder;


    @FXML
    public Label errorLabels;

    @FXML
    private TextField idEdit;

    @FXML
    private Label idEditLabel;

    @FXML
    private Label idLabel;

    @FXML
    private TextField idTextField;

    @FXML
    private Button order;

    @FXML
    private Button productPage;

    @FXML
    private Button save;

    @FXML
    private Button tableContentButton;
    private boolean addClientPressed = false;
    private boolean deleteClientPressed = false;
    private boolean editClientPressed = false;
    private HelloApplication application;

    public void setApplication(HelloApplication application) {
        this.application = application;
    }
    /**
     * Handles the action when the "Add Order" button is clicked.
     *
     * @param event The event triggered by clicking the button.
     */
    @FXML
    void addOrder_onAction(ActionEvent event) {
        addClientPressed = true;
        deleteClientPressed = false;
        editClientPressed = false;

        // Set labels visible
        clientIDLabel.setVisible(true);
        productIDLabel.setVisible(true);
        quantityLabel.setVisible(true);
        clientID_TextFiled.setVisible(true);
        productID_TextField.setVisible(true);
        quantity_TextField.setVisible(true);

        //set invisible
        idLabel.setVisible(false);
        idTextField.setVisible(false);
        idEdit.setVisible(false);
        idEditLabel.setVisible(false);

        errorLabels.setText("");
        save.setVisible(true);
    }

    @FXML
    void deleteOrder_onAction(ActionEvent event) {
        addClientPressed = false;
        deleteClientPressed = true;
        editClientPressed = false;

        // Set labels invisible
        clientIDLabel.setVisible(false);
        productIDLabel.setVisible(false);
        quantityLabel.setVisible(false);
        clientID_TextFiled.setVisible(false);
        productID_TextField.setVisible(false);
        quantity_TextField.setVisible(false);

        //set visible
        idLabel.setVisible(true);
        idTextField.setVisible(true);
        idEdit.setVisible(false);
        idEditLabel.setVisible(false);

        errorLabels.setText("");
        save.setVisible(true);
    }

    @FXML
    void editOrder_onAction(ActionEvent event) {
        addClientPressed = false;
        deleteClientPressed = false;
        editClientPressed = true;

        // Set labels visible
        clientIDLabel.setVisible(true);
        productIDLabel.setVisible(true);
        quantityLabel.setVisible(true);
        clientID_TextFiled.setVisible(true);
        productID_TextField.setVisible(true);
        quantity_TextField.setVisible(true);
        idEdit.setVisible(true);
        idEditLabel.setVisible(true);

        //set invisible
        idLabel.setVisible(false);
        idTextField.setVisible(false);

        errorLabels.setText("");
        save.setVisible(true);
    }


    @FXML
    void clientPage_onAction(ActionEvent event) throws IOException {
        if (application != null) {
            Stage stage = (Stage) editOrder.getScene().getWindow();
            application.startMainPage(stage);
        } else {
            System.err.println("Error: HelloApplication instance not set.");
        }
    }

    @FXML
    void order_onAction(ActionEvent event) throws IOException {
        if (application != null) {
            Stage stage = (Stage) editOrder.getScene().getWindow();
            application.startOrderPage(stage);
        } else {
            System.err.println("Error: HelloApplication instance not set.");
        }
    }

    @FXML
    void productPage_onAction(ActionEvent event) throws IOException {
        if (application != null) {
            Stage stage = (Stage) editOrder.getScene().getWindow();
            application.startProductPage(stage);
        } else {
            System.err.println("Error: HelloApplication instance not set.");
        }
    }

    @FXML
    void save_onAction(ActionEvent event) {
        if (addClientPressed) {
            String clientID_String = clientID_TextFiled.getText();
            String productID_String = productID_TextField.getText();
            String quantity_String = quantity_TextField.getText();

            if (!clientID_String.isEmpty() && !productID_String.isEmpty() && !quantity_String.isEmpty()) {
                int clientID = Integer.parseInt( clientID_String);
                int productID = Integer.parseInt( productID_String);
                int quantity = Integer.parseInt( quantity_String);

                insert(clientID, productID, quantity);
            } else {
                errorLabels.setText("All fields must be completed!");
            }
        }
        if (editClientPressed) {
            String clientID_String = clientID_TextFiled.getText();
            String productID_String = productID_TextField.getText();
            String quantity_String = quantity_TextField.getText();
            String idText = idEdit.getText();

            if (!clientID_String.isEmpty() && !productID_String.isEmpty() && !quantity_String.isEmpty() && !idText.isEmpty()) {
                int clientID = Integer.parseInt( clientID_String);
                int productID = Integer.parseInt( productID_String);
                int quantity = Integer.parseInt( quantity_String);
                int id = Integer.parseInt(idText);
                edit(id, clientID, productID, quantity);
            } else {
                errorLabels.setText("All fields must be completed!");
            }
        }

        if (deleteClientPressed) {
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
     * Inserts a new order into the database.
     *
     * @param clientID  The ID of the client associated with the order.
     * @param productID The ID of the product associated with the order.
     * @param quantity  The quantity of the product ordered.
     */
    void insert(int clientID, int productID, int quantity) {
        Orders order = new Orders(clientID, productID, quantity);
        OrderBLL orderBLL = new OrderBLL();
        orderBLL.setOrderPageController(this);
        orderBLL.insertOrder(order);

        //update changes in the table
        List<Orders> orders = orderBLL.findAll();
        populateTable(orders, OrderTable);
    }

    void delete(int id) {
        OrderBLL orderBLL = new OrderBLL();
        orderBLL.setOrderPageController(this);
        orderBLL.deleteOrderById(id);

        //update changes in the table
        List<Orders> orders = orderBLL.findAll();
        populateTable(orders, OrderTable);
    }
    /**
     * Edits an existing order in the database.
     *
     * @param id        The ID of the order to be edited.
     * @param clientID  The new client ID associated with the order.
     * @param productID The new product ID associated with the order.
     * @param quantity  The new quantity of the product ordered.
     */
    void edit(int id, int clientID, int productID, int quantity) {
        Orders order = new Orders(clientID, productID, quantity);
        OrderBLL orderBLL = new OrderBLL();
        orderBLL.setOrderPageController(this);
        orderBLL.editOrderById(id,order);

        //update changes in the table
        List<Orders> orders = orderBLL.findAll();
        populateTable(orders, OrderTable);
    }
    /**
     * Populates the TableView with a list of orders.
     *
     * @param objects    The list of orders to be displayed in the table.
     * @param tableView  The TableView object to populate.
     * @param <T>        The type of objects to be displayed in the table.
     */
    public static <T> void populateTable(List<T> objects, TableView<T> tableView) {
        if (objects.isEmpty()) {
            return;
        }
        // Get the class type of the first object in the list
        Class<?> objectClass = objects.get(0).getClass();

        // Clear existing columns from the table
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

        // Populate the table with the list of objects
        tableView.setItems(FXCollections.observableArrayList(objects));
    }

    @FXML
    void tableContentButton_onAction(ActionEvent event) {
        OrderBLL orderBLL = new OrderBLL();
        List<Orders> orders = orderBLL.findAll();
        populateTable(orders, OrderTable);
    }

}
