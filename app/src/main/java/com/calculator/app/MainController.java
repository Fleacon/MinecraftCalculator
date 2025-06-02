package com.calculator.app;

import dao.RüstungDAO;
import javafx.animation.PauseTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Rüstung;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;



import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class MainController implements Initializable {
    @FXML private VBox vBox;
    @FXML private GridPane gridPane;
    @FXML private ImageView background;
    @FXML private ImageView logo;

    @FXML public StackPane mobSelector;
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
    @FXML public Button calculateButton;

    @FXML private Label trollText;

    public boolean ka = true;

    private ToggleGroup armorSelectionGroup = new ToggleGroup();
    private ArrayList<Group> helmets;
    private ArrayList<Group> chestplates;
    private ArrayList<Group> leggings;
    private ArrayList<Group> boots;
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

        RüstungDAO rüstungDAO = new RüstungDAO();

        trollText.setVisible(false);

        optionsButton.setOnAction(e ->{
            if (ka) {
                ka = false;
                PauseTransition pause2 = new PauseTransition(Duration.seconds(1));
                pause2.setOnFinished(event2 -> {
                    trollText.setVisible(true);
                    Stage stage = (Stage) optionsButton.getScene().getWindow();
                    Media sound = new Media(getClass().getResource("/res/ehhehehe.mp3").toExternalForm());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    PauseTransition pause = new PauseTransition(Duration.seconds(2.5));
                    pause.setOnFinished(event -> {
                        stage.close();
                    });
                    pause.play();
                });
                pause2.play();
            }
        });


        try {
            helmets = generateArmorTiles(rüstungDAO.getAllRüstungByTyp("Helm"), inventoryContent, helmetsGroup);
            chestplates = generateArmorTiles(rüstungDAO.getAllRüstungByTyp("Brustplatte"), inventoryContent, chestplatesGroup);
            leggings = generateArmorTiles(rüstungDAO.getAllRüstungByTyp("Hose"), inventoryContent, leggingsGroup);
            boots = generateArmorTiles(rüstungDAO.getAllRüstungByTyp("Schuhe"), inventoryContent, bootsGroup);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        armorSelectionGroup.selectedToggleProperty().addListener((obs, oldSel, newSel) -> {
            armorInv.getChildren().clear();
            switch (armorSelectionGroup.getToggles().indexOf(armorSelectionGroup.getSelectedToggle())) {
                case 0:
                    armorInv.getChildren().addAll(helmets);
                    break;
                case 1:
                    armorInv.getChildren().addAll(chestplates);
                    break;
                case 2:
                    armorInv.getChildren().addAll(leggings);
                    break;
                case 3:
                    armorInv.getChildren().addAll(boots);
                    break;
                default:
                    System.out.println("nothing there :(");
                    break;
            }
        });

        generateArmorButtons(inventoryContent, armorSelector, armorSelectionGroup);
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

    private ArrayList<Group> generateArmorTiles(ArrayList<Rüstung> rüstungen, Pane outerContainer, ToggleGroup group) {
        ArrayList<Group> armorType= new ArrayList<Group>();
        Image basicTile = new Image(getClass().getResource("/res/tile.png").toExternalForm());

        double size = 8.0;
        DoubleBinding tileSize = Bindings.createDoubleBinding(() -> {
            double w = outerContainer.getWidth() / size;
            double h = outerContainer.getHeight() / size;
            return Math.min(w, h);
        }, outerContainer.widthProperty(), outerContainer.heightProperty());

        for (Rüstung rüstung : rüstungen) {
            ImageView tileImage = new ImageView(); // Reuse same tileImage

            tileImage.setImage(basicTile);
            tileImage.setPreserveRatio(true);
            tileImage.setSmooth(false);

            tileImage.fitWidthProperty().bind(tileSize);
            tileImage.fitHeightProperty().bind(tileSize);

            ImageView rüstungImage = new ImageView(new Image(getClass().getResource(rüstung.getTextur()).toExternalForm()));
            rüstungImage.setPreserveRatio(true);
            rüstungImage.setSmooth(false);

            rüstungImage.fitWidthProperty().bind(tileSize);
            rüstungImage.fitHeightProperty().bind(tileSize);

            tileImage.fitWidthProperty().bind(tileSize);
            tileImage.fitHeightProperty().bind(tileSize);

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

            Group tile = new Group(tileImage, rüstungImage, button);
            armorType.add(tile);
        }
        return armorType;
    }

    private void generateArmorButtons(Pane outerContainer, Pane innerContainer, ToggleGroup group) {
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

        configureButtonWithImage(calculateButton, "/res/calculateButton.png", e -> {
            System.out.println("Calculate button clicked");
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
