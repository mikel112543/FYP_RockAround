package com.example.rockaroundapp.viewmodel;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rockaroundapp.model.GroupArtist;
import com.example.rockaroundapp.repository.GroupSetupRepository;

import java.util.List;
import java.util.Objects;

public class GroupSetupViewModel extends ViewModel {

    private MutableLiveData<String> groupName;
    private MutableLiveData<String> bio;
    private MutableLiveData<String> noOfMembers;
    private MutableLiveData<String> contactNumber;
    private MutableLiveData<String> price;
    private MutableLiveData<String> city;
    private MutableLiveData<GroupArtist> groupArtistMutable;
    private final MutableLiveData<String> genresStringMutable;
    private Uri profileImageUri;
    private GroupArtist groupArtist;
    private final GroupSetupRepository groupSetupRepository;
    private MutableLiveData<Boolean> setUpSuccess;
    private final MutableLiveData<List<String>> genresMutable;

    public GroupSetupViewModel() {
        groupSetupRepository = new GroupSetupRepository();
        groupName = new MutableLiveData<>("");
        bio = new MutableLiveData<>("");
        city = new MutableLiveData<>("");
        noOfMembers = new MutableLiveData<>("");
        contactNumber = new MutableLiveData<>("");
        price = new MutableLiveData<>("");
        groupArtistMutable = new MutableLiveData<>();
        genresStringMutable = new MutableLiveData<>("");
        genresMutable = new MutableLiveData<>();
        setUpSuccess = groupSetupRepository.getSuccess();
    }

    public MutableLiveData<GroupArtist> getGroupArtist() {
        if(groupArtistMutable == null) {
            groupArtistMutable = new MutableLiveData<>();
        }
        return groupArtistMutable;
    }

    public MutableLiveData<String> getCity() {
        return city;
    }

    public void setCity(MutableLiveData<String> city) {
        this.city = city;
    }

    public void setGenre(List<String> genres) {
        genresMutable.postValue(genres);
    }

    public void setGenresString(String genresString) {
        genresStringMutable.postValue(genresString);
    }

    public MutableLiveData<String> getGroupName() {
        return groupName;
    }

    public void setGroupName(MutableLiveData<String> groupName) {
        this.groupName = groupName;
    }

    public MutableLiveData<String> getBio() {
        return bio;
    }

    public void setBio(MutableLiveData<String> bio) {
        this.bio = bio;
    }

    public MutableLiveData<String> getNoOfMembers() {
        return noOfMembers;
    }

    public void setNoOfMembers(MutableLiveData<String> noOfMembers) {
        this.noOfMembers = noOfMembers;
    }

    public MutableLiveData<String> getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(MutableLiveData<String> contactNumber) {
        this.contactNumber = contactNumber;
    }

    public MutableLiveData<String> getPrice() {
        return price;
    }

    public void setPrice(MutableLiveData<String> price) {
        this.price = price;
    }

    public MutableLiveData<String> getGenresStringMutable() {
        return genresStringMutable;
    }

    public MutableLiveData<Boolean> getSetUpSuccess() {
        return setUpSuccess;
    }

    public void setProfileImageUri(Uri profileImageUri) {
        this.profileImageUri = profileImageUri;
    }

    public void onSaveClicked() {
        groupArtist = new GroupArtist();
        groupArtist.setBio(bio.getValue());
        groupArtist.setStageName(groupName.getValue());
        groupArtist.setPrice(price.getValue());
        groupArtist.setNoOfMembers(noOfMembers.getValue());
        groupArtist.setGenres(genresMutable.getValue());
        groupArtist.setContact(contactNumber.getValue());
        groupArtist.setCity(city.getValue());
        if(profileImageUri != null){
            groupArtist.setProfileImg(profileImageUri.toString());
        }else{
            groupArtist.setProfileImg("");
        }
        groupArtistMutable.setValue(groupArtist);
    }

    public void saveInfo() {
        groupSetupRepository.saveToDB(groupArtist, profileImageUri);
    }
}
