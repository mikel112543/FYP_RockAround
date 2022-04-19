package com.example.rockaroundapp.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class VenueReview extends Review {

    private float settingRating;
    private float reliabilityRating;
    private float atmosphereRating;
    private float communicationRating;

    public VenueReview() {
    }

    public double getOverallRating() {
        return (double) (communicationRating+settingRating+reliabilityRating+atmosphereRating) / 4;
    }

    public float getSettingRating() {
        return settingRating;
    }

    public void setSettingRating(float settingRating) {
        this.settingRating = settingRating;
    }

    public float getReliabilityRating() {
        return reliabilityRating;
    }

    public void setReliabilityRating(float reliabilityRating) {
        this.reliabilityRating = reliabilityRating;
    }

    public float getAtmosphereRating() {
        return atmosphereRating;
    }

    public void setAtmosphereRating(float atmosphereRating) {
        this.atmosphereRating = atmosphereRating;
    }

    public float getCommunicationRating() {
        return communicationRating;
    }

    public void setCommunicationRating(float communicationRating) {
        this.communicationRating = communicationRating;
    }
}
