package com.company;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Program extends Application {
    private SQLProgram sqlProgram = new SQLProgram();
    private ObservableList<Customer> customersNames = FXCollections.observableArrayList();
    private ObservableList<Room> rooms = FXCollections.observableArrayList();
    private ObservableList<Customer> customers = FXCollections.observableArrayList();
    private ObservableList<Room> listWithFilteredRooms = FXCollections.observableArrayList();
    private Room currentRoom = null;
    private Customer currentCustomer = null;

    private Scene menuMainScene;
    private Scene menuRoomScene;
    private Scene menuRoomSettingsScene;
    private Scene menuAddCustomerScene;
    private Stage stagePrimary;

    private LocalDate localDate = LocalDate.now();
    private Date dateNow = Date.valueOf(localDate);
    private Date dateTomorrow = dateNow;
    private Date dateFrom = dateNow;
    private Date dateTo = dateNow;

    private Boolean stageActive = false;
    private Boolean optionRating = false, optionAvailability = false, optionCheckedFacilityRestaurant = false, optionCheckedFacilityPool = false,
            optionCheckedFacilityEveningEntertainment = false, optionCheckedFacilityChildrenClub = false,
            optionCheckedAddBoardFull = false, optionCheckedAddBoardHalf = false, optionCheckedAddExtraBed = false;
    private int optionRatingValue = 0;


    public void init(Stage stage) throws Exception {
    }

    //-----------------------------------------------------------------------------------------------

    public void start(Stage stage) throws Exception {
        aaaTempRoom();
        ///////////////////////////////////////////
        getRooms();
        getCustomers();
        menuAddUser();
        menuRoom();
        menuRoomChangeRoomSettings();
        ///////////////////////////////////////////

        //System.out.println(currentRoom.size() + " CurrentRoom Size");
        //stageStage();
        //stageWindow(menuRoom());
        //stageWindow(sceneRoomSettings);
        //mainStage();
        //System.out.println("----------------");

        mainStage(menuMain());
    }

    //-----------------------------------------------------------------------------------------------

    private void aaaa() {
        String[] letters = {"Faris", "Erik", "Nils", "Victor", "Patrik", "Jim", "Tobbe", "Mantas", "Shala", "Matthias", "Helena",
                "Sebbe", "Tobias", "Alberts", "Sandra", "Anna", "Lisa", "Johan W", "Benjamin", "Ammar", "Aling", "Soue", "Chang", "Arnold", "Sture", "Hamid", "Shoa", "Annie", "Stao", "Kurre", "Ammar"};
        /*Arrays.stream(letters)
                .filter( x -> x.toLowerCase().startsWith("a"))
                .filter( x -> x.toLowerCase().contains("n"))
                .forEach(System.out::println);*/


    }

    private void aaaTempRoom() {
        currentRoom = new Room(1, "Malmö", 19, dateNow, dateTomorrow, 4,
                true, true, true, true,
                true, true, true,
                12, 9, true);
    }

    private void stageStage() {
        //Stage stage = SC.uniStageMain("TEST STAGE");
        Stage stage = new Stage();
        stage.setTitle("stageStage");
        GridPane grid = SC.uniGrid();
        Scene scene = new Scene(grid);

        Button buttonExit = SC.uniButton("Exit");
        buttonExit.setOnAction(e -> stage.close());

        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText(dateNow.toString());
        datePicker.setOnAction(e -> {
            System.out.println(dateNow.toLocalDate() + " dateNow");
            System.out.println(datePicker.getValue() + " datePicker.getValue()");
            dateTomorrow = Date.valueOf(datePicker.getValue());
            if (dateTomorrow.toLocalDate().isBefore(dateNow.toLocalDate())) {
                System.out.println("Välj annat datum, senare än idag eller samma");
            }
            System.out.println(dateTomorrow + " dateTomorrow");
        });


        grid.add(buttonExit, 0, 0);
        grid.add(datePicker, 0, 1);

        stage.setScene(scene);
        stage.show();
    }

    private void stageWindow(Scene newScene) {
        Stage stage = SC.uniStageMain("TEST STAGE");
        //Stage stage = new Stage();
        GridPane grid = new GridPane();
        Scene scene = newScene;

        grid.setPadding(new Insets(10, 10, 10, 10));

        DatePicker datePicker = new DatePicker();


        grid.add(datePicker, 0, 0);

        stage.setScene(scene);
        stage.show();
        //stageWindow = stage;
    }

    //-----------------------------------------------------------------------------------------------

    private Stage mainStage(Scene newScene) {
        Stage stage = SC.uniStageMain("TEST STAGE");
        Scene scene = newScene;

        stage.setScene(scene);
        stage.show();
        stagePrimary = stage;
        return stage;
    }

    private Stage mainStageTest(Scene newScene) {
        Stage stage = stagePrimary;

        stage.setScene(newScene);
        stagePrimary = stage;
        return stage;
    }

    private Scene menuMain() {
        GridPane grid = SC.uniGrid();

        Button buttonAddUser = SC.uniButton("Add User");
        buttonAddUser.setOnAction(e -> {
            //stagePrimary.setScene(menuAddUser());
            mainStageTest( menuAddUser());
        });

        Button buttonRoomBooking = SC.uniButton("Rooms\nBooking\nCancellation");
        buttonRoomBooking.setOnAction(e -> {
            stagePrimary.setScene(menuRoom());
        });

        Button buttonQuit = SC.uniButton("Quit");
        buttonQuit.setOnAction(e -> stagePrimary.close());

        grid.add(buttonAddUser, 0, 1);
        grid.add(buttonRoomBooking, 0, 2);
        grid.add(buttonQuit, 0, 4);

        Scene scene = SC.uniScene(grid);
        return scene;
    }

    private Scene menuRoom() {
        listWithFilteredRooms = rooms;

        /*GridPane gridMain = SC.uniGrid();
        GridPane gridTop = SC.uniGrid();
        GridPane gridCenter = SC.uniGrid();
        GridPane gridBottom = SC.uniGrid();*/
        GridPane gridMain = SC.uniGrid();
        GridPane gridTop = SC.uniGrid();
        GridPane gridCenter = SC.uniGrid();
        GridPane gridBottom = SC.uniGrid();

        //-----------------------------------------------------------------------------------------TableView

        TableView<Room> tableViewRoom = SC.uniTableView();
        tableViewRoom.setItems(listWithFilteredRooms);
        tableViewRoom.setPlaceholder(SC.uniLabel("Your search didn't match any of our rooms... :("));

        tableViewRoom.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //-----------------------------------------------------------------------------------------TableColumn

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
        TableColumn<Room, Boolean> tableColumnAvailability = new TableColumn("Available");
        tableColumnAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));

        tableViewRoom.getColumns().addAll(tableColumnCity, tableColumnRoomSize, tableColumnBookingStart, tableColumnBookingEnd,
                tableColumnMaxAmountOfCustomers, tableColumnFacilitiesRestaurant, tableColumnFacilitiesPool, tableColumnFacilitiesEveningEntertainment,
                tableColumnFacilitiesChildrenClub, tableColumnAdditionalServiceBoardHalf, tableColumnAdditionalServiceBoardFull,
                tableColumnAdditionalServiceExtraBed, tableColumnPricePerNight, tableColumnRating, tableColumnAvailability);

        //---------------------------------------------------------------------------------------------CheckBox

        CheckBox checkBoxFacilitiesRestaurant = new CheckBox("Restaurant");
        CheckBox checkBoxFacilitiesPool = new CheckBox("Pool");
        CheckBox checkBoxFacilitiesChildrenClub = new CheckBox("Children Club");
        CheckBox checkBoxFacilitiesEveningEntertainment = new CheckBox("Evening Entertainment");
        CheckBox checkBoxAdditionalBoardHalf = new CheckBox("Half board");
        CheckBox checkBoxAdditionalBoardFull = new CheckBox("Full board");
        CheckBox checkBoxAdditionalExtraBed = new CheckBox("Extra Bed");
        CheckBox checkBoxAvailability = new CheckBox("Available");

        checkBoxFacilitiesRestaurant.setOnAction(e -> roomOptionFacilitiesRestaurant(checkBoxFacilitiesRestaurant, tableViewRoom));
        checkBoxFacilitiesPool.setOnAction(e -> roomOptionFacilitiesPool(checkBoxFacilitiesPool, tableViewRoom));
        checkBoxFacilitiesChildrenClub.setOnAction(e -> roomOptionFacilitiesChildrenClub(checkBoxFacilitiesChildrenClub, tableViewRoom));
        checkBoxFacilitiesEveningEntertainment.setOnAction(e -> roomOptionFacilitiesEveningEntertainment(checkBoxFacilitiesEveningEntertainment, tableViewRoom));
        checkBoxAdditionalBoardHalf.setOnAction(e -> roomOptionAdditionalBoardHalf(checkBoxAdditionalBoardHalf, tableViewRoom));
        checkBoxAdditionalBoardFull.setOnAction(e -> roomOptionAdditionalBoardFull(checkBoxAdditionalBoardFull, tableViewRoom));
        checkBoxAdditionalExtraBed.setOnAction(e -> roomOptionAdditionalExtraBed(checkBoxAdditionalExtraBed, tableViewRoom));
        checkBoxAvailability.setOnAction(e -> roomOptionAvailability(checkBoxAvailability, tableViewRoom));

        //--------------------------------------------------------------------------------------------ComboBox

        ComboBox comboBoxRating = new ComboBox();

        comboBoxRating.setPromptText("Rating");
        comboBoxRating.getItems().addAll("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

        comboBoxRating.setOnAction(e -> {
            String ratingValue = comboBoxRating.getValue().toString();
            optionRatingValue = Integer.parseInt(ratingValue);
            roomOptionRating(comboBoxRating, tableViewRoom);
            System.out.println(optionRatingValue + " optionRatingValue");
        });

        //-----------------------------------------------------------------------------------------------Button

        Button buttonCancel = SC.uniButton("Cancel");
        buttonCancel.setOnAction(e -> stagePrimary.setScene(menuMain()));

        Button buttonExit = SC.uniButton("Exit");
        buttonExit.setOnAction(e -> stagePrimary.close());

        tableViewRoom.setOnMouseClicked(e -> {
            if (SC.mouseEventDoubleClick(e)) {
                System.out.println("menuRoom - Uppdaterar currentRoom!");
                currentRoom = tableViewRoom.getSelectionModel().getSelectedItem();
                stagePrimary.setScene(menuRoomChangeRoomSettings());
            }
        });

        //-----------------------------------------------------------------------------------------------

        gridMain.add(gridTop, 0, 0);
        gridMain.add(gridCenter, 0, 1);
        gridMain.add(gridBottom, 0, 2);

        gridTop.add(checkBoxAvailability, 0, 0);
        gridTop.add(checkBoxFacilitiesRestaurant, 1, 0);
        gridTop.add(checkBoxFacilitiesPool, 2, 0);
        gridTop.add(checkBoxFacilitiesChildrenClub, 3, 0);
        gridTop.add(checkBoxFacilitiesEveningEntertainment, 4, 0);
        gridTop.add(checkBoxAdditionalBoardHalf, 0, 1);
        gridTop.add(checkBoxAdditionalBoardFull, 1, 1);
        gridTop.add(checkBoxAdditionalExtraBed, 2, 1);
        gridTop.add(comboBoxRating, 3, 1);

        gridCenter.add(tableViewRoom, 0, 1);

        gridBottom.add(buttonCancel, 0, 0);
        gridBottom.add(buttonExit, 0, 1);

        //------------------------------------------------------------------------------------------return

        Scene scene = SC.uniScene(gridMain);
        return scene;
    }

    private Scene menuRoomChangeRoomSettings() {
        GridPane gridMain = SC.uniGrid();
        GridPane gridTop = SC.uniGrid();
        GridPane gridCenter = SC.uniGrid();
        GridPane gridBottom = SC.uniGrid();

        //--------------------------------------------------------------------------------------------CheckBox

        CheckBox checkBoxExtraBed = new CheckBox();
        checkBoxExtraBed.setText("Extra bed (costs " + (currentRoom.getPricePerNight() / 10) + " kr. extra)");

        //---------------------------------------------------------------------------------------------DatePicker

        DatePicker datePickerBookingFrom = new DatePicker();
        datePickerBookingFrom.setMaxSize(200, 100);
        datePickerBookingFrom.setPromptText("Rent room from this date");
        datePickerBookingFrom.setOnAction(e -> {
            dateFrom = Date.valueOf(datePickerBookingFrom.getValue());
            roomOptionDateBookingFrom();
        });

        DatePicker datePickerBookingTo = new DatePicker();
        datePickerBookingTo.setMaxSize(200, 100);
        datePickerBookingTo.setPromptText("Rent room to this date");
        datePickerBookingTo.setOnAction(e -> {
            dateTo = Date.valueOf(datePickerBookingTo.getValue());
            roomOptionDateBookingTo();
        });

        //------------------------------------------------------------------------------------------Button

        Button buttonSaveSettings = SC.uniButton("Save settings");
        //buttonSaveSettings.setOnAction(e -> stagePrimary.setScene(sceneRoom));

        Button buttonRoomCancel = SC.uniButton("Clear booking");
        //buttonRoomCancel.setOnAction(e -> stagePrimary.setScene(sceneRoom));

        Button buttonSceneCancel = SC.uniButton("Cancel");
        buttonSceneCancel.setOnAction(e -> stagePrimary.setScene(menuRoom()));

        Button buttonSceneExit = SC.uniButton("Exit");
        buttonSceneExit.setOnAction(e -> stagePrimary.close());

        //----------------------------------------------------------------------------------tableViewRenter

        TableView<Customer> tableViewRenter = SC.uniTableView();
        if (currentRoom != null) {

            tableViewRenter.setItems(returnRenters(currentRoom.getId()));

            TableColumn<Customer, Character> tableColumnRenterName = new TableColumn<>("Renter Name");
            tableColumnRenterName.setCellValueFactory(new PropertyValueFactory<>("name"));

            //tableColumnCustomerRoomId.setMinWidth( 30 );
            tableViewRenter.getColumns().addAll(tableColumnRenterName);
            tableViewRenter.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        }
        if (returnRenters(currentRoom.getId()).size() <= 0) {
            tableViewRenter.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
            tableViewRenter.setPlaceholder(SC.uniLabel("This room has no guests... Jet!"));
        }

        tableViewRenter.setOnMouseClicked(e -> {
            System.out.println("1 Inne i tableViewRenter");
            if (SC.mouseEventDoubleClick(e) && (returnRenters(currentRoom.getId()).size() > 0)) {
                System.out.println("2 Inne i tableViewRenter");

                currentCustomer = tableViewRenter.getSelectionModel().getSelectedItem();
                sqlProgram.removeCustomerFromRoom(currentCustomer.getId());

                /*if (returnRenters(currentRoom.getId()).size() < currentRoom.getMaxAmountOfCustomers()) {
                    System.out.println("2 Inne i tableViewRenter");

                }*/
            }
        });

        //--------------------------------------------------------------------------------------tableViewCustomer

        TableView<Customer> tableViewCustomer = SC.uniTableView();
        if (returnRenters(currentRoom.getId()).size() <= currentRoom.getMaxAmountOfCustomers()) {
            tableViewCustomer.setItems(customers);
            tableViewCustomer.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        } else {
            tableViewCustomer.isTableMenuButtonVisible();
            tableViewCustomer.setItems(customers);
            //tableViewCustomer.setSelectionModel(null);
            tableViewCustomer.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
            tableViewCustomer.setPlaceholder(SC.uniLabel("This room has no guests... Jet!"));
        }
        TableColumn<Customer, Character> tableColumnCustomerName = new TableColumn<>("Available Customers");
        tableColumnCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));

        tableViewCustomer.getColumns().addAll(tableColumnCustomerName);

        tableViewCustomer.setOnMouseClicked(e -> {
            System.out.println("1 Inne i customers");

            if (SC.mouseEventDoubleClick(e)) {
                System.out.println("2 Inne i customers");

                if (returnRenters(currentRoom.getId()).size() <= currentRoom.getMaxAmountOfCustomers()) {
                    currentCustomer = tableViewCustomer.getSelectionModel().getSelectedItem();
                    System.out.println(currentCustomer.getName() + "3 currentCustomer i customers");


                    System.out.println("4 utanför for loop customers");
                    for (Customer customer : returnRenters(currentRoom.getId())) {
                        System.out.println("4.1 Inne i for loop customers");
                        if (customer.getId() == currentCustomer.getId()) {
                            System.out.println("5 Inne i for-loop-if customers");
                            SC.uniStageAlert(currentCustomer.getName() + " already rents this room!");
                            return;
                        }
                    }

                    //currentSaveSettingsers.add(currentCustomer);
                    System.out.println("6  customers");
                    sqlProgram.addCustomerToRoom(currentRoom.getId(), currentCustomer.getId());
                    //sqlProgram.refreshMethods();
                    return;
                }

                System.out.println("7  customers");

                SC.uniStageAlert("This room is fully booked!\n\n" +
                        "Please choose another room, or remove renting customer(s)!");
            }
        });

        //-----------------------------------------------------------------------------------------------

        Label label = new Label();
        if (currentRoom != null) {
            label.setText("Welcome to room editing page. Here you can edit rooms, add and remove customers.\n" +
                    "By double clicking on a customer, you can either add or remove her/him, IF the room isnt full yet.\n\n" +
                    "Information about this room:\n" +
                    "City: " + currentRoom.getCity() + "Room size: " + currentRoom.getRoomSize() + "Price/night: " + currentRoom.getPricePerNight() + "\n\n" +
                    "Facilities and additions: " + "\n" +
                    "Rent per night: " + "\n");

            System.out.println(currentRoom.getCity() + " currentRoom.getCity()\nfrom menuSettings");
        }

        //-----------------------------------------------------------------------------------------------

        gridMain.add(gridTop, 0, 0);
        gridMain.add(gridCenter, 0, 1);
        gridMain.add(gridBottom, 0, 2);

        gridTop.setAlignment(Pos.CENTER_LEFT);
        gridTop.add(label, 0, 0);
        gridTop.add(datePickerBookingFrom, 0, 1);
        gridTop.add(datePickerBookingTo, 0, 2);
        gridTop.add(checkBoxExtraBed, 1, 1);
        gridTop.add(buttonSaveSettings, 1, 2);

        gridCenter.add(tableViewRenter, 0, 0);
        gridCenter.add(tableViewCustomer, 1, 0);

        // node, columnIndex, rowIndex, columnSpan, rowSpan:
        gridBottom.add(buttonSceneCancel, 0, 0, 1, 1);
        gridBottom.add(buttonSceneExit, 1, 0, 1, 1);

        //-----------------------------------------------------------------------------------------------

        Scene scene = SC.uniScene(gridMain);
        return scene;
    }

    private Scene menuAddUser() {

        GridPane grid = SC.uniGrid();

        //-----------------------------------------------------------------------------------------Label

        Label labelUsername = SC.uniLabel("Register/add new user");
        Label labelMsg = SC.uniLabel("Register/add new user");

        //------------------------------------------------------------------------------------------TextField

        TextField textFieldUsername = new TextField();
        textFieldUsername.setPromptText("Username");

        //----------------------------------------------------------------------------------------TableView

        TableView<Customer> tableViewCustomer = SC.uniTableView();
        tableViewCustomer.setItems(getCustomers());

        tableViewCustomer.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<Customer, String> tableColumnCustomerName = new TableColumn<>("Name");
        tableColumnCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));

        tableViewCustomer.getColumns().addAll(tableColumnCustomerName);

        //------------------------------------------------------------------------------------------Button

        Button buttonAddUser = SC.uniButton("Add user");
        buttonAddUser.setOnAction(e -> {
            System.out.println(textFieldUsername.getText());
            labelMsg.setText(sqlProgram.registerNewCustomer(textFieldUsername.getText()));
            /*if (!textFieldUsername.getText().isBlank() && textFieldUsername.getText().length() >= 3) {
                customersNames.add(new Customer(textFieldUsername.getText()));
            }*/
            textFieldUsername.clear();
            tableViewCustomer.refresh();
        });

        Button buttonCancel = SC.uniButton("Cancel");
        buttonCancel.setOnAction(e -> stagePrimary.setScene(menuMain()));

        Button buttonExit = SC.uniButton("Exit");
        buttonExit.setOnAction(e -> stagePrimary.close());

        //-------------------------------------------------------------------------------------------GridPane

        grid.add(labelUsername, 0, 0);
        grid.add(textFieldUsername, 0, 1);
        grid.add(labelMsg, 0, 2);
        grid.add(tableViewCustomer, 0, 3);
        grid.add(buttonAddUser, 1, 1);
        grid.add(buttonCancel, 1, 2);
        grid.add(buttonExit, 1, 3);

        //-----------------------------------------------------------------------------------------------

        Scene scene = SC.uniScene(grid);
        menuAddCustomerScene = scene;
        return scene;
    }

    //-----------------------------------------------------------------------------------------------

    private ObservableList<Customer> returnRenters(int roomId) {
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        for (Customer customer : this.customers) {
            if (customer.getRoom() == roomId) {
                customers.add(customer);
            }
        }
        return customers;
    }

    //-----------------------------------------------------------------------------------------------

    private void roomOptionDateBookingFrom() {
        if (dateFrom.toLocalDate().isBefore(dateNow.toLocalDate())) {
            SC.uniStageAlert("Please pick another date that is FROM TODAY and on...");
            return;
        }

        if (dateFrom.toLocalDate().isEqual(dateNow.toLocalDate()) ||
                dateFrom.toLocalDate().isAfter(dateNow.toLocalDate())) {

            sqlProgram.updateRoomBookingStart(dateFrom, currentRoom.getId());
        }
    }

    private void roomOptionDateBookingTo() {

        if (dateTo.toLocalDate().isBefore(dateNow.toLocalDate())) {
            SC.uniStageAlert("Please pick another date that is FROM TODAY and on...");
            return;
        }

        if (dateTo.toLocalDate().isEqual(dateNow.toLocalDate()) ||
                dateTo.toLocalDate().isAfter(dateNow.toLocalDate())) {

            sqlProgram.updateRoomBookingEnd(dateTo, currentRoom.getId());
        }
    }

    private void roomOptionRating(ComboBox comboBox, TableView tableView) {

        if (optionRatingValue > 0) {

            System.out.println("inne i roomOptionRating");
            optionRating = true;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);

        } else {

            System.out.println("inne i !roomOptionRating");
            optionRating = false;
            listWithFilteredRooms = rooms;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);

        }
    }

    private void roomOptionAvailability(CheckBox checkBox, TableView tableView) {
        if (checkBox.isSelected()) {
            System.out.println("inne i optionAvailability");
            optionAvailability = true;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);
        }

        if (!checkBox.isSelected()) {
            System.out.println("inne i !optionAvailability");
            optionAvailability = false;
            listWithFilteredRooms = rooms;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);
        }
    }

    private void roomOptionFacilitiesRestaurant(CheckBox checkBox, TableView tableView) {
        if (checkBox.isSelected()) {
            System.out.println("inne i roomOptionFacilitiesRestaurant");
            optionCheckedFacilityRestaurant = true;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);
        }

        if (!checkBox.isSelected()) {
            System.out.println("inne i !roomOptionFacilitiesRestaurant");
            optionCheckedFacilityRestaurant = false;
            listWithFilteredRooms = rooms;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);
        }
    }

    private void roomOptionFacilitiesPool(CheckBox checkBox, TableView tableView) {
        if (checkBox.isSelected()) {
            System.out.println("inne i roomOptionFacilitiesPool");
            optionCheckedFacilityPool = true;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);
        }

        if (!checkBox.isSelected()) {
            System.out.println("inne i !roomOptionFacilitiesPool");
            optionCheckedFacilityPool = false;
            listWithFilteredRooms = rooms;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);
        }
    }

    private void roomOptionFacilitiesChildrenClub(CheckBox checkBox, TableView tableView) {
        if (checkBox.isSelected()) {
            System.out.println("inne i roomOptionFacilitiesChildrenClub");
            optionCheckedFacilityChildrenClub = true;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);
        }

        if (!checkBox.isSelected()) {
            System.out.println("inne i optionCheckedFacilityChildrenClub");
            optionCheckedFacilityChildrenClub = false;
            listWithFilteredRooms = rooms;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);
        }
    }

    private void roomOptionFacilitiesEveningEntertainment(CheckBox checkBox, TableView tableView) {
        if (checkBox.isSelected()) {
            System.out.println("inne i optionCheckedFacilityEveningEntertainment");
            optionCheckedFacilityEveningEntertainment = true;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);
        }

        if (!checkBox.isSelected()) {
            System.out.println("inne i optionCheckedFacilityEveningEntertainment");
            optionCheckedFacilityEveningEntertainment = false;
            listWithFilteredRooms = rooms;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);
        }
    }

    private void roomOptionAdditionalBoardHalf(CheckBox checkBox, TableView tableView) {
        if (checkBox.isSelected()) {
            System.out.println("inne i optionCheckedAddBoardHalf");
            optionCheckedAddBoardHalf = true;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);
        }

        if (!checkBox.isSelected()) {
            System.out.println("inne i optionCheckedAddBoardHalf");
            optionCheckedAddBoardHalf = false;
            listWithFilteredRooms = rooms;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);
        }
    }

    private void roomOptionAdditionalBoardFull(CheckBox checkBox, TableView tableView) {
        if (checkBox.isSelected()) {
            System.out.println("inne i optionCheckedAddBoardFull");
            optionCheckedAddBoardFull = true;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);
        }

        if (!checkBox.isSelected()) {
            System.out.println("inne i optionCheckedAddBoardFull");
            optionCheckedAddBoardFull = false;
            listWithFilteredRooms = rooms;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);
        }
    }

    private void roomOptionAdditionalExtraBed(CheckBox checkBox, TableView tableView) {
        if (checkBox.isSelected()) {
            System.out.println("inne i optionCheckedAddExtraBed");
            optionCheckedAddExtraBed = true;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);
        }

        if (!checkBox.isSelected()) {
            System.out.println("inne i optionCheckedAddExtraBed");
            optionCheckedAddExtraBed = false;
            listWithFilteredRooms = rooms;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);
        }
    }

    //------------------------------------------------------------------------------------------------------------------

    private void checkWhichOptionsAreChecked() {

        if (optionRating == true) {
            ObservableList<Room> tempList = FXCollections.observableArrayList();
            listWithFilteredRooms = rooms;
            for (Room room : listWithFilteredRooms) {
                if (room.getRating() <= optionRatingValue) {
                    System.out.println("getRating");
                    //listWithFilteredRooms.add(room);
                    tempList.add(room);
                    //optionAvailability = false;
                }
            }
            listWithFilteredRooms = tempList;
        }

        if (optionAvailability == true) {
            ObservableList<Room> tempList = FXCollections.observableArrayList();
            for (Room room : listWithFilteredRooms) {
                if (room.isAvailability().equalsIgnoreCase("yes")) {
                    System.out.println("isAvailability");
                    //listWithFilteredRooms.add(room);
                    tempList.add(room);
                    //optionAvailability = false;
                }
            }
            listWithFilteredRooms = tempList;
        }

        if (optionCheckedFacilityRestaurant == true) {
            ObservableList<Room> tempList = FXCollections.observableArrayList();
            for (Room room : listWithFilteredRooms) {
                if (room.isFacilitiesRestaurant().equalsIgnoreCase("yes")) {
                    System.out.println("isFacilitiesRestaurant");
                    //listWithFilteredRooms.add(room);
                    tempList.add(room);
                    //optionCheckedFacilityRestaurant = false;
                }
            }
            listWithFilteredRooms = tempList;
        }

        if (optionCheckedFacilityPool == true) {
            ObservableList<Room> tempList = FXCollections.observableArrayList();
            for (Room room : listWithFilteredRooms) {
                if (room.isFacilitiesPool().equalsIgnoreCase("yes")) {
                    System.out.println("isFacilitiesPool");
                    //listWithFilteredRooms.add(room);
                    tempList.add(room);
                    //optionCheckedFacilityPool = false;
                }
            }
            listWithFilteredRooms = tempList;
        }

        if (optionCheckedFacilityChildrenClub == true) {
            ObservableList<Room> tempList = FXCollections.observableArrayList();
            for (Room room : listWithFilteredRooms) {
                if (room.isFacilitiesChildrenClub().equalsIgnoreCase("yes")) {
                    System.out.println("isFacilitiesChildrenClub");
                    //listWithFilteredRooms.add(room);
                    tempList.add(room);
                    //optionCheckedFacilityChildrenClub = false;
                }
            }
            listWithFilteredRooms = tempList;
        }

        if (optionCheckedFacilityEveningEntertainment == true) {
            ObservableList<Room> tempList = FXCollections.observableArrayList();
            for (Room room : listWithFilteredRooms) {
                if (room.isFacilitiesEveningEntertainment().equalsIgnoreCase("yes")) {
                    System.out.println("isFacilitiesEveningEntertainment");
                    //listWithFilteredRooms.add(room);
                    tempList.add(room);
                    //optionCheckedFacilityEveningEntertainment = false;
                }
            }
            listWithFilteredRooms = tempList;
        }

        if (optionCheckedAddBoardFull == true) {
            ObservableList<Room> tempList = FXCollections.observableArrayList();
            for (Room room : listWithFilteredRooms) {
                if (room.isAdditionalServiceBoardFull().equalsIgnoreCase("yes")) {
                    System.out.println("optionCheckedAddBoardFull");
                    //listWithFilteredRooms.add(room);
                    tempList.add(room);
                    //optionCheckedAddBoardFull = false;
                }
            }
            listWithFilteredRooms = tempList;
        }

        if (optionCheckedAddBoardHalf == true) {
            ObservableList<Room> tempList = FXCollections.observableArrayList();
            for (Room room : listWithFilteredRooms) {
                if (room.isAdditionalServiceBoardHalf().equalsIgnoreCase("yes")) {
                    System.out.println("optionCheckedAddBoardHalf");
                    //listWithFilteredRooms.add(room);
                    tempList.add(room);
                    //optionCheckedAddBoardHalf = false;
                }
            }
            listWithFilteredRooms = tempList;
        }

        if (optionCheckedAddExtraBed == true) {
            ObservableList<Room> tempList = FXCollections.observableArrayList();
            for (Room room : listWithFilteredRooms) {
                if (room.isAdditionalServiceExtraBed().equalsIgnoreCase("yes")) {
                    System.out.println("optionCheckedAddExtraBed");
                    //listWithFilteredRooms.add(room);
                    tempList.add(room);
                    //optionCheckedAddExtraBed = false;
                }
            }
            listWithFilteredRooms = tempList;
        }


    }

    //------------------------------------------------------------------------------------------------------------------

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

    private void getRooms() {
        ObservableList<Room> rooms = FXCollections.observableArrayList();
        for (Room room : sqlProgram.getRooms()) {
            rooms.add(new Room(
                    room.getId(), room.getCity(), room.getRoomSize(), room.getBookingStart(), room.getBookingEnd(), room.getMaxAmountOfCustomers(), returnTrueIfYesFalseIfNo(room.isFacilitiesRestaurant()),
                    returnTrueIfYesFalseIfNo(room.isFacilitiesPool()), returnTrueIfYesFalseIfNo(room.isFacilitiesEveningEntertainment()), returnTrueIfYesFalseIfNo(room.isFacilitiesChildrenClub()), returnTrueIfYesFalseIfNo(room.isAdditionalServiceBoardHalf()),
                    returnTrueIfYesFalseIfNo(room.isAdditionalServiceBoardFull()), returnTrueIfYesFalseIfNo(room.isAdditionalServiceExtraBed()), room.getPricePerNight(), room.getRating(), returnTrueIfYesFalseIfNo(room.isAvailability())
            ));
        }
        this.rooms = rooms;
    }

    //------------------------------------------------------------------------------------------------------------------

    private boolean returnTrueIfYesFalseIfNo(String str) {
        if (str.equalsIgnoreCase("yes")) {
            return true;
        }
        return false;
    }

    //------------------------------------------------------------------------------------------------------------------

    public void stop() throws Exception {
    }

    //------------------------------------------------------------------------------------------------------------------


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

    /*private void miscMethods(){
        ObservableList<Room> newList =
                rooms.stream()
                        .filter(x -> x.isFacilitiesRestaurant().equals("Yes"))
                        .collect(Collectors.collectingAndThen(toList(), l -> FXCollections.observableArrayList(l)));

        tableView.setItems(newList);
    }*/

}
