package com.company;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Program extends Application {
    private SQLProgram sqlProgram = new SQLProgram();
    private ObservableList<Customer> customersNames = FXCollections.observableArrayList();
    private ObservableList<Room> rooms = FXCollections.observableArrayList();
    private ObservableList<Room> roomsBackUp = FXCollections.observableArrayList();
    private ObservableList<Room> roomSelected = FXCollections.observableArrayList();
    //private ArrayList<String> customersNames = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private Stage stagePrimary;
    private Scene sceneMain;
    private Scene sceneAddUser;
    private Scene sceneRoom;
    private Scene sceneRoomOptions;
    private Scene sceneRoomCancel;
    private Scene sceneRoomBooking;
    //TableView<Room> tableViewRoom;

    public void init(Stage stage) throws Exception {
    }

    public void start(Stage stage) throws Exception {
        roomOptions();
        getRooms();
        menuAddUser();
        menuRoom();
        ///////////////////////////////////////////

        stageWincow(sceneRoom);
        //menuMain();
    }

    private void stageWincow(Scene newScene) {
        Stage stage = SC.uniStageMain("TEST STAGE");
        GridPane grid = new GridPane();
        Scene scene = newScene;

        grid.setPadding(new Insets(10, 10, 10, 10));


        grid.getChildren().addAll();
        stage.setScene(scene);
        stage.show();
    }

    private void menuMain() {
        Stage stage = SC.uniStageMain("MAIN MENU");
        //Stage stage = new Stage();
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid);

        grid.setMinSize(333, 333);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Button buttonAddUser = SC.uniButton("Add User");
        buttonAddUser.setOnAction(e -> {
            stage.setScene(sceneAddUser);
        });

        Button buttonRoomBooking = SC.uniButton("Rooms\nBooking\nCancellation");
        buttonRoomBooking.setOnAction(e -> {
            stage.setScene(sceneRoom);
        });

        Button buttonQuit = SC.uniButton("Quit");
        buttonQuit.setOnAction(e -> stage.close());

        GridPane.setConstraints(buttonAddUser, 0, 1);
        GridPane.setConstraints(buttonRoomBooking, 0, 2);
        GridPane.setConstraints(buttonQuit, 0, 4);
        //GridPane userAdd = menuAddUser();
        //GridPane.setConstraints( userAdd, 1, 1 );

        grid.getChildren().addAll(buttonAddUser, buttonRoomBooking, buttonQuit);
        stage.setScene(scene);
        sceneMain = scene;
        stagePrimary = stage;
        stage.show();
    }

    private void menuAddUser() {
        Stage stagea = SC.uniStageMain("Add User");
        Stage stage = new Stage();
        stage.setTitle("Add User");
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid);

        grid.setMinSize(333, 333);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        /*//Password
        Label passwordLabel = new Label("Password");
        GridPane.setConstraints( passwordLabel, 0, 1 );
        TextField passwordTextField = new TextField();
        passwordTextField.setPromptText("Password");
        GridPane.setConstraints(passwordTextField, 1, 1 );*/

        //Name
        Label labelUsername = new Label("Register/add new user");
        GridPane.setConstraints(labelUsername, 0, 0);

        TextField textFieldUsername = new TextField();
        textFieldUsername.setPromptText("Username");
        GridPane.setConstraints(textFieldUsername, 0, 1);

        Label labelMsg = new Label();
        GridPane.setConstraints(labelMsg, 0, 2);

        Button buttonAddUser = SC.uniButton("Add user");
        GridPane.setConstraints(buttonAddUser, 1, 1);
        buttonAddUser.setOnAction(e -> {
            System.out.println(textFieldUsername.getText());
            labelMsg.setText(sqlProgram.registerNewCustomer(textFieldUsername.getText()));
            if (!textFieldUsername.getText().isBlank() && textFieldUsername.getText().length() >= 3) {
                customersNames.add(new Customer(textFieldUsername.getText()));
            }
            textFieldUsername.clear();
        });

        /*ListView<String> listViewUsernames = new ListView<>();
        listViewUsernames.setPrefWidth(100);
        listViewUsernames.setPrefHeight(200);
        listViewUsernames.getItems().addAll(sqlProgram.getUserNames());
        GridPane.setConstraints( listViewUsernames, 0, 3 );*/
        TableView<Customer> tableViewCustomer = new TableView<>();
        tableViewCustomer.setItems(getCustomers());
        //tableViewCustomer.setPrefWidth(160);
        tableViewCustomer.setMaxHeight(210);
        tableViewCustomer.setMaxWidth(210);
        tableViewCustomer.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<Customer, String> tableColumnCustomerName = new TableColumn<>("Name");
        //tableColumnCustomerName.setMinWidth(150);
        tableColumnCustomerName.setMaxWidth(200);
        tableColumnCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        /*TableColumn<Customer, Integer> tableColumnCustomerAge = new TableColumn<>("Age");
        tableColumnCustomerAge.setMinWidth( 30 );
        tableColumnCustomerAge.setMaxWidth( 40 );
        tableColumnCustomerAge.setCellValueFactory( new PropertyValueFactory<>("age"));*/
        tableViewCustomer.getColumns().addAll(tableColumnCustomerName);
        GridPane.setConstraints(tableViewCustomer, 0, 3);


        Button buttonMainMenu = SC.uniButton("Cancel");
        GridPane.setConstraints(buttonMainMenu, 1, 2);
        buttonMainMenu.setOnAction(e -> stagePrimary.setScene(sceneMain));

        /*Button vButtonAddUser = SC.uniButton("Add user");
        Button vButtonExit = SC.buttonCloseCurrentWindow(stage, "EXIT", 50, 50);
        TextField addNameField = new TextField();
        ImageView image = SC.imageCover("hotel");

        image.setFitWidth(640);
        image.setFitHeight(100);
        image.setPreserveRatio(true);
        image.setSmooth(true);
        image.setCache(true);

        hBox.setAlignment( Pos.CENTER );
        hBox.getChildren().addAll( image );
        vBox.getChildren().addAll( vButtonAddUser, vButtonExit );

        borderPane.setTop( hBox );
        borderPane.setLeft( vBox );*/

        grid.setAlignment(Pos.CENTER);
        grid.getChildren().addAll(labelUsername, textFieldUsername, buttonAddUser, buttonMainMenu,
                labelMsg, tableViewCustomer);
        //stage.setScene( scene );
        //stage.show();
        //return scene;
        sceneAddUser = scene;
    }

    private void menuRoom() {
        GridPane gridMain = SC.uniGrid();
        GridPane gridOptions = SC.uniGrid();
        GridPane grid = SC.uniGrid();
        Scene scene = new Scene(gridMain);
        HBox hBox = new HBox();
        Button addUserButton = new Button("Add User");
        CheckBox checkBoxFacilitiesRestaurant = new CheckBox("Restaurant");
        CheckBox checkBoxFacilitiesPool = new CheckBox("Pool");
        CheckBox checkBoxFacilitiesChildrenClub = new CheckBox("Children Club");
        CheckBox checkBoxFacilitiesEveningEntertainment = new CheckBox("Evening Entertainment");
        CheckBox checkBoxAdditionalBoardHalf = new CheckBox("Half board");
        CheckBox checkBoxAdditionalBoardFull = new CheckBox("Full board");
        CheckBox checkBoxAdditionalExtraBed = new CheckBox("Extra Bed");
        ComboBox comboBoxRating = new ComboBox();

        TableView<Room> tableViewRoom = new TableView();
        tableViewRoom.setItems(rooms);
        tableViewRoom.setMaxHeight(333);
        tableViewRoom.setMaxWidth(555);
        //tableViewRoom.setPadding(new Insets(10, 10, 10, 10));
        //tableViewRoom.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        comboBoxRating.setPromptText("Rating");
        comboBoxRating.getItems().addAll("1", "2");

        //RoomsCity
        TableColumn<Room, String> tableColumnCity = new TableColumn("City");
        tableColumnCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        //Rooms roomSize
        TableColumn<Room, Integer> tableColumnRoomSize = new TableColumn("Room\nSize");
        tableColumnRoomSize.setCellValueFactory(new PropertyValueFactory<>("roomSize"));
        //Rooms bookingStart
        TableColumn<Room, LocalDateTime> tableColumnBookingStart = new TableColumn("Booking\ntart");
        tableColumnBookingStart.setCellValueFactory(new PropertyValueFactory<>("bookingStart"));
        //Rooms bookingEnd
        TableColumn<Room, LocalDateTime> tableColumnBookingEnd = new TableColumn("Booking\nStart ");
        tableColumnBookingEnd.setCellValueFactory(new PropertyValueFactory<>("bookingEnd"));
        //Rooms maxAmountOfCustomers
        TableColumn<Room, Integer> tableColumnMaxAmountOfCustomers = new TableColumn("Customer\namount\nallowed");
        tableColumnMaxAmountOfCustomers.setCellValueFactory(new PropertyValueFactory<>("maxAmountOfCustomers"));
        //Rooms facilitiesRestaurant
        TableColumn<Room, Boolean> tableColumnFacilitiesRestaurant = new TableColumn("Restaurant");
        tableColumnFacilitiesRestaurant.setCellValueFactory(new PropertyValueFactory<>("facilitiesRestaurant"));
        //Rooms facilitiesPool
        TableColumn<Room, Boolean> tableColumnFacilitiesPool = new TableColumn("Pool");
        tableColumnFacilitiesPool.setCellValueFactory(new PropertyValueFactory<>("facilitiesPool"));
        //Rooms facilitiesEveningEntertainment
        TableColumn<Room, Boolean> tableColumnFacilitiesEveningEntertainment = new TableColumn("Evening\nEntertainment");
        tableColumnFacilitiesEveningEntertainment.setCellValueFactory(new PropertyValueFactory<>("facilitiesEveningEntertainment"));
        //Rooms facilitiesChildrenClub
        TableColumn<Room, Boolean> tableColumnFacilitiesChildrenClub = new TableColumn("Children\nClub");
        tableColumnFacilitiesChildrenClub.setCellValueFactory(new PropertyValueFactory<>("facilitiesChildrenClub"));
        //Rooms additionalServiceBoardHalf
        TableColumn<Room, Boolean> tableColumnAdditionalServiceBoardHalf = new TableColumn("BoardHalf");
        tableColumnAdditionalServiceBoardHalf.setCellValueFactory(new PropertyValueFactory<>("additionalServiceBoardHalf"));
        //Rooms additionalServiceBoardFull
        TableColumn<Room, Boolean> tableColumnAdditionalServiceBoardFull = new TableColumn("Board\nFull");
        tableColumnAdditionalServiceBoardFull.setCellValueFactory(new PropertyValueFactory<>("additionalServiceBoardFull"));
        //Rooms additionalServiceExtraBed
        TableColumn<Room, Boolean> tableColumnAdditionalServiceExtraBed = new TableColumn("Extra\nBed");
        tableColumnAdditionalServiceExtraBed.setCellValueFactory(new PropertyValueFactory<>("additionalServiceExtraBed"));
        //Rooms pricePerNight
        TableColumn<Room, Double> tableColumnPricePerNight = new TableColumn("Price\nPer\nNight");
        tableColumnPricePerNight.setCellValueFactory(new PropertyValueFactory<>("pricePerNight"));
        //Rooms rating
        TableColumn<Room, Double> tableColumnRating = new TableColumn("Rating");
        tableColumnRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        //Rooms Availability
        TableColumn<Room, Boolean> tableColumnAvailability = new TableColumn("Availability");
        tableColumnAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));

        tableViewRoom.getColumns().addAll(tableColumnCity, tableColumnRoomSize, tableColumnBookingStart, tableColumnBookingEnd,
                tableColumnMaxAmountOfCustomers, tableColumnFacilitiesRestaurant, tableColumnFacilitiesPool, tableColumnFacilitiesEveningEntertainment,
                tableColumnFacilitiesChildrenClub, tableColumnAdditionalServiceBoardHalf, tableColumnAdditionalServiceBoardFull,
                tableColumnAdditionalServiceExtraBed, tableColumnPricePerNight, tableColumnRating, tableColumnAvailability);

        checkBoxFacilitiesRestaurant.setOnAction(e -> roomOptionRestaurant( checkBoxFacilitiesRestaurant, tableViewRoom ));
        checkBoxFacilitiesPool.setOnAction(e -> roomOptionRestaurant( checkBoxFacilitiesRestaurant, tableViewRoom ));
        checkBoxFacilitiesChildrenClub.setOnAction(e -> roomOptionRestaurant( checkBoxFacilitiesRestaurant, tableViewRoom ));
        checkBoxFacilitiesEveningEntertainment.setOnAction(e -> roomOptionRestaurant( checkBoxFacilitiesRestaurant, tableViewRoom ));
        checkBoxAdditionalBoardHalf.setOnAction(e -> roomOptionRestaurant( checkBoxFacilitiesRestaurant, tableViewRoom ));
        checkBoxAdditionalBoardFull.setOnAction(e -> roomOptionRestaurant( checkBoxFacilitiesRestaurant, tableViewRoom ));
        checkBoxAdditionalExtraBed.setOnAction(e -> roomOptionRestaurant( checkBoxFacilitiesRestaurant, tableViewRoom ));
        /*
        checkBoxFacilitiesRestaurant.setOnAction(e -> {
            ObservableList<Room> tempObsList = FXCollections.observableArrayList();
            if (checkBoxFacilitiesRestaurant.isSelected()) {
                for (Room r : rooms) {
                    if (r.isAvailability()) {
                        tempObsList.add(r);
                    }
                }
                tableViewRoom.setItems(tempObsList);
                //tableViewRoom.setItems( sqlProgram.returnRoomsAvailableRestaurant());
                deleteOnCheckBox();
            } else if (!checkBoxFacilitiesRestaurant.isSelected()) {
                tableViewRoom.setItems(getRooms());
                deleteOnCheckBox();
            }
        });
         */

        Button buttonCancel = SC.uniButton("Cancel");
        buttonCancel.setOnAction(e -> stagePrimary.setScene(sceneMain) );
        Button buttonExit = SC.uniButton("Exit");
        buttonCancel.setOnAction(e -> stagePrimary.close() );

        GridPane.setConstraints(gridOptions, 0, 0);
        GridPane.setConstraints(grid, 0, 1);
        GridPane.setConstraints(checkBoxFacilitiesRestaurant, 0, 0);
        GridPane.setConstraints(checkBoxFacilitiesPool, 1, 0);
        GridPane.setConstraints(checkBoxFacilitiesChildrenClub, 2, 0);
        GridPane.setConstraints(checkBoxFacilitiesEveningEntertainment, 3, 0);
        GridPane.setConstraints(checkBoxAdditionalBoardHalf, 0, 1);
        GridPane.setConstraints(checkBoxAdditionalBoardFull, 1, 1);
        GridPane.setConstraints(checkBoxAdditionalExtraBed, 2, 1);
        GridPane.setConstraints(comboBoxRating, 3, 1);
        GridPane.setConstraints(tableViewRoom, 0, 1);
        GridPane.setConstraints(buttonCancel, 0, 2);
        GridPane.setConstraints(buttonExit, 1, 2);

        gridOptions.getChildren().addAll(comboBoxRating, checkBoxFacilitiesRestaurant, checkBoxFacilitiesPool, checkBoxFacilitiesEveningEntertainment,
                checkBoxFacilitiesChildrenClub, checkBoxAdditionalBoardFull, checkBoxAdditionalExtraBed, checkBoxAdditionalBoardHalf);
        grid.getChildren().addAll(buttonExit, buttonCancel, tableViewRoom);
        /*
        grid.getChildren().addAll(buttonCancel, checkBoxFacilitiesRestaurant, checkBoxFacilitiesPool, checkBoxFacilitiesEveningEntertainment,
                checkBoxFacilitiesChildrenClub, checkBoxAdditionalBoardFull, checkBoxAdditionalExtraBed, checkBoxAdditionalBoardHalf, tableViewRoom);
         */
        gridMain.getChildren().addAll( gridOptions, grid );
        //stage.setScene( scene );
        //stage.show();
        sceneRoom = scene;
        //return scene;
    }

    private void roomOptionRestaurant(CheckBox checkBox, TableView tableView){
        ObservableList<Room> tempObsList = FXCollections.observableArrayList();
        if (checkBox.isSelected()) {
            for (Room r : rooms) {
                if (r.getRoomSize() >= 50 ) {
                    tempObsList.add(r);
                }
            }
            tableView.setItems(tempObsList);
            //tableViewRoom.setItems( sqlProgram.returnRoomsAvailableRestaurant());
            //deleteOnCheckBox();
        } else if (!checkBox.isSelected()) {
            tableView.setItems(getRooms());
            //deleteOnCheckBox();
        }
    }

    private void roomOptions() {
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid);
        TableView<Room> table = new TableView();
        Button buttonRentRoom = SC.uniButton("Rent Room");
        Button buttonCancelRoom = SC.uniButton("Cancel\nBooking");
        Button buttonExitScene = SC.uniButton("Back");

        ListView<String> listRent = new ListView<>();
        listRent.setPrefWidth(200);
        listRent.setPrefHeight(200);
        listRent.getItems().addAll(sqlProgram.getCustomerNames());
        ListView<String> listCancel = new ListView<>();
        listCancel.setPrefWidth(200);
        listCancel.setPrefHeight(200);
        listCancel.getItems().addAll(sqlProgram.getCustomerNames());

        Label label = new Label();
        label.setText("Select customers that \n" +
                "City: " + "\n" +
                "Room Size: " + "\n" +
                "Rent per night: " + "\n");

        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        GridPane.setConstraints(label, 0, 0);
        GridPane.setConstraints(buttonRentRoom, 0, 1);
        GridPane.setConstraints(listRent, 0, 2);
        GridPane.setConstraints(buttonCancelRoom, 1, 1);
        GridPane.setConstraints(listCancel, 1, 2);
        GridPane.setConstraints(buttonExitScene, 0, 3);

        buttonExitScene.setOnAction(e -> stagePrimary.setScene(sceneMain));

        grid.getChildren().addAll(buttonExitScene, label, listRent, listCancel, buttonCancelRoom, buttonRentRoom);
        sceneRoomOptions = scene;
    }

    private void deleteOnCheckBox() {
        ObservableList<Room> checkedFacility, allRooms;
        allRooms = getRooms();
        //checkedFacility = tableViewRoom.getSelectionModel().getSelectedItems();
        //checkedFacility.forEach( allRooms :: remove );
            /*if( checkBoxFacilitiesRestaurant.isSelected() ) {
            tableViewRoom.getItems().removeAll( rooms );
            }*/
        while (!rooms.get(1).isFacilitiesRestaurant()) {
            rooms.remove(rooms.get(1));
            System.out.println("not available!");
            return;
        }
        System.out.println("available!");
    }

    private BorderPane menuCancelRoom() {
        Stage stage = SC.uniStageMain("Add User");
        BorderPane borderPane = new BorderPane();
        HBox hBox = new HBox();
        VBox vBox = new VBox();
        Scene scene = new Scene(borderPane);
        Button vButtonAddUser = SC.uniButton("Add user");
        Button vButtonExit = SC.buttonCloseCurrentWindow(stage, "EXIT", 50, 50);
        TextField addNameField = new TextField();
        ImageView image = SC.imageCover("hotel");

        image.setFitWidth(640);
        image.setFitHeight(100);
        image.setPreserveRatio(true);
        image.setSmooth(true);
        image.setCache(true);

        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(image);
        vBox.getChildren().addAll(vButtonAddUser, vButtonExit);

        borderPane.setTop(hBox);
        borderPane.setLeft(vBox);

        scene.setFill(Color.BLACK);

        stage.setScene(scene);
        stage.show();
        return borderPane;
    }

    private ObservableList<Customer> getCustomers() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        for (String name : sqlProgram.getCustomerNames()) {
            customers.add(new Customer(name));
        }
        customersNames = customers;
        return customers;
    }

    private ObservableList<Room> getRooms() {
        ObservableList<Room> rooms = FXCollections.observableArrayList();
        for (Room r : sqlProgram.getRooms()) {
            rooms.add(new Room(
                    r.getCity(), r.getRoomSize(), r.getBookingStart(), r.getBookingEnd(), r.getMaxAmountOfCustomers(), r.isFacilitiesRestaurant(),
                    r.isFacilitiesPool(), r.isFacilitiesEveningEntertainment(), r.isFacilitiesChildrenClub(), r.isAdditionalServiceBoardHalf(),
                    r.isAdditionalServiceBoardFull(), r.isAdditionalServiceExtraBed(), r.getPricePerNight(), r.getRating(), r.isAvailability()
            ));
        }
        roomsBackUp = rooms;
        this.rooms = rooms;
        return rooms;
    }

    public void stop() throws Exception {
    }

}
