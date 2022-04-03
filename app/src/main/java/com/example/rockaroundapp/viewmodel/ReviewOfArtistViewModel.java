package com.example.rockaroundapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rockaroundapp.model.ArtistReview;
import com.example.rockaroundapp.model.Venue;
import com.example.rockaroundapp.repository.ArtistReviewsRepository;
import com.example.rockaroundapp.repository.UserRepository;

import java.util.List;

public class ReviewOfArtistViewModel extends ViewModel {

    private ArtistReviewsRepository repository;
    private UserRepository userRepository;
    private ArtistReviewsRepository reviewsRepository;
    private MutableLiveData<String> reviewTitle;
    private MutableLiveData<String> reviewDescription;
    private ArtistReview review;
    private MutableLiveData<ArtistReview> _review;
    private MutableLiveData<Boolean> success;
    private MutableLiveData<List<Venue>> _artistReviewers;
    private int stagePresenceRating;
    private int vocalsRating;
    private int communicationRating;
    private int reliabilityRating;

    public ReviewOfArtistViewModel() {
        repository = new ArtistReviewsRepository();
        userRepository = new UserRepository();
        reviewsRepository = new ArtistReviewsRepository();
        reviewTitle = new MutableLiveData<>("");
        reviewDescription = new MutableLiveData<>("");
        _review = new MutableLiveData<>();
        _artistReviewers = new MutableLiveData<>();
        success = repository.getSuccess();
    }

    public MutableLiveData<ArtistReview> get_review() {
        if (_review == null) {
            _review = new MutableLiveData<>();
        }
        return _review;
    }

    public MutableLiveData<List<Venue>> get_artistReviewers(List<ArtistReview> artistReviews) {
        return repository.getReviewersForArtist(artistReviews);
    }

    public MutableLiveData<Boolean> getSuccess() {
        return success;
    }

    public MutableLiveData<String> getUserType(String userId) {
        return userRepository.findUserType(userId);
    }

    public MutableLiveData<String> getReviewTitle() {
        return reviewTitle;
    }

    public MutableLiveData<String> getReviewDescription() {
        return reviewDescription;
    }

    public void setStagePresenceRating(int stagePresenceRating) {
        this.stagePresenceRating = stagePresenceRating;
    }

    public void setVocalsRating(int vocalsRating) {
        this.vocalsRating = vocalsRating;
    }

    public void setCommunicationRating(int communicationRating) {
        this.communicationRating = communicationRating;
    }

    public void setReliabilityRating(int reliabilityRating) {
        this.reliabilityRating = reliabilityRating;
    }

    public void onSubmitClick() {
        review = new ArtistReview();
        review.setTitle(reviewTitle.getValue());
        review.setDescription(reviewDescription.getValue());
        review.setCommunicationRating(communicationRating);
        review.setReliabilityRating(reliabilityRating);
        review.setVocalsRating(vocalsRating);
        review.setStagePresenceRating(stagePresenceRating);
        _review.postValue(review);
    }

    public void submitReview(ArtistReview artistReview) {
        repository.submitArtistReview(artistReview);
    }

    public MutableLiveData<List<ArtistReview>> getAllReviews(String artistId) {
        return repository.getAllReviews(artistId);
    }
}