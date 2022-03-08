package com.example.rockaroundapp.viewmodel;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rockaroundapp.model.Venue;
import com.example.rockaroundapp.repository.VenueSetupRepository;

import java.util.Objects;

public class VenueSetupViewModel extends ViewModel {

    private MutableLiveData<String> venueName;
    private MutableLiveData<String> venueBio;
    private MutableLiveData<String> addressLineOne;
    private MutableLiveData<String> addressLineTwo;
    private MutableLiveData<String> city;
    private MutableLiveData<String> county;
    private MutableLiveData<String> country;
    private MutableLiveData<String> maxCapacity;
    private MutableLiveData<String> contactNumber;
    private MutableLiveData<Boolean> success;
    private MutableLiveData<Venue> venueMutableLiveData;
    private VenueSetupRepository venueSetupRepository;
    private Venue venue;

    public VenueSetupViewModel() {
        venueSetupRepository = new VenueSetupRepository();
        success = venueSetupRepository.getSuccess();
        venueName = new MutableLiveData<>("");
        venueBio = new MutableLiveData<>("");
        addressLineOne = new MutableLiveData<>("");
        addressLineTwo = new MutableLiveData<>("");
        city = new MutableLiveData<>("");
        county = new MutableLiveData<>("");
        country = new MutableLiveData<>("");
        maxCapacity = new MutableLiveData<>("");
        contactNumber = new MutableLiveData<>("");
        venue = new Venue();
    }

    public MutableLiveData<Venue> getVenueMutableLiveData() {
        if (venueMutableLiveData == null) {
            venueMutableLiveData = new MutableLiveData<>();
        }
        return venueMutableLiveData;
    }

    public MutableLiveData<Boolean> getSuccess() {
        return success;
    }

    public MutableLiveData<String> getVenueName() {
        return venueName;
    }

    public MutableLiveData<String> getVenueBio() {
        return venueBio;
    }

    public MutableLiveData<String> getAddressLineOne() {
        return addressLineOne;
    }

    public MutableLiveData<String> getAddressLineTwo() {
        return addressLineTwo;
    }

    public MutableLiveData<String> getCity() {
        return city;
    }

    public MutableLiveData<String> getCounty() {
        return county;
    }

    public MutableLiveData<String> getCountry() {
        return country;
    }

    public MutableLiveData<String> getMaxCapacity() {
        return maxCapacity;
    }

    public MutableLiveData<String> getContactNumber() {
        return contactNumber;
    }

    public void onSaveClicked() {
        venue.setVenueName(getVenueName().getValue());
        venue.setBio(getVenueBio().getValue());
        venue.setAddressLineOne(getAddressLineOne().getValue());
        venue.setAddressLineTwo(getAddressLineTwo().getValue());
        venue.setCity(getCity().getValue());
        venue.setCounty(getCounty().getValue());
        venue.setCountry(getCountry().getValue());
        venue.setContact(getContactNumber().getValue());
        if(!Objects.requireNonNull(maxCapacity.getValue()).isEmpty()) {
            venue.setCapacity(Integer.parseInt(Objects.requireNonNull(getMaxCapacity().getValue())));
        }
        venueMutableLiveData.postValue(venue);
    }

    public void saveInfo() {
        venueSetupRepository.saveToDB(venueMutableLiveData);
    }

    public void setProfileImageUri(Uri selectedImageUri) {
        venue.setProfileImgURL(selectedImageUri);
    }
}
