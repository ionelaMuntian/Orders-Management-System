package Presentation;
import BusinessLogic.ClientBLL;
import BusinessLogic.OrderBLL;
import DataAccess.ClientDAO;
import DataAccess.ProductDAO;
import Model.Client;
import Model.Orders;
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
import Model.Client;
/**
 * Controller class for the client page. It manages all the operation related to the client though the insert, edit,
 * delete and show tables buttons. It also receives the information from the fields.
 */
public class MainPageController {
    @FXML
    private TableView<Client> ClientTable;

    @FXML
    private TableColumn<?, ?> Client_Age;

    @FXML
    private TableColumn<?, ?> Client_Email;

    @FXML
    private TableColumn<?, ?> Client_ID;

    @FXML
    private TableColumn<?, ?> Client_Name;

    @FXML
    private TextField idEdit;

    @FXML
    private Label idEditLabel;

    @FXML
    private Button addClient;

    @FXML
    private Label ageLabel;

    @FXML
    private TextField ageTextField;

    @FXML
    private Button clientPage;

    @FXML
    private Button deleteClient;

    @FXML
    private Button editClient;

    @FXML
    private Label emailLabel;

    @FXML
    private TextField emailTextField;

    @FXML
    private Label nameLabel;

    @FXML
    private TextField nameTextField;

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
    private Label errorLabels;
    private boolean addClientPressed = false;
    private boolean deleteClientPressed = false;
    private boolean editClientPressed = false;
    private HelloApplication application;
    /**
     * Sets the reference to the main application.
     *
     * @param application The reference to the main application.
     */
    public void setApplication(HelloApplication application) {
        this.application = application;
    }

    /**
     * Fills the tables from the interface with the data from the database
     * @param event
     */
    @FXML
    void tableContentButton_onAction(ActionEvent event) {
        ClientBLL clientBLL = new ClientBLL();
        List<Client> orders = clientBLL.findAll();
        populateTable(orders, ClientTable);
    }

    /**
     * Inserts a new client. Updates the interface so display only the labels related to the insertion.
     * @param event
     */
    @FXML
    void addClient_onAction(ActionEvent event) {
        addClientPressed = true;
        deleteClientPressed = false;
        editClientPressed = false;

        // Set labels visible
        ageLabel.setVisible(true);
        emailLabel.setVisible(true);
        nameLabel.setVisible(true);
        nameTextField.setVisible(true);
        emailTextField.setVisible(true);
        ageTextField.setVisible(true);

        //set invisible
        idLabel.setVisible(false);
        idTextField.setVisible(false);
        idEdit.setVisible(false);
        idEditLabel.setVisible(false);
        save.setVisible(true);
    }
    /**
     * Handles the action when the "Delete Client" button is clicked.
     *
     * @param event The event triggered by clicking the button.
     */
    @FXML
    void deleteClient_onAction(ActionEvent event) {
        addClientPressed = false;
        deleteClientPressed = true;
        editClientPressed = false;

        // Set labels invisible
        ageLabel.setVisible(false);
        emailLabel.setVisible(false);
        nameLabel.setVisible(false);
        nameTextField.setVisible(false);
        emailTextField.setVisible(false);
        ageTextField.setVisible(false);

        //set visible
        idLabel.setVisible(true);
        idTextField.setVisible(true);
        idEdit.setVisible(false);
        idEditLabel.setVisible(false);
        save.setVisible(true);
    }
    /**
     * Handles the action when the "Edit Client" button is clicked.
     *
     * @param event The event triggered by clicking the button.
     */
    @FXML
    void editClient_onAction(ActionEvent event) {
        addClientPressed = false;
        deleteClientPressed = false;
        editClientPressed = true;

        // Set labels visible
        ageLabel.setVisible(true);
        emailLabel.setVisible(true);
        nameLabel.setVisible(true);
        nameTextField.setVisible(true);
        emailTextField.setVisible(true);
        ageTextField.setVisible(true);
        idEdit.setVisible(true);
        idEditLabel.setVisible(true);

        //set invisible
        idLabel.setVisible(false);
        idTextField.setVisible(false);
        save.setVisible(true);
    }
    @FXML
    void clientPage_onAction(ActionEvent event) throws IOException {
        if (application != null) {
            Stage stage = (Stage) addClient.getScene().getWindow();
            application.startMainPage(stage);
        } else {
            System.err.println("Error: HelloApplication instance not set.");
        }
    }
    @FXML
    void order_onAction(ActionEvent event) throws IOException {
        if (application != null) {
            Stage stage = (Stage) addClient.getScene().getWindow();
            application.startOrderPage(stage);
        } else {
            System.err.println("Error: HelloApplication instance not set.");
        }
    }
    @FXML
    void productPage_onAction(ActionEvent event) throws IOException {
        if (application != null) {
            Stage stage = (Stage) addClient.getScene().getWindow();
            application.startProductPage(stage);
        } else {
            System.err.println("Error: HelloApplication instance not set.");
        }
    }
    @FXML
    void save_onAction(ActionEvent event) {
        if (addClientPressed) {
            String name = nameTextField.getText();
            String ageText = ageTextField.getText();
            String email = emailTextField.getText();

            if (!name.isEmpty() && !ageText.isEmpty() && !email.isEmpty()) {
                int age = Integer.parseInt(ageText);
                insert(name, age, email);
            } else {
                errorLabels.setText("All fields must be completed!");
            }
        }
        if (editClientPressed) {
            String name = nameTextField.getText();
            String ageText = ageTextField.getText();
            String email = emailTextField.getText();
            String idText = idEdit.getText();

            if (!name.isEmpty() && !ageText.isEmpty() && !email.isEmpty()) {
                int age = Integer.parseInt(ageText);
                int id = Integer.parseInt(idText);
                edit(id, name, age, email);
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
    void insert(String name, int age, String email) {
        Client client = new Client(name, age, email);
        ClientBLL clientBLL = new ClientBLL();
        clientBLL.insertClient(client);

        //update changes in the table
        List<Client> orders = clientBLL.findAll();
        populateTable(orders, ClientTable);
    }
    void delete(int id) {
        ClientBLL clientBLL = new ClientBLL();
        clientBLL.deleteClientById(id);

        //update
        List<Client> orders = clientBLL.findAll();
        populateTable(orders, ClientTable);
    }
    void edit(int id, String name, int age, String email) {
        Client client = new Client(name, age, email);
        ClientBLL clientBLL = new ClientBLL();
        clientBLL.editClientById(id, client);

        //update changes in the table
        List<Client> orders = clientBLL.findAll();
        populateTable(orders, ClientTable);
    }
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
}
