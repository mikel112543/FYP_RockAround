package com.example.rockaroundapp.repository;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.rockaroundapp.model.Artist;
import com.example.rockaroundapp.model.GroupArtist;
import com.example.rockaroundapp.model.User;
import com.example.rockaroundapp.model.Venue;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Objects;

public class UserRepository {
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private final MutableLiveData<Boolean> registerSuccess;
    private MutableLiveData<Boolean> loginSuccess;
    private MutableLiveData<String> userType;
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
        loginSuccess = new MutableLiveData<>();
        userType = new MutableLiveData<>();

        if (auth.getCurrentUser() != null) {
            firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
        }
    }

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public MutableLiveData<String> getUserType() {
        return userType;
    }

    public MutableLiveData<Boolean> getLoginSuccess() {
        return loginSuccess;
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
                findByUserType();
            } else {
                loginSuccess.postValue(false);
            }
        });
    }

    public void register(User user, String pass) {
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
                    documentReference = db.collection("venue")
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

    private void findByUserType() {
        documentReference = db.collection("solo").document(auth.getCurrentUser().getUid());
        documentReference.addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                Log.e(TAG, "User not solo");
            }
            assert snapshot != null;
            if (snapshot.getData() != null) {
                Artist artist = snapshot.toObject(Artist.class);
                assert artist != null;
                userType.postValue(artist.getUserType());
            }
        });
        documentReference = db.collection("group").document(auth.getCurrentUser().getUid());
        documentReference.addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                Log.e(TAG, "User not group");
            }
            assert snapshot != null;
            if (snapshot.getData() != null) {
                GroupArtist groupArtist = snapshot.toObject(GroupArtist.class);
                assert groupArtist != null;
                userType.postValue(groupArtist.getUserType());
            }
        });
        documentReference = db.collection("venue").document(auth.getCurrentUser().getUid());
        documentReference.addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                Log.e(TAG, "User not venue");
            }
            assert snapshot != null;
            if (snapshot.getData() != null) {
                Venue venue = snapshot.toObject(Venue.class);
                assert venue != null;
                userType.postValue(venue.getUserType());
            }
        });
    }

    public void signOut() {
        auth.signOut();
    }
}
