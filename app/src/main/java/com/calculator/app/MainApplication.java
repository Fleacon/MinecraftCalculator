package com.calculator.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/calculator/app/main-view.fxml"));
        Scene scene = new Scene(root, 800, 450);

        scene.getStylesheets().addAll(this.getClass().getResource("/com/calculator/app/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Minecraft Calculation Edition");
        stage.setMinWidth(800);
        stage.setMinHeight(450);
        //stage.setResizable(false);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
