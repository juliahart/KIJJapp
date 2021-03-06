package com.example.kijjapp;
/**
 * This is the PetSitter Class
 * @authors: Team KIJJ
 */

public class PetSitter {

    private String email;
    private String first;
    private String last;
    private String address;
    private String city;
    private String state;
    private int zip;
    private double lat;
    private double longi;
    public int points;

    /**
     * Constructor for the Pet sitter
     * @param email
     * @param first
     * @param last
     * @param address
     * @param city
     * @param state
     * @param zip
     * @param lat
     * @param longi
     * @param points
     */
    public PetSitter(String email, String first, String last, String address, String city, String state, int zip, double lat, double longi, int points) {
        this.email = email;
        this.first = first;
        this.last = last;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.lat = lat;
        this.longi = longi;
        this.points = points;
    }

    public PetSitter() {

    }

    /**
     * getters and setters for the pet sitter information
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongi() {
        return longi;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int amount)
    {
        points += amount;
    }

    public void setPoints(int points) {
        this.points = points;
    }

}
