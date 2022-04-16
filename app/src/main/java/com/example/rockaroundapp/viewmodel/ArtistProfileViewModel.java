package com.example.rockaroundapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.rockaroundapp.model.Artist;
import com.example.rockaroundapp.model.ArtistReview;
import com.example.rockaroundapp.model.GroupArtist;
import com.example.rockaroundapp.model.Venue;
import com.example.rockaroundapp.repository.ArtistRepository;
import com.example.rockaroundapp.repository.ArtistReviewsRepository;
import com.example.rockaroundapp.repository.VenueRepository;

import java.util.List;

public class ArtistProfileViewModel extends ViewModel {

    private ArtistRepository repository;
    private ArtistReviewsRepository reviewsRepository;

    public ArtistProfileViewModel() {
        repository = new ArtistRepository();
        reviewsRepository = new ArtistReviewsRepository();
    }

    public LiveData<Artist> getSoloArtist(String artistId) {
        return repository.findSoloById(artistId);
    }

    public LiveData<GroupArtist> getGroupArtist(String artistId){
        return repository.findByGroupId(artistId);
    }

    public LiveData<Boolean> alreadyReviewed(String artistId) { return repository.alreadyReviewed(artistId, "venue");}

    public LiveData<List<ArtistReview>> getReviews(String artistId) {
        return reviewsRepository.getReviews(artistId);
    }

    public LiveData<Venue> getReviewer(String venueId) {
        return new VenueRepository().findById(venueId);
    }
}

