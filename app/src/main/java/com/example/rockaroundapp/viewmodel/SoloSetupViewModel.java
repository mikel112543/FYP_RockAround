package com.example.rockaroundapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rockaroundapp.model.Artist;
import com.example.rockaroundapp.repository.SoloSetupRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SoloSetupViewModel extends ViewModel {

    public MutableLiveData<String> stageName;
    public MutableLiveData<List<String>> genre;
    public MutableLiveData<String> bio;
    public MutableLiveData<String> price;
    public MutableLiveData<String> contactNumber;

    private MutableLiveData<Artist> artistMutable;
    private SoloSetupRepository soloSetupRepository;

    public SoloSetupViewModel() {
        soloSetupRepository = new SoloSetupRepository();
        stageName = new MutableLiveData<>();
        genre = new MutableLiveData<>();
        bio = new MutableLiveData<>();
        artistMutable = new MutableLiveData<>();
        price = new MutableLiveData<>();
        contactNumber = new MutableLiveData<>();
    }

    public MutableLiveData<Artist> getArtist() {
        if(artistMutable== null) {
            artistMutable = new MutableLiveData<>();
        }
        return artistMutable;
    }

    public void onSaveClicked() {
        Artist artist = new Artist();
        artist.setBio(bio.getValue());
        artist.setStageName(stageName.getValue());
        artist.setPrice(price.getValue());
        artistMutable.postValue(artist);
    }

}
