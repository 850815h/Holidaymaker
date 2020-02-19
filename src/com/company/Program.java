package com.company;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Program extends Application {
    private SQLProgram sqlProgram = new SQLProgram();
    private ArrayList<String> customersNames = sqlProgram.getUserNames();
    private Scanner scanner = new Scanner(System.in);
    private Stage stagePrimary;
    private Scene sceneMain;
    private Scene sceneAddUser;
    private Scene sceneRoomCancel;
    private Scene sceneRoomBooking;

    public void init(Stage stage) throws Exception {
    }
    public void start(Stage stage) throws Exception {
        menuAddUser();
        ///////////////////////////////////////////

        menuMain();
    }

    private ObservableList<Customer> getCustomer(){
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        customers.add( new Customer( "Ommah", 59 ));
        customers.add( new Customer( "Abbas", 32 ));
        customers.add( new Customer( "Zeineb", 30 ));
        return customers;
    }

    private void menuAddUser(){
        Stage stagea = SC.uniStageMain("Add User");
        Stage stage = new Stage();
        stage.setTitle("Add User");
        GridPane grid = new GridPane();
        Scene scene = new Scene( grid );

        grid.setMinSize(333, 333 );
        grid.setPadding( new Insets(10, 10, 10, 10 ));
        grid.setVgap(8);
        grid.setHgap(10);

        /*//Password
        Label passwordLabel = new Label("Password");
        GridPane.setConstraints( passwordLabel, 0, 1 );
        TextField passwordTextField = new TextField();
        passwordTextField.setPromptText("Password");
        GridPane.setConstraints(passwordTextField, 1, 1 );*/

        //Name
        Label labelUsername = new Label( "Register/add new user" );
        GridPane.setConstraints( labelUsername, 0,0 );

        TextField textFieldUsername = new TextField();
        textFieldUsername.setPromptText("Username");
        GridPane.setConstraints( textFieldUsername, 0, 1 );

        Label labelMsg = new Label();
        GridPane.setConstraints( labelMsg, 0, 2);

        Button buttonAddUser = SC.uniButton( "Add user");
        GridPane.setConstraints( buttonAddUser, 1, 1 );
        buttonAddUser.setOnAction( e -> {
            System.out.println(textFieldUsername.getText());
            labelMsg.setText( sqlProgram.registerNewCustomer(textFieldUsername.getText()) );
            textFieldUsername.clear();
            //sqlProgram.registerNewCustomer(textFieldUsername.getText());
        });

        /*ListView<String> listViewUsernames = new ListView<>();
        listViewUsernames.setPrefWidth(100);
        listViewUsernames.setPrefHeight(200);
        listViewUsernames.getItems().addAll(sqlProgram.getUserNames());
        GridPane.setConstraints( listViewUsernames, 0, 3 );*/
        TableView<Customer> tableViewCustomer = new TableView<>();
        tableViewCustomer.setItems( getCustomer() );
        tableViewCustomer.setPrefWidth(160);
        tableViewCustomer.setMaxHeight(210);
        TableColumn<Customer, String> tableColumnCustomerName = new TableColumn<>("Namefdsaf");
        tableColumnCustomerName.setMinWidth(90);
        tableColumnCustomerName.setCellValueFactory( new PropertyValueFactory<>("name"));
        TableColumn<Customer, Integer> tableColumnCustomerAge = new TableColumn<>("Agefadsf");
        tableColumnCustomerAge.setMinWidth( 20 );
        tableColumnCustomerAge.setCellValueFactory( new PropertyValueFactory<>("age"));
        tableViewCustomer.getColumns().addAll( tableColumnCustomerName, tableColumnCustomerAge );
        GridPane.setConstraints( tableViewCustomer, 0,3 );


        Button buttonMainMenu = SC.uniButton( "Cancel");
        GridPane.setConstraints( buttonMainMenu, 1,2 );
        buttonMainMenu.setOnAction( e -> stagePrimary.setScene( sceneMain ));

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

        grid.setAlignment( Pos.CENTER );
        grid.getChildren().addAll( labelUsername, textFieldUsername, buttonAddUser, buttonMainMenu,
                labelMsg, tableViewCustomer );
        //stage.setScene( scene );
        //stage.show();
        //return scene;
        sceneAddUser = scene;
    }

    private void menuMain(){
        Stage stagea = SC.uniStageMain("Add User");
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        Scene scene = new Scene( grid );

        grid.setMinSize(333, 333 );
        grid.setPadding( new Insets( 10, 10, 10, 10 ));
        grid.setVgap( 8 );
        grid.setHgap( 10 );

        Button buttonAddUser = SC.uniButton( "Add User");
        GridPane.setConstraints( buttonAddUser, 0 , 1 );
        buttonAddUser.setOnAction( e -> {
            System.out.println("AddUser");
            stage.setScene( sceneAddUser );
        });

        Button buttonRoomBooking = SC.uniButton( "Room Bookingfadsf");
        buttonRoomBooking.setWrapText( true );
        buttonRoomBooking.setText( "Room Booking");
        GridPane.setConstraints( buttonRoomBooking, 0, 2 );

        Button buttonRoomCancel = SC.uniButton( "Room Cancel");
        buttonRoomCancel.setWrapText( true );
        buttonRoomCancel.setText( "Room Cancel");
        GridPane.setConstraints( buttonRoomCancel, 0, 3 );

        Button buttonQuit = SC.uniButton( "Quit");
        buttonQuit.setWrapText( true );
        buttonQuit.setText( "Quit");
        GridPane.setConstraints( buttonQuit, 0, 4 );


        //GridPane userAdd = menuAddUser();
        //GridPane.setConstraints( userAdd, 1, 1 );

        grid.getChildren().addAll( buttonAddUser, buttonRoomBooking, buttonRoomCancel, buttonQuit );
        stage.setScene( scene );
        sceneMain = scene;
        stagePrimary = stage;
        stage.show();
    }

    private Scene menuMainaa(){
        Stage stagea = SC.uniStageMain("Add User");
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        Scene scene = new Scene( grid );
        Button addUserButton = new Button("Add User");

        grid.setMinSize(333, 333 );

        //GridPane userAdd = menuAddUser();
        //GridPane.setConstraints( userAdd, 1, 1 );

        //addUserButton.setOnAction( e -> stage.setScene( menuAddUser(  ) ));

        grid.getChildren().addAll( addUserButton );
        //stage.setScene( scene );
        //stage.show();
        return scene;
    }



    private BorderPane menuCancelRoom(){
        Stage stage = SC.uniStageMain("Add User");
        BorderPane borderPane = new BorderPane();
        HBox hBox = new HBox();
        VBox vBox = new VBox();
        Scene scene = new Scene( borderPane );
        Button vButtonAddUser = SC.uniButton("Add user");
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
        borderPane.setLeft( vBox );

        scene.setFill(Color.BLACK);

        stage.setScene( scene );
        stage.show();
        return borderPane;
    }

    public void stop() throws Exception {
    }

}
