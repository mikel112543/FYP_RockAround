package com.example.rockaroundapp.repository;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;

import com.example.rockaroundapp.model.GroupArtist;
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

public class GroupSetupRepository {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference documentReference;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private String currentUiD = auth.getUid();
    private HashMap<String, Object> group;
    private MutableLiveData<Boolean> success;
    private Uri imagePath;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    Logger logger = Logger.getLogger(GroupSetupRepository.class);

    public GroupSetupRepository() {
        success = new MutableLiveData<>(false);
    }

    public void saveToDB(GroupArtist groupArtist) {
        StorageReference reference = storageReference.child("users/" + currentUiD + "/" + "profile.jpg");
        //Upload file to storage
        UploadTask uploadTask = reference.putFile(groupArtist.getProfileImgURL());
        uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw Objects.requireNonNull(task.getException());
            }
            return reference.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                //Add User to DB
                imagePath = task.getResult();
                documentReference = db.collection("group").document(currentUiD);
                group = new HashMap<>();
                group.put("groupName", groupArtist.getStageName());
                group.put("bio", groupArtist.getBio());
                group.put("profileImg", imagePath);
                group.put("genres", groupArtist.getGenres());
                group.put("price", groupArtist.getPrice());
                group.put("contact", groupArtist.getContactNumber());
                //group.put("address", groupArtist.getAddress());
                //group.put("instruments", groupArtist.getInstruments());
                //group.put("sampleTracks", groupArtist.getSampleTracks());
                //group.put("images", groupArtist.getArtistImages());
                documentReference.set(group, SetOptions.merge()).addOnSuccessListener(unused -> {
                    logger.info("Setup Successful");
                    success.postValue(true);
                });
            }
        });
    }

    public MutableLiveData<Boolean> getSuccess() {
        return success;
    }

/*    private void uploadProfileImage(Uri profileImgURL) {
        StorageReference reference = storageReference.child("users/" + currentUiD +"/" + "profile.jpg");
        reference.putFile(profileImgURL).addOnSuccessListener(taskSnapshot -> {
            logger.info("Image uploaded successfully");
            reference.getDownloadUrl().addOnSuccessListener(uri ->
                    imagePath = uri)

                    .addOnFailureListener(e -> logger.error("error getting filepath url" + e.getMessage()));
        });
    }*/
}