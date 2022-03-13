package com.example.rockaroundapp.model;

import android.net.Uri;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.mysql.cj.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    private Map<String, Object> address;
    private String userType;
    private String profileImg;
    private final ColorGenerator generator = ColorGenerator.MATERIAL;

    public Venue() {
        setUserType("VENUE");
        address = new HashMap<>();
    }

    public Venue(String firstname, String lastname, String email) {
        super(firstname, lastname, email);
        setUserType("VENUE");
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

    public Map<String, Object> getAddress() {
        address.put("addressLineOne", addressLineOne);
        address.put("addressLineTwo", addressLineTwo);
        address.put("city", city);
        address.put("county", county);
        address.put("country", country);
        return address;
    }

    public void setAddress(Map<String, Object> address) {
        this.address = address;
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
        return Objects.requireNonNull(address.get("city")).toString();
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

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImgURL) {
        this.profileImg = profileImg;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Override
    public String getUserType() {
        return userType;
    }

    @Override
    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public Map objectMap(User user) {
        return super.objectMap(user);
    }

    public TextDrawable getDefaultProfiler() {
        return TextDrawable.builder().buildRect(String.valueOf(venueName.charAt(0)), generator.getRandomColor());
    }
}
