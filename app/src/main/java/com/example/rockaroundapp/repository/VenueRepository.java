package com.example.rockaroundapp.repository;

import static android.content.ContentValues.TAG;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.rockaroundapp.model.Artist;
import com.example.rockaroundapp.model.Venue;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class VenueRepository {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference documentReference;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private String currentUiD = auth.getUid();
    private HashMap<String, Object> venueMp;
    private HashMap<String, Object> addressMp;
    private MutableLiveData<Boolean> success;
    private MutableLiveData<Venue> _venue;
    private List<Venue> venueList;
    private MutableLiveData<List<Venue>> venueListMutable;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    public VenueRepository() {
        success = new MutableLiveData<>(false);
        venueListMutable = new MutableLiveData<>();
        _venue = new MutableLiveData<>();
        venueList = new ArrayList<>();
    }

    public MutableLiveData<Boolean> getSuccess() {
        return success;
    }

    public MutableLiveData<List<Venue>> getVenueListMutable() {
        findAll();
        venueListMutable.postValue(venueList);
        return venueListMutable;
    }

    public void saveToDB(Venue venue, Uri imageUri) {
        venueMp = new HashMap<>();
        documentReference = db.collection("venue").document(auth.getUid());
        if (imageUri != null) {
            StorageReference reference = storageReference.child("users/" + auth.getUid() + "/" + "profile.jpg");
            UploadTask uploadTask = reference.putFile(imageUri);
            uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    throw Objects.requireNonNull(task.getException());
                }
                return reference.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                venueMp = new HashMap<>();
                assert venue != null;
                venue.setProfileImg(task.getResult().toString());
                venueMp.put("profileImg", venue.getProfileImg());
                documentReference.set(venueMp, SetOptions.merge());
            });
        } else {
            venueMp.put("profileImg", venue.getProfileImg());
        }
        assert venue != null;
        venueMp.put("venueName", venue.getVenueName());
        venueMp.put("bio", venue.getBio());
        venueMp.put("address", venue.getAddress());
        venueMp.put("capacity", venue.getCapacity());
        venueMp.put("contact", venue.getContact());
        documentReference.set(venueMp, SetOptions.merge()).addOnSuccessListener(unused -> {
            Log.d(TAG, "Setup Successful");
            success.postValue(true);
        });
    }

    public void findAll() {
        venueList.clear();
        db.collection("venue").addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                Log.e(TAG, "No venues found", e);
            }
            if (snapshot != null) {
                for (DocumentSnapshot document : snapshot) {
                    Venue venue = document.toObject(Venue.class);
                    assert venue != null;
                    venue.setAddressMap((HashMap<String, Object>) document.get("address"));
                    if (!Objects.equals(currentUiD, venue.getId())) {
                        venueList.add(venue);
                    }
                }
                venueListMutable.postValue(venueList);
            }
        });
    }

    public void sortList(int orderPosition) {
        if (orderPosition == 0) { //Descending
            Collections.sort(venueList, Comparator.comparing(Venue::getVenueName).reversed());
        } else if (orderPosition == 1) { //Ascending
            Collections.sort(venueList, Comparator.comparing(Venue::getVenueName));
        } else if (orderPosition == 2) { //Rating (High - low)
            //Collections.sort(venueList, Comparator.comparing(Artist::getAvgOverallRating).reversed());
        } else { //Rating (Low - high)
            //Collections.sort(venueList, Comparator.comparing(Artist::getAvgOverallRating));
        }
        venueListMutable.postValue(venueList);
    }

    public LiveData<Venue> findById(String venueId) {
        db.collection("venue").document(venueId).get().addOnSuccessListener(snapshot -> {
            if (snapshot.exists()) {
                _venue.postValue(snapshot.toObject(Venue.class));
                Log.d(TAG, "Retrieved venue");
            } else {
                Log.d(TAG, "Failed to get document");
            }
        });
        return _venue;

    }
}
