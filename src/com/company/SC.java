package com.company;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileNotFoundException;

public class SC extends Application {
    private static final int STAGE_MIN_WIDTH = 640;
    private static final int STAGE_MIN_HEIGHT = 480;
    private static final int STAGE_MAX_WIDTH = 640;
    private static final int STAGE_MAX_HEIGHT = 480;
    private static final int BUTTON_MIN_WIDTH = 50;
    private static final int BUTTON_MIN_HEIGHT = 30;
    private static final int BUTTON_MAX_WIDTH = 1200;
    private static final int BUTTON_MAX_HEIGHT = 1000;
    private static final int TABLEVIEW_MIN_WIDTH = 30;
    private static final int TABLEVIEW_MIN_HEIGHT = 20;
    private static final int TABLEVIEW_MAX_WIDTH = 1000;
    private static final int TABLEVIEW_MAX_HEIGHT = 900;
    //private static boolean exit = false;

    //************************************************************************************************* TableView

    public static TableView uniTableView() {
        TableView tableView = new TableView();

        //tableView.setPrefWidth(999);

        tableView.setMinWidth(TABLEVIEW_MIN_WIDTH);
        tableView.setMinHeight(TABLEVIEW_MIN_HEIGHT);
        tableView.setMaxWidth(TABLEVIEW_MAX_WIDTH);
        tableView.setMaxHeight(TABLEVIEW_MAX_HEIGHT);

        //tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //tableViewRoom.setPadding(new Insets(10, 10, 10, 10));


        return tableView;
    }

    //*** GRID *********************************************************************************************************

    public static GridPane uniGrid() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(8);
        grid.setVgap(10);
        return grid;
    }

    //*** STAGE ********************************************************************************************************

    public static Stage stageExitConfirm(Stage stageToClose) {
        ////////////
        /*Group root = new Group();
        primarystage.setTitle("Color Example");
        Rectangle rect = new Rectangle();
        rect.setX(50);
        rect.setY(20);
        rect.setWidth(200);
        rect.setHeight(250);
        rect.setEffect(new DropShadow());
        root.getChildren().add(rect);
        Scene scene = new Scene(root,300,400,Color.hsb(180, 1, 1));
        primarystage.setScene(scene);
        primarystage.show();*/

        /////////
        Stage stage = stageApplicationModal("You are about to exit application!", 222, 222);
        HBox hBox = new HBox();
        Scene scene = new Scene(hBox);
        ImageView imageView = imageCover("cb");
        Button button = new Button("Icon");
        Button button1 = new Button("Icon");
        Button buttonYes = uniButton("Yes", 88, 33);
        Button buttonNo = uniButton("No", 88, 33);

        buttonYes.setStyle("" +
                "-fx-background-color: #1f2e2e;" +
                "-fx-text-fill: #ff0000;" +
                "-fx-font-size: 2em; " +
                "-fx-border-color: #000000;" +
                "-fx-border-width: 1px;");
        buttonNo.setStyle("" +
                "-fx-background-color: #1f2e2e;" +
                "-fx-text-fill: #00cc00;" +
                "-fx-font-size: 2em; " +
                "-fx-border-color: #000000;" +
                "-fx-border-width: 1px;");


        buttonYes.setOnAction(e -> {
            stageToClose.close();
            stage.close();
        });
        buttonNo.setOnAction(e -> stage.close());

        hBox.setAlignment(Pos.CENTER);
        uniLabel("Are you sure you want to exit?");
        hBox.getChildren().addAll(buttonYes, buttonNo);

        stage.setScene(scene);
        stage.show();
        return stage;
    }

    public static Stage stageApplicationModal(String title, int minWidth, int minHeight) {
        Stage stage = uniStageMain(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        //stage.initStyle(StageStyle.UTILITY);
        //stage.initStyle(StageStyle.UNIFIED);
        //stage.initStyle(StageStyle.DECORATED);
        stage.setWidth(minWidth);
        stage.setHeight(minHeight);
        stage.setMaxWidth(STAGE_MAX_WIDTH);
        stage.setMaxHeight(STAGE_MAX_HEIGHT);
        stage.setTitle(title);
        stage.initStyle(StageStyle.TRANSPARENT);
        return stage;
    }

    public static Stage uniStageMain(String title /*, int width, int height*/) {
        Stage stage = new Stage();

        /*stage.setTitle(title);
        stage.setWidth(STAGE_MIN_WIDTH);
        stage.setHeight(STAGE_MIN_HEIGHT);
        stage.setMaxWidth(STAGE_MAX_WIDTH);
        stage.setMaxHeight(STAGE_MAX_HEIGHT);*/

        stage.initStyle(StageStyle.TRANSPARENT);

        //window.show();
        return stage;
    }

    public static Stage uniStageMainOrri(String title /*, int width, int height*/) {
        Stage stage = new Stage();

        stage.setTitle(title);
        stage.setWidth(STAGE_MIN_WIDTH);
        stage.setHeight(STAGE_MIN_HEIGHT);
        stage.setMaxWidth(STAGE_MAX_WIDTH);
        stage.setMaxHeight(STAGE_MAX_HEIGHT);

        stage.initStyle(StageStyle.TRANSPARENT);

        //window.show();
        return stage;
    }

    public static Stage uniStageAlert(String msg /*, int width, int height*/) {
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);

        Label label = uniLabel("");


        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        stage.setTitle(msg);
        stage.setMinWidth(222);
        stage.setMinHeight(111);
        stage.setMaxWidth(555);
        stage.setMaxHeight(444);

        Button buttonOk = uniButton(msg);
        buttonOk.setOnAction(e -> stage.close());

        grid.add(label, 1, 0);
        grid.add(buttonOk, 1, 2);

        stage.setScene(scene);
        stage.show();
        return stage;
    }

    public static void stageXYPosition(Stage stage, double xPosition, double yPosition) {
        stage.setX(xPosition);
        stage.setY(yPosition);
    }

    public static void stageSetWidthAndHeight(Stage stage, double setStageWidth, double setStateHeight) {
        stage.setMaxWidth(setStageWidth);
        stage.setHeight(setStateHeight);
    }

    public static void stageTitle(String setStageTitle, Stage stage) {
        stage.setTitle(setStageTitle);
    }

    //** BUTTON ********************************************************************************************************

    public static Button buttonCloseCurrentWindow(Stage stageToClose, String txt, int width, int height) {
        Button button = uniButton(txt, width, height);
        //button.setOnAction(e -> stageToClose.close());
        button.setOnAction(e -> stageExitConfirm(stageToClose));
        return button;
    }

    public static BorderPane buttonChangeScene(BorderPane borderPaneToChange, String txt, int width, int height) {
        BorderPane borderPane = borderPaneToChange;
        Button button = uniButton(txt, width, height);
        //button.setOnAction();
        return borderPane;
    }

    /*
    public static Button buttonChangeScene(VBox vBoxToLoad, String txt, int width, int height ){
        VBox vBox = vBoxToLoad;
        Button button = uniButton(txt, width, height );
        //button.setOnAction(e -> vBoxToLoad );
        return button;
    }
     */

    public static Boolean buttonConfirm(String msg) {
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);

        Label label = uniLabel(msg);

        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        stage.setWidth(222);
        stage.setHeight(100);
        stage.setMaxWidth(222);
        stage.setMaxHeight(100);

        Button buttonYes = uniButton("Yes", 55, 33);
        buttonYes.setOnAction(e -> {
            stage.close();
            //return true;
        });

        Button buttonNo = uniButton("No", 55, 33);
        buttonNo.setOnAction(e -> {
            stage.close();
            //return false;
        });

        grid.add(buttonYes, 1, 1);
        grid.add(buttonNo, 1, 3);

        stage.setScene(scene);
        stage.show();
        return false;
    }

    public static Button uniButton(String txt, int width, int height /*, int posX, int posY */) {
        /*
        button.setStyle("-fx-border-color: #ff0000; -fx-border-width: 5px;");
        buttonYes.setStyle("-fx-background-color: #00ff00");
        buttonNo.setStyle("-fx-font-size: 2em; ");
        button1.setStyle("-fx-text-fill: #0000ff");
         */
        Button button = new Button(txt);
        button.setMinWidth(width);
        button.setMinHeight(height);
        button.setLayoutX(100);
        return button;
    }

    public static Button uniButton(String txt) {
        Button button = new Button(txt);

        button.setStyle("-fx-border-color: #ff0000; -fx-border-width: 5px;");
        button.setStyle("-fx-background-color: #00ff00");
        button.setStyle("-fx-font-size: 2em; ");
        button.setStyle("-fx-text-fill: #0000ff");
        //button.setStyle(" -fx-stroke-width: 5");

        //button.setPadding( new Insets( 10, 44, 10, 44 ));
        button.setWrapText(true);
        button.setTextAlignment(TextAlignment.CENTER);
        button.setMinWidth(BUTTON_MIN_WIDTH);
        button.setMinHeight(BUTTON_MIN_HEIGHT);
        button.setMaxWidth(BUTTON_MAX_WIDTH);
        button.setMaxHeight(BUTTON_MAX_HEIGHT);
        //button.setLayoutX(100);
        return button;
    }

    //** LABEL *********************************************************************************************************

    public static Label uniLabel(String txt) {
        Label label = new Label(txt);
        label.setWrapText(true);
        return label;
    }

    //** SCEN **********************************************************************************************************

    public static Scene uniScene(GridPane grid) {
        //uniScene(GridPane grid, int i, int j)
        Scene scene = new Scene(grid, 800, 600);
        return scene;
    }

    //** VBOX **********************************************************************************************************

    public static VBox uniVBox() {
        VBox vBox = new VBox();
        return vBox;
    }

    //** IMAGE *********************************************************************************************************

    public static ImageView imageCover(String imageName) {
        Image image = new Image("file:img/" + imageName + ".jpg");
        ImageView imageView = new ImageView(image);

        imageView.fitHeightProperty();
        //imageView.setRotate(90);
        //imageView.setScaleX(0); //100%

        return imageView;
    }

    private void imageScene() {
        File file = new File("/img/");
        //List<String> lines = Files.readAllLines( "/img/cb.jpg");
        String[] images = {"", "", "", "", "", "", "", "", ""};
        Stage stage = new Stage();
        VBox parent = new VBox();
        Scene scene = new Scene(parent);
        Image image = new Image("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcT31irsXvYgFx9D0gfrdnEKeiwRRFgq9l0NMCwTjjHgkXZKU8ga");
        ImageView imageView = new ImageView(image);
        imageView.setRotate(90);
        imageView.setScaleX(1); //100%
        stage.setWidth(333);
        stage.setHeight(333);

        parent.getChildren().addAll(imageView);
        stage.setScene(scene);
        stage.show();
    }

    private void imagesScene() throws FileNotFoundException {
        Image image = new Image("file:img/solid_box_black.jpg");
        Stage stage = new Stage();
        VBox vbox = new VBox();
        ImageView imageView = new ImageView();
        Scene scene = new Scene(vbox, 200, 100);

        imageView.setImage(image);
        imageView.preserveRatioProperty();
        imageView.setFitHeight(111);
        imageView.setFitWidth(111);
        imageView.setX(111);
        imageView.setY(111);

        stage.setWidth(333);
        stage.setHeight(333);

        vbox.getChildren().addAll(imageView);
        stage.setScene(scene);
        stage.show();
    }

    public void start(Stage stage) throws Exception {

    }

    //** MISC. *********************************************************************************************************

    public static void resetRoomList(ObservableList<Room> roomList) {
        while (roomList.size() > 0) {
            roomList.remove(0);
        }
    }

    public static void resetCustomerList(ObservableList<Customer> roomList) {
        while (roomList.size() > 0) {
            roomList.remove(0);
        }
    }

    //** MouseClick. *********************************************************************************************************

    public static boolean mouseEventDoubleClick(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if (mouseEvent.getClickCount() == 2) {
                System.out.println("Double clicked");
                return true;
            }
        }
        return false;
    }


}
















