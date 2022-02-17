package com.example.rockaroundapp.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Artist extends User {

    private int artistId;
    private String stageName;
    private String addressLineOne;
    private String addressLineTwo;
    private String county;
    private String country;
    private String profileImgURL;
    private String userType;
    private String price;
    private List<String> instruments;
    private List<String> artistImages;
    private List<String> genres;
    private List<SampleTrack> sampleTracks;
    private List<ArtistReview> reviews;
    private HashMap<String, Object> address;
    private ObjectMapper oMapper;

    public Artist() {
    }
/*
    public Artist(String firstname, String lastname, String email, String userType, String bio, String contactNumber, String stageName, int price, String addressLineOne, String addressLineTwo, String city, String county, String country, String profileImgURL, List<String> instruments) {
        super(firstname, lastname, email, userType, bio, contactNumber);
        this.stageName = stageName;
        this.addressLineOne = addressLineOne;
        this.addressLineTwo = addressLineTwo;
        this.city = city;
        this.county = county;
        this.country = country;
        this.profileImgURL = profileImgURL;
        this.instruments = instruments;
        this.price = price;
        sampleTracks = new ArrayList<>();
        artistImages = new ArrayList<>();
        reviews = new ArrayList<>();
        oMapper = new ObjectMapper();

    }*/

    public Artist(String firstname, String lastname, String email) {
        super(firstname, lastname, email);
        setUserType("SOLO");
    }

    @Override
    public String getUserType() {
        return userType;
    }

    public Artist(String stageName, String profileImgURL, String price, List<String> instruments, List<String> artistImages, List<String> genres, List<SampleTrack> sampleTracks, HashMap<String, Object> address) {
        this.stageName = stageName;
        this.profileImgURL = profileImgURL;
        this.price = price;
        this.instruments = instruments;
        this.artistImages = artistImages;
        this.genres = genres;
        this.sampleTracks = sampleTracks;
        this.address = address;
    }

    public HashMap<String, Object> getAddress() {
        return address;
    }

    @Override
    public void setUserType(String userType) {
        this.userType = userType;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<ArtistReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<ArtistReview> reviews) {
        this.reviews = reviews;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setAddress() {
        address = new HashMap<>();
        address.put("addressLineOne", addressLineOne);
        address.put("addressLineTwo", addressLineTwo);
        address.put("county", county);
        address.put("country", country);
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
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

    public String getProfileImgURL() {
        return profileImgURL;
    }

    public void setProfileImgURL(String profileImgURL) {
        this.profileImgURL = profileImgURL;
    }

    public List<String> getInstruments() {
        return instruments;
    }

    public void setInstruments(List<String> instruments) {
        this.instruments = instruments;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<String> getArtistImages() {
        return artistImages;
    }

    public void setArtistImages(List<String> artistImages) {
        this.artistImages = artistImages;
    }

    public List<SampleTrack> getSampleTracks() {
        return sampleTracks;
    }

    public void setSampleTracks(List<SampleTrack> sampleTracks) {
        this.sampleTracks = sampleTracks;
    }
}
