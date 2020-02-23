package com.company;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public class SQLProgram {
    private Connection connection = null;
    private PreparedStatement preparedStatement;

    private ArrayList<Room> rooms = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<String> customerNames = new ArrayList<>();
    private ArrayList<String> cityNames = new ArrayList<>();

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private LocalDateTime localDateTime = LocalDateTime.now();

    public SQLProgram() {
        connect();
        refreshMethods();
    }

    public void refreshMethods(){
        returnCustomers();
        returnCustomersNames();
        returnRooms();
    }

    public void removeCustomerFromRoom(int customerId){
        String query = "UPDATE customers SET room = null WHERE id = ?;";

        try {
            preparedStatement = connection.prepareStatement(query);

            //preparedStatement.setInt(1, 0);
            preparedStatement.setInt(1, customerId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addCustomerToRoom(int roomId, int customerId){
        System.out.println("1 inne i addCustomerToRoom");
        String query = "UPDATE customers SET room = ? WHERE id = ?;";

        try {
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, roomId);
            preparedStatement.setInt(2, customerId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateRoomBookingStart(Date date, int roomId) {
        String queries = "UPDATE rooms SET booking_start = ? WHERE id = ?;";

        try {
            preparedStatement = connection.prepareStatement(queries);
            preparedStatement.setDate(1, date);
            preparedStatement.setInt(2, roomId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateRoomBookingEnd(Date date, int roomId) {
        String queries = "UPDATE rooms SET booking_end = ? WHERE id = ?;";

        try {
            preparedStatement = connection.prepareStatement(queries);
            preparedStatement.setDate(1, date);
            preparedStatement.setInt(2, roomId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateRoomAvailability(int roomId, int availability) {
        String queries = "UPDATE rooms SET availability = ? WHERE id = ?;";

        try {
            preparedStatement = connection.prepareStatement(queries);
            preparedStatement.setInt(1, availability);
            preparedStatement.setInt(2, roomId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void returnCustomers() {
        ArrayList<Customer> tempCustomerList = new ArrayList<>();

        Statement stmt = null;
        String query = "SELECT * FROM customers";
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("customer_name");
                int room = rs.getInt("room");

                tempCustomerList.add( new Customer(id,name,room));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        customers = tempCustomerList;
    }

    private void returnRooms() {
        ArrayList<Room> tempRoomList = new ArrayList<>();
        Statement stmt = null;
        String query = "SELECT * FROM rooms";
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                int city = rs.getInt("city");
                int roomSize = rs.getInt("room_size");
                Date bookingStart = rs.getDate("booking_start");
                Date bookingEnd = rs.getDate("booking_end");
                int maxAmountOfCustomers = rs.getInt("max_amount_of_customer");
                boolean facilitiesRestaurant = rs.getBoolean("facilities_restaurant");
                boolean facilitiesPool = rs.getBoolean("facilities_pool");
                boolean facilitiesEveningEntertainment = rs.getBoolean("facilities_evening_entertainment");
                boolean facilitiesChildrenClub = rs.getBoolean("facilities_children_club");
                boolean additionalServiceBoardHalf = rs.getBoolean("additional_services_board_half");
                boolean additionalServiceBoardFull = rs.getBoolean("additional_services_board_full");
                boolean additionalServiceExtraBed = rs.getBoolean("additional_services_extra_bed");
                double pricePerNight = rs.getDouble("price_per_night");
                double rating = rs.getDouble("rating");
                double distanceToCity = rs.getDouble("distance_to_city");
                double distanceToBeach = rs.getDouble("distance_to_beach");
                boolean availability = rs.getBoolean("availability");

                tempRoomList.add(new Room(id, selectCityName(city), roomSize, bookingStart, bookingEnd, maxAmountOfCustomers, facilitiesRestaurant, facilitiesPool,
                        facilitiesEveningEntertainment, facilitiesChildrenClub, additionalServiceBoardHalf, additionalServiceBoardFull, additionalServiceExtraBed,
                        pricePerNight, rating, distanceToCity, distanceToBeach, availability));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        rooms = tempRoomList;
    }

    private String selectCityName(int cityID) {
        Statement stmt = null;
        String query = "SELECT city_name" +
                " FROM rooms, cities" +
                " WHERE cities.id = " + cityID + ";";

        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String city = rs.getString("city_name");
                return city;
                //System.out.println( city );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String registerNewCustomer(String userName) {
        if (userName.isBlank() || userName.length() < 3) {
            return "Try again with at least 3 symbols!";
        }

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

        return userName + " added!";
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

        customerNames = names;
        return names;
    }

    private void connect() { //koppla db till java
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/holidaymaker?user=root&password=mysql&serverTimezone=UTC");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<Customer> getCustomers() {
        returnCustomers();
        return customers;
    }

    public ArrayList<Room> getRooms() {
        returnRooms();
        return rooms;
    }

}
