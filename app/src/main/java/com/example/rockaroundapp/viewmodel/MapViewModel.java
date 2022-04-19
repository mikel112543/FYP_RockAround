package com.example.rockaroundapp.viewmodel;

import android.location.Location;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rockaroundapp.model.Artist;
import com.example.rockaroundapp.model.Venue;
import com.example.rockaroundapp.repository.ArtistRepository;
import com.example.rockaroundapp.repository.UserRepository;
import com.example.rockaroundapp.repository.VenueRepository;

import java.util.List;

public class MapViewModel extends ViewModel {

    private final UserRepository userRepository;
    private final VenueRepository venueRepository;
    private final ArtistRepository artistRepository;

    public MapViewModel() {
        userRepository = new UserRepository();
        venueRepository = new VenueRepository();
        artistRepository = new ArtistRepository();
    }

    public MutableLiveData<List<Double>> saveCoordinates(double devLat, double devLong, String userType) {
        return userRepository.saveLocation(devLat, devLong, userType);
    }

    public MutableLiveData<String> getUserType(String userId) {
        return userRepository.findUserType(userId);
    }

    public MutableLiveData<List<Venue>> getVenues() {
        return venueRepository.getVenueListMutable();
    }


    public MutableLiveData<List<Artist>> getArtists() {
        return artistRepository.getArtistListMutable();
    }
}
