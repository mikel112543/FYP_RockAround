package com.example.rockaroundapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rockaroundapp.model.Artist;
import com.example.rockaroundapp.model.Venue;
import com.example.rockaroundapp.repository.ArtistRepository;
import com.example.rockaroundapp.repository.VenueRepository;

import java.util.List;

public class DiscoverViewModel extends ViewModel {

    private MutableLiveData<List<Venue>> venueList;
    private ArtistRepository artistRepository;
    private VenueRepository venueRepository;

    public DiscoverViewModel() {
        artistRepository = new ArtistRepository();
        venueRepository = new VenueRepository();
    }

    public MutableLiveData<List<Artist>> getArtistList(int order) {
        return artistRepository.getArtistListMutable(order);
    }

    public MutableLiveData<List<Venue>> getVenueList(int order) {
        return venueRepository.getVenueListMutable();
    }
}
