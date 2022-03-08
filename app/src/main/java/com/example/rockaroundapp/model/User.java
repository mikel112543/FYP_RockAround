package com.example.rockaroundapp.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public abstract class User {

    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String userType;
    private String bio;
    private String contact;
    private String password;
    private ObjectMapper oMapper;

    protected User() {
    }

    public User(String firstname, String lastname, String email) {
        this.id = "0";
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        oMapper = new ObjectMapper();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Map objectMap(User user) {
        return oMapper.convertValue(user, Map.class);
    }


}
