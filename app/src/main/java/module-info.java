module com.example.app {
    requires javafx.controls;
    requires javafx.fxml;


    exports com.calculator.app;
    opens com.calculator.app to javafx.fxml;
}