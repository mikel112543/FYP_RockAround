package com.example.rockaroundapp.repository;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.rockaroundapp.dao.ArtistDaoImpl;
import com.example.rockaroundapp.dao.VenueDaoImpl;
import com.example.rockaroundapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;


public class UserRepository {
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private final MutableLiveData<String> loginFailureMsg;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private String failedRegistration;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public UserRepository() {
        firebaseUserMutableLiveData = new MutableLiveData<>();
        loginFailureMsg = new MutableLiveData<>();

        if(auth.getCurrentUser() != null) {
            firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
        }
    }

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public String getFailedRegistration() {
        return failedRegistration;
    }

    public MutableLiveData<String> getLoginFailureMsg() {
        return loginFailureMsg;
    }

    public void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
            }else{
                loginFailureMsg.postValue("Error logging in");
            }
        });
    }

    public void register(User user) {
        auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
                if(user.getUserType().equals("SOLO")) {
                    DocumentReference documentReference = db.collection("solo")
                                                            .document(Objects.requireNonNull(auth.getCurrentUser()).getUid());
                    documentReference.set(user.objectMap(user));
                }else if(user.getUserType().equals("GROUP")) {
                    DocumentReference documentReference = db.collection("group")
                                                            .document(Objects.requireNonNull(auth.getCurrentUser()).getUid());
                    documentReference.set(user.objectMap(user));
                }else {
                    DocumentReference documentReference = db.collection("venues")
                            .document(Objects.requireNonNull(auth.getCurrentUser()).getUid());
                    documentReference.set(user.objectMap(user));
                }
            }else{
                failedRegistration = "Registration Failed please!";
            }
        });
    }

    public void signOut() {
        auth.signOut();
    }
}
