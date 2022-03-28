package com.example.rockaroundapp.model;

import static java.time.format.FormatStyle.MEDIUM;
import static java.time.format.FormatStyle.SHORT;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.UUID;

public abstract class Review {

    private String id;
    private String title;
    private String description;
    private String reviewerId;
    private String reviewedId;
    private String date;
    private String time;

    public Review() {
        id = UUID.randomUUID().toString();
        date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        time = LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(SHORT));
    }

    protected Review(String id, String title, String description, String reviewerId, String reviewedId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.reviewerId = reviewerId;
        this.reviewedId = reviewedId;
        date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        time = LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(SHORT));
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

    public String getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(String reviewerId) {
        this.reviewerId = reviewerId;
    }

    public String getReviewedId() {
        return reviewedId;
    }

    public void setReviewedId(String reviewedId) {
        this.reviewedId = reviewedId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

