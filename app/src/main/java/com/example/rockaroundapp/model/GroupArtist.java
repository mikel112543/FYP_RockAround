package com.example.rockaroundapp.model;

import java.util.List;

public class GroupArtist extends Artist {

    private String formationDate;
    private int noOfMembers;
    private List<GroupMember> groupMembers;


    //Constructor with Id
    public GroupArtist(String firstname, String lastname, String email, String password, String userType, String bio, String contactNumber, int artistId, String stageName, String addressLineOne, String addressLineTwo, String county, String country, String profileImgURL, List<String> instruments, String formationDate, int noOfMembers, List<GroupMember> groupMembers) {
        super(firstname, lastname, email, password, userType, bio, contactNumber, artistId, stageName, addressLineOne, addressLineTwo, county, country, profileImgURL, instruments);
        this.formationDate = formationDate;
        this.noOfMembers = noOfMembers;
        this.groupMembers = groupMembers;
    }
    //Constructor with no Id
    public GroupArtist(String firstname, String lastname, String email, String password, String userType, String bio, String contactNumber, String stageName, String addressLineOne, String addressLineTwo, String county, String country, String profileImgURL, List<String> instruments, String formationDate, int noOfMembers, List<GroupMember> groupMembers) {
        super(firstname, lastname, email, password, userType, bio, contactNumber, stageName, addressLineOne, addressLineTwo, county, country, profileImgURL, instruments);
        this.formationDate = formationDate;
        this.noOfMembers = noOfMembers;
        this.groupMembers = groupMembers;
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
}
