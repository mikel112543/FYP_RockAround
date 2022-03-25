package com.example.rockaroundapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rockaroundapp.model.Artist;
import com.example.rockaroundapp.model.Venue;
import com.example.rockaroundapp.repository.ArtistRepository;
import com.example.rockaroundapp.repository.VenueRepository;

import java.util.List;

public class DiscoverViewModel extends ViewModel {

    private MutableLiveData<List<Artist>> artistList;
    private MutableLiveData<List<Venue>> venueList;
    private ArtistRepository artistRepository;
    private VenueRepository venueRepository;

    public DiscoverViewModel() {
        artistRepository = new ArtistRepository();
        venueRepository = new VenueRepository();
        artistList = artistRepository.getArtistListMutable();
        venueList = venueRepository.getVenueListMutable();
    }

    public MutableLiveData<List<Artist>> getArtistList() {
        return artistList;
    }

    public MutableLiveData<List<Venue>> getVenueList() {
        return venueList;
    }
}