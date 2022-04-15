package com.example.rockaroundapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rockaroundapp.model.VenueReview;
import com.example.rockaroundapp.repository.UserRepository;
import com.example.rockaroundapp.repository.VenueReviewsRepository;

public class VenueReviewsViewModel extends ViewModel {

    private final VenueReviewsRepository reviewsRepository;
    private final UserRepository userRepository;
    private MutableLiveData<String> reviewTitle;
    private MutableLiveData<String> reviewDescription;
    private VenueReview review;
    private MutableLiveData<VenueReview> _venueReview;
    private MutableLiveData<Boolean> success;
    private int atmosphereRating;
    private int settingRating;
    private int communicationRating;
    private int reliabilityRating;

    //TODO Linkup Reviews with LiveData and Repo

    public VenueReviewsViewModel() {
        reviewsRepository = new VenueReviewsRepository();
        userRepository = new UserRepository();
        reviewTitle = new MutableLiveData<>();
        reviewDescription = new MutableLiveData<>();
        _venueReview = new MutableLiveData<>();
    }

    public MutableLiveData<String> getReviewTitle() {
        return reviewTitle;
    }

    public MutableLiveData<String> getReviewDescription() {
        return reviewDescription;
    }

    public VenueReview getReview() {
        return review;
    }

    public MutableLiveData<VenueReview> get_venueReview() {
        return _venueReview;
    }

    public MutableLiveData<Boolean> getSuccess() {
        return success;
    }

    public void setAtmosphereRating(int atmosphereRating) {
        this.atmosphereRating = atmosphereRating;
    }

    public void setSettingRating(int settingRating) {
        this.settingRating = settingRating;
    }

    public void setCommunicationRating(int communicationRating) {
        this.communicationRating = communicationRating;
    }

    public void setReliabilityRating(int reliabilityRating) {
        this.reliabilityRating = reliabilityRating;
    }

    public void onSubmitClick() {
    }
}
