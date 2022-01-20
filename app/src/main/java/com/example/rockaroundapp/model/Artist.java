package com.example.rockaroundapp.model;

import java.util.ArrayList;
import java.util.List;

public class Artist extends User {

    private int artistId;
    private String stageName;
    private String addressLineOne;
    private String addressLineTwo;
    private String county;
    private String country;
    private String profileImgURL;
    private List<String> instruments;
    private List<String> artistImages;
    private List<String> genres;
    private List<SampleTrack> sampleTracks;
    private List<ArtistReview> reviews;

    public Artist(String firstname, String lastname, String email, String password, String userType, String bio, String contactNumber, String stageName, String addressLineOne, String addressLineTwo, String county, String country, String profileImgURL, List<String> instruments) {
        super(firstname, lastname, email, password, userType, bio, contactNumber);
        this.stageName = stageName;
        this.addressLineOne = addressLineOne;
        this.addressLineTwo = addressLineTwo;
        this.county = county;
        this.country = country;
        this.profileImgURL = profileImgURL;
        this.instruments = instruments;
        sampleTracks = new ArrayList<>();
        artistImages = new ArrayList<>();
        reviews = new ArrayList<>();
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
