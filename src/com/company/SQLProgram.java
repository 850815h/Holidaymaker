package com.company;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public class SQLProgram {
    private Connection connection = null;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private DatabaseMetaData db;

    private ArrayList<Room> rooms = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<String> customerNames = new ArrayList<>();
    private ArrayList<String> cityNames = new ArrayList<>();

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private LocalDateTime localDateTime = LocalDateTime.now();
    private String dateTime = localDateTime.format(dateTimeFormatter);
    private String aaa = "POINT( positions.pos_lat, positions.pos_long );";

    public SQLProgram() {
        connect();
        //selectCityName(1);
        returnCustomers();
        returnCustomersNames();
        returnRooms();

        ///////////////////////
        updateRoomAvailability( 1, 0 );

        /*for( Room s : rooms ){
            System.out.println( s.getCity() + " " + s.getRoomSize() + " " + s.isAvailability() );
        }*/

        /*for( Customer customer : customers ){
            System.out.println(customer.getName());
        }*/
    }

    private void updateRoomAvailability(int roomId, int availability) {
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
        Statement stmt = null;
        String query = "SELECT * FROM customers";
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("customer_name");
                int room = rs.getInt("room");

                customers.add( new Customer(id,name,room));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Room> returnRooms() {
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
                double pricePerNight = rs.getInt("price_per_night");
                double rating = rs.getInt("rating");
                boolean availability = rs.getBoolean("availability");

                rooms.add(new Room(id, selectCityName(city), roomSize, bookingStart, bookingEnd, maxAmountOfCustomers, facilitiesRestaurant, facilitiesPool,
                        facilitiesEveningEntertainment, facilitiesChildrenClub, additionalServiceBoardHalf, additionalServiceBoardFull, additionalServiceExtraBed,
                        pricePerNight, rating, availability));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rooms;
    }

    /*public ObservableList<Room> returnRoomsAvailableRestaurant() {
        ObservableList<Room> roomsAvailableRestaurant = FXCollections.observableArrayList();
        Statement stmt = null;
        String query = "SELECT * FROM rooms WHERE facilities_restaurant = 1";
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
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
                double pricePerNight = rs.getInt("price_per_night");
                double rating = rs.getInt("rating");
                boolean availability = rs.getBoolean("availability");

                roomsAvailableRestaurant.add(new Room(selectCityName(city), roomSize, bookingStart, bookingEnd, maxAmountOfCustomers, facilitiesRestaurant, facilitiesPool,
                        facilitiesEveningEntertainment, facilitiesChildrenClub, additionalServiceBoardHalf, additionalServiceBoardFull, additionalServiceExtraBed,
                        pricePerNight, rating, availability));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return roomsAvailableRestaurant;
    }*/

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

    public ArrayList<String> getCustomerNames() {
        return customerNames;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public ArrayList<String> getCityNames() {
        return cityNames;
    }
    //public ObservableList<Room> getRoomsAvailableRestaurant(){ return roomsAvailableRestaurant; }

}
