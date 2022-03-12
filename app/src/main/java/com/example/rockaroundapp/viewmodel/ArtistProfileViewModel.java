package com.example.rockaroundapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rockaroundapp.model.Artist;
import com.example.rockaroundapp.model.GroupArtist;
import com.example.rockaroundapp.repository.ArtistRepository;

public class ArtistProfileViewModel extends ViewModel {

    private ArtistRepository repository;

    public ArtistProfileViewModel() {
        repository = new ArtistRepository();
    }

    public LiveData<Artist> getSoloArtist(String id) {
        return repository.findSoloById(id);
    }

    public LiveData<GroupArtist> getGroupArtist(String id){
        return repository.findByGroupId(id);
    }
}

