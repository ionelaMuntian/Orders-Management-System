package Presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
public class HelloApplication extends Application {
    /**
     * Method called when the application starts.
     *
     * @param stage The primary stage for this application.
     * @throws IOException If an error occurs during loading.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loginPage.fxml"));
        Parent root = loader.load();
        LoginPageController controller = loader.getController();
        controller.setApplication(this); // Set the application reference in the controller
        stage.setScene(new Scene(root, 1060, 700));
        stage.show();
    }
    /**
     * Starts the login page of the application.
     *
     * @param stage The stage to display the login page.
     * @throws IOException If an error occurs during loading.
     */
    public void startMainPage(Stage stage) throws IOException {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("mainPage.fxml"));
       Parent root = loader.load();
       MainPageController controller = loader.getController();
       controller.setApplication(this); // Set the application reference in the controller
       stage.setScene(new Scene(root, 1060, 700));
       stage.show();
   }
    /**
     * Starts the product page of the application.
     *
     * @param stage The stage to display the product page.
     * @throws IOException If an error occurs during loading.
     */
    public void startProductPage(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("productPage.fxml"));
        Parent root = loader.load();
        ProductPageController controller = loader.getController();
        controller.setApplication(this); // Set the application reference in the controller
        stage.setScene(new Scene(root, 1060, 700));
        stage.show();
    }
    /**
     * Starts the order page of the application.
     *
     * @param stage The stage to display the order page.
     * @throws IOException If an error occurs during loading.
     */
    public void startOrderPage(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("orderPage.fxml"));
        Parent root = loader.load();
        OrderPageController controller = loader.getController();
        controller.setApplication(this); // Set the application reference in the controller
        stage.setScene(new Scene(root, 1060, 700));
        stage.show();
    }
    /**
     * Main method to launch the application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        launch();
    }
}