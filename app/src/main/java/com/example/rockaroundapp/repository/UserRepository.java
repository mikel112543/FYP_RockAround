package com.example.rockaroundapp.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.rockaroundapp.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Objects;

public class UserRepository {
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private MutableLiveData<Boolean> registerSuccess;
    private final MutableLiveData<String> loginFailureMsg;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private String failedRegistration;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference documentReference;
    private HashMap<String, Object> userData;
    Logger logger = Logger.getLogger(UserRepository.class);

    public UserRepository() {
        firebaseUserMutableLiveData = new MutableLiveData<>();
        loginFailureMsg = new MutableLiveData<>();
        registerSuccess = new MutableLiveData<>(false);

        if (auth.getCurrentUser() != null) {
            firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
        }
    }

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public MutableLiveData<Boolean> getRegisterSuccess() {
        return registerSuccess;
    }

    public String getFailedRegistration() {
        return failedRegistration;
    }

    public MutableLiveData<String> getLoginFailureMsg() {
        return loginFailureMsg;
    }

    public void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
            } else {
                loginFailureMsg.postValue("Error logging in");
            }
        });
    }

    public void register( User user, String pass) {
        auth.createUserWithEmailAndPassword(user.getEmail(), pass).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (user.getUserType() == "SOLO") {
                    documentReference = db.collection("solo")
                            .document(Objects.requireNonNull(auth.getCurrentUser()).getUid());
                    userData = new HashMap<>();
                    userData.put("id", auth.getCurrentUser().getUid());
                    userData.put("firstname", user.getFirstname());
                    userData.put("lastname", user.getLastname());
                    userData.put("email", user.getEmail());
                    //documentReference.set(user.objectMap(user));
                } else if (user.getUserType() == "GROUP") {
                    documentReference = db.collection("group")
                            .document(Objects.requireNonNull(auth.getCurrentUser()).getUid());
                    userData = new HashMap<>();
                    userData.put("id", auth.getCurrentUser().getUid());
                    userData.put("firstname", user.getFirstname());
                    userData.put("lastname", user.getLastname());
                    userData.put("email", user.getEmail());
                } else {
                    documentReference = db.collection("venues")
                            .document(Objects.requireNonNull(auth.getCurrentUser()).getUid());
                    //documentReference.set(user.objectMap(user));
                    userData = new HashMap<>();
                    userData.put("id", auth.getCurrentUser().getUid());
                    userData.put("firstname", user.getFirstname());
                    userData.put("lastname", user.getLastname());
                    userData.put("email", user.getEmail());
                }
                documentReference.set(userData).addOnCompleteListener(unused -> {
                    logger.info("Successfully registered");
                    registerSuccess.postValue(true);
                    //firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
                }).addOnFailureListener(e -> Log.e("Failed to register", e.getMessage()));
            } else {
                failedRegistration = "Registration Failed please!";
            }
        })
        .addOnFailureListener(e -> {
            Log.e("Failed to register", e.getMessage());

        });
    }

    public void signOut() {
        auth.signOut();
    }
}
