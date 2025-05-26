package com.calculator.app;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML private VBox vBox;
    @FXML private GridPane gridPane;
    @FXML private ImageView background;
    @FXML private ImageView logo;
    @FXML private ImageView inventoryBg;
    @FXML private StackPane inventory;
    @FXML private Pane inventoryContainer;
    @FXML private VBox inventoryContent;
    @FXML private HBox armorInv;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        background.setImage(new Image(getClass().getResource("/res/background_texture.png").toExternalForm()));
        logo.setImage(new Image(getClass().getResource("/res/logo.png").toExternalForm()));
        logo.fitHeightProperty().bind(vBox.heightProperty().multiply(0.25));

        Image invBgImg = new Image(getClass().getResource("/res/inv_Window.png").toExternalForm());
        inventoryBg.setImage(invBgImg);
        double ratio = invBgImg.getWidth() / invBgImg.getHeight();

        inventoryContent.prefWidthProperty().bind(inventoryContainer.widthProperty());
        inventoryContent.prefHeightProperty().bind(inventoryContainer.widthProperty().multiply(1/ratio));

        inventoryContainer.widthProperty().addListener((obs, oldVal, newVal) -> centerVBox());
        inventoryContainer.heightProperty().addListener((obs, oldVal, newVal) -> centerVBox());
        inventoryContent.widthProperty().addListener((obs, oldVal, newVal) -> centerVBox());
        inventoryContent.heightProperty().addListener((obs, oldVal, newVal) -> centerVBox());

    }

    private void centerVBox() {
        double containerWidth = inventoryContainer.getWidth();
        double containerHeight = inventoryContainer.getHeight();
        double contentWidth = inventoryContent.getWidth();
        double contentHeight = inventoryContent.getHeight();

        double x = (containerWidth - contentWidth) / 2;
        double y = (containerHeight - contentHeight) / 2;

        inventoryContent.setLayoutX(x);
        inventoryContent.setLayoutY(y);
    }

        Image sharedImage = new Image(getClass().getResource("/res/tile.png").toExternalForm());
        for (int i = 0; i < 2; i++) {
            ImageView image = new ImageView(); // Reuse same image
            image.maxWidth(16);
            image.maxHeight(16);
            image.setImage(sharedImage);
            image.setPreserveRatio(true);

            Button button = new Button();
            button.setOpacity(0);
            button.prefWidthProperty().bind(image.fitWidthProperty());
            button.prefHeightProperty().bind(image.fitHeightProperty());

            StackPane tileButton = new StackPane();
            tileButton.getChildren().addAll(image, button);

            armorInv.getChildren().add(tileButton);
        }
    }
}
