package Presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Connection.DatabaseConnection;
/**
 * Controller class for the login page.
 */
public class LoginPageController {

    @FXML
    private Label ErrorLabel;

    @FXML
    private Button clientPage;

    @FXML
    private Button loginButton;

    @FXML
    private Button order;

    @FXML
    private TextField password;

    @FXML
    private Button productPage;

    @FXML
    private TextField username;
    private HelloApplication application;

    @FXML
    void clientPage_onAction(ActionEvent event) {

    } /**
     * Handles the action when the login button is clicked.
     *
     * @param event The ActionEvent triggered by clicking the button.
     */
    @FXML
    void loginButton_onAction(ActionEvent event) {
        if (!username.getText().isBlank() && !password.getText().isBlank()) {
            validateLogin();
        } else {
            ErrorLabel.setText("Please enter username and password!");
        }
    }
    @FXML
    void order_onAction(ActionEvent event) {

    }
    @FXML
    void productPage_onAction(ActionEvent event) {

    }
    /**
     * Sets the reference to the main application.
     *
     * @param application The reference to the main application.
     */
    public void setApplication(HelloApplication application) {
        this.application = application;
    }
    /**
     * Validates the login credentials.
     * If valid, navigates to the main page.
     * Otherwise, displays an error message.
     */
    public void validateLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        if (connectDB != null) {
            String verifyLogin = "SELECT count(1) FROM login WHERE username = ? AND password = ?";

            try {
                PreparedStatement preparedStatement = connectDB.prepareStatement(verifyLogin);
                preparedStatement.setString(1, username.getText());
                preparedStatement.setString(2, password.getText());

                ResultSet queryResult = preparedStatement.executeQuery();

                while (queryResult.next()) {
                    if (queryResult.getInt(1) == 1) {
                        ErrorLabel.setText("Welcome!");
                        if (application != null) {
                            Stage stage = (Stage) loginButton.getScene().getWindow();
                            application.startMainPage(stage); // Call the start2 method from the application
                        } else {
                            System.err.println("Error: HelloApplication instance not set.");
                        }
                    } else {
                        ErrorLabel.setText("Invalid Login. Please try again!");
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            ErrorLabel.setText("Failed to connect to the database. Please try again later.");
        }
    }

}
