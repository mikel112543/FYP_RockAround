package com.example.rockaroundapp.repository;

import android.content.ContentResolver;

import android.net.Uri;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.rockaroundapp.model.Artist;
import com.example.rockaroundapp.viewmodel.SoloSetupViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.apache.log4j.Logger;

import java.util.HashMap;

public class SoloSetupRepository {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference documentReference;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private StorageTask urlTask;
    private String currentUiD = auth.getUid();
    private HashMap<String, Object> artistSetup;
    private MutableLiveData<Boolean> success;
    private String filePath;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private Uri imageUri;
    Logger logger = Logger.getLogger(SoloSetupRepository.class);

    public SoloSetupRepository() {
        success = new MutableLiveData<>();
    }

    public void saveToDB(Artist artist) {
        documentReference = db.collection("solo").document(currentUiD);
        artistSetup = new HashMap<>();
        artistSetup.put("stagename", artist.getStageName());
        artistSetup.put("bio", artist.getBio());
        artistSetup.put("profileImg", artist.getProfileImgURL());
        artistSetup.put("genres", artist.getGenres());
        artistSetup.put("price", artist.getPrice());
        artistSetup.put("contact", artist.getContactNumber());
        artistSetup.put("address", artist.getAddress());
        artistSetup.put("instruments", artist.getInstruments());
        artistSetup.put("sampleTracks", artist.getSampleTracks());
        artistSetup.put("images", artist.getArtistImages());
        documentReference.set(artistSetup, SetOptions.merge()).addOnSuccessListener(unused -> {
            logger.info("Setup Successful");
            success.postValue(true);
        });
    }

    public String saveProfileImage() {
        if (imageUri != null) {
            StorageReference profileImgRef = storageReference.child("users/" + auth.getCurrentUser().getUid() + "/profile.jpg");
            urlTask = profileImgRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> profileImgRef.getDownloadUrl()
                    .addOnSuccessListener(uri1 -> {
                        filePath = uri1.toString();
                    }).addOnFailureListener(e -> logger.error("Image could not be saved", e)));
        }
        return filePath;
    }
}
