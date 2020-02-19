package com.company;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class SQLProgram {
    private Connection connection = null;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private DatabaseMetaData db;

    private ArrayList<String> userNames = new ArrayList<>();

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private LocalDateTime localDateTime = LocalDateTime.now();
    private String dateTime = localDateTime.format(dateTimeFormatter);
    private String aaa = "POINT( positions.pos_lat, positions.pos_long );";


    public SQLProgram() {
        connect();
        returnCustomersNames();

        ///////////////////////

        //registerNewCustomer("Jamal");
    }

    public String registerNewCustomer(String userName) {
        for (String name : returnCustomersNames()) {
            if (name.equalsIgnoreCase(userName)) {
                return "Name already exists!";
            }
        }

        String query = "INSERT INTO customers ("
                + "customer_name )" +
                "VALUES (?)";

        try {
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, userName);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userName + " added.";
    }

    public String deleteCustomer(String userName) {
        for (String name : returnCustomersNames()) {
            if (!name.equalsIgnoreCase(userName)) {
                return "Name already exists!";
            }
        }

        String query = "DELETE FROM customers ("
                + "customer_name )" +
                "VALUES (?)";

        try {
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, userName);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userName + " added.";
    }

    private void insertIntoCustomers(String name) {
        String query = "REPLACE customers ("
                + "id, customer_name )" +
                "VALUES (?, ?)";

        try {
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, 20);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updatePositions(int posCarPickupDropoff, double positionLat, double positionLong) {
        String[] queries = {
                "UPDATE cars INNER JOIN positions SET position_car = POINT( ( ? ), ( ? ) );",
                "UPDATE cars INNER JOIN positions SET position_customer_pickup = POINT( ( ? ), ( ? ) );",
                "UPDATE cars INNER JOIN positions SET position_customer_dropoff = POINT( ( ? ), ( ? ) );"};

        try {
            preparedStatement = connection.prepareStatement(queries[posCarPickupDropoff]);
            preparedStatement.setDouble(1, positionLat);
            preparedStatement.setDouble(2, positionLong);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void replaceIntoCars(int id, String model, double pricePerKm, int owner, int customer, String
            bookingStart, String bookingEnd,
                                 double positionCarLat, double positionCarLong, double positionCustomerPickUpLat,
                                 double positionCustomerPickUpLong,
                                 double positionCustomerDropOffLat, double positionCustomerDropOffLong) {

        String queryReplaceInto = "REPLACE cars( id, model, price_per_km, owner, customer, booking_start, booking_end, position_car, position_customer_pickup, position_customer_dropoff, total_distance_in_km )" +
                "VALUES( ?, ?, ?, ?, ?, ?, ?, POINT(0,0), POINT(0,0), POINT(0,0), ? );";

        try {

            preparedStatement = connection.prepareStatement(queryReplaceInto);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, model);
            preparedStatement.setDouble(3, pricePerKm);
            preparedStatement.setInt(4, owner);
            preparedStatement.setInt(5, customer);
            preparedStatement.setString(6, bookingStart);
            preparedStatement.setString(7, bookingEnd);
            preparedStatement.setDouble(8, 10.1);
            preparedStatement.executeQuery();

            updatePositions(0, positionCarLat, positionCarLong);
            updatePositions(1, positionCustomerPickUpLat, positionCustomerPickUpLong);
            updatePositions(2, positionCustomerDropOffLat, positionCustomerDropOffLong);

            preparedStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchByFirstName(String name) {
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM cars WHERE model LIKE ?");
            preparedStatement.setString(1, name); //1an är för vilken %?% det gället från förra raden ("SELECT * FROM persons WHERE name LIKE %?%").
            resultSet = preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> returnCustomersNames() {
        ArrayList<String> names = new ArrayList<>();
        Statement stmt = null;
        String query = "SELECT customer_name FROM customers";
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String personName = rs.getString("customer_name");
                names.add(personName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        userNames = names;
        return names;
    }

    private void connect() { //koppla db till java
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/holidaymaker?user=root&password=mysql&serverTimezone=UTC");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<String> getUserNames(){
        return userNames;
    }

}
