package com.example.rockaroundapp.repository;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.rockaroundapp.model.ArtistReview;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ReviewRepository {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private MutableLiveData<Boolean> _success;
    private MutableLiveData<Boolean> alreadyReviewed;
    private String currentUid = FirebaseAuth.getInstance().getUid();

    public ReviewRepository() {
        _success = new MutableLiveData<>();
        alreadyReviewed = new MutableLiveData<>();
    }

    public MutableLiveData<Boolean> getSuccess() {
        if (_success == null) {
            _success = new MutableLiveData<>();
        }
        return _success;
    }

    public void submitArtistReview(ArtistReview artistReview) {
        db.collection("solo").document(artistReview.getReviewedId()).get().addOnCompleteListener(snapshot -> {
            if (snapshot.getResult().getData() != null) {
                db.collection("solo").document(artistReview.getReviewedId()).collection("reviews").document(currentUid).set(artistReview).addOnCompleteListener(upload -> {
                    Log.w(TAG, "Solo review upload successful");
                    db.collection("venue").document(currentUid).collection("writtenReviews").document(artistReview.getReviewedId()).set(artistReview).addOnCompleteListener(venueUpload -> {
                        if (venueUpload.isSuccessful()) {
                            Log.w(TAG, "Review of venue upload successful");
                            _success.postValue(true);
                        }
                    });
                });
            } else {
                db.collection("group").document(artistReview.getReviewedId()).collection("reviews").document(currentUid).set(artistReview).addOnCompleteListener(groupUpload -> {
                    if (groupUpload.isSuccessful()) {
                        Log.w(TAG, "Group review upload successful");
                        db.collection("venue").document(currentUid).collection("writtenReviews").document(artistReview.getReviewedId()).set(artistReview).addOnCompleteListener(venueUpload -> {
                            if (venueUpload.isSuccessful()) {
                                Log.w(TAG, "Review of venue upload successful");
                                _success.postValue(true);
                            }
                        });
                    }
                });
            }
        });
    }
}
