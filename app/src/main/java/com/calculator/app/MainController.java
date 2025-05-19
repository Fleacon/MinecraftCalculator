package com.calculator.app;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML private BorderPane borderPane;
    @FXML private ImageView background;
    @FXML private ImageView logo;
    @FXML private ImageView panel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        background.setImage(new Image(getClass().getResource("/res/background_texture.png").toExternalForm()));
        background.setSmooth(true);
        logo.setImage(new Image(getClass().getResource("/res/logo.png").toExternalForm()));
        logo.setSmooth(true);
        panel.setImage(new Image(getClass().getResource("/res/window_texture.png").toExternalForm()));
        panel.setSmooth(true);


        borderPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.widthProperty().addListener((o, oldVal, newVal) -> {
                    logo.setFitWidth(newVal.doubleValue() * 0.5);
                    panel.setFitWidth(newVal.doubleValue() * 0.55);
                });
            }
        });
    }
}
