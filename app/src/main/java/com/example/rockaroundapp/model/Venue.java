package com.example.rockaroundapp.model;

import android.app.usage.UsageEvents;

import java.util.List;

public class Venue extends User{

    private String venueName;
    private int capacity;
    private String venueType;
    private String addressLineOne;
    private String addressLineTwo;
    private String county;
    private String country;
    private List<Event> events;
    private List<Event> pastEvents;
    private List<VenueReview> reviews;

    public Venue(String firstname, String lastname, String email, String password, String userType, String bio, String contactNumber, String venueName, int capacity, String venueType, String addressLineOne, String addressLineTwo, String county, String country) {
        super(firstname, lastname, email, password, userType, bio, contactNumber);
        this.venueName = venueName;
        this.capacity = capacity;
        this.venueType = venueType;
        this.addressLineOne = addressLineOne;
        this.addressLineTwo = addressLineTwo;
        this.county = county;
        this.country = country;
    }
}
