package com.example.rockaroundapp.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class ArtistReview extends Review {

    private int stagePresenceRating;
    private int vocalsRating;
    private int reliabilityRating;
    private int communicationRating;

    public ArtistReview() {
    }

    public ArtistReview(String id, String title, String description, String reviewerId, String reviewedId, int stagePresenceRating, int vocalsRating, int reliabilityRating, int communicationRating) {
        super(id, title, description, reviewerId, reviewedId);
        this.stagePresenceRating = stagePresenceRating;
        this.vocalsRating = vocalsRating;
        this.reliabilityRating = reliabilityRating;
        this.communicationRating = communicationRating;
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
