package com.company;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Program extends Application {
    private SQLProgram sqlProgram = new SQLProgram();
    private ObservableList<Customer> customersNames = FXCollections.observableArrayList();
    private ObservableList<Room> rooms = FXCollections.observableArrayList();
    private ObservableList<Customer> customers = FXCollections.observableArrayList();
    private ObservableList<Customer> currentRoomRenters = FXCollections.observableArrayList();
    private ObservableList<Room> listWithFilteredRooms = FXCollections.observableArrayList();
    private ObservableList<Room> poolFilterSearch = FXCollections.observableArrayList();
    private ObservableList<Room> restaurantFilterSearch = FXCollections.observableArrayList();
    private ObservableList<Room> childrenClubFilterSearch = FXCollections.observableArrayList();
    //private ObservableList<Room> currentRoom = FXCollections.observableArrayList();
    private Room currentRoom = null;
    private ObservableList<Room> tempTempListOfRooms = FXCollections.observableArrayList();
    private ObservableList<Room> poolFilterSearchFull = FXCollections.observableArrayList();
    private ObservableList<Room> tempRoomsPoolFull1 = FXCollections.observableArrayList();
    private ObservableList<Room> tempRoomLists = FXCollections.observableArrayList();
    private ObservableList<Room> tempCurrentLists = FXCollections.observableArrayList();
    private ObservableList<Room> fillListWithFilteredResultAfterIsNotSelected = FXCollections.observableArrayList();
    private ObservableList<Room> listOfRoomsForOneFilter = FXCollections.observableArrayList();


    //private ArrayList<String> customersNames = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private Stage stagePrimary;
    private Stage stageWindow;
    private Scene sceneMain;
    private Scene sceneAddUser;
    private Scene sceneRoom;
    private Scene sceneRoomSettings;
    private Scene sceneRoomCancel;
    private Scene sceneRoomBooking;
    //TableView<Room> tableViewRoom;

    private Date dateNow = new Date(2020, 02, 01);
    private Date dateTomorrow = new Date(2020, 03, 01);

    private Boolean boolFacilityRestaurantCheckedOut = false, boolFacilityPoolCheckedOut = false, boolFacilityEveningEntertainmentCheckedOut = false, boolFacilityChildrenClubCheckedOut = false, boolAddBoardFullCheckedOut = false, boolAddBoardHalvCheckedOut = false, boolAddExtraBedCheckedOut = false;
    private Boolean[] boolRoomOptions = {boolFacilityRestaurantCheckedOut, boolFacilityPoolCheckedOut, boolFacilityEveningEntertainmentCheckedOut, boolFacilityChildrenClubCheckedOut, boolAddBoardFullCheckedOut, boolAddBoardHalvCheckedOut, boolAddExtraBedCheckedOut};

    public void init(Stage stage) throws Exception {
    }

    public void start(Stage stage) throws Exception {
        aaaTempRoom();
        System.out.println("1");
        ///////////////////////////////////////////
        getRooms();
        System.out.println("2");
        getCustomers();
        System.out.println("3");
        menuAddUser();
        System.out.println("4");
        menuRoom();
        System.out.println("5");
        menuRoomChangeRoomSettings();
        System.out.println("6");
        ///////////////////////////////////////////

        //System.out.println(currentRoom.size() + " CurrentRoom Size");

        stageWindow(sceneRoom);
        //stageWindow(sceneRoomSettings);
        //menuMain();
    }

    private void aaaTempRoom() {
        /*currentRoom.add( new Room(1, "Malmö", 19, dateNow, dateTomorrow, 4,
                true, true, true, true,
                true, true, true,
                12, 9, true));*/
    }

    private void stageWindow(Scene newScene) {
        //Stage stage = SC.uniStageMain("TEST STAGE");
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        Scene scene = newScene;

        grid.setPadding(new Insets(10, 10, 10, 10));


        grid.getChildren().addAll();
        stage.setScene(scene);
        stage.show();
        stageWindow = stage;
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
            /*if (!textFieldUsername.getText().isBlank() && textFieldUsername.getText().length() >= 3) {
                customersNames.add(new Customer(textFieldUsername.getText()));
            }*/
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

    private Scene menuRoomChangeRoomSettings() {
        System.out.println("Inne i menuRoomChangeRoomSettings");
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid);

        /*ListView<String> listRent = new ListView<>();
        listRent.setPrefWidth(200);
        listRent.setPrefHeight(200);
        listRent.getItems().addAll(sqlProgram.getCustomerNames());
        ListView<String> listCancel = new ListView<>();
        listCancel.setPrefWidth(200);
        listCancel.setPrefHeight(200);
        listCancel.getItems().addAll(sqlProgram.getCustomerNames());*/

        //-----------------------------------------------------------------------------------------------

        Button buttonRoomRent = SC.uniButton("Add customer to\nrent room");
        buttonRoomRent.setOnAction(e -> stagePrimary.setScene(sceneRoom));
        Button buttonRoomCancel = SC.uniButton("Remove customer from\nrenting list");
        buttonRoomCancel.setOnAction(e -> stagePrimary.setScene(sceneRoom));
        Button buttonSceneCancel = SC.uniButton("Cancel");
        //buttonSceneCancel.setOnAction(e -> stageWindow.setScene(sceneRoom));
        buttonSceneCancel.setOnAction(e -> stageWindow.setScene( menuRoom() ));
        Button buttonSceneExit = SC.uniButton("Exit");
        buttonSceneExit.setOnAction(e -> stageWindow.close());

        //-----------------------------------------------------------------------------------------------

        TableView<Customer> tableViewRenter = new TableView<>();
        if (currentRoom != null) {
            tableViewRenter.setItems(returnRenters(currentRoom.getId()));
        }
        //tableViewRenter.setItems(returnRenters(1));
        //tableViewRenter.setPrefWidth(160);
        //tableViewRenter.setMaxHeight(200);
        //tableViewRenter.setMaxWidth(200);
        tableViewRenter.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //tableColumnCustomerName.setMinWidth(150);
        //tableColumnCustomerName.setMaxWidth(200);
        TableColumn<Customer, Character> tableColumnRenterName = new TableColumn<>("Renter Name");
        tableColumnRenterName.setCellValueFactory(new PropertyValueFactory<>("name"));

        //tableColumnCustomerRoomId.setMinWidth( 30 );
        tableViewRenter.getColumns().addAll(tableColumnRenterName);

        //-----------------------------------------------------------------------------------------------

        TableView<Customer> tableViewCustomer = new TableView<>();
        tableViewCustomer.setItems(customers);
        //tableViewCustomer.setItems(returnRenters(1));
        //tableViewCustomer.setPrefWidth(160);
        //tableViewCustomer.setMaxHeight(200);
        //tableViewCustomer.setMaxWidth(200);
        tableViewCustomer.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //tableColumnCustomerName.setMinWidth(150);
        //tableColumnCustomerName.setMaxWidth(200);
        TableColumn<Customer, Character> tableColumnCustomerName = new TableColumn<>("Available Customers");
        tableColumnCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));

        //tableColumnCustomerRoomId.setMinWidth( 30 );
        tableViewCustomer.getColumns().addAll(tableColumnCustomerName);

        //-----------------------------------------------------------------------------------------------

        Label label = new Label();
        if (currentRoom != null) {
            label.setText("Select customers that \n" +
                    "City: " + currentRoom.getCity() + "\n" +
                    "Renters: " + "\n" +
                    "Rent per night: " + "\n");

            System.out.println(currentRoom.getCity() + " currentRoom.getCity()\nfrom menuSettings");
        }
        //-----------------------------------------------------------------------------------------------


        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(label, 0, 0);
        grid.add(buttonRoomRent, 0, 1);
        grid.add(tableViewRenter, 0, 2);

        grid.add(buttonRoomCancel, 1, 1);
        grid.add(tableViewCustomer, 1, 2);

        // node, columnIndex, rowIndex, columnSpan, rowSpan:
        grid.add(buttonSceneCancel, 0, 3, 1, 1);
        grid.add(buttonSceneExit, 0, 4, 1, 1);

        //-----------------------------------------------------------------------------------------------

        sceneRoomSettings = scene;
        return scene;
    }

    private ObservableList<Customer> returnRenters(int roomId) {
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        for (Customer customer : this.customers) {
            if (customer.getRoom() == roomId) {
                customers.add(customer);
            }
        }
        currentRoomRenters = customers;
        return customers;
    }

    private Scene menuRoom() {
        System.out.println("Inne i menuRoom");
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

        //-----------------------------------------------------------------------------------------------

        TableView<Room> tableViewRoom = new TableView();
        tableViewRoom.setItems(rooms);
        tableViewRoom.setMaxHeight(333);
        tableViewRoom.setMaxWidth(555);
        //tableViewRoom.setPadding(new Insets(10, 10, 10, 10));
        //tableViewRoom.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableViewRoom.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //-----------------------------------------------------------------------------------------------

        comboBoxRating.setPromptText("Rating");
        comboBoxRating.getItems().addAll("1", "2");

        //-----------------------------------------------------------------------------------------------

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

        //-----------------------------------------------------------------------------------------------

        checkBoxFacilitiesRestaurant.setOnAction(e -> roomOptionFacilitiesRestaurant(checkBoxFacilitiesRestaurant, tableViewRoom));
        checkBoxFacilitiesPool.setOnAction(e -> roomOptionFacilitiesPool(checkBoxFacilitiesPool, tableViewRoom));
        checkBoxFacilitiesChildrenClub.setOnAction(e -> roomOptionFacilitiesChildrenClub(checkBoxFacilitiesChildrenClub, tableViewRoom));
        checkBoxFacilitiesEveningEntertainment.setOnAction(e -> roomOptionRoomSize(checkBoxFacilitiesEveningEntertainment, tableViewRoom));
        checkBoxAdditionalBoardHalf.setOnAction(e -> roomOptionFacilitiesPool(checkBoxAdditionalBoardHalf, tableViewRoom));
        checkBoxAdditionalBoardFull.setOnAction(e -> roomOptionFacilitiesPool(checkBoxAdditionalBoardFull, tableViewRoom));
        checkBoxAdditionalExtraBed.setOnAction(e -> roomOptionFacilitiesPool(checkBoxAdditionalExtraBed, tableViewRoom));
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
        //-----------------------------------------------------------------------------------------------

        Button buttonCancel = SC.uniButton("Cancel");
        buttonCancel.setOnAction(e -> stagePrimary.setScene(sceneMain));

        Button buttonExit = SC.uniButton("Exit");
        buttonExit.setOnAction(e -> stageWindow.close());

        tableViewRoom.setOnMouseClicked(e -> {
            if( SC.mouseEventDoubleClick( e )) {
                System.out.println("Clicked with mouse!");
                currentRoom = tableViewRoom.getSelectionModel().getSelectedItem();
                stageWindow.setScene(menuRoomChangeRoomSettings());
                System.out.println(currentRoom.getCity());
            }

            /*if (SC.mouseEventDoubleClick(e)) {
                TablePosition tablePosition = tableViewRoom.getSelectionModel().getSelectedCells().get(0);
                int rowClicked = tablePosition.getRow();
                currentRoom = tableViewRoom.getItems().get( rowClicked );
                System.out.println(currentRoom.getCity());
                stageWindow.setScene(sceneRoomSettings);
            }*/
        });
        //-----------------------------------------------------------------------------------------------

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
        gridMain.getChildren().addAll(gridOptions, grid);

        //-----------------------------------------------------------------------------------------------

        //stage.setScene( scene );
        //stage.show();
        sceneRoom = scene;
        return scene;
    }

    private Boolean checkAfterListeners() {
        for (Boolean bool : boolRoomOptions) {
            if (bool) {
                return true;
            }
        }
        return false;
    }

    private void roomOptionFacilitiesRestaurant(CheckBox checkBox, TableView tableView) {

        if (checkBox.isSelected()) {
            System.out.println("1 Rest");

            if (listWithFilteredRooms.size() > 0) {
                System.out.println("2 Rest");
                for (Room room : listWithFilteredRooms) {
                    if (room.isFacilitiesRestaurant().equalsIgnoreCase("yes")) {
                        System.out.println("3 Rest");
                        restaurantFilterSearch.add(room);
                    }
                }

                tableView.setItems(restaurantFilterSearch);
                gatherAllFilterLists();
                System.out.println(listWithFilteredRooms.size() + "listWithFilteredRooms rest");
                return;
            }

            for (Room room : rooms) {
                if (room.isFacilitiesRestaurant().equalsIgnoreCase("yes")) {
                    System.out.println("4 Rest");
                    restaurantFilterSearch.add(room);
                }
            }

            tableView.setItems(restaurantFilterSearch);
            gatherAllFilterLists();
            System.out.println(listWithFilteredRooms.size() + "listWithFilteredRooms rest");
            return;
        }

        if (!checkBox.isSelected()) {
            System.out.println("5 Rest");
            SC.resetRoomList(restaurantFilterSearch);
            gatherAllFilterLists();
            System.out.println(listWithFilteredRooms.size() + "listWithFilteredRooms rest");
            onlyOneFilterLeft();

            if (listWithFilteredRooms.size() > 0) {
                System.out.println("6 Rest");
                tableView.setItems(listWithFilteredRooms);
                System.out.println(listWithFilteredRooms.size() + "filtersList Rest");
                return;
            }

            tableView.setItems(rooms);
        }
    }

    private void roomOptionFacilitiesPool(CheckBox checkBox, TableView tableView) {
        if (checkBox.isSelected()) {
            System.out.println("1 Pool");

            if (listWithFilteredRooms.size() > 0) {
                System.out.println("2 Pool");
                for (Room room : listWithFilteredRooms) {
                    if (room.isFacilitiesPool().equalsIgnoreCase("yes")) {
                        System.out.println("3 Pool");
                        poolFilterSearch.add(room);
                    }
                }

                tableView.setItems(poolFilterSearch);
                gatherAllFilterLists();
                System.out.println(listWithFilteredRooms.size() + "listWithFilteredRooms Pool");
                return;
            }

            for (Room room : rooms) {
                if (room.isFacilitiesPool().equalsIgnoreCase("yes")) {
                    System.out.println("4 Pool");
                    poolFilterSearch.add(room);
                }
            }

            tableView.setItems(poolFilterSearch);
            gatherAllFilterLists();
            System.out.println(listWithFilteredRooms.size() + "listWithFilteredRooms Pool");
            return;
        }

        if (!checkBox.isSelected()) {
            System.out.println("5 Pool");
            SC.resetRoomList(poolFilterSearch);
            gatherAllFilterLists();
            System.out.println(listWithFilteredRooms.size() + "listWithFilteredRooms Pool");
            onlyOneFilterLeft();

            if (listWithFilteredRooms.size() > 0) {
                System.out.println("6 Pool");
                System.out.println(listWithFilteredRooms.size() + "listWithFilteredRooms Pool");
                tableView.setItems(listWithFilteredRooms);
                return;
            }

            tableView.setItems(rooms);
        }
    }

    private void roomOptionFacilitiesChildrenClub(CheckBox checkBox, TableView tableView) {
        if (checkBox.isSelected()) {
            System.out.println("1");

            if (listWithFilteredRooms.size() > 0) {
                System.out.println("2");
                for (Room room : listWithFilteredRooms) {
                    if (room.isFacilitiesChildrenClub().equalsIgnoreCase("yes")) {
                        System.out.println("3");
                        childrenClubFilterSearch.add(room);
                    }
                }

                tableView.setItems(childrenClubFilterSearch);
                gatherAllFilterLists();
                System.out.println(listWithFilteredRooms.size() + "listWithFilteredRooms chi");
                return;
            }

            for (Room room : rooms) {
                if (room.isFacilitiesChildrenClub().equalsIgnoreCase("yes")) {
                    System.out.println("4");
                    childrenClubFilterSearch.add(room);
                }
            }

            tableView.setItems(childrenClubFilterSearch);
            gatherAllFilterLists();
            System.out.println(listWithFilteredRooms.size() + "listWithFilteredRooms chi");
            return;
        }

        if (!checkBox.isSelected()) {
            System.out.println("5");
            SC.resetRoomList(childrenClubFilterSearch);
            gatherAllFilterLists();
            System.out.println(listWithFilteredRooms.size() + "listWithFilteredRooms chi");
            //boolFacilityChildrenClubCheckedOut = true;
            onlyOneFilterLeft();

            if (listWithFilteredRooms.size() > 0) {
                System.out.println("6");
                tableView.setItems(listWithFilteredRooms);
                return;
            }

            tableView.setItems(rooms);
        }
    }

    private void roomOptionRoomSize(CheckBox checkBox, TableView tableView) {
        if (checkBox.isSelected()) {

            if (listWithFilteredRooms.size() > 0) {
                for (Room room : listWithFilteredRooms) {
                    if (room.isFacilitiesChildrenClub().equalsIgnoreCase("yes")) {
                        childrenClubFilterSearch.add(room);
                    }
                }

                tableView.setItems(childrenClubFilterSearch);
                gatherAllFilterLists();
                return;
            }

            for (Room room : rooms) {
                if (room.isFacilitiesChildrenClub().equalsIgnoreCase("yes")) {
                    childrenClubFilterSearch.add(room);
                }
            }

            tableView.setItems(childrenClubFilterSearch);
            gatherAllFilterLists();
            return;
        }

        if (!checkBox.isSelected()) {
            SC.resetRoomList(childrenClubFilterSearch);
            gatherAllFilterLists();

            onlyOneFilterLeft();

            if (listWithFilteredRooms.size() > 0) {
                tableView.setItems(listWithFilteredRooms);
                return;
            }

            tableView.setItems(rooms);
        }
    }

    //------------------------------------------------------------------------------------------------------------------

    private void gatherAllFilterLists() {
        SC.resetRoomList(listWithFilteredRooms);
        for (Room room : restaurantFilterSearch) {
            listWithFilteredRooms.add(room);
        }
        for (Room room : poolFilterSearch) {
            listWithFilteredRooms.add(room);
        }
        for (Room room : childrenClubFilterSearch) {
            listWithFilteredRooms.add(room);
        }

        if (listWithFilteredRooms.size() > 0) {

            if (restaurantFilterSearch.size() > 0) {
                for (Room room : listWithFilteredRooms) {
                    if (room.isFacilitiesRestaurant().equalsIgnoreCase("yes")) {
                        restaurantFilterSearch.add(room);
                    }
                }
            }
            if (poolFilterSearch.size() > 0) {
                for (Room room : listWithFilteredRooms) {
                    if (room.isFacilitiesPool().equalsIgnoreCase("yes")) {
                        poolFilterSearch.add(room);
                    }
                }
            }
            if (childrenClubFilterSearch.size() > 0) {
                for (Room room : listWithFilteredRooms) {
                    if (room.isFacilitiesRestaurant().equalsIgnoreCase("yes")) {
                        childrenClubFilterSearch.add(room);
                    }
                }
            }

        }
    }

    private void onlyOneFilterLeft() {
        //SC.resetRoomList(listWithFilteredRooms);
        if (restaurantFilterSearch.size() > 0) {
            SC.resetRoomList(restaurantFilterSearch);
            for (Room room : rooms) {
                if (room.isFacilitiesRestaurant().equalsIgnoreCase("yes")) {
                    System.out.println("Rest onlyOneFilterLeft");
                    restaurantFilterSearch.add(room);
                }
            }
        }

        if (poolFilterSearch.size() > 0) {
            SC.resetRoomList(poolFilterSearch);
            for (Room room : rooms) {
                if (room.isFacilitiesPool().equalsIgnoreCase("yes")) {
                    System.out.println("Pool onlyOneFilterLeft");
                    poolFilterSearch.add(room);
                }
            }
        }

        if (childrenClubFilterSearch.size() > 0) {
            SC.resetRoomList(childrenClubFilterSearch);
            for (Room room : rooms) {
                if (room.isFacilitiesChildrenClub().equalsIgnoreCase("yes")) {
                    System.out.println("ChiCl onlyOneFilterLeft");
                    childrenClubFilterSearch.add(room);
                }
            }
        }
        gatherAllFilterLists();
    }
    /*
    private void gatherAllFilterLists() {
        SC.resetRoomList(listWithFilteredRooms);
        for (Room room : restaurantFilterSearch) {
            listWithFilteredRooms.add(room);
        }
        for (Room room : poolFilterSearch) {
            listWithFilteredRooms.add(room);
        }
        for (Room room : childrenClubFilterSearch) {
            listWithFilteredRooms.add(room);
        }
    }

    private void onlyOneFilterLeft() {
        //SC.resetRoomList(listWithFilteredRooms);
        if (restaurantFilterSearch.size() > 0) {
            SC.resetRoomList(restaurantFilterSearch);
            for (Room room : rooms) {
                if (room.isFacilitiesRestaurant()) {
                    System.out.println("Rest onlyOneFilterLeft");
                    restaurantFilterSearch.add(room);
                }
            }
        }

        if (poolFilterSearch.size() > 0) {
            SC.resetRoomList(poolFilterSearch);
            for (Room room : rooms) {
                if (room.isFacilitiesPool()) {
                    System.out.println("Pool onlyOneFilterLeft");
                    poolFilterSearch.add(room);
                }
            }
        }

        if (childrenClubFilterSearch.size() > 0) {
            SC.resetRoomList(childrenClubFilterSearch);
            for (Room room : rooms) {
                if (room.isFacilitiesChildrenClub()) {
                    System.out.println("ChiCl onlyOneFilterLeft");
                    childrenClubFilterSearch.add(room);
                }
            }
        }
        gatherAllFilterLists();
    }

    /*
    private void onlyOneFilterLeft(){
        //SC.resetRoomList(listWithFilteredRooms);
        if( restaurantFilterSearch.size() > 0 ) {
            SC.resetRoomList(restaurantFilterSearch);
            for (Room room : rooms) {
                if (room.isFacilitiesRestaurant()) {
                    System.out.println("Rest onlyOneFilterLeft");
                    restaurantFilterSearch.add(room);
                }
            }
        }

        if( poolFilterSearch.size() > 0 ) {
            SC.resetRoomList(poolFilterSearch);
            for (Room room : rooms) {
                if (room.isFacilitiesPool()) {
                    System.out.println("Pool onlyOneFilterLeft");
                    poolFilterSearch.add(room);
                }
            }
        }

        if( childrenClubFilterSearch.size() > 0 ) {
            SC.resetRoomList(childrenClubFilterSearch);
            for (Room room : rooms) {
                if (room.isFacilitiesChildrenClub()) {
                    System.out.println("ChiCl onlyOneFilterLeft");
                    childrenClubFilterSearch.add(room);
                }
            }
        }
        gatherAllFilterLists();
    }
     */

    /*
    private void gatherAllFilterLists() {
        SC.resetRoomList(listWithFilteredRooms);
        for (Room room : restaurantFilterSearch) {
            listWithFilteredRooms.add(room);
        }
        for (Room room : poolFilterSearch) {
            listWithFilteredRooms.add(room);
        }
    }

    private void onlyOneFilterLeft(){
        //SC.resetRoomList(listWithFilteredRooms);
        if( restaurantFilterSearch.size() > 0 ) {
            SC.resetRoomList(restaurantFilterSearch);
            for (Room room : rooms) {
                if (room.isFacilitiesRestaurant()) {
                    System.out.println("Rest onlyOneFilterLeft");
                    restaurantFilterSearch.add(room);
                }
            }
        }

        if( poolFilterSearch.size() > 0 ) {
            SC.resetRoomList(poolFilterSearch);
            for (Room room : rooms) {
                if (room.isFacilitiesPool()) {
                    System.out.println("Pool onlyOneFilterLeft");
                    poolFilterSearch.add(room);
                }
            }
        }
        gatherAllFilterLists();
    }

    private void roomOptionFacilitiesRestaurant(CheckBox checkBox, TableView tableView) {

        if (checkBox.isSelected()) {
            System.out.println("1 Rest");

            if (listWithFilteredRooms.size() > 0) {
                System.out.println("2 Rest");
                for (Room room : listWithFilteredRooms) {
                    if (room.isFacilitiesRestaurant()) {
                        System.out.println("3 Rest");
                        restaurantFilterSearch.add(room);
                    }
                }

                tableView.setItems(restaurantFilterSearch);
                gatherAllFilterLists();
                return;
            }

            for (Room room : rooms) {
                if (room.isFacilitiesRestaurant()) {
                    System.out.println("4 Rest");
                    restaurantFilterSearch.add(room);
                }
            }
            tableView.setItems(restaurantFilterSearch);
            gatherAllFilterLists();
            return;
        }

        if (!checkBox.isSelected()) {
            System.out.println("5 Rest");
            SC.resetRoomList(restaurantFilterSearch);
            gatherAllFilterLists();

            onlyOneFilterLeft();

            if (listWithFilteredRooms.size() > 0) {
                System.out.println(listWithFilteredRooms.size() + "filtersList Rest");
                System.out.println("6 Rest");
                tableView.setItems(listWithFilteredRooms);
                return;
            }

            tableView.setItems(rooms);
        }
    }

    private void roomOptionFacilitiesPool(CheckBox checkBox, TableView tableView) {
        if (checkBox.isSelected()) {
            System.out.println("1 Pool");

            if (listWithFilteredRooms.size() > 0) {
                System.out.println("2 Pool");
                for (Room room : listWithFilteredRooms) {
                    if (room.isFacilitiesPool()) {
                        System.out.println("3 Pool");
                        poolFilterSearch.add(room);
                    }
                }

                tableView.setItems(poolFilterSearch);
                gatherAllFilterLists();
                return;
            }

            for (Room room : rooms) {
                if (room.isFacilitiesPool()) {
                    System.out.println("4 Pool");
                    poolFilterSearch.add(room);
                }
            }

            tableView.setItems(poolFilterSearch);
            gatherAllFilterLists();
            return;
        }

        if (!checkBox.isSelected()) {
            System.out.println("5 Pool");
            SC.resetRoomList(poolFilterSearch);
            gatherAllFilterLists();

            onlyOneFilterLeft();

            if (listWithFilteredRooms.size() > 0) {
                System.out.println("6 Pool");
                tableView.setItems(listWithFilteredRooms);
                return;
            }

            tableView.setItems(rooms);
        }
    }
     */

    //------------------------------------------------------------------------------------------------------------------

    private void deleteOnCheckBox() {
        ObservableList<Room> checkedFacility, allRooms;
        allRooms = getRooms();
        //checkedFacility = tableViewRoom.getSelectionModel().getSelectedItems();
        //checkedFacility.forEach( allRooms :: remove );
            /*if( checkBoxFacilitiesRestaurant.isSelected() ) {
            tableViewRoom.getItems().removeAll( rooms );
            }
        while (!rooms.get(1).isFacilitiesRestaurant()) {
            rooms.remove(rooms.get(1));
            System.out.println("not available!");
            return;
        }
        System.out.println("available!");*/
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
        for (Customer customer : sqlProgram.getCustomers()) {
            customers.add(new Customer(
                    customer.getId(), customer.getName(), customer.getRoom()
            ));
        }

        this.customers = customers;
        customersNames = customers;
        return customers;
    }

    private ObservableList<Room> getRooms() {
        ObservableList<Room> rooms = FXCollections.observableArrayList();
        for (Room r : sqlProgram.getRooms()) {
            rooms.add(new Room(
                    r.getId(), r.getCity(), r.getRoomSize(), r.getBookingStart(), r.getBookingEnd(), r.getMaxAmountOfCustomers(), returnTrueIfYesFalseIfNo(r.isFacilitiesRestaurant()),
                    returnTrueIfYesFalseIfNo(r.isFacilitiesPool()), returnTrueIfYesFalseIfNo(r.isFacilitiesEveningEntertainment()), returnTrueIfYesFalseIfNo(r.isFacilitiesChildrenClub()), returnTrueIfYesFalseIfNo(r.isAdditionalServiceBoardHalf()),
                    returnTrueIfYesFalseIfNo(r.isAdditionalServiceBoardFull()), returnTrueIfYesFalseIfNo(r.isAdditionalServiceExtraBed()), r.getPricePerNight(), r.getRating(), returnTrueIfYesFalseIfNo(r.isAvailability())
            ));
        }
        this.rooms = rooms;
        return rooms;
    }
    /*
    private ObservableList<Room> getRooms() {
        ObservableList<Room> rooms = FXCollections.observableArrayList();
        for (Room r : sqlProgram.getRooms()) {
            rooms.add(new Room(
                    r.getCity(), r.getRoomSize(), r.getBookingStart(), r.getBookingEnd(), r.getMaxAmountOfCustomers(), r.isFacilitiesRestaurant(),
                    r.isFacilitiesPool(), r.isFacilitiesEveningEntertainment(), r.isFacilitiesChildrenClub(), r.isAdditionalServiceBoardHalf(),
                    r.isAdditionalServiceBoardFull(), r.isAdditionalServiceExtraBed(), r.getPricePerNight(), r.getRating(), r.isAvailability()
            ));
        }
        this.rooms = rooms;
        return rooms;
    }
     */

    private boolean returnTrueIfYesFalseIfNo(String str) {
        if (str.equalsIgnoreCase("yes")) {
            return true;
        }
        return false;
    }

    public void stop() throws Exception {
    }

    /*
    private void roomOptionFacilitiesRestaurant(CheckBox checkBox, TableView tableView) {

        if (checkBox.isSelected()) {
            System.out.println("1 Rest");

            if (listWithFilteredRooms.size() > 0) {
                System.out.println("2 Rest");
                for (Room r : listWithFilteredRooms) {
                    if (r.isFacilitiesRestaurant()) {
                        System.out.println("3 Rest");
                        restaurantFilterSearch.add(r);
                    }
                }
                tableView.setItems(restaurantFilterSearch);
                gatherAllFilterLists();
                return;
            }

            for (Room r : rooms) {
                if (r.isFacilitiesRestaurant()) {
                    System.out.println("4 Rest");
                    restaurantFilterSearch.add(r);
                }
            }
            tableView.setItems(restaurantFilterSearch);
            gatherAllFilterLists();
            return;
        }

        if (!checkBox.isSelected()) {
            System.out.println("5 Rest");
            SC.resetRoomList(restaurantFilterSearch);
            gatherAllFilterLists();

            if (listWithFilteredRooms.size() > 0) {
                System.out.println("6 Rest");
                tableView.setItems(listWithFilteredRooms);
                return;
            }

            tableView.setItems(rooms);
        }
    }
    }*/

}
