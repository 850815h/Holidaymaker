package com.company;

import java.sql.Date;
import java.time.LocalDateTime;

public class Room {
    private int id;
    //private int city;
    private String city;
    private int roomSize;
    private Date bookingStart;
    private Date bookingEnd;
    private int maxAmountOfCustomers;
    private boolean facilitiesRestaurant;
    private boolean facilitiesPool;
    private boolean facilitiesEveningEntertainment;
    private boolean facilitiesChildrenClub;
    private boolean additionalServiceBoardHalf;
    private boolean additionalServiceBoardFull;
    private boolean additionalServiceExtraBed;
    private double pricePerNight;
    private double rating;
    private boolean availability;

    public Room(/*int id,*/ String city, int roomSize, Date bookingStart, Date bookingEnd, int maxAmountOfCustomers,
                boolean facilitiesRestaurant, boolean facilitiesPool, boolean facilitiesEveningEntertainment,
                boolean facilitiesChildrenClub, boolean additionalServiceBoardHalf, boolean additionalServiceBoardFull,
                boolean additionalServiceExtraBed, double pricePerNight, double rating, boolean availability) {
        this.id = id;
        this.city = city;
        this.roomSize = roomSize;
        this.bookingStart = bookingStart;
        this.bookingEnd = bookingEnd;
        this.maxAmountOfCustomers = maxAmountOfCustomers;
        this.facilitiesRestaurant = facilitiesRestaurant;
        this.facilitiesPool = facilitiesPool;
        this.facilitiesEveningEntertainment = facilitiesEveningEntertainment;
        this.facilitiesChildrenClub = facilitiesChildrenClub;
        this.additionalServiceBoardHalf = additionalServiceBoardHalf;
        this.additionalServiceBoardFull = additionalServiceBoardFull;
        this.additionalServiceExtraBed = additionalServiceExtraBed;
        this.pricePerNight = pricePerNight;
        this.rating = rating;
        this.availability = availability;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(int roomSize) {
        this.roomSize = roomSize;
    }

    public Date getBookingStart() {
        return bookingStart;
    }

    public void setBookingStart(Date bookingStart) {
        this.bookingStart = bookingStart;
    }

    public Date getBookingEnd() {
        return bookingEnd;
    }

    public void setBookingEnd(Date bookingEnd) {
        this.bookingEnd = bookingEnd;
    }

    public int getMaxAmountOfCustomers() {
        return maxAmountOfCustomers;
    }

    public void setMaxAmountOfCustomers(int maxAmountOfCustomers) {
        this.maxAmountOfCustomers = maxAmountOfCustomers;
    }

    public boolean isFacilitiesRestaurant() {
        return facilitiesRestaurant;
    }

    public void setFacilitiesRestaurant(boolean facilitiesRestaurant) {
        this.facilitiesRestaurant = facilitiesRestaurant;
    }

    public boolean isFacilitiesPool() {
        return facilitiesPool;
    }

    public void setFacilitiesPool(boolean facilitiesPool) {
        this.facilitiesPool = facilitiesPool;
    }

    public boolean isFacilitiesEveningEntertainment() {
        return facilitiesEveningEntertainment;
    }

    public void setFacilitiesEveningEntertainment(boolean facilitiesEveningEntertainment) {
        this.facilitiesEveningEntertainment = facilitiesEveningEntertainment;
    }

    public boolean isFacilitiesChildrenClub() {
        return facilitiesChildrenClub;
    }

    public void setFacilitiesChildrenClub(boolean facilitiesChildrenClub) {
        this.facilitiesChildrenClub = facilitiesChildrenClub;
    }

    public boolean isAdditionalServiceBoardHalf() {
        return additionalServiceBoardHalf;
    }

    public void setAdditionalServiceBoardHalf(boolean additionalServiceBoardHalf) {
        this.additionalServiceBoardHalf = additionalServiceBoardHalf;
    }

    public boolean isAdditionalServiceBoardFull() {
        return additionalServiceBoardFull;
    }

    public void setAdditionalServiceBoardFull(boolean additionalServiceBoardFull) {
        this.additionalServiceBoardFull = additionalServiceBoardFull;
    }

    public boolean isAdditionalServiceExtraBed() {
        return additionalServiceExtraBed;
    }

    public void setAdditionalServiceExtraBed(boolean additionalServiceExtraBed) {
        this.additionalServiceExtraBed = additionalServiceExtraBed;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}
