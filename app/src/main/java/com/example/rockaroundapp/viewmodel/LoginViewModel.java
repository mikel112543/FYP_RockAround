package com.example.rockaroundapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rockaroundapp.repository.UserRepository;
import com.example.rockaroundapp.view.LoginFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class LoginViewModel extends ViewModel {

    private final UserRepository userRepository;
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private MutableLiveData<String> loginFailureMsg;
    private MutableLiveData<String> email = new MutableLiveData<>("");
    private MutableLiveData<String> password = new MutableLiveData<>("");
    private MutableLiveData<String> userType = new MutableLiveData<>(UserType.NONE.name());
    private MutableLiveData<List<String>> loginDetails = new MutableLiveData<>();
    private MutableLiveData<Boolean> registerButtonPressed = new MutableLiveData<>(false);

    public LoginViewModel() {
        userRepository = new UserRepository();
        firebaseUserMutableLiveData = userRepository.getFirebaseUserMutableLiveData();
        loginFailureMsg = userRepository.getLoginFailureMsg();
    }

    public MutableLiveData<String> getLoginFailureMsg() {
        return loginFailureMsg;
    }

    public enum UserType {
        NONE,
        SOLO,
        GROUP,
        VENUE
    }

    public MutableLiveData<Boolean> getRegisterButtonPressed() {
        return registerButtonPressed;
    }

    public void signOut() {
        userRepository.signOut();
    }

    public MutableLiveData<List<String>> getDetails() {
        if (loginDetails == null) {
            loginDetails = new MutableLiveData<>();
        }
        return loginDetails;
    }

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public MutableLiveData<String> getEmail() {
        return email;
    }

    public MutableLiveData<String> getPassword() {
        return password;
    }

    public void setUserType(LoginViewModel.UserType type) {
        userType.postValue(type.name());
    }

    public MutableLiveData<String> getUserType() {
        return userType;
    }

    public void onLoginClick() {
        ArrayList<String> details = new ArrayList<>();
        details.add(email.getValue());
        details.add(password.getValue());
        loginDetails.setValue(details);
    }

    /*public void onRegisterClick() {
        //userType.postValue(userType.getValue());
        registerButtonPressed.postValue(true);
    }*/

    public void loginUser(String loginEmail, String pass) {
        userRepository.loginUser(loginEmail, pass);
    }
}
