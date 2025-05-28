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

    @FXML private ImageView button1;
    @FXML private ImageView button2;
    @FXML private ImageView button3;
    @FXML private ImageView button4;

    @FXML private StackPane inventory;
    @FXML private Pane inventoryContainer;
    @FXML private VBox inventoryContent;
    @FXML private HBox armorInv;

    @FXML private Pane paneWaponbuttom;
    @FXML private Pane entityWaponbuttom;
    @FXML private Pane armorWaponbuttom;
    @FXML private Pane optionsWaponbuttom;

    @FXML private Button button11;
    @FXML private Button button21;
    @FXML private Button button31;
    @FXML private Button button41;

    @FXML private VBox buttonsvbox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        background.setImage(new Image(getClass().getResource("/res/background_texture.png").toExternalForm()));
        logo.setImage(new Image(getClass().getResource("/res/logo.png").toExternalForm()));
        logo.fitHeightProperty().bind(vBox.heightProperty().multiply(0.25));

        button1.setImage(new Image(getClass().getResource("/res/weaponButton.png").toExternalForm()));
        button2.setImage(new Image(getClass().getResource("/res/entityButton.png").toExternalForm()));
        button3.setImage(new Image(getClass().getResource("/res/armorButton.png").toExternalForm()));
        button4.setImage(new Image(getClass().getResource("/res/optionsButton.png").toExternalForm()));

        Image invBgImg = new Image(getClass().getResource("/res/inv_Window.png").toExternalForm());
        inventoryBg.setImage(invBgImg);
        double ratio = invBgImg.getWidth() / invBgImg.getHeight();

        inventoryContent.prefWidthProperty().bind(inventoryContainer.widthProperty());
        inventoryContent.prefHeightProperty().bind(inventoryContainer.widthProperty().multiply(1/ratio));

        inventoryContainer.widthProperty().addListener((obs, oldVal, newVal) -> centerVBox());
        inventoryContainer.heightProperty().addListener((obs, oldVal, newVal) -> centerVBox());
        inventoryContent.widthProperty().addListener((obs, oldVal, newVal) -> centerVBox());
        inventoryContent.heightProperty().addListener((obs, oldVal, newVal) -> centerVBox());
        int i = 7;
        generateTiles(i);

        paneWaponbuttom.prefWidthProperty().bind(paneWaponbuttom.widthProperty());
        entityWaponbuttom.prefWidthProperty().bind(entityWaponbuttom.widthProperty());
        armorWaponbuttom.prefWidthProperty().bind(armorWaponbuttom.widthProperty());
        optionsWaponbuttom.prefWidthProperty().bind(optionsWaponbuttom.widthProperty());


        button21.prefWidthProperty().bind(button2.fitWidthProperty());
        button31.prefWidthProperty().bind(button3.fitWidthProperty());
        button41.prefWidthProperty().bind(button4.fitWidthProperty());

        double ratiobuttons = 80/392;


        button21.prefHeightProperty().bind(button2.fitWidthProperty().multiply(ratiobuttons));
        button31.prefHeightProperty().bind(button3.fitWidthProperty().multiply(ratiobuttons));
        button41.prefHeightProperty().bind(button4.fitWidthProperty().multiply(ratiobuttons));




        button11.prefWidthProperty().bind(button1.fitWidthProperty());
        button11.autosize();


        //button11.minHeightProperty().bind(buttonsvbox.widthProperty().multiply(ratiobuttons));

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

    private void generateTiles(int tileCount) {
        Image sharedImage = new Image(getClass().getResource("/res/tile.png").toExternalForm());

        double size = 8.0;
        DoubleBinding tileSize = Bindings.createDoubleBinding(() -> {
            double w = inventoryContent.getWidth() / size;
            double h = inventoryContent.getHeight() / size;
            return Math.min(w, h);
        }, inventoryContent.widthProperty(), inventoryContent.heightProperty());

        for (int i = 0; i < tileCount; i++) {
            ImageView image = new ImageView(); // Reuse same image

            image.setImage(sharedImage);
            image.setPreserveRatio(true);

            image.fitWidthProperty().bind(tileSize);
            image.fitHeightProperty().bind(tileSize);

            Button button = new Button();
            button.setOpacity(0);
            button.setPadding(Insets.EMPTY);

            button.minWidthProperty().bind(tileSize);
            button.prefWidthProperty().bind(tileSize);
            button.maxWidthProperty().bind(tileSize);

            button.minHeightProperty().bind(tileSize);
            button.prefHeightProperty().bind(tileSize);
            button.maxHeightProperty().bind(tileSize);

            Group tile = new Group(image, button);
            armorInv.getChildren().add(tile);
        }
    }
}
