package com.example.rockaroundapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rockaroundapp.model.VenueReview;
import com.example.rockaroundapp.repository.UserRepository;
import com.example.rockaroundapp.repository.VenueReviewsRepository;

public class VenueReviewsViewModel extends ViewModel {

    private final VenueReviewsRepository reviewsRepository;
    private final UserRepository userRepository;
    private final MutableLiveData<String> reviewTitle;
    private final MutableLiveData<String> reviewDescription;
    private MutableLiveData<VenueReview> _venueReview;
    private MutableLiveData<Boolean> success;
    private int atmosphereRating;
    private int settingRating;
    private int communicationRating;
    private int reliabilityRating;

    public VenueReviewsViewModel() {
        reviewsRepository = new VenueReviewsRepository();
        userRepository = new UserRepository();
        reviewTitle = new MutableLiveData<>("");
        reviewDescription = new MutableLiveData<>("");
        _venueReview = new MutableLiveData<>();
        success = reviewsRepository.getSuccess();
    }

    public MutableLiveData<String> getReviewTitle() {
        return reviewTitle;
    }

    public MutableLiveData<String> getReviewDescription() {
        return reviewDescription;
    }

    public MutableLiveData<VenueReview> get_venueReview() {
        if(_venueReview == null) {
            _venueReview = new MutableLiveData<>();
        }
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
        VenueReview review = new VenueReview();
        review.setTitle(reviewTitle.getValue());
        review.setDescription(reviewDescription.getValue());
        review.setCommunicationRating(communicationRating);
        review.setReliabilityRating(reliabilityRating);
        review.setSettingRating(settingRating);
        review.setAtmosphereRating(atmosphereRating);
        _venueReview.postValue(review);
    }

    public void saveReview(VenueReview venueReview) {
        reviewsRepository.saveVenueReview(venueReview);
    }
}
