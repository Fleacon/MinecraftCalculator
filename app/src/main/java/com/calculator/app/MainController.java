package com.calculator.app;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML private VBox vBox;
    @FXML private GridPane gridPane;
    @FXML private ImageView background;
    @FXML private ImageView logo;
    @FXML private ImageView mobWindow;
    @FXML private ImageView inventoryBg;

    @FXML private StackPane inventory;
    @FXML private Pane inventoryContainer;
    @FXML private VBox inventoryContent;
    @FXML private HBox armorSelector;
    @FXML private HBox armorInv;

    @FXML private VBox buttonsvbox;

    @FXML private Button imageButton1;
    @FXML private Button imageButton2;
    @FXML private Button imageButton3;
    @FXML private Button imageButton4;
    private ToggleGroup helmetsGroup;
    private ToggleGroup chestplatesGroup;
    private ToggleGroup leggingsGroup;
    private ToggleGroup bootsGroup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        background.setImage(new Image(getClass().getResource("/res/background_texture.png").toExternalForm()));
        logo.setImage(new Image(getClass().getResource("/res/logo.png").toExternalForm()));
        logo.fitHeightProperty().bind(vBox.heightProperty().multiply(0.25));



        mobWindow.setImage(new Image(getClass().getResource("/res/mobWindow.png").toExternalForm()));

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






        // Bild laden
        Image image1 = new Image(getClass().getResource("/res/weaponButton.png").toExternalForm());

        // ImageView vorbereiten
        ImageView imageView1 = new ImageView(image1);
        imageView1.setPreserveRatio(true);

        // Das Bild wird zunächst "normal" eingesetzt
        imageButton1.setGraphic(imageView1);

        // WICHTIG: Buttongröße an Parent binden
        imageButton1.prefWidthProperty().bind(((Region) imageButton1.getParent()).widthProperty().multiply(0.9));
        imageButton1.prefHeightProperty().bind(((Region) imageButton1.getParent()).heightProperty().multiply(0.9));

        // NACH Buttongröße → Bildgröße daran binden
        imageView1.fitWidthProperty().bind(imageButton1.prefWidthProperty());
        imageView1.fitHeightProperty().bind(imageButton1.prefHeightProperty());

        // Optional: Transparenter Buttonhintergrund
        //imageButton.setStyle("-fx-background-color: transparent;");



        // Bild laden
        Image image2 = new Image(getClass().getResource("/res/entityButton.png").toExternalForm());

        // ImageView vorbereiten
        ImageView imageView2 = new ImageView(image2);
        imageView2.setPreserveRatio(true);

        // Das Bild wird zunächst "normal" eingesetzt
        imageButton2.setGraphic(imageView2);

        // WICHTIG: Buttongröße an Parent binden
        imageButton2.prefWidthProperty().bind(((Region) imageButton2.getParent()).widthProperty().multiply(0.9));
        imageButton2.prefHeightProperty().bind(((Region) imageButton2.getParent()).heightProperty().multiply(0.9));

        // NACH Buttongröße → Bildgröße daran binden
        imageView2.fitWidthProperty().bind(imageButton2.prefWidthProperty());
        imageView2.fitHeightProperty().bind(imageButton2.prefHeightProperty());

        // Optional: Transparenter Buttonhintergrund
        //imageButton.setStyle("-fx-background-color: transparent;");



        // Bild laden
        Image image3 = new Image(getClass().getResource("/res/armorButton.png").toExternalForm());

        // ImageView vorbereiten
        ImageView imageView3 = new ImageView(image3);
        imageView3.setPreserveRatio(true);

        // Das Bild wird zunächst "normal" eingesetzt
        imageButton3.setGraphic(imageView3);

        // WICHTIG: Buttongröße an Parent binden
        imageButton3.prefWidthProperty().bind(((Region) imageButton3.getParent()).widthProperty().multiply(0.9));
        imageButton3.prefHeightProperty().bind(((Region) imageButton3.getParent()).heightProperty().multiply(0.9));

        // NACH Buttongröße → Bildgröße daran binden
        imageView3.fitWidthProperty().bind(imageButton3.prefWidthProperty());
        imageView3.fitHeightProperty().bind(imageButton3.prefHeightProperty());

        // Optional: Transparenter Buttonhintergrund
        //imageButton.setStyle("-fx-background-color: transparent;");



        // Bild laden
        Image image = new Image(getClass().getResource("/res/optionsButton.png").toExternalForm());

        // ImageView vorbereiten
        ImageView imageView4 = new ImageView(image);
        imageView4.setPreserveRatio(true);

        // Das Bild wird zunächst "normal" eingesetzt
        imageButton4.setGraphic(imageView4);

        // WICHTIG: Buttongröße an Parent binden
        imageButton4.prefWidthProperty().bind(((Region) imageButton4.getParent()).widthProperty().multiply(0.9));
        imageButton4.prefHeightProperty().bind(((Region) imageButton4.getParent()).heightProperty().multiply(0.9));

        // NACH Buttongröße → Bildgröße daran binden
        imageView4.fitWidthProperty().bind(imageButton4.prefWidthProperty());
        imageView4.fitHeightProperty().bind(imageButton4.prefHeightProperty());

        // Optional: Transparenter Buttonhintergrund
        //imageButton.setStyle("-fx-background-color: transparent;");



        generateArmorTiles(7, inventoryContent, armorInv, helmetsGroup);

        generateArmorButtons(inventoryContent, armorSelector);
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

    private void generateArmorTiles(int tileCount, Pane outerContainer, Pane innerContainer, ToggleGroup group) {
        Image basicTile = new Image(getClass().getResource("/res/tile.png").toExternalForm());

        double size = 8.0;
        DoubleBinding tileSize = Bindings.createDoubleBinding(() -> {
            double w = outerContainer.getWidth() / size;
            double h = outerContainer.getHeight() / size;
            return Math.min(w, h);
        }, outerContainer.widthProperty(), outerContainer.heightProperty());

        for (int i = 0; i < tileCount; i++) {
            ImageView image = new ImageView(); // Reuse same image

            image.setImage(basicTile);
            image.setPreserveRatio(true);
            image.setSmooth(false);

            image.fitWidthProperty().bind(tileSize);
            image.fitHeightProperty().bind(tileSize);

            RadioButton button = new RadioButton();
            button.setToggleGroup(group);
            button.setOpacity(0);
            button.setPadding(Insets.EMPTY);

            button.minWidthProperty().bind(tileSize);
            button.prefWidthProperty().bind(tileSize);
            button.maxWidthProperty().bind(tileSize);

            button.minHeightProperty().bind(tileSize);
            button.prefHeightProperty().bind(tileSize);
            button.maxHeightProperty().bind(tileSize);

            Group tile = new Group(image, button);
            innerContainer.getChildren().add(tile);
        }
    }

    private void generateArmorButtons(Pane outerContainer, Pane innerContainer) {
        ToggleGroup group = new ToggleGroup();

        Image helmetBtnImg = new Image(getClass().getResource("/res/helmetButton.png").toExternalForm());
        Image chestplateBtnImg = new Image(getClass().getResource("/res/chestplateButton.png").toExternalForm());
        Image leggingsBtnImg = new Image(getClass().getResource("/res/leggingsButton.png").toExternalForm());
        Image bootsBtnImg = new Image(getClass().getResource("/res/bootsButton.png").toExternalForm());

        Image armorSelectorImg = new Image(getClass().getResource("/res/armorSelector.png").toExternalForm());

        ArrayList<Image> imgList = new ArrayList<>();
        imgList.add(helmetBtnImg);
        imgList.add(chestplateBtnImg);
        imgList.add(leggingsBtnImg);
        imgList.add(bootsBtnImg);

        double size = 8.0;
        DoubleBinding tileSize = Bindings.createDoubleBinding(() -> {
            double w = outerContainer.getWidth() / size;
            double h = outerContainer.getHeight() / size;
            return Math.min(w, h);
        }, outerContainer.widthProperty(), outerContainer.heightProperty());

        for (Image img : imgList) {
            ImageView image = new ImageView();

            image.setImage(img);
            image.setPreserveRatio(true);
            image.setSmooth(false);

            image.fitWidthProperty().bind(tileSize);
            image.fitHeightProperty().bind(tileSize);

            ImageView selector = new ImageView();

            selector.setImage(armorSelectorImg);
            selector.setPreserveRatio(true);
            selector.setSmooth(false);
            selector.setOpacity(0);

            selector.fitWidthProperty().bind(tileSize);
            selector.fitHeightProperty().bind(tileSize);

            RadioButton button = new RadioButton();
            button.setToggleGroup(group);
            button.setOpacity(0);
            button.setPadding(Insets.EMPTY);

            button.minWidthProperty().bind(tileSize);
            button.prefWidthProperty().bind(tileSize);
            button.maxWidthProperty().bind(tileSize);

            button.minHeightProperty().bind(tileSize);
            button.prefHeightProperty().bind(tileSize);
            button.maxHeightProperty().bind(tileSize);

            button.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                if (isNowSelected) {
                    selector.setOpacity(1);
                } else {
                    selector.setOpacity(0);
                }
            });

            Group tile = new Group(image, button, selector);
            innerContainer.getChildren().add(tile);
        }
    }
}
