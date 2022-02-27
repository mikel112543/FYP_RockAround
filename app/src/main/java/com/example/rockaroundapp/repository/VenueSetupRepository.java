package com.example.rockaroundapp.repository;

import static android.content.ContentValues.TAG;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.rockaroundapp.model.Venue;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Objects;

public class VenueSetupRepository {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference documentReference;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private String currentUiD = auth.getUid();
    private HashMap<String, Object> venueMp;
    private HashMap<String, Object> addressMp;
    private MutableLiveData<Boolean> success;
    private Uri imagePath;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    Logger logger = Logger.getLogger(GroupSetupRepository.class);

    public VenueSetupRepository() {
        success = new MutableLiveData<>(false);
    }

    public MutableLiveData<Boolean> getSuccess() {
        return success;
    }

    public void saveToDB(MutableLiveData<Venue> venueMutableLiveData) {
        Venue venue = venueMutableLiveData.getValue();
        StorageReference reference = storageReference.child("users/" + "test" + "/" + "profile.jpg");
        UploadTask uploadTask = reference.putFile(venue.getProfileImgURL());
        uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw Objects.requireNonNull(task.getException());
            }
            return reference.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                imagePath = task.getResult();
                venueMp = new HashMap<>();
                addressMp = new HashMap<>();
                documentReference = db.collection("venue").document("test");
                addressMp.put("addressLineOne", venue.getAddressLineOne());
                addressMp.put("addressLineTwo", venue.getAddressLineTwo());
                addressMp.put("city", venue.getCity());
                addressMp.put("county", venue.getCounty());
                addressMp.put("country", venue.getCounty());
                venueMp.put("venueName", venue.getVenueName());
                venueMp.put("bio", venue.getBio());
                venueMp.put("profileImg", imagePath.toString());
                venueMp.put("address", addressMp);
                venueMp.put("capacity", venue.getCapacity());
                venueMp.put("contact", venue.getContactNumber());
                documentReference.set(venueMp, SetOptions.merge()).addOnSuccessListener(unused -> {
                    Log.d(TAG, "Setup Successful");
                    success.postValue(true);
                });
            }
        });
    }

    private void uploadProfileImage(Uri profileImgURL) {
        StorageReference reference = storageReference.child("users/" + "test" + "/" + "profile.jpg");
        UploadTask uploadTask = reference.putFile(profileImgURL);
        uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw Objects.requireNonNull(task.getException());
            }
            return reference.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                imagePath = task.getResult();
            }
        });

    }
}
