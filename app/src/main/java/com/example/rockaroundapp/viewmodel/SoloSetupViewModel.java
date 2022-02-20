package com.example.rockaroundapp.viewmodel;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rockaroundapp.model.Artist;
import com.example.rockaroundapp.repository.SoloSetupRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SoloSetupViewModel extends ViewModel {

    public MutableLiveData<String> stageName;
    public MutableLiveData<List<String>> genresMutable;
    public MutableLiveData<String> bio;
    public MutableLiveData<String> price;
    public MutableLiveData<String> contactNumber;
    public MutableLiveData<String> genresStringMutable;
    private MutableLiveData<Artist> artistMutable;
    private MutableLiveData<Boolean> setUpSuccess;
    private MutableLiveData<Uri> imagePath;
    private SoloSetupRepository soloSetupRepository;
    private List<String> genreList;
    private Uri profileImageUri;
    private Artist artist;

    public SoloSetupViewModel() {
        soloSetupRepository = new SoloSetupRepository();
        stageName = new MutableLiveData<>();
        bio = new MutableLiveData<>();
        artistMutable = new MutableLiveData<>();
        price = new MutableLiveData<>();
        contactNumber = new MutableLiveData<>();
        genresMutable = new MutableLiveData<>();
        genresStringMutable = new MutableLiveData<>();
        genreList = new ArrayList<>();
        artist = new Artist();
        setUpSuccess = soloSetupRepository.getSuccess();

    }

    public MutableLiveData<Artist> getArtist() {
        if(artistMutable== null) {
            artistMutable = new MutableLiveData<>();
        }
        return artistMutable;
    }

    public void setGenre(List<String> genres) {
        genresMutable.setValue(genres);
    }

    public void setGenresString(String genresString) {
        genresStringMutable.setValue(genresString);
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
        artist.setBio(bio.getValue());
        artist.setStageName(stageName.getValue());
        artist.setPrice(price.getValue());
        artist.setGenres(genresMutable.getValue());
        artist.setContactNumber(contactNumber.getValue());
        artist.setProfileImgURL(profileImageUri);
        artistMutable.setValue(artist);
    }

    public void saveInfo() {
        soloSetupRepository.saveToDB(artist);
    }
}
