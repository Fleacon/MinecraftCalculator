package com.calculator.app;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class MainApplication extends Application {

    private static final double ASPECT_RATIO = 16.0 / 9.0;
    private boolean isAdjusting = false;
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/calculator/app/main-view.fxml"));
        Scene scene = new Scene(root, 800, 450);

        scene.getStylesheets().addAll(this.getClass().getResource("/com/calculator/app/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Minecraft Calculation Edition");
        stage.setMinWidth(800);
        stage.setMinHeight(800/ASPECT_RATIO);

        ChangeListener<Number> sizeListener = (obs, oldVal, newVal) -> {
            if (isAdjusting) return;
            isAdjusting = true;

            double newWidth = stage.getWidth();
            double newHeight = newWidth * ASPECT_RATIO;

            stage.setHeight(newHeight);

            isAdjusting = false;
        };

        // Listen to width changes only
        stage.widthProperty().addListener(sizeListener);

        // Block user attempts to resize height
        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            if (isAdjusting) return;
            isAdjusting = true;

            double correctHeight = stage.getWidth() / ASPECT_RATIO;
            stage.setHeight(correctHeight); // Force correct aspect

            isAdjusting = false;
        });

        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
