package com.example.rockaroundapp.model;

import android.net.Uri;

import java.util.List;
import java.util.Map;

public class Venue extends User{

    private String venueName;
    private int capacity;
    private String venueType;
    private String addressLineOne;
    private String addressLineTwo;
    private String county;
    private String country;
    private String city;
    private String contactNumber;
    private List<Event> events;
    private List<Event> pastEvents;
    private List<VenueReview> reviews;
    private String userType;
    private Uri profileImgURL;

 /*   public Venue(String firstname, String lastname, String email, String userType, String bio, String contactNumber, String venueName, int capacity, String venueType, String addressLineOne, String addressLineTwo, String county, String country) {
        super(firstname, lastname, email, userType, bio, contactNumber);
        this.venueName = venueName;
        this.capacity = capacity;
        this.venueType = venueType;
        this.addressLineOne = addressLineOne;
        this.addressLineTwo = addressLineTwo;
        this.county = county;
        this.country = country;
    }*/

    public Venue() {
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getVenueType() {
        return venueType;
    }

    public void setVenueType(String venueType) {
        this.venueType = venueType;
    }

    public String getAddressLineOne() {
        return addressLineOne;
    }

    public void setAddressLineOne(String addressLineOne) {
        this.addressLineOne = addressLineOne;
    }

    public String getAddressLineTwo() {
        return addressLineTwo;
    }

    public void setAddressLineTwo(String addressLineTwo) {
        this.addressLineTwo = addressLineTwo;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Event> getPastEvents() {
        return pastEvents;
    }

    public void setPastEvents(List<Event> pastEvents) {
        this.pastEvents = pastEvents;
    }

    public List<VenueReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<VenueReview> reviews) {
        this.reviews = reviews;
    }

    public Uri getProfileImgURL() {
        return profileImgURL;
    }

    public void setProfileImgURL(Uri profileImgURL) {
        this.profileImgURL = profileImgURL;
    }

    @Override
    public String getUserType() {
        return userType;
    }

    @Override
    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Venue(String firstname, String lastname, String email) {
        super(firstname, lastname, email);
        setUserType("VENUE");
    }

    @Override
    public Map objectMap(User user) {
        return super.objectMap(user);
    }
}
