package com.example.rockaroundapp.model;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Venue extends User {

    private String venueName;
    private int capacity;
    private String venueType;
    private String addressLineOne;
    private String addressLineTwo;
    private String county;
    private String country;
    private String city;
    private String contactNumber;
    private double avgCommunicationRating;
    private double avgReliabilityRating;
    private double avgSettingRating;
    private double avgAtmosphereRating;
    private double totalCommunicationRating;
    private double totalReliabilityRating;
    private double totalSettingRating;
    private double totalAtmosphereRating;
    private double avgOverallRating;
    private List<Event> events;
    private List<Event> pastEvents;
    private List<VenueReview> reviews;
    private Map<String, Object> address;
    private String userType;
    private String profileImg;
    private String randomAccountColour;

    public Venue() {
        setUserType("Venue");
        address = new HashMap<>();
    }

    public Venue(String firstname, String lastname, String email) {
        super(firstname, lastname, email);
        setUserType("Venue");
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
        return address;
    }

    public void setAddress() {
        address.put("addressLineOne", addressLineOne);
        address.put("addressLineTwo", addressLineTwo);
        address.put("city", city);
        address.put("county", county);
        address.put("country", country);
    }

    public void setAddressMap(Map<String, Object> map) {
        this.address = map;
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
        if (!(city == null || city.isEmpty())) {
            return city;
        }
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

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public double getAvgCommunicationRating() {
        return avgCommunicationRating;
    }

    public void setAvgCommunicationRating(double avgCommunicationRating) {
        this.avgCommunicationRating = avgCommunicationRating;
    }

    public double getAvgReliabilityRating() {
        return avgReliabilityRating;
    }

    public void setAvgReliabilityRating(double avgReliabilityRating) {
        this.avgReliabilityRating = avgReliabilityRating;
    }

    public double getAvgSettingRating() {
        return avgSettingRating;
    }

    public void setAvgSettingRating(double avgSettingRating) {
        this.avgSettingRating = avgSettingRating;
    }

    public double getAvgAtmosphereRating() {
        return avgAtmosphereRating;
    }

    public void setAvgAtmosphereRating(double avgAtmosphereRating) {
        this.avgAtmosphereRating = avgAtmosphereRating;
    }

    public double getTotalCommunicationRating() {
        return totalCommunicationRating;
    }

    public void setTotalCommunicationRating(double totalCommunicationRating) {
        this.totalCommunicationRating = totalCommunicationRating;
    }

    public double getTotalReliabilityRating() {
        return totalReliabilityRating;
    }

    public void setTotalReliabilityRating(double totalReliabilityRating) {
        this.totalReliabilityRating = totalReliabilityRating;
    }

    public double getTotalSettingRating() {
        return totalSettingRating;
    }

    public void setTotalSettingRating(double totalSettingRating) {
        this.totalSettingRating = totalSettingRating;
    }

    public double getTotalAtmosphereRating() {
        return totalAtmosphereRating;
    }

    public void setTotalAtmosphereRating(double totalAtmosphereRating) {
        this.totalAtmosphereRating = totalAtmosphereRating;
    }

    public double getAvgOverallRating() {
        return avgOverallRating;
    }

    public void setAvgOverallRating(double avgOverallRating) {
        this.avgOverallRating = avgOverallRating;
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
        return TextDrawable.builder().buildRect(String.valueOf(venueName.charAt(0)), ColorGenerator.MATERIAL.getRandomColor());
    }
}
