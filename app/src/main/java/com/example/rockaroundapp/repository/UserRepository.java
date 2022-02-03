package com.example.rockaroundapp.repository;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.rockaroundapp.dao.ArtistDaoImpl;
import com.example.rockaroundapp.dao.VenueDaoImpl;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;


public class UserRepository {
    private Application application;
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private MutableLiveData<String> loginFailureMsg;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private ArtistDaoImpl artistDAO;
    private VenueDaoImpl venueDAO;

    public UserRepository() {
        artistDAO = new ArtistDaoImpl();
        venueDAO = new VenueDaoImpl();
        firebaseUserMutableLiveData = new MutableLiveData<>();
        loginFailureMsg = new MutableLiveData<>();

        if(auth.getCurrentUser() != null) {
            firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
        }
    }

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public MutableLiveData<String> getLoginFailureMsg() {
        return loginFailureMsg;
    }

    public void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
                }else{
                    loginFailureMsg.postValue("Error logging in");
                }
            }
        });
    }


    public boolean checkEmail(String email) {
        //TRUE IF EMAIL EXISTS
        //FALSE IF NO EMAIL IS FOUND
        return artistDAO.findByEmail(email) != null;
    }
}
