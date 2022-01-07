package com.example.rockaroundapp.model;

import java.time.LocalDate;
import java.time.LocalTime;

abstract class Review {

    private int reviewId;
    private String reviewTitle;
    private String reviewDescription;
    private int reviewerId;
    private int reviewedId;
    private LocalDate reviewDate;
    private LocalTime reviewTime;

    protected Review(int reviewId, String reviewTitle, String reviewDescription, int reviewerId, int reviewedId, LocalDate reviewDate, LocalTime reviewTime) {
        this.reviewId = reviewId;
        this.reviewTitle = reviewTitle;
        this.reviewDescription = reviewDescription;
        this.reviewerId = reviewerId;
        this.reviewedId = reviewedId;
        this.reviewDate = reviewDate;
        this.reviewTime = reviewTime;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public String getReviewDescription() {
        return reviewDescription;
    }

    public void setReviewDescription(String reviewDescription) {
        this.reviewDescription = reviewDescription;
    }

    public int getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(int reviewerId) {
        this.reviewerId = reviewerId;
    }

    public int getReviewedId() {
        return reviewedId;
    }

    public void setReviewedId(int reviewedId) {
        this.reviewedId = reviewedId;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public LocalTime getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(LocalTime reviewTime) {
        this.reviewTime = reviewTime;
    }
}

