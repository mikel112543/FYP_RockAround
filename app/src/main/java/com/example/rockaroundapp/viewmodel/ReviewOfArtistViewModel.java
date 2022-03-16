package com.example.rockaroundapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rockaroundapp.model.ArtistReview;
import com.example.rockaroundapp.repository.ArtistRepository;

public class ReviewOfArtistViewModel extends ViewModel {

    private ArtistRepository repository;
    private MutableLiveData<String> reviewTitle;
    private MutableLiveData<String> reviewDescription;
    private ArtistReview review;
    private MutableLiveData<ArtistReview> _review;
    private int stagePresenceRating;
    private int vocalsRating;
    private int communicationRating;
    private int reliabilityRating;

    public ReviewOfArtistViewModel() {
        repository = new ArtistRepository();
        reviewTitle = new MutableLiveData<>();
        reviewDescription = new MutableLiveData<>();
        _review = new MutableLiveData<>();
    }

    public MutableLiveData<ArtistReview> get_review() {
        if(_review == null) {
            _review = new MutableLiveData<>();
        }
        return _review;
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
}