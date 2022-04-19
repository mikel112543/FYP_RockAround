package com.example.rockaroundapp.model;

public class ArtistReview extends Review {

    private float stagePresenceRating;
    private float vocalsRating;
    private float reliabilityRating;
    private float communicationRating;

    public ArtistReview() {
    }

    public double getOverallRating() {
        return (double) (communicationRating+vocalsRating+reliabilityRating+stagePresenceRating) / 4;
    }

    public float getStagePresenceRating() {
        return stagePresenceRating;
    }

    public void setStagePresenceRating(float stagePresenceRating) {
        this.stagePresenceRating = stagePresenceRating;
    }

    public float getVocalsRating() {
        return vocalsRating;
    }

    public void setVocalsRating(float vocalsRating) {
        this.vocalsRating = vocalsRating;
    }

    public float getReliabilityRating() {
        return reliabilityRating;
    }

    public void setReliabilityRating(float reliabilityRating) {
        this.reliabilityRating = reliabilityRating;
    }

    public float getCommunicationRating() {
        return communicationRating;
    }

    public void setCommunicationRating(float communicationRating) {
        this.communicationRating = communicationRating;
    }
}
