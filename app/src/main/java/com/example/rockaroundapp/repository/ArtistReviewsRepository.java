package com.example.rockaroundapp.repository;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.rockaroundapp.model.Artist;
import com.example.rockaroundapp.model.ArtistReview;
import com.example.rockaroundapp.model.Review;
import com.example.rockaroundapp.model.Venue;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ArtistReviewsRepository {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private VenueRepository venueRepository;
    private MutableLiveData<Boolean> _success;
    private MutableLiveData<List<ArtistReview>> _artistReviews;
    private MutableLiveData<List<Venue>> _artistReviewers;
    private String currentUid = FirebaseAuth.getInstance().getUid();
    private List<ArtistReview> artistReviews;
    private List<Venue> artistReviewers;

    public ArtistReviewsRepository() {
        venueRepository = new VenueRepository();
        _success = new MutableLiveData<>();
        _artistReviews = new MutableLiveData<>();
        _artistReviewers = new MutableLiveData<>();
        artistReviews = new ArrayList<>();
        artistReviewers = new ArrayList<>();
    }

    public MutableLiveData<Boolean> getSuccess() {
        if (_success == null) {
            _success = new MutableLiveData<>();
        }
        return _success;
    }

    public void setArtistAverageRatings(ArtistReview review, String userType) {
        AtomicInteger reviewCount = new AtomicInteger();
        AtomicReference<Artist> artist = new AtomicReference<>(new Artist());
        db.collection(userType).document(review.getReviewedId()).collection("reviews").addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                Log.w(TAG, "Error finding artist reviews");
            }
            if (snapshot != null) {
                reviewCount.set(snapshot.size());
                db.collection(userType).document(review.getReviewedId()).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        artist.set(task.getResult().toObject(Artist.class));
                        HashMap<String, Object> map = new HashMap<>();
                        artist.get().setTotalCommunicationRating(artist.get().getTotalCommunicationRating() + review.getCommunicationRating());
                        artist.get().setTotalReliabilityRating(artist.get().getTotalReliabilityRating() + review.getReliabilityRating());
                        artist.get().setTotalVocalsRating(artist.get().getTotalVocalsRating() + review.getVocalsRating());
                        artist.get().setTotalStagePresenceRating(artist.get().getTotalStagePresenceRating() + review.getStagePresenceRating());

                        artist.get().setAvgCommunicationRating((double) artist.get().getTotalCommunicationRating() / reviewCount.get());
                        artist.get().setAvgReliabilityRating((double) artist.get().getTotalReliabilityRating() / reviewCount.get());
                        artist.get().setAvgVocalsRating((double) artist.get().getTotalVocalsRating() / reviewCount.get());
                        artist.get().setAvgStagePresenceRating((double) artist.get().getTotalStagePresenceRating() / reviewCount.get());
                        //TODO properly calculate overall average

                        artist.get().setAvgOverallRating((artist.get().getAvgCommunicationRating() / 4) + (artist.get().getAvgVocalsRating() / 4) +
                                (artist.get().getAvgReliabilityRating() / 4) + (artist.get().getAvgStagePresenceRating() / 4));

                        map.put("avgCommunicationRating", artist.get().getAvgCommunicationRating());
                        map.put("avgReliabilityRating", artist.get().getAvgReliabilityRating());
                        map.put("avgVocalsRating", artist.get().getAvgVocalsRating());
                        map.put("avgStagePresenceRating", artist.get().getAvgStagePresenceRating());
                        map.put("totalCommunicationRating", artist.get().getTotalCommunicationRating());
                        map.put("totalReliabilityRating", artist.get().getTotalReliabilityRating());
                        map.put("totalVocalsRating", artist.get().getTotalVocalsRating());
                        map.put("totalStagePresenceRating", artist.get().getTotalStagePresenceRating());

                        map.put("avgOverallRating", artist.get().getAvgOverallRating());

                        db.collection(userType).document(review.getReviewedId()).set(map, SetOptions.merge()).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                _success.postValue(true);
                            }
                        });
                    }
                });
            }
        });
    }

    //For venues to submit reviews of artists
    public void submitArtistReview(ArtistReview artistReview) {
        db.collection("solo").document(artistReview.getReviewedId()).get().addOnCompleteListener(snapshot -> {
            if (snapshot.getResult().getData() != null) {
                db.collection("solo").document(artistReview.getReviewedId()).collection("reviews").document(currentUid).set(artistReview).addOnCompleteListener(upload -> {
                    Log.w(TAG, "Solo review upload successful");
                    db.collection("venue").document(currentUid).collection("writtenReviews").document(artistReview.getReviewedId()).set(artistReview).addOnCompleteListener(venueUpload -> {
                        if (venueUpload.isSuccessful()) {
                            Log.w(TAG, "Review of venue upload successful");
                            setArtistAverageRatings(artistReview, "solo");
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
                                setArtistAverageRatings(artistReview, "group");
                            }
                        });
                    }
                });
            }
        });
    }

    //Return all reviews for either solo or group artist
    public MutableLiveData<List<ArtistReview>> getAllReviews(String artistId) {
        artistReviews.clear();
        db.collection("solo").document(artistId).collection("reviews").addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                Log.w(TAG, "Failed to get reviews for solo artist");
            }
            if (snapshot != null) {
                if (!snapshot.isEmpty()) {
                    for (DocumentSnapshot documentSnapshot : snapshot) {
                        artistReviews.add(documentSnapshot.toObject(ArtistReview.class));
                    }
                    _artistReviews.postValue(artistReviews);
                } else {
                    db.collection("group").document(artistId).collection("reviews").addSnapshotListener((snapshot1, e1) -> {
                        if (e1 != null) {
                            Log.w(TAG, "Failed to get reviews for group artist");
                        }
                        if (snapshot1 != null) {
                            if (!snapshot1.isEmpty()) {
                                for (DocumentSnapshot documentSnapshot : snapshot1) {
                                    artistReviews.add(documentSnapshot.toObject(ArtistReview.class));
                                }
                                _artistReviews.postValue(artistReviews);
                            }
                        }
                    });
                }
            }
        });
        return _artistReviews;
    }

    public MutableLiveData<List<Venue>> getReviewersForArtist(List<ArtistReview> artistReviews) {
        artistReviewers.clear();
            for (ArtistReview artistReview : artistReviews) {
                db.collection("venue").document(artistReview.getReviewerId()).addSnapshotListener((snapshot, e) -> {
                    assert snapshot != null;
                    if (snapshot.getData()!= null) {
                        artistReviewers.add(snapshot.toObject(Venue.class));
                        _artistReviewers.postValue(artistReviewers);
                    } else {
                        Log.w(TAG, "Could not find reviewer for artist");
                    }
                });
            }
        _artistReviewers.postValue(artistReviewers);
        return _artistReviewers;
    }
}