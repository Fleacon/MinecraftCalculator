module com.example.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.media;

    exports com.calculator.app;
    opens com.calculator.app to javafx.fxml;
}