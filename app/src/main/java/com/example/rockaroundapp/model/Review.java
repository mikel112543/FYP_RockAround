package com.example.rockaroundapp.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

abstract class Review {

    private String id;
    private String title;
    private String description;
    private int reviewerId;
    private int reviewedId;
    private LocalDate date;
    private LocalTime time;

    public Review() {
        id = UUID.randomUUID().toString();
        date = LocalDate.now();
        time = LocalTime.now();
    }

    protected Review(String id, String title, String description, int reviewerId, int reviewedId) {
        this.id = description;
        this.title = title;
        this.description = description;
        this.reviewerId = reviewerId;
        this.reviewedId = reviewedId;
        this.time = LocalTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}

