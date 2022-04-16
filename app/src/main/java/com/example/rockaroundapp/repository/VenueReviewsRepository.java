package com.example.rockaroundapp.repository;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.rockaroundapp.model.Artist;
import com.example.rockaroundapp.model.GroupArtist;
import com.example.rockaroundapp.model.User;
import com.example.rockaroundapp.model.Venue;
import com.example.rockaroundapp.model.VenueReview;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class VenueReviewsRepository {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private MutableLiveData<Boolean> _success;
    private final MutableLiveData<List<VenueReview>> _venueReviews;
    private final String currentUid = FirebaseAuth.getInstance().getUid();
    private UserRepository userRepository;

    public VenueReviewsRepository() {
        userRepository = new UserRepository();
        _success = new MutableLiveData<>();
        _venueReviews = new MutableLiveData<>();
    }

    public MutableLiveData<Boolean> getSuccess() {
        if (_success == null) {
            _success = new MutableLiveData<>();
        }
        return _success;

    }

    //For Artists to submit reviews on venues
    public void saveVenueReview(VenueReview venueReview) {
        db.collection("solo").document(venueReview.getReviewerId()).get().addOnCompleteListener(snapshot -> {
            if (snapshot.getResult().getData() != null) {
                Artist artistReviewer = snapshot.getResult().toObject(Artist.class);
                HashMap<String, Object> artistMp = new HashMap<>();
                artistMp.put("iD", artistReviewer.getId());
                artistMp.put("userType", artistReviewer.getUserType());
                artistMp.put("profileImg", artistReviewer.getProfileImg());
                artistMp.put("stageName", artistReviewer.getStageName());
                venueReview.setReviewer(artistMp);
                db.collection("solo").document(artistReviewer.getId())
                        .collection("writtenReviews").document(venueReview.getReviewedId()).set(venueReview).addOnCompleteListener(upload -> {
                    if (upload.isSuccessful()) {
                        uploadVenueData(venueReview, artistMp);
                    }
                });
            } else {
                db.collection("group").document(venueReview.getReviewerId()).get().addOnCompleteListener(groupSnapshot -> {
                    GroupArtist artistReviewer = snapshot.getResult().toObject(GroupArtist.class);
                    HashMap<String, Object> artistMp = new HashMap<>();
                    artistMp.put("iD", artistReviewer.getId());
                    artistMp.put("userType", artistReviewer.getUserType());
                    artistMp.put("profileImg", artistReviewer.getProfileImg());
                    artistMp.put("stageName", artistReviewer.getStageName());
                    venueReview.setReviewer(artistMp);
                    db.collection("group").document(artistReviewer.getId())
                            .collection("writtenReviews").document(venueReview.getReviewedId())
                            .set(venueReview).addOnCompleteListener(upload -> {
                        if (upload.isSuccessful()) {
                            uploadVenueData(venueReview, artistMp);
                        }
                    });
                });
            }
        });
    }

    private void uploadVenueData(VenueReview venueReview, Map<String, Object> artistMp) {
        db.collection("venue").document(venueReview.getReviewedId()).collection("reviews")
                .document(currentUid).set(venueReview).addOnCompleteListener(upload -> {
            if (upload.isSuccessful()) {
                setVenueAvgRatings(venueReview);
            }
        });
    }

    /**
     *
     * @param userId
     * @return true if current artist has already reviewed the venue. Otherwise false.
     *
     */
    public MutableLiveData<Boolean> alreadyReviewed(String userId) {
        MutableLiveData<Boolean> alreadyReviewed = new MutableLiveData<>();
        db.collection("venue").document(userId).collection("reviews").document(currentUid).get().addOnCompleteListener(document -> {
            if(document.isSuccessful()) {
                if(document.getResult().getData() != null) {
                    Log.w(TAG, "Artist has already reviewed venue");
                    alreadyReviewed.postValue(true);
                }else{
                    alreadyReviewed.postValue(false);
                }
            }
        });
        return alreadyReviewed;
    }

    private void setVenueAvgRatings(VenueReview venueReview) {
        AtomicInteger reviewCount = new AtomicInteger();
        AtomicReference<Venue> venue = new AtomicReference<>(new Venue());
        db.collection("venue").document(venueReview.getReviewedId()).collection("reviews").addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                Log.w(TAG, "Error finding venue reviews");
            }
            if (snapshot != null) {
                reviewCount.set(snapshot.size());
                db.collection("venue").document(venueReview.getReviewedId()).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        venue.set(task.getResult().toObject(Venue.class));
                        HashMap<String, Object> map = new HashMap<>();
                        venue.get().setTotalCommunicationRating(venue.get().getTotalCommunicationRating() + venueReview.getCommunicationRating());
                        venue.get().setTotalReliabilityRating(venue.get().getTotalReliabilityRating() + venueReview.getReliabilityRating());
                        venue.get().setTotalAtmosphereRating(venue.get().getTotalAtmosphereRating() + venueReview.getAtmosphereRating());
                        venue.get().setTotalSettingRating(venue.get().getTotalSettingRating() + venueReview.getSettingRating());

                        venue.get().setAvgCommunicationRating((double) venue.get().getTotalCommunicationRating() / reviewCount.get());
                        venue.get().setAvgReliabilityRating((double) venue.get().getTotalReliabilityRating() / reviewCount.get());
                        venue.get().setAvgAtmosphereRating((double) venue.get().getTotalAtmosphereRating() / reviewCount.get());
                        venue.get().setAvgSettingRating((double) venue.get().getTotalSettingRating() / reviewCount.get());

                        venue.get().setAvgOverallRating((venue.get().getAvgCommunicationRating() / 4) + (venue.get().getAvgAtmosphereRating() / 4) +
                                (venue.get().getAvgReliabilityRating() / 4) + (venue.get().getAvgSettingRating() / 4));

                        map.put("avgCommunicationRating", venue.get().getAvgCommunicationRating());
                        map.put("avgReliabilityRating", venue.get().getAvgReliabilityRating());
                        map.put("avgSettingRating", venue.get().getAvgSettingRating());
                        map.put("avgAtmosphereRating", venue.get().getAvgAtmosphereRating());
                        map.put("totalCommunicationRating", venue.get().getTotalCommunicationRating());
                        map.put("totalReliabilityRating", venue.get().getTotalReliabilityRating());
                        map.put("totalSettingRating", venue.get().getTotalSettingRating());
                        map.put("totalAtmosphereRating", venue.get().getTotalAtmosphereRating());

                        map.put("avgOverallRating", venue.get().getAvgOverallRating());

                        db.collection("venue").document(venueReview.getReviewedId()).set(map, SetOptions.merge()).addOnCompleteListener(task2 -> {
                            if (task2.isSuccessful()) {
                                Log.w(TAG, "Rating calculation completed. Upload Done!");
                                _success.postValue(true);
                            }
                        });
                    }
                });
            }
        });
    }

    public LiveData<List<VenueReview>> getReviews(String id) {
        List<VenueReview> venueReviews = new ArrayList<>();
        db.collection("venue").document(id).collection("reviews").addSnapshotListener((snapshot, e) -> {
            if(e != null) {
                Log.w(TAG, "No documents found");
            }
            if(snapshot != null) {
                for(DocumentSnapshot document : snapshot) {
                    VenueReview venueReview = document.toObject(VenueReview.class);
                    venueReviews.add(venueReview);
                }
            }
            _venueReviews.postValue(venueReviews);
        });
        return _venueReviews;
    }
}
