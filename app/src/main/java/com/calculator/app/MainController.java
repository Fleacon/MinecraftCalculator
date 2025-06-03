package com.calculator.app;

import dao.RüstungDAO;
import dao.WaffeDAO;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Rüstung;
import model.Waffe;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.PauseTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class MainController implements Initializable {
    @FXML private VBox vBox;
    @FXML private GridPane gridPane;
    @FXML private ImageView background;
    @FXML private ImageView logo;

    @FXML private StackPane mobSelector;
    @FXML private ImageView mobWindow;
    @FXML private ImageView mobWindowMob;
    @FXML private ImageView inventoryBg;

    @FXML private StackPane armorBar;
    @FXML private WrappedImageView armorBarBg;
    @FXML private VBox armorBarContent;

    @FXML private StackPane inventory;
    @FXML private Pane inventoryContainer;
    @FXML private VBox inventoryContent;
    private HBox armorSelector = new HBox();
    private HBox armorInv = new HBox();

    private TilePane weaponInv = new TilePane();

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
    private ArrayList<Rüstung> helmetsModels = new ArrayList<>();
    private ArrayList<Rüstung> chestplatesModels = new ArrayList<>();
    private ArrayList<Rüstung> leggingsModels = new ArrayList<>();
    private ArrayList<Rüstung> bootsModels = new ArrayList<>();
    private ToggleGroup helmetsGroup = new ToggleGroup();
    private ToggleGroup chestplatesGroup = new ToggleGroup();
    private ToggleGroup leggingsGroup = new ToggleGroup();
    private ToggleGroup bootsGroup = new ToggleGroup();

    private ArrayList<Group> weapons = new ArrayList<>();
    private ArrayList<Waffe> weaponsModels = new ArrayList<>();
    private ToggleGroup weaponsGroup = new ToggleGroup();

    ArrayList<ImageView> overlayViews = new ArrayList<ImageView>();
    Image zombie = new Image(getClass().getResource("/res/zombie.png").toExternalForm());
    Image skeleton = new Image(getClass().getResource("/res/skeleton.png").toExternalForm());

    private MainConnector connector = new MainConnector();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        background.setImage(new Image(getClass().getResource("/res/background_texture.png").toExternalForm()));
        logo.setImage(new Image(getClass().getResource("/res/logo.png").toExternalForm()));
        logo.fitHeightProperty().bind(vBox.heightProperty().multiply(0.25));

        armorSelector.setAlignment(Pos.CENTER);
        armorSelector.setMinHeight(75);
        armorSelector.setSpacing(5);

        armorInv.setSpacing(0);
        armorInv.setAlignment(Pos.CENTER);
        armorInv.setStyle("-fx-border-color: black");
        VBox.setVgrow(armorInv, Priority.ALWAYS);

        weaponInv.setAlignment(Pos.CENTER);
        weaponInv.setStyle("-fx-border-color: black");
        weaponInv.setPrefColumns(6);
        VBox.setVgrow(weaponInv, Priority.ALWAYS);

        generateUserButtons();

        setupMobSelectionBackground();

        generateArmorBar(armorBar, armorBarContent);

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
            helmetsModels = rüstungDAO.getAllRüstungByTyp("Helm");
            chestplatesModels = rüstungDAO.getAllRüstungByTyp("Brustplatte");
            leggingsModels = rüstungDAO.getAllRüstungByTyp("Hose");
            bootsModels = rüstungDAO.getAllRüstungByTyp("Schuhe");

            helmets = generateArmorTiles(helmetsModels, inventoryContent, helmetsGroup);
            chestplates = generateArmorTiles(chestplatesModels, inventoryContent, chestplatesGroup);
            leggings = generateArmorTiles(leggingsModels, inventoryContent, leggingsGroup);
            boots = generateArmorTiles(bootsModels, inventoryContent, bootsGroup);

            setupArmorGroupListeners();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        WaffeDAO waffeDAO = new WaffeDAO();

        try {
            weaponsModels = waffeDAO.getAllWaffen();
            weapons = generateWeaponTiles(weaponsModels, inventoryContent, weaponsGroup);

            setupWeaponGroupListener(weaponsGroup, weaponsModels);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        weaponInv.getChildren().addAll(weapons);

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
        inventoryBg.setFitHeight(0);
        inventoryBg.setFitWidth(0);
        double invRatio = invBgImg.getWidth() / invBgImg.getHeight();

        inventoryContent.prefWidthProperty().bind(inventoryContainer.widthProperty());
        inventoryContent.prefHeightProperty().bind(inventoryContainer.widthProperty().multiply(1/invRatio));

        inventoryContainer.widthProperty().addListener((obs, oldVal, newVal) -> centerVBox());
        inventoryContainer.heightProperty().addListener((obs, oldVal, newVal) -> centerVBox());
        inventoryContent.widthProperty().addListener((obs, oldVal, newVal) -> centerVBox());
        inventoryContent.heightProperty().addListener((obs, oldVal, newVal) -> centerVBox());
    }

    private void generateArmorBar(Pane outerContainer, Pane innerContainer) {
        Image armorBarImg = new Image(getClass().getResource("/res/armorBar.png").toExternalForm());

        armorBarBg.setImage(armorBarImg);

        Image tileImg = new Image(getClass().getResource("/res/tile.png").toExternalForm());

        double size = 1.5;
        DoubleBinding tileSize = Bindings.createDoubleBinding(() -> {
            double w = outerContainer.getWidth() / size;
            double h = outerContainer.getHeight() / size;
            return Math.min(w, h);
        }, outerContainer.widthProperty(), outerContainer.heightProperty());

        Image headOverlay = new Image(getClass().getResource("/res/headOverlay.png").toExternalForm());
        Image chestOverlay = new Image(getClass().getResource("/res/chestOverlay.png").toExternalForm());
        Image legsOverlay = new Image(getClass().getResource("/res/pantsOverlay.png").toExternalForm());
        Image bootsOverlay = new Image(getClass().getResource("/res/bootsOverlay.png").toExternalForm());

        ArrayList<Image> list = new ArrayList<>();
        list.add(headOverlay);
        list.add(chestOverlay);
        list.add(legsOverlay);
        list.add(bootsOverlay);

        for (Image img : list) {
            ImageView basicTile = new ImageView();

            basicTile.setImage(tileImg);
            basicTile.setPreserveRatio(true);
            basicTile.setSmooth(false);

            basicTile.fitWidthProperty().bind(tileSize);
            basicTile.fitHeightProperty().bind(tileSize);

            ImageView overlay = new ImageView(img);
            overlay.setPreserveRatio(true);
            overlay.setSmooth(false);

            overlay.fitWidthProperty().bind(tileSize);
            overlay.fitHeightProperty().bind(tileSize);

            overlayViews.add(overlay);

            Group tile = new Group(basicTile, overlay);
            innerContainer.getChildren().add(tile);
        }
    }

    private void setupArmorGroupListeners() {
        setupOverlayListener(helmetsGroup, helmetsModels, "/res/headOverlay.png", 0);    // Head
        setupOverlayListener(chestplatesGroup, chestplatesModels, "/res/chestOverlay.png",1); // Chest
        setupOverlayListener(leggingsGroup, leggingsModels, "/res/pantsOverlay.png", 2);     // Legs
        setupOverlayListener(bootsGroup, bootsModels, "/res/bootsOverlay.png", 3);       // Boots
    }

    private void setupOverlayListener(ToggleGroup group, ArrayList<Rüstung> rüstungen, String oldOverlayPath, int index) {
        List<Toggle> toggles = group.getToggles();

        for (int i = 0; i < toggles.size(); i++) {
            final int toggleIndex = i;
            Toggle toggle = toggles.get(i);

            // Extra MouseEvent Listener: doppelt geklickt auf das gleiche Toggle
            if (toggle instanceof Node) {
                ((Node) toggle).addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
                    if (group.getSelectedToggle() == toggle) {
                        int selectedIndex = group.getToggles().indexOf(toggle);
                        if (selectedIndex >= 0 && selectedIndex < rüstungen.size()) {
                            Rüstung rüstung = rüstungen.get(selectedIndex);
                            switch (rüstung.getKörperteil()) {
                                case "Helm":
                                    connector.setHelmet(null);
                                    break;
                                case "Brustplatte":
                                    connector.setChestplate(null);
                                    break;
                                case "Hose":
                                    connector.setLeggings(null);
                                    break;
                                case "Schuhe":
                                    connector.setBoots(null);
                                    break;
                            }
                        }
                        // Deselektiere den Toggle
                        group.selectToggle(null);
                        // Setze das alte Overlay zurück
                        Image oldOverlay = new Image(getClass().getResource(oldOverlayPath).toExternalForm());
                        overlayViews.get(index).setImage(oldOverlay);
                        event.consume(); // Verhindert, dass der Toggle erneut aktiviert wird
                    }
                });
            }
        }

        // Standard-Selection-Change-Listener
        group.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null && index < overlayViews.size()) {
                int selectedIndex = group.getToggles().indexOf(newToggle);
                if (selectedIndex >= 0 && selectedIndex < rüstungen.size()) {
                    Rüstung rüstung = rüstungen.get(selectedIndex);
                    switch (rüstung.getKörperteil()) {
                        case "Helm":
                            connector.setHelmet(rüstung);
                            break;
                        case "Brustplatte":
                            connector.setChestplate(rüstung);
                            break;
                        case "Hose":
                            connector.setLeggings(rüstung);
                            break;
                        case "Schuhe":
                            connector.setBoots(rüstung);
                            break;
                    }
                    Image newOverlay = new Image(getClass().getResource(rüstung.getTextur()).toExternalForm());
                    overlayViews.get(index).setImage(newOverlay);
                }
            }
        });
    }

    private void setupWeaponGroupListener(ToggleGroup group, ArrayList<Waffe> waffen) {
        List<Toggle> toggles = group.getToggles();

        for (int i = 0; i < toggles.size(); i++) {
            final int toggleIndex = i;
            Toggle toggle = toggles.get(i);

            if (toggle instanceof Node) {
                ((Node) toggle).addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
                    if (group.getSelectedToggle() == toggle) {
                        group.selectToggle(null);
                        connector.setWeapon(null);
                        event.consume();
                    }
                });
            }
        }

        // Toggle-Wechsel Listener
        group.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                int selectedIndex = group.getToggles().indexOf(newToggle);
                if (selectedIndex >= 0 && selectedIndex < waffen.size()) {
                    Waffe waffe = waffen.get(selectedIndex);

                    connector.setWeapon(waffe); // oder: handleWeaponSelected(waffe);
                }
            }
        });
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

        Image armorSelectorImg = new Image(getClass().getResource("/res/armorSelector.png").toExternalForm());

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

            ImageView selector = new ImageView();

            selector.setImage(armorSelectorImg);
            selector.setPreserveRatio(true);
            selector.setSmooth(false);
            selector.setOpacity(0);

            selector.fitWidthProperty().bind(tileSize);
            selector.fitHeightProperty().bind(tileSize);

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

            Group tile = new Group(tileImage, selector, rüstungImage, button);
            armorType.add(tile);

            button.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                if (isNowSelected) {
                    selector.setOpacity(1);
                } else {
                    selector.setOpacity(0);
                }
            });
        }
        return armorType;
    }

    private ArrayList<Group> generateWeaponTiles(ArrayList<Waffe> waffen, Pane outerContainer, ToggleGroup group) {
        ArrayList<Group> weaponList = new ArrayList<Group>();
        Image basicTile = new Image(getClass().getResource("/res/tile.png").toExternalForm());

        Image armorSelectorImg = new Image(getClass().getResource("/res/armorSelector.png").toExternalForm());

        double size = 8.0;
        DoubleBinding tileSize = Bindings.createDoubleBinding(() -> {
            double w = outerContainer.getWidth() / size;
            double h = outerContainer.getHeight() / size;
            return Math.min(w, h);
        }, outerContainer.widthProperty(), outerContainer.heightProperty());

        weaponInv.prefWidthProperty().bind(tileSize.multiply(7));

        for (Waffe waffe : waffen) {
            ImageView tileImage = new ImageView(); // Reuse same tileImage

            tileImage.setImage(basicTile);
            tileImage.setPreserveRatio(true);
            tileImage.setSmooth(false);

            tileImage.fitWidthProperty().bind(tileSize);
            tileImage.fitHeightProperty().bind(tileSize);

            ImageView rüstungImage = new ImageView(new Image(getClass().getResource(waffe.getTextur()).toExternalForm()));
            rüstungImage.setPreserveRatio(true);
            rüstungImage.setSmooth(false);

            rüstungImage.fitWidthProperty().bind(tileSize);
            rüstungImage.fitHeightProperty().bind(tileSize);

            tileImage.fitWidthProperty().bind(tileSize);
            tileImage.fitHeightProperty().bind(tileSize);

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

            Group tile = new Group(tileImage, selector, rüstungImage, button);
            weaponList.add(tile);

            button.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                if (isNowSelected) {
                    selector.setOpacity(1);
                } else {
                    selector.setOpacity(0);
                }
            });
        }
        return weaponList;
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
            inventoryContent.getChildren().clear();
            inventoryContent.getChildren().addAll(weaponInv);
        });

        configureButtonWithImage(entityButton, "/res/entityButton.png", e -> {
            System.out.println("Entity button clicked");
            entityMobSelection.setVisible(!entityMobSelection.isVisible());
        });

        configureButtonWithImage(armorButton, "/res/armorButton.png", e -> {
            // Your armorButton logic here
            System.out.println("Armor button clicked");
            inventoryContent.getChildren().clear();
            inventoryContent.getChildren().addAll(armorSelector, armorInv);
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
        imageView.setFitWidth(0);
        imageView.setFitHeight(0);
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
