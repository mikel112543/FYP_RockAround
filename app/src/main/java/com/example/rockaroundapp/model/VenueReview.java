package com.example.rockaroundapp.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class VenueReview extends Review {

    private int settingRating;
    private int reliabilityRating;
    private int atmosphereRating;
    private int communicationRating;

    protected VenueReview(int reviewId, String reviewTitle, String reviewDescription, int reviewerId, int reviewedId, int settingRating, int reliabilityRating, int atmosphereRating, int communicationRating) {
        super(reviewId, reviewTitle, reviewDescription, reviewerId, reviewedId);
        this.settingRating = settingRating;
        this.reliabilityRating = reliabilityRating;
        this.atmosphereRating = atmosphereRating;
        this.communicationRating = communicationRating;
    }

    public int getSettingRating() {
        return settingRating;
    }

    public void setSettingRating(int settingRating) {
        this.settingRating = settingRating;
    }

    public int getReliabilityRating() {
        return reliabilityRating;
    }

    public void setReliabilityRating(int reliabilityRating) {
        this.reliabilityRating = reliabilityRating;
    }

    public int getAtmosphereRating() {
        return atmosphereRating;
    }

    public void setAtmosphereRating(int atmosphereRating) {
        this.atmosphereRating = atmosphereRating;
    }

    public int getCommunicationRating() {
        return communicationRating;
    }

    public void setCommunicationRating(int communicationRating) {
        this.communicationRating = communicationRating;
    }
}
