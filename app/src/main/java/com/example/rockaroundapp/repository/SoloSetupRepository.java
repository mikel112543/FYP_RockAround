package com.example.rockaroundapp.repository;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;

import com.example.rockaroundapp.model.Artist;
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

public class SoloSetupRepository {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference documentReference;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private String currentUiD = auth.getUid();
    private HashMap<String, Object> artistSetup;
    private MutableLiveData<Boolean> success;
    private String imagePath;
    private Uri imageUri;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    Logger logger = Logger.getLogger(SoloSetupRepository.class);

    public SoloSetupRepository() {
        success = new MutableLiveData<>(false);
    }

    public void saveToDB(Artist artist, Uri imageUri) {
        StorageReference reference = storageReference.child("users/" + currentUiD + "/" + "profile.jpg");
        UploadTask uploadTask = reference.putFile(imageUri);
        uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw Objects.requireNonNull(task.getException());
            }
            return reference.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                imagePath = task.getResult().toString();
                documentReference = db.collection("solo").document(currentUiD);
                artistSetup = new HashMap<>();
                artistSetup.put("stagename", artist.getStageName());
                artistSetup.put("bio", artist.getBio());
                artistSetup.put("profileImg", imagePath);
                artistSetup.put("genres", artist.getGenres());
                artistSetup.put("price", artist.getPrice());
                artistSetup.put("contact", artist.getContact());
                //artistSetup.put("address", artist.getAddress());
                //artistSetup.put("instruments", artist.getInstruments());
                //artistSetup.put("sampleTracks", artist.getSampleTracks());
                //artistSetup.put("images", artist.getArtistImages());
                documentReference.set(artistSetup, SetOptions.merge()).addOnSuccessListener(unused -> {
                    logger.info("Setup Successful");
                    success.postValue(true);
                });
            }
        });
    }

    public MutableLiveData<Boolean> getSuccess() {
        return success;
    }

/*    public void uploadProfileImage(Uri profileImgURL) {
        StorageReference reference = storageReference.child("users/" + auth.getCurrentUser().getUid() + "profile.jpg");
        reference.putFile(profileImgURL).addOnSuccessListener(taskSnapshot -> {
            logger.info("Image uploaded successfully");
            reference.getDownloadUrl().addOnSuccessListener(uri ->
                    imagePath = uri)

                    .addOnFailureListener(e -> logger.error("error getting filepath url" + e.getMessage()));
        });
    }*/
}
