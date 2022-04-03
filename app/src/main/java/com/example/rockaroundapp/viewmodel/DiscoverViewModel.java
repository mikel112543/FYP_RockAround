package com.example.rockaroundapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rockaroundapp.model.Artist;
import com.example.rockaroundapp.model.Venue;
import com.example.rockaroundapp.repository.ArtistRepository;
import com.example.rockaroundapp.repository.UserRepository;
import com.example.rockaroundapp.repository.VenueRepository;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class DiscoverViewModel extends ViewModel {

    private MutableLiveData<List<Venue>> venueList;
    private MutableLiveData<String> currentUserType;
    private ArtistRepository artistRepository;
    private VenueRepository venueRepository;
    private UserRepository userRepository;
    private String currentId = FirebaseAuth.getInstance().getUid();

    public DiscoverViewModel() {
        userRepository = new UserRepository();
        artistRepository = new ArtistRepository();
        venueRepository = new VenueRepository();
    }

    public MutableLiveData<List<Artist>> getArtistList() {
        return artistRepository.getArtistListMutable();
    }

    public MutableLiveData<List<Venue>> getVenueList() {
        return venueRepository.getVenueListMutable();
    }

    public MutableLiveData<String> getCurrentUserType() {
        return userRepository.findUserType(currentId);
    }

    public void sortList(int order, String userType) {
        if (userType.equalsIgnoreCase("venue")) {
            artistRepository.sortList(order);
        }else{
            //TODO sort venue list
        }
    }
}
