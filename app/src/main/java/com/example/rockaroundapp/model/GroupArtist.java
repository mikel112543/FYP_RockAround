package com.example.rockaroundapp.model;

import java.util.List;
import java.util.Map;

public class GroupArtist extends Artist {

    private String formationDate;
    private int noOfMembers;
    private List<GroupMember> groupMembers;
    private String userType;

 /*   public GroupArtist(String firstname, String lastname, String email, String userType, String bio, String contactNumber, String stageName, int price, String addressLineOne, String addressLineTwo, String city, String county, String country, String profileImgURL, List<String> instruments, String formationDate, int noOfMembers, List<GroupMember> groupMembers) {
        super(firstname, lastname, email, userType, bio, contactNumber, stageName, price, addressLineOne, addressLineTwo, city, county, country, profileImgURL, instruments);
        this.formationDate = formationDate;
        this.noOfMembers = noOfMembers;
        this.groupMembers = groupMembers;
    }*/

    public GroupArtist(String firstname, String lastname, String email) {
        super(firstname, lastname, email);
        setUserType("GROUP");
    }

    @Override
    public String getUserType() {
        return userType;
    }

    @Override
    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getFormationDate() {
        return formationDate;
    }

    public void setFormationDate(String formationDate) {
        this.formationDate = formationDate;
    }

    public int getNoOfMembers() {
        return noOfMembers;
    }

    public void setNoOfMembers(int noOfMembers) {
        this.noOfMembers = noOfMembers;
    }

    public List<GroupMember> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(List<GroupMember> groupMembers) {
        this.groupMembers = groupMembers;
    }

    @Override
    public Map<String, Object> objectMap(User user) {
        return super.objectMap(user);
    }
}
