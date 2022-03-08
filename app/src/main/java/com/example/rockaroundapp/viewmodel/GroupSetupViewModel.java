package com.example.rockaroundapp.viewmodel;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rockaroundapp.model.GroupArtist;
import com.example.rockaroundapp.repository.GroupSetupRepository;

import java.util.List;
import java.util.Objects;

public class GroupSetupViewModel extends ViewModel {

    public MutableLiveData<String> groupName;
    public MutableLiveData<String> bio;
    public MutableLiveData<String> noOfMembers;
    public MutableLiveData<String> contactNumber;
    public MutableLiveData<String> price;
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

    public void setGenre(List<String> genres) {
        genresMutable.postValue(genres);
    }

    public void setGenresString(String genresString) {
        genresStringMutable.postValue(genresString);
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
        groupArtist.setProfileImg(profileImageUri.toString());
        groupArtistMutable.setValue(groupArtist);
    }

    public void saveInfo() {
        groupSetupRepository.saveToDB(groupArtist, profileImageUri);
    }
}
