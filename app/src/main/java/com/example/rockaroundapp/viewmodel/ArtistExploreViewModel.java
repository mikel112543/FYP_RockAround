package com.example.rockaroundapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rockaroundapp.model.Artist;
import com.example.rockaroundapp.repository.ArtistRepository;

import java.util.ArrayList;
import java.util.List;

public class ArtistExploreViewModel extends ViewModel {

    private MutableLiveData<List<Artist>> artistList;
    private ArtistRepository artistRepository;

    public ArtistExploreViewModel() {
        artistList = new MutableLiveData<>();
        artistRepository = new ArtistRepository();
    }

    public MutableLiveData<List<Artist>> getArtistList() {
        artistList = artistRepository.getArtistListMutable();
        return artistList;
    }
}
