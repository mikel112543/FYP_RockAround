package com.example.rockaroundapp.viewmodel;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rockaroundapp.model.Artist;
import com.example.rockaroundapp.repository.ArtistRepository;
import com.example.rockaroundapp.repository.SoloSetupRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SoloSetupViewModel extends ViewModel {

    private MutableLiveData<String> stageName;
    private final MutableLiveData<List<String>> genresMutable;
    private MutableLiveData<String> bio;
     MutableLiveData<String> price;
    private MutableLiveData<String> contactNumber;
    private final MutableLiveData<String> genresStringMutable;
    private MutableLiveData<Artist> artistMutable;
    private final MutableLiveData<Boolean> setUpSuccess;
    private MutableLiveData<String> city;
    private final SoloSetupRepository soloSetupRepository;
    private Uri profileImageUri;
    private final Artist artist;

    public SoloSetupViewModel() {
        soloSetupRepository = new SoloSetupRepository();
        stageName = new MutableLiveData<>("");
        bio = new MutableLiveData<>("");
        artistMutable = new MutableLiveData<>();
        price = new MutableLiveData<>("");
        contactNumber = new MutableLiveData<>("");
        genresMutable = new MutableLiveData<>();
        genresStringMutable = new MutableLiveData<>("");
        city = new MutableLiveData<>("");
        artist = new Artist();
        setUpSuccess = soloSetupRepository.getSuccess();

    }

    public MutableLiveData<Artist> getArtist() {
        if(artistMutable== null) {
            artistMutable = new MutableLiveData<>();
        }
        return artistMutable;
    }

    public MutableLiveData<String> getCity() {
        return city;
    }

    public void setCity(MutableLiveData<String> city) {
        this.city = city;
    }

    public MutableLiveData<String> getStageName() {
        return stageName;
    }

    public void setStageName(MutableLiveData<String> stageName) {
        this.stageName = stageName;
    }

    public MutableLiveData<String> getBio() {
        return bio;
    }

    public void setBio(MutableLiveData<String> bio) {
        this.bio = bio;
    }

    public MutableLiveData<String> getPrice() {
        return price;
    }

    public void setPrice(MutableLiveData<String> price) {
        this.price = price;
    }

    public MutableLiveData<String> getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(MutableLiveData<String> contactNumber) {
        this.contactNumber = contactNumber;
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
        artist.setContact(contactNumber.getValue());
        artist.setCity(city.getValue());
        if(profileImageUri != null) {
            artist.setProfileImg(profileImageUri.toString());
        }else {
            artist.setProfileImg("");
        }
        artistMutable.setValue(artist);
    }

    public void saveInfo() {
        soloSetupRepository.saveToDB(artist, profileImageUri);
    }
}
