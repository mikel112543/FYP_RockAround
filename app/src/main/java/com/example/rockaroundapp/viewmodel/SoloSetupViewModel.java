package com.example.rockaroundapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rockaroundapp.model.Artist;
import com.example.rockaroundapp.repository.SoloSetupRepository;

public class SoloSetupViewModel extends ViewModel {

    private MutableLiveData<String> stageName;
    private MutableLiveData<String> genre;
    private MutableLiveData<String> bio;
    public MutableLiveData<Artist> artist;
    private SoloSetupRepository soloSetupRepository;

    public SoloSetupViewModel() {
        soloSetupRepository = new SoloSetupRepository();
        stageName = new MutableLiveData<>();
        genre = new MutableLiveData<>();
        bio = new MutableLiveData<>();
        artist = new MutableLiveData<>();
    }
}
