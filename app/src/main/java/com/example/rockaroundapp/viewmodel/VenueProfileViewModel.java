package com.example.rockaroundapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rockaroundapp.model.Venue;
import com.example.rockaroundapp.model.VenueReview;
import com.example.rockaroundapp.repository.VenueRepository;
import com.example.rockaroundapp.repository.VenueReviewsRepository;

import java.util.List;

public class VenueProfileViewModel extends ViewModel {

    private final VenueRepository repository;
    private final VenueReviewsRepository reviewsRepository;

    public VenueProfileViewModel(){
        repository = new VenueRepository();
        reviewsRepository = new VenueReviewsRepository();
    }

    public LiveData<Venue> getVenue(String id) {
        return repository.findById(id);
    }

    public MutableLiveData<Boolean> getAlreadyReviewed(String userId) {
        return reviewsRepository.alreadyReviewed(userId);
    }

    public LiveData<List<VenueReview>> getReviews(String id) {
        return reviewsRepository.getReviews(id);
    }
}
