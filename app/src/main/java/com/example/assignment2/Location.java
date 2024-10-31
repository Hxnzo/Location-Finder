package com.example.assignment2;

public class Location {
    private long id;
    private String address;
    private double latitude;
    private double longitude;

    // Constructor
    public Location(long id, String address, double latitude, double longitude) {
        this.id = id;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // Override toString() for displaying in ListView
    @Override
    public String toString() {
        return "Address: " + address + "\nLatitude: " + latitude + ", Longitude: " + longitude;
    }
}
