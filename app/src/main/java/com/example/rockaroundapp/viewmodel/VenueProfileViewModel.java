package com.example.rockaroundapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.rockaroundapp.model.Venue;
import com.example.rockaroundapp.repository.VenueRepository;

public class VenueProfileViewModel extends ViewModel {

    private VenueRepository repository;

    public VenueProfileViewModel(){
        repository = new VenueRepository();
    }

    public LiveData<Venue> getVenue(String id) {
        return repository.findById(id);
    }
}
