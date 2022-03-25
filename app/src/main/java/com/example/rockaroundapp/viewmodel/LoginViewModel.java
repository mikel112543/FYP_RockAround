package com.example.rockaroundapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rockaroundapp.repository.UserRepository;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class LoginViewModel extends ViewModel {

    private final UserRepository userRepository;
    private final MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private final MutableLiveData<String> loginFailureMsg;
    private final MutableLiveData<String> email = new MutableLiveData<>("");
    private final MutableLiveData<String> password = new MutableLiveData<>("");
    private final MutableLiveData<String> userType;
    private MutableLiveData<List<String>> loginDetails = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loginSuccess;

    public LoginViewModel() {
        userRepository = new UserRepository();
        firebaseUserMutableLiveData = userRepository.getFirebaseUserMutableLiveData();
        loginFailureMsg = userRepository.getLoginFailureMsg();
        loginSuccess = userRepository.getLoginSuccess();
        userType = userRepository.getUserType();
    }

    public enum UserType {
        NONE,
        SOLO,
        GROUP,
        VENUE
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

    public MutableLiveData<Boolean> getLoginSuccess() {
        return loginSuccess;
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
