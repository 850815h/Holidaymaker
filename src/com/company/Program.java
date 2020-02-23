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
import javafx.util.StringConverter;

import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Program extends Application {
    private SQLProgram sqlProgram = new SQLProgram();
    private ObservableList<Customer> customers = FXCollections.observableArrayList();
    private ObservableList<Customer> tempCustomers = FXCollections.observableArrayList();
    private ObservableList<Room> rooms = FXCollections.observableArrayList();
    private ObservableList<Room> listWithFilteredRooms = FXCollections.observableArrayList();
    private Room currentRoom = null;
    private Customer currentCustomer = null;

    private Stage stagePrimary;
    private Stage stageWindow;

    private LocalDate localDate = LocalDate.now();
    private Date dateNow = Date.valueOf(localDate);
    private Date dateTomorrow = dateNow;
    //private Date dateTomorrow = currentRoom.getBookingEnd();
    private Date dateFrom = dateNow;
    private Date dateTo = dateNow;

    private Boolean boolSettingsChangedButNoSaved = false;
    private Boolean stageActive = false;
    private Boolean optionDistanceToCity = false, optionDistanceToBeach = false, optionRating = false, optionAvailability = false, optionCheckedFacilityRestaurant = false, optionCheckedFacilityPool = false,
            optionCheckedFacilityEveningEntertainment = false, optionCheckedFacilityChildrenClub = false,
            optionCheckedAddBoardFull = false, optionCheckedAddBoardHalf = false, optionCheckedAddExtraBed = false;
    private int optionRatingValue = 0;

    private DecimalFormat df2 = new DecimalFormat("#.##");
    private double distanceToCity = 0;
    private double distanceToBeach = 0;


    public void init(Stage stage) throws Exception {
    }

    //-----------------------------------------------------------------------------------------------

    public void start(Stage stage) throws Exception {
        getRooms();
        getCustomers();
        aaaTempRoom();
        ///////////////////////////////////////////
        ///////////////////////////////////////////

        mainStage(menuMain());
    }

    //-----------------------------------------------------------------------------------------------

    private void aaaTempRoom() {
        currentRoom = new Room(1, "Malmö", 19, dateNow, sqlProgram.getRooms().get(8).getBookingEnd(), 6,
                true, true, true, true,
                true, true, true,
                12, 9, 12, 4, false);

        tempCustomers.add(new Customer(1, "Erik", 1));
        tempCustomers.add(new Customer(2, "Faris", 1));
        tempCustomers.add(new Customer(3, "Ommah", 1));
        tempCustomers.add(new Customer(4, "Sosi", 1));
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
            mainStageTest(menuAddUser());
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
        //Distance To city
        TableColumn<Room, Double> tableColumnDistanceToCity = new TableColumn("Distance To\nCity");
        tableColumnDistanceToCity.setCellValueFactory(new PropertyValueFactory<>("distanceToCity"));
        ///Distance To beach
        TableColumn<Room, Double> tableColumnDistanceToBeach = new TableColumn("Distance To\nBeach");
        tableColumnDistanceToBeach.setCellValueFactory(new PropertyValueFactory<>("distanceToBeach"));
        //Rooms Availability
        TableColumn<Room, Boolean> tableColumnAvailability = new TableColumn("Available");
        tableColumnAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));

        tableViewRoom.getColumns().addAll(tableColumnCity, tableColumnRoomSize, tableColumnBookingStart, tableColumnBookingEnd,
                tableColumnMaxAmountOfCustomers, tableColumnFacilitiesRestaurant, tableColumnFacilitiesPool, tableColumnFacilitiesEveningEntertainment,
                tableColumnFacilitiesChildrenClub, tableColumnAdditionalServiceBoardHalf, tableColumnAdditionalServiceBoardFull,
                tableColumnAdditionalServiceExtraBed, tableColumnPricePerNight, tableColumnRating, tableColumnDistanceToCity, tableColumnDistanceToBeach, tableColumnAvailability);

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


        //------------------------------------------------------------------------------------------Slider

        Slider sliderDistanceToCity = SC.uniSlider(0, 20, 0, 1);
        sliderDistanceToCity.setOnDragDetected(e -> {
            distanceToCity = sliderDistanceToCity.getValue();
            roomOptionDistanceToCity(tableViewRoom);
        });

        sliderDistanceToCity.setOnMouseClicked(e -> {
            distanceToCity = sliderDistanceToCity.getValue();
            roomOptionDistanceToCity(tableViewRoom);
        });

        Slider sliderDistanceToBeach = SC.uniSlider(0, 20, 0, 1);
        sliderDistanceToBeach.setOnDragDetected(e -> {
            distanceToBeach = sliderDistanceToBeach.getValue();
            roomOptionDistanceToBeach(tableViewRoom);
        });

        sliderDistanceToBeach.setOnMouseClicked(e -> {
            distanceToBeach = sliderDistanceToBeach.getValue();
            roomOptionDistanceToBeach(tableViewRoom);
        });

        //-----------------------------------------------------------------------------------------------

        Label labelDistanceToCity = SC.uniLabel("Distance to city in km");
        Label labelDistanceToBeach = SC.uniLabel("Distance to beach in km");

        //-----------------------------------------------------------------------------------------------


        gridMain.add(gridTop, 0, 0);
        gridMain.add(gridCenter, 0, 1);
        gridMain.add(gridBottom, 0, 2);

        gridTop.add(checkBoxAvailability, 0, 0);
        gridTop.add(labelDistanceToCity, 1, 0);
        gridTop.add(labelDistanceToBeach, 2, 0);
        gridTop.add(checkBoxFacilitiesRestaurant, 3, 0);

        gridTop.add(comboBoxRating, 0, 1);
        gridTop.add(sliderDistanceToCity, 1, 1);
        gridTop.add(sliderDistanceToBeach, 2, 1);
        gridTop.add(checkBoxFacilitiesPool, 3, 1);

        gridTop.add(checkBoxFacilitiesChildrenClub, 0, 2);
        gridTop.add(checkBoxFacilitiesEveningEntertainment, 1, 2);
        gridTop.add(checkBoxAdditionalBoardHalf, 2, 2);
        gridTop.add(checkBoxAdditionalBoardFull, 3, 2);
        gridTop.add(checkBoxAdditionalExtraBed, 4, 2);

        gridCenter.add(tableViewRoom, 0, 1);

        gridBottom.add(buttonCancel, 0, 0);
        gridBottom.add(buttonExit, 1, 0);

        //------------------------------------------------------------------------------------------return

        Scene scene = SC.uniScene(gridMain);
        return scene;
    }

    private Scene menuRoomChangeRoomSettings() {
        customers = getCustomers();

        GridPane gridMain = SC.uniGrid();
        GridPane gridTop = SC.uniGrid();
        GridPane gridCenter = SC.uniGrid();
        GridPane gridBottom = SC.uniGrid();

        //--------------------------------------------------------------------------------------------CheckBox

        CheckBox checkBoxHalfBoard = new CheckBox();
        if (currentRoom.isAdditionalServiceBoardHalf().equals("Available")) {
            checkBoxHalfBoard.setDisable(true);
            checkBoxHalfBoard.setSelected(true);
            checkBoxHalfBoard.setText("Half board for " + df2.format(currentRoom.getPricePerNight() / 10) + " kr. included");
        } else {
            checkBoxHalfBoard.setDisable(true);
            checkBoxHalfBoard.setText("Half board is not available for this room ;(");
        }

        CheckBox checkBoxFullBoard = new CheckBox();
        if (currentRoom.isAdditionalServiceBoardFull().equals("Available")) {
            checkBoxFullBoard.setDisable(true);
            checkBoxFullBoard.setSelected(true);
            checkBoxFullBoard.setText("Full board for " + df2.format(currentRoom.getPricePerNight() / 5) + " kr. included");
        } else {
            checkBoxFullBoard.setDisable(true);
            checkBoxFullBoard.setText("Full board is not available for this room ;(");
        }

        CheckBox checkBoxExtraBed = new CheckBox();
        if (currentRoom.isAdditionalServiceExtraBed().equals("Available")) {
            checkBoxExtraBed.setDisable(true);
            checkBoxExtraBed.setSelected(true);
            checkBoxExtraBed.setText("Extra bed for " + df2.format(currentRoom.getPricePerNight() / 20) + " kr. included");
        } else {
            checkBoxExtraBed.setDisable(true);
            checkBoxExtraBed.setText("Extra bed is not available for this room ;(");
        }


        //---------------------------------------------------------------------------------------------DatePicker

        DatePicker datePickerBookingFrom = SC.datePicker();
        datePickerBookingFrom.setMaxSize(200, 100);
        if (currentRoom.isAvailability().equals("Not available")) {
            datePickerBookingFrom.setPromptText("Booked from " + currentRoom.getBookingStart().toString());
        } else {
            datePickerBookingFrom.setPromptText("Set a new booking-start date");
        }
        datePickerBookingFrom.setOnAction(e -> {
            dateFrom = Date.valueOf(datePickerBookingFrom.getValue());

            if (dateFrom.toLocalDate().isEqual(dateNow.toLocalDate())) {
                SC.uniStageAlert("Why change it to its already booked date? :/");
                return;
            }

            if (dateFrom.toLocalDate().isBefore(dateNow.toLocalDate())) {
                SC.uniStageAlert("Please pick another date that is FROM TODAY and on...");
                return;
            }

            if (dateFrom.toLocalDate().isAfter(dateTo.toLocalDate())) {
                SC.uniStageAlert("How is it even possible to begin your vacation\n" +
                        "before your start-date? Back to the future vibes? ;)");
                return;
            }

            roomOptionDateBookingFrom();
        });

        DatePicker datePickerBookingTo = SC.datePicker();
        datePickerBookingTo.setMaxSize(200, 100);
        if (currentRoom.isAvailability().equals("Not available")) {
            datePickerBookingTo.setPromptText("Booked to " + currentRoom.getBookingEnd().toString());
        } else {
            datePickerBookingTo.setPromptText("Set a new booking-end date");
        }
        datePickerBookingTo.setOnAction(e -> {
            dateTo = Date.valueOf(datePickerBookingTo.getValue());

            if (dateTo.toLocalDate().isBefore(dateNow.toLocalDate())) {
                SC.uniStageAlert("Try again, and this time pick a date FROM today's date and so on!");
                return;
            }

            if (dateTo.toLocalDate().isBefore(dateFrom.toLocalDate())) {
                SC.uniStageAlert("Are you trying to bend time,\n" +
                        "or just trying to crash this awesome app ;)\n" +
                        "How will you end your not-yet-started vacation?");
                return;
            }


            roomOptionDateBookingTo();
        });

        //------------------------------------------------------------------------------------------Button

        Button buttonCancel = SC.uniButton("Cancel");
        buttonCancel.setOnAction(e -> {
            stagePrimary.setScene(menuRoom());
        });

        Button buttonSceneExit = SC.uniButton("Exit");
        buttonSceneExit.setOnAction(e -> {
            stagePrimary.close();
        });

        //----------------------------------------------------------------------------------tableViewRenter

        TableView<Customer> tableViewRenter = SC.uniTableView();
        if (currentRoom != null) {

            tableViewRenter.setItems(returnRenters(currentRoom.getId())); //show customers in currentRoom

            TableColumn<Customer, Character> tableColumnRenterName = new TableColumn<>("Renter Name");
            tableColumnRenterName.setCellValueFactory(new PropertyValueFactory<>("name"));

            tableViewRenter.getColumns().addAll(tableColumnRenterName);
        }

        if (returnRenters(currentRoom.getId()).size() <= 0) {
            tableViewRenter.setPlaceholder(SC.uniLabel("This room has no guests... Jet!"));
        }

        tableViewRenter.setOnMouseClicked(e -> {
            if (SC.mouseEventDoubleClick(e) && (returnRenters(currentRoom.getId()).size() > 0)) {

                currentCustomer = tableViewRenter.getSelectionModel().getSelectedItem();
                currentCustomer.setRoom(0);
                //returnRenters(currentRoom.getId());
                tableViewRenter.setItems(returnRenters(currentRoom.getId()));

                sqlProgram.removeCustomerFromRoom(currentCustomer.getId());
            }
        });

        //--------------------------------------------------------------------------------------tableViewCustomer

        TableView<Customer> tableViewCustomer = SC.uniTableView();
        if (returnRenters(currentRoom.getId()).size() < currentRoom.getMaxAmountOfCustomers()) {
            tableViewCustomer.setItems(customers);
        } else {
            tableViewCustomer.setItems(customers);
            tableViewCustomer.setPlaceholder(SC.uniLabel("This room has no guests... Jet!"));
        }

        TableColumn<Customer, Character> tableColumnCustomerName = new TableColumn<>("Available Customers");
        tableColumnCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));

        tableViewCustomer.getColumns().addAll(tableColumnCustomerName);

        tableViewCustomer.setOnMouseClicked(e -> {

            if (SC.mouseEventDoubleClick(e)) {

                if (returnRenters(currentRoom.getId()).size() < currentRoom.getMaxAmountOfCustomers()) {

                    currentCustomer = tableViewCustomer.getSelectionModel().getSelectedItem();

                    for (Customer customerRoomRenter : returnRenters(currentRoom.getId())) {
                        if (customerRoomRenter.getName().equals(currentCustomer.getName())) {
                            SC.uniStageAlert(currentCustomer.getName() + " already rents this room!");
                            return;
                        }
                    }

                    sqlProgram.addCustomerToRoom(currentRoom.getId(), currentCustomer.getId());
                    currentCustomer.setRoom(currentRoom.getId());
                    tableViewRenter.setItems(returnRenters(currentRoom.getId()));
                    return;
                }

                SC.uniStageAlert("This room is fully booked!\n\n" +
                        "Please choose another room, or remove renting customer(s)!");
            }
        });

        //-----------------------------------------------------------------------------------------------

        Label label = SC.uniLabel("");
        if (currentRoom != null) {
            label.setText("Double clicking on a customer, you can either add or remove her/him.\n" +
                    "Information about this room:\n" +
                    "City: " + currentRoom.getCity() + " Room size: " + currentRoom.getRoomSize() + "Price/night: " + currentRoom.getPricePerNight() + "\n\n" +
                    "Facilities and additions: " + "\n" +
                    "Rent per night: " + "\n");
        }

        //-----------------------------------------------------------------------------------------------

        gridMain.add(gridTop, 0, 0);
        gridMain.add(gridCenter, 0, 1);
        gridMain.add(gridBottom, 0, 2);

        gridTop.setAlignment(Pos.CENTER_LEFT);
        gridTop.add(label, 0, 0);

        gridCenter.add(datePickerBookingFrom, 0, 0);
        gridCenter.add(datePickerBookingTo, 0, 1);
        gridCenter.add(tableViewRenter, 0, 3);
        gridCenter.add(checkBoxHalfBoard, 1, 0);
        gridCenter.add(checkBoxFullBoard, 1, 1);
        gridCenter.add(checkBoxExtraBed, 1, 2);
        gridCenter.add(tableViewCustomer, 1, 3);

        gridBottom.add(buttonCancel, 0, 0, 1, 1);
        gridBottom.add(buttonSceneExit, 1, 0, 1, 1);

        //-----------------------------------------------------------------------------------------------

        Scene scene = SC.uniScene(gridMain);
        return scene;
    }

    private Scene menuAddUser() {
        customers = getCustomers();

        GridPane grid = SC.uniGrid();

        //-----------------------------------------------------------------------------------------Label

        Label labelMsg = SC.uniLabel("Any new customers?...");

        //------------------------------------------------------------------------------------------TextField

        TextField textFieldUsername = new TextField();
        textFieldUsername.setPromptText("Username");

        //----------------------------------------------------------------------------------------TableView

        TableView<Customer> tableViewCustomer = SC.uniTableView();
        tableViewCustomer.setItems(customers);

        TableColumn<Customer, String> tableColumnCustomerName = new TableColumn<>("Name");
        tableColumnCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));

        tableViewCustomer.getColumns().addAll(tableColumnCustomerName);

        //------------------------------------------------------------------------------------------Button

        Button buttonAddUser = SC.uniButton("Add user");
        buttonAddUser.setOnAction(e -> {

            String addedMsg = sqlProgram.registerNewCustomer(textFieldUsername.getText());
            if (addedMsg.contains("already")) {
                labelMsg.setText(textFieldUsername.getText() + " is already registered!");
                return;
            }

            if (textFieldUsername.getLength() < 3 || addedMsg.isBlank()) {
                labelMsg.setText("Try again with at least 3 symbols!");
                return;
            }

            sqlProgram.registerNewCustomer(textFieldUsername.getText());
            sqlProgram.refreshMethods();
            stagePrimary.setScene(menuAddUser());
        });

        Button buttonCancel = SC.uniButton("Cancel");
        buttonCancel.setOnAction(e -> stagePrimary.setScene(menuMain()));

        Button buttonExit = SC.uniButton("Exit");
        buttonExit.setOnAction(e -> stagePrimary.close());

        //-------------------------------------------------------------------------------------------GridPane

        grid.add(labelMsg, 0, 0, 2, 1);
        grid.add(textFieldUsername, 0, 1, 1, 1);
        grid.add(buttonAddUser, 1, 1, 1, 1);
        grid.add(tableViewCustomer, 0, 3, 2, 1);
        grid.add(buttonCancel, 0, 4, 1, 1);
        grid.add(buttonExit, 1, 4, 1, 1);

        //-----------------------------------------------------------------------------------------------

        Scene scene = SC.uniScene(grid);
        //sceneAddCustomerScene = scene;
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

        SC.uniStageAlert("You have changed the booking start-date to:\n" +
                dateFrom);

        dateFrom = Date.valueOf(dateFrom.toLocalDate().plusDays(1));
        sqlProgram.updateRoomBookingStart(dateFrom, currentRoom.getId());
        sqlProgram.updateRoomAvailability(currentRoom.getId(), 0);
    }

    private void roomOptionDateBookingTo() {
        SC.uniStageAlert("You have changed the booking end-date to:\n" +
                dateTo);

        dateTo = Date.valueOf(dateTo.toLocalDate().plusDays(1));

        sqlProgram.updateRoomBookingEnd(dateTo, currentRoom.getId());
        sqlProgram.updateRoomAvailability(currentRoom.getId(), 0);
    }

    private void roomOptionDistanceToBeach(TableView tableView) {

        if (distanceToBeach >= 1) {

            optionDistanceToBeach = true;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);

        } else {

            optionDistanceToBeach = false;
            listWithFilteredRooms = rooms;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);

        }
    }

    private void roomOptionDistanceToCity(TableView tableView) {
        if (distanceToCity >= 1) {

            optionDistanceToCity = true;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);

        } else {

            optionDistanceToCity = false;
            listWithFilteredRooms = rooms;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);

        }
    }

    private void roomOptionRating(ComboBox comboBox, TableView tableView) {

        if (optionRatingValue > 0) {

            optionRating = true;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);

        } else {

            optionRating = false;
            listWithFilteredRooms = rooms;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);

        }
    }

    private void roomOptionAvailability(CheckBox checkBox, TableView tableView) {
        if (checkBox.isSelected()) {
            optionAvailability = true;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);
        }

        if (!checkBox.isSelected()) {
            optionAvailability = false;
            listWithFilteredRooms = rooms;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);
        }
    }

    private void roomOptionFacilitiesRestaurant(CheckBox checkBox, TableView tableView) {
        if (checkBox.isSelected()) {
            optionCheckedFacilityRestaurant = true;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);
        }

        if (!checkBox.isSelected()) {
            optionCheckedFacilityRestaurant = false;
            listWithFilteredRooms = rooms;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);
        }
    }

    private void roomOptionFacilitiesPool(CheckBox checkBox, TableView tableView) {
        if (checkBox.isSelected()) {
            optionCheckedFacilityPool = true;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);
        }

        if (!checkBox.isSelected()) {
            optionCheckedFacilityPool = false;
            listWithFilteredRooms = rooms;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);
        }
    }

    private void roomOptionFacilitiesChildrenClub(CheckBox checkBox, TableView tableView) {
        if (checkBox.isSelected()) {
            optionCheckedFacilityChildrenClub = true;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);
        }

        if (!checkBox.isSelected()) {
            optionCheckedFacilityChildrenClub = false;
            listWithFilteredRooms = rooms;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);
        }
    }

    private void roomOptionFacilitiesEveningEntertainment(CheckBox checkBox, TableView tableView) {
        if (checkBox.isSelected()) {
            optionCheckedFacilityEveningEntertainment = true;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);
        }

        if (!checkBox.isSelected()) {
            optionCheckedFacilityEveningEntertainment = false;
            listWithFilteredRooms = rooms;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);
        }
    }

    private void roomOptionAdditionalBoardHalf(CheckBox checkBox, TableView tableView) {
        if (checkBox.isSelected()) {
            optionCheckedAddBoardHalf = true;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);
        }

        if (!checkBox.isSelected()) {
            optionCheckedAddBoardHalf = false;
            listWithFilteredRooms = rooms;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);
        }
    }

    private void roomOptionAdditionalBoardFull(CheckBox checkBox, TableView tableView) {
        if (checkBox.isSelected()) {
            optionCheckedAddBoardFull = true;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);
        }

        if (!checkBox.isSelected()) {
            optionCheckedAddBoardFull = false;
            listWithFilteredRooms = rooms;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);
        }
    }

    private void roomOptionAdditionalExtraBed(CheckBox checkBox, TableView tableView) {
        if (checkBox.isSelected()) {
            optionCheckedAddExtraBed = true;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);
        }

        if (!checkBox.isSelected()) {
            optionCheckedAddExtraBed = false;
            listWithFilteredRooms = rooms;
            checkWhichOptionsAreChecked();
            tableView.setItems(listWithFilteredRooms);
        }
    }

    //------------------------------------------------------------------------------------------------------------------

    private void checkWhichOptionsAreChecked() {

        if (optionDistanceToBeach == true) {
            ObservableList<Room> tempList = FXCollections.observableArrayList();
            listWithFilteredRooms = rooms;
            for (Room room : listWithFilteredRooms) {
                if (room.getDistanceToBeach() <= distanceToBeach) {
                    tempList.add(room);
                }
            }
            listWithFilteredRooms = tempList;
        }

        if (optionDistanceToCity == true) {
            ObservableList<Room> tempList = FXCollections.observableArrayList();
            listWithFilteredRooms = rooms;
            for (Room room : listWithFilteredRooms) {
                if (room.getDistanceToCity() <= distanceToCity) {
                    tempList.add(room);
                }
            }
            listWithFilteredRooms = tempList;
        }

        if (optionRating == true) {
            ObservableList<Room> tempList = FXCollections.observableArrayList();
            listWithFilteredRooms = rooms;
            for (Room room : listWithFilteredRooms) {
                if (room.getRating() <= optionRatingValue) {
                    tempList.add(room);
                }
            }
            listWithFilteredRooms = tempList;
        }

        if (optionAvailability == true) {
            ObservableList<Room> tempList = FXCollections.observableArrayList();
            for (Room room : listWithFilteredRooms) {
                if (room.isAvailability().equalsIgnoreCase("available")) {
                    tempList.add(room);
                }
            }
            listWithFilteredRooms = tempList;
        }

        if (optionCheckedFacilityRestaurant == true) {
            ObservableList<Room> tempList = FXCollections.observableArrayList();
            for (Room room : listWithFilteredRooms) {
                if (room.isFacilitiesRestaurant().equalsIgnoreCase("available")) {
                    tempList.add(room);
                }
            }
            listWithFilteredRooms = tempList;
        }

        if (optionCheckedFacilityPool == true) {
            ObservableList<Room> tempList = FXCollections.observableArrayList();
            for (Room room : listWithFilteredRooms) {
                if (room.isFacilitiesPool().equalsIgnoreCase("available")) {
                    tempList.add(room);
                }
            }
            listWithFilteredRooms = tempList;
        }

        if (optionCheckedFacilityChildrenClub == true) {
            ObservableList<Room> tempList = FXCollections.observableArrayList();
            for (Room room : listWithFilteredRooms) {
                if (room.isFacilitiesChildrenClub().equalsIgnoreCase("available")) {
                    tempList.add(room);
                }
            }
            listWithFilteredRooms = tempList;
        }

        if (optionCheckedFacilityEveningEntertainment == true) {
            ObservableList<Room> tempList = FXCollections.observableArrayList();
            for (Room room : listWithFilteredRooms) {
                if (room.isFacilitiesEveningEntertainment().equalsIgnoreCase("available")) {
                    tempList.add(room);
                }
            }
            listWithFilteredRooms = tempList;
        }

        if (optionCheckedAddBoardFull == true) {
            ObservableList<Room> tempList = FXCollections.observableArrayList();
            for (Room room : listWithFilteredRooms) {
                if (room.isAdditionalServiceBoardFull().equalsIgnoreCase("available")) {
                    tempList.add(room);
                }
            }
            listWithFilteredRooms = tempList;
        }

        if (optionCheckedAddBoardHalf == true) {
            ObservableList<Room> tempList = FXCollections.observableArrayList();
            for (Room room : listWithFilteredRooms) {
                if (room.isAdditionalServiceBoardHalf().equalsIgnoreCase("available")) {
                    tempList.add(room);
                }
            }
            listWithFilteredRooms = tempList;
        }

        if (optionCheckedAddExtraBed == true) {
            ObservableList<Room> tempList = FXCollections.observableArrayList();
            for (Room room : listWithFilteredRooms) {
                if (room.isAdditionalServiceExtraBed().equalsIgnoreCase("available")) {
                    tempList.add(room);
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
        return customers;
    }

    private void getRooms() {
        ObservableList<Room> rooms = FXCollections.observableArrayList();
        for (Room room : sqlProgram.getRooms()) {
            rooms.add(new Room(
                    room.getId(), room.getCity(), room.getRoomSize(), room.getBookingStart(), room.getBookingEnd(), room.getMaxAmountOfCustomers(), returnTrueIfYesFalseIfNo(room.isFacilitiesRestaurant()),
                    returnTrueIfYesFalseIfNo(room.isFacilitiesPool()), returnTrueIfYesFalseIfNo(room.isFacilitiesEveningEntertainment()), returnTrueIfYesFalseIfNo(room.isFacilitiesChildrenClub()), returnTrueIfYesFalseIfNo(room.isAdditionalServiceBoardHalf()),
                    returnTrueIfYesFalseIfNo(room.isAdditionalServiceBoardFull()), returnTrueIfYesFalseIfNo(room.isAdditionalServiceExtraBed()), room.getPricePerNight(), room.getRating(),
                    room.getDistanceToCity(), room.getDistanceToBeach(), returnTrueIfYesFalseIfNo(room.isAvailability())
            ));
        }
        this.rooms = rooms;
    }

    //------------------------------------------------------------------------------------------------------------------

    private boolean returnTrueIfYesFalseIfNo(String str) {
        if (str.equalsIgnoreCase("available")) {
            return true;
        }
        return false;
    }

    /*private boolean returnTrueIfYesFalseIfNo(String str) {
        if (str.equalsIgnoreCase("yes")) {
            return true;
        }
        return false;
    }*/

    //------------------------------------------------------------------------------------------------------------------

    public void stop() throws Exception {
    }

    //------------------------------------------------------------------------------------------------------------------


}
