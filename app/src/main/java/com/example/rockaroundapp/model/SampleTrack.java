package com.example.rockaroundapp.model;

public class SampleTrack {

    private String trackTitle;
    private String trackPreview;

    public SampleTrack(String trackTitle, String trackPreview) {
        this.trackTitle = trackTitle;
        this.trackPreview = trackPreview;
    }

    public String getTrackTitle() {
        return trackTitle;
    }

    public void setTrackTitle(String trackTitle) {
        this.trackTitle = trackTitle;
    }

    public String getTrackPreview() {
        return trackPreview;
    }

    public void setTrackPreview(String trackPreview) {
        this.trackPreview = trackPreview;
    }
}
