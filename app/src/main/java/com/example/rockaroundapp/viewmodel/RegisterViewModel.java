package com.example.rockaroundapp.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rockaroundapp.model.Artist;
import com.example.rockaroundapp.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RegisterViewModel extends ViewModel {

    private UserRepository userRepo = new UserRepository();

    public void init(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public MutableLiveData<String> email = new MutableLiveData<>("");
    public MutableLiveData<String> password = new MutableLiveData<>("");
    public MutableLiveData<String> firstname = new MutableLiveData<>("");
    public MutableLiveData<String> surname = new MutableLiveData<>("");
    public MutableLiveData<String> userType = new MutableLiveData<>(UserType.NONE.name());

    private MutableLiveData<List<String>> registerDetails;

    public enum UserType {
        NONE,
        SOLO,
        GROUP,
        VENUE;
    }

    public boolean checkEmail(String checkEmail) {
        //init(userRepo);
        return userRepo.checkEmail(checkEmail);
    }

    public void setButton(UserType type) {
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

        registerDetails.setValue(details);
    }

    public boolean validateEmail() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return Objects.requireNonNull(email.getValue()).matches(emailPattern);
    }
    public boolean validatePassword() {
        return TextUtils.isEmpty(password.getValue());
    }
    public boolean validateFirstname() {
        return TextUtils.isEmpty(firstname.getValue());
    }
    public boolean validateLastname() {
        return TextUtils.isEmpty(surname.getValue());
    }
    public boolean checkType() {
        return !Objects.equals(userType.getValue(), UserType.NONE.name());
    }

}
