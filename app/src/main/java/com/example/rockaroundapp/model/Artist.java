package com.example.rockaroundapp.model;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Artist extends User {

    private String stageName;
    private String addressLineOne;
    private String addressLineTwo;
    private String county;
    private String country;
    private String profileImg;
    private String userType;
    private String price;
    private double avgCommunicationRating;
    private double avgReliabilityRating;
    private double avgVocalsRating;
    private double avgStagePresenceRating;
    private int totalCommunicationRating;
    private int totalReliabilityRating;
    private int totalVocalsRating;
    private int totalStagePresenceRating;
    private double avgOverallRating;
    private List<String> instruments;
    private List<String> artistImages;
    private List<String> genres;
    private String genreString;
    private List<SampleTrack> sampleTracks;
    private List<ArtistReview> reviews;
    private HashMap<String, Object> address;
    private ObjectMapper oMapper;

    public Artist() {
        genres = new ArrayList<>();
        setUserType("SOLO");
    }

    public Artist(String firstname, String lastname, String email) {
        super(firstname, lastname, email);
        setUserType("SOLO");
    }

    @Override
    public String getUserType() {
        return userType;
    }

    public Artist(String stageName, String profileImg, String price, List<String> instruments, List<String> artistImages, List<String> genres, List<SampleTrack> sampleTracks, HashMap<String, Object> address) {
        this.stageName = stageName;
        this.profileImg = profileImg;
        this.price = price;
        this.instruments = instruments;
        this.artistImages = artistImages;
        this.genres = genres;
        this.sampleTracks = sampleTracks;
        this.address = address;
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

    public double getAvgVocalsRating() {
        return avgVocalsRating;
    }

    public void setAvgVocalsRating(double avgVocalsRating) {
        this.avgVocalsRating = avgVocalsRating;
    }

    public double getAvgStagePresenceRating() {
        return avgStagePresenceRating;
    }

    public void setAvgStagePresenceRating(double avgStagePresenceRating) {
        this.avgStagePresenceRating = avgStagePresenceRating;
    }

    public int getTotalCommunicationRating() {
        return totalCommunicationRating;
    }

    public void setTotalCommunicationRating(int totalCommunicationRating) {
        this.totalCommunicationRating = totalCommunicationRating;
    }

    public int getTotalReliabilityRating() {
        return totalReliabilityRating;
    }

    public void setTotalReliabilityRating(int totalReliabilityRating) {
        this.totalReliabilityRating = totalReliabilityRating;
    }

    public int getTotalVocalsRating() {
        return totalVocalsRating;
    }

    public void setTotalVocalsRating(int totalVocalsRating) {
        this.totalVocalsRating = totalVocalsRating;
    }

    public int getTotalStagePresenceRating() {
        return totalStagePresenceRating;
    }

    public void setTotalStagePresenceRating(int totalStagePresenceRating) {
        this.totalStagePresenceRating = totalStagePresenceRating;
    }

    public void setAvgOverallRating(double avgOverallRating) {
        this.avgOverallRating = avgOverallRating;
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


    public void setAddress() {
        address = new HashMap<>();
        address.put("addressLineOne", addressLineOne);
        address.put("addressLineTwo", addressLineTwo);
        address.put("county", county);
        address.put("country", country);
    }

    public String getGenreString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < genres.size(); i++) {
            builder.append(genres.get(i));
            if (i != genres.size() - 1) {
                builder.append(", ");
            }
        }
        genreString = builder.toString();
        return genreString;
    }

    public double getAvgOverallRating() {
        return avgOverallRating;
    }

    public String getStageName() {
        return stageName;
    }

    public void setGenreString(String genreString) {
        this.genreString = genreString;
    }

    public void setAddress(HashMap<String, Object> address) {
        this.address = address;
    }

    public ObjectMapper getoMapper() {
        return oMapper;
    }

    public void setoMapper(ObjectMapper oMapper) {
        this.oMapper = oMapper;
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

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
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

    public TextDrawable getDefaultProfiler() {
        ColorGenerator generator = ColorGenerator.MATERIAL;
        if (stageName == null || stageName.isEmpty()) {
            return TextDrawable.builder().buildRect(String.valueOf(getFirstname().charAt(0)), generator.getRandomColor());
        }
        return TextDrawable.builder().buildRect(String.valueOf(getStageName().charAt(0)), generator.getRandomColor());
    }
}
