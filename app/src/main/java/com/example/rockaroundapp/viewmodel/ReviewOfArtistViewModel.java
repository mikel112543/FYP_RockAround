package com.example.rockaroundapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rockaroundapp.model.ArtistReview;
import com.example.rockaroundapp.repository.ArtistRepository;
import com.example.rockaroundapp.repository.ReviewRepository;
import com.example.rockaroundapp.repository.UserRepository;

public class ReviewOfArtistViewModel extends ViewModel {

    private ReviewRepository repository;
    private UserRepository userRepository;
    private MutableLiveData<String> reviewTitle;
    private MutableLiveData<String> reviewDescription;
    private ArtistReview review;
    private MutableLiveData<ArtistReview> _review;
    private MutableLiveData<Boolean> success;
    private int stagePresenceRating;
    private int vocalsRating;
    private int communicationRating;
    private int reliabilityRating;

    public ReviewOfArtistViewModel() {
        repository = new ReviewRepository();
        userRepository = new UserRepository();
        reviewTitle = new MutableLiveData<>("");
        reviewDescription = new MutableLiveData<>("");
        _review = new MutableLiveData<>();
        success = repository.getSuccess();
    }

    public MutableLiveData<ArtistReview> get_review() {
        if(_review == null) {
            _review = new MutableLiveData<>();
        }
        return _review;
    }

    public MutableLiveData<Boolean> getSuccess() {
        return success;
    }

    public MutableLiveData<String> getUserType(String userId) {
        return userRepository.findByUserType(userId);
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
}