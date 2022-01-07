package com.example.rockaroundapp.viewmodel;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RegisterViewModel extends ViewModel {

    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<String> firstname = new MutableLiveData<>();
    public MutableLiveData<String> surname = new MutableLiveData<>();
    public MutableLiveData<String> userType = new MutableLiveData<>(UserType.NONE.name());

    private MutableLiveData<List<String>> registerDetails;

    @BindingAdapter("app:errorText")
    public static void setErrorMessage(TextInputLayout view, String error) {
        view.setError(error);
    }

    public enum UserType {
        NONE,
        SOLO,
        GROUP,
        VENUE;
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

    public boolean badEmailPattern() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return Objects.requireNonNull(email.getValue()).matches(emailPattern);
    }

}
