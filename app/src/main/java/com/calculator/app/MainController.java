package com.calculator.app;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public StackPane mobSelector;
    @FXML private VBox vBox;
    @FXML private GridPane gridPane;
    @FXML private ImageView background;
    @FXML private ImageView logo;
    @FXML private ImageView mobWindow;
    @FXML private ImageView mobWindowMob;
    @FXML private ImageView inventoryBg;

    @FXML private StackPane inventory;
    @FXML private Pane inventoryContainer;
    @FXML private VBox inventoryContent;
    @FXML private HBox armorSelector;
    @FXML private HBox armorInv;

    @FXML private HBox entityMobSelection;

    @FXML private Button zombieButton;
    @FXML private Button skeletonButton;

    @FXML private VBox buttonsvbox;

    @FXML private Button weaponButton;
    @FXML private Button entityButton;
    @FXML private Button armorButton;
    @FXML private Button optionsButton;

    private ToggleGroup helmetsGroup;
    private ToggleGroup chestplatesGroup;
    private ToggleGroup leggingsGroup;
    private ToggleGroup bootsGroup;

    Image zombie = new Image(getClass().getResource("/res/zombie.png").toExternalForm());
    Image skeleton = new Image(getClass().getResource("/res/skeleton.png").toExternalForm());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        background.setImage(new Image(getClass().getResource("/res/background_texture.png").toExternalForm()));
        logo.setImage(new Image(getClass().getResource("/res/logo.png").toExternalForm()));
        logo.fitHeightProperty().bind(vBox.heightProperty().multiply(0.25));

        generateUserButtons();

        setupMobSelectionBackground();

        generateMobEggs();

        generateInventory();

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

    private void generateInventory() {
        Image invBgImg = new Image(getClass().getResource("/res/inv_Window.png").toExternalForm());
        inventoryBg.setImage(invBgImg);
        double invRatio = invBgImg.getWidth() / invBgImg.getHeight();

        inventoryContent.prefWidthProperty().bind(inventoryContainer.widthProperty());
        inventoryContent.prefHeightProperty().bind(inventoryContainer.widthProperty().multiply(1/invRatio));

        inventoryContainer.widthProperty().addListener((obs, oldVal, newVal) -> centerVBox());
        inventoryContainer.heightProperty().addListener((obs, oldVal, newVal) -> centerVBox());
        inventoryContent.widthProperty().addListener((obs, oldVal, newVal) -> centerVBox());
        inventoryContent.heightProperty().addListener((obs, oldVal, newVal) -> centerVBox());
    }

    private void setupMobSelectionBackground() {
        Image entityMobselectionImage = new Image(getClass().getResource("/res/entityContainer.png").toExternalForm());
        double mobSelRatio = entityMobselectionImage.getWidth() / entityMobselectionImage.getHeight();

        BackgroundImage entityMobselectionBackgroundImage = new BackgroundImage(
                entityMobselectionImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false));

        Background entityMobselectionBackground = new Background(entityMobselectionBackgroundImage);

        entityMobSelection.setBackground(entityMobselectionBackground);
        entityMobSelection.maxWidthProperty().bind(mobSelector.widthProperty().multiply(0.6));
        entityMobSelection.maxHeightProperty().bind(mobSelector.widthProperty().multiply(0.6*(1/mobSelRatio)));

        mobWindow.setImage(new Image(getClass().getResource("/res/mobWindow.png").toExternalForm()));
        mobWindowMob.setImage(zombie);
        mobWindowMob.fitWidthProperty().bind(mobWindow.fitWidthProperty().multiply(0.6));
        mobWindowMob.fitHeightProperty().bind(mobWindow.fitHeightProperty().multiply(0.6));
    }

    private void generateMobEggs() {
        Image zombieSpawnEgg = new Image(getClass().getResource("/res/zombie_spawn_egg.png").toExternalForm());
        Image skeletonSpawnEgg = new Image(getClass().getResource("/res/skeleton_spawn_egg.png").toExternalForm());

        ImageView imageViewzombie = new ImageView(zombieSpawnEgg);
        imageViewzombie.setPreserveRatio(true);

        ImageView imageViewskeleton = new ImageView(skeletonSpawnEgg);
        imageViewskeleton.setPreserveRatio(true);

        zombieButton.setGraphic(imageViewzombie);
        skeletonButton.setGraphic(imageViewskeleton);

        DoubleBinding tileSize = Bindings.createDoubleBinding(() -> {
            double w = mobSelector.getWidth() / 6;
            double h = mobSelector.getHeight() / 6;
            return Math.min(w, h);
        }, mobSelector.widthProperty(), mobSelector.heightProperty());

        zombieButton.prefWidthProperty().bind(tileSize);
        zombieButton.prefHeightProperty().bind(tileSize);

        skeletonButton.prefWidthProperty().bind(tileSize);
        skeletonButton.prefHeightProperty().bind(tileSize);

        imageViewzombie.fitWidthProperty().bind(tileSize);
        imageViewzombie.fitHeightProperty().bind(tileSize);

        imageViewskeleton.fitWidthProperty().bind(tileSize);
        imageViewskeleton.fitHeightProperty().bind(tileSize);

        entityMobSelection.setVisible(false);

        skeletonButton.setOnAction(e ->{
            System.out.println("Skelett");
            mobWindowMob.setImage(skeleton);
        });

        zombieButton.setOnAction(e ->{
            System.out.println("Zombie");
            mobWindowMob.setImage(zombie);
        });
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

    private void generateUserButtons() {
        configureButtonWithImage(weaponButton, "/res/weaponButton.png", e -> {
            // Your weaponButton logic here
            System.out.println("Weapon button clicked");
        });

        configureButtonWithImage(entityButton, "/res/entityButton.png", e -> {
            System.out.println("Entity button clicked");
            entityMobSelection.setVisible(!entityMobSelection.isVisible());
        });

        configureButtonWithImage(armorButton, "/res/armorButton.png", e -> {
            // Your armorButton logic here
            System.out.println("Armor button clicked");
        });

        configureButtonWithImage(optionsButton, "/res/optionsButton.png", e -> {
            // Your optionsButton logic here
            System.out.println("Options button clicked");
        });
    }

    private void configureButtonWithImage(Button button, String imagePath, EventHandler<ActionEvent> onClick) {
        Image image = new Image(getClass().getResource(imagePath).toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);

        button.setGraphic(imageView);
        button.setStyle("-fx-background-color: transparent;");

        // Bind button size to its parent
        button.prefWidthProperty().bind(((Region) button.getParent()).widthProperty().multiply(0.9));
        button.prefHeightProperty().bind(((Region) button.getParent()).heightProperty().multiply(0.9));

        // Bind image size to button size
        imageView.fitWidthProperty().bind(button.prefWidthProperty());
        imageView.fitHeightProperty().bind(button.prefHeightProperty());

        // Set the button's action
        button.setOnAction(onClick);
    }
}
