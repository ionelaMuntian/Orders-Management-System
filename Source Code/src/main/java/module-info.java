module com.example.orders {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens Presentation to javafx.fxml;
    exports Presentation;
    exports Connection;
    opens Connection to javafx.fxml;
}