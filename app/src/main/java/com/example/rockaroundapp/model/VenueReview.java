package com.example.rockaroundapp.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class VenueReview extends Review {

    private int settingRating;
    private int reliabilityRating;
    private int atmosphereRating;
    private int communicationRating;

    public VenueReview() {
    }

    public double getOverallRating() {
        return (double) (communicationRating+settingRating+reliabilityRating+atmosphereRating) / 4;
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
