package com.example.rockaroundapp.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.rockaroundapp.model.Artist;
import com.example.rockaroundapp.repository.ArtistRepository;

public class SoloProfileViewModel extends ViewModel {

    private ArtistRepository repository;
    private Artist artist;

    public SoloProfileViewModel() {
        repository = new ArtistRepository();
    }

    public Artist getArtist(String id) {
        return repository.findSoloById(id);
    }
}
