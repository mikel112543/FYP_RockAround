package com.example.rockaroundapp.model;

public class ArtistReview extends Review {

    private int stagePresenceRating;
    private int vocalsRating;
    private int reliabilityRating;
    private int communicationRating;

    public ArtistReview() {
    }

    public double getOverallRating() {
        return (double) (communicationRating+vocalsRating+reliabilityRating+stagePresenceRating) / 4;
    }

    public int getStagePresenceRating() {
        return stagePresenceRating;
    }

    public void setStagePresenceRating(int stagePresenceRating) {
        this.stagePresenceRating = stagePresenceRating;
    }

    public int getVocalsRating() {
        return vocalsRating;
    }

    public void setVocalsRating(int vocalsRating) {
        this.vocalsRating = vocalsRating;
    }

    public int getReliabilityRating() {
        return reliabilityRating;
    }

    public void setReliabilityRating(int reliabilityRating) {
        this.reliabilityRating = reliabilityRating;
    }

    public int getCommunicationRating() {
        return communicationRating;
    }

    public void setCommunicationRating(int communicationRating) {
        this.communicationRating = communicationRating;
    }
}
