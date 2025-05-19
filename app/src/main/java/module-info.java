module com.example.app {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.calculator.app to javafx.fxml;
    exports com.calculator.app;
}