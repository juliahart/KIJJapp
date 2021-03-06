package com.example.kijjapp;

/**
 * This is the PetOwner Class
 * @authors: Team KIJJ
 */

public class PetOwner {

    private String email;
    private String first;
    private String last;
    private String address;
    private String city;
    private String state;
    private int zip;
    private double lat;
    private double longi;
    private String desc;
    private double rating;
    private String type;
    private String breed;
    private String petName;

    /**
     * Constructor for the pet owner
     * @param email
     * @param first
     * @param last
     * @param address
     * @param city
     * @param state
     * @param zip
     * @param lat
     * @param longi
     * @param desc
     * @param rating
     * @param type
     * @param breed
     * @param petName
     */
    public PetOwner(String email, String first, String last, String address, String city, String state, int zip, double lat, double longi, String desc, double rating, String type, String breed, String petName) {
        this.email = email;
        this.first = first;
        this.last = last;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.lat = lat;
        this.longi = longi;
        this.desc = desc;
        this.rating = rating;
        this.type = type;
        this.breed = breed;
        this.petName = petName;
    }

    /**
     * Getters and setter for the pet owner information
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getDesc() {
        return desc;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    @Override
    public String toString() {
        return "PetOwner{" +
                "email='" + email + '\'' +
                ", first='" + first + '\'' +
                ", last='" + last + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip=" + zip +
                ", lat=" + lat +
                ", longi=" + longi +
                ", desc='" + desc + '\'' +
                ", type='" + type + '\'' +
                ", breed='" + breed + '\'' +
                ", petName='" + petName + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }
}
