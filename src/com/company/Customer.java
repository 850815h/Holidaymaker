package com.company;

public class Customer {
    private int id;
    private String name;
    private int room;

    public Customer(int id, String name, int room) {
        this.id = id;
        this.name = name;
        this.room = room;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }
}
