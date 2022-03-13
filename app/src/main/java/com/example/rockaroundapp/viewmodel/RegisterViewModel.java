package com.example.rockaroundapp.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rockaroundapp.model.Artist;
import com.example.rockaroundapp.model.GroupArtist;
import com.example.rockaroundapp.model.Venue;
import com.example.rockaroundapp.repository.UserRepository;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RegisterViewModel extends ViewModel {

    private UserRepository userRepository;
    private MutableLiveData<FirebaseUser> firebaseUserRegister;
    private MutableLiveData<String> email;
    private MutableLiveData<String> password;
    private MutableLiveData<String> firstname;
    private MutableLiveData<String> surname;
    private MutableLiveData<String> userType;
    private MutableLiveData<List<String>> registerDetails;
    private MutableLiveData<Boolean> registerSuccess;
    private String failedRegMessage;

    public enum UserType {
        NONE,
        SOLO,
        GROUP,
        VENUE
    }

    public RegisterViewModel() {
        userRepository = new UserRepository();
        firebaseUserRegister = userRepository.getFirebaseUserMutableLiveData();
        failedRegMessage = userRepository.getFailedRegistration();
        email = new MutableLiveData<>("");
        password = new MutableLiveData<>("");
        firstname = new MutableLiveData<>("");
        surname = new MutableLiveData<>("");
        userType = new MutableLiveData<>(LoginViewModel.UserType.NONE.name());
        registerSuccess = userRepository.getRegisterSuccess();

    }

    public String getFailedRegMessage() {
        return failedRegMessage;
    }

    public MutableLiveData<String> getEmail() {
        return email;
    }

    public MutableLiveData<String> getPassword() {
        return password;
    }

    public MutableLiveData<String> getFirstname() {
        return firstname;
    }

    public MutableLiveData<String> getSurname() {
        return surname;
    }

    public MutableLiveData<Boolean> getRegisterSuccess() {
        return registerSuccess;
    }

    public void register() {
        if (userType.getValue() == UserType.SOLO.name()) {
            Artist artist = new Artist(firstname.getValue(), surname.getValue(), email.getValue());
            userRepository.register(artist, password.getValue());
        } else if (userType.getValue() == UserType.GROUP.name()) {
            GroupArtist groupArtist = new GroupArtist(firstname.getValue(), surname.getValue(), email.getValue());
            userRepository.register(groupArtist, password.getValue());
        } else {
            Venue venue = new Venue(firstname.getValue(), surname.getValue(), email.getValue());
            userRepository.register(venue, password.getValue());
        }
    }

    public void signOut() {
        userRepository.signOut();
    }

    public void setUserType(LoginViewModel.UserType type) {
        userType.postValue(type.name());
    }


    public MutableLiveData<List<String>> getDetails() {
        if (registerDetails == null) {
            registerDetails = new MutableLiveData<>();
        }
        return registerDetails;
    }

    public void onRegisterClick() {
        ArrayList<String> details = new ArrayList<>();
        details.add(email.getValue());
        details.add(password.getValue());
        details.add(userType.getValue());
        details.add(firstname.getValue());
        details.add(surname.getValue());

        registerDetails.postValue(details);
    }

    public boolean validateEmail() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return Objects.requireNonNull(email.getValue()).matches(emailPattern) && !TextUtils.isEmpty(email.getValue());
    }

    public boolean validatePassword() {
        return !TextUtils.isEmpty(password.getValue());
    }

    public boolean validateFirstname() {
        return !TextUtils.isEmpty(firstname.getValue());
    }

    public boolean validateLastname() {
        return !TextUtils.isEmpty(surname.getValue());
    }

    public boolean validateType() {
        return !Objects.equals(userType.getValue(), "NONE");
    }


}
