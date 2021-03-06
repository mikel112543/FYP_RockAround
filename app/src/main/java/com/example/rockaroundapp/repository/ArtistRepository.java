package com.example.rockaroundapp.repository;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.rockaroundapp.model.Artist;
import com.example.rockaroundapp.model.GroupArtist;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ArtistRepository {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private MutableLiveData<List<Artist>> artistListMutable;
    private MutableLiveData<Artist> _artist;
    private MutableLiveData<GroupArtist> _groupArtist;
    private MutableLiveData<Boolean> alreadyReviewed;
    private List<Artist> artistList;
    private final String currentId = auth.getUid();


    public ArtistRepository() {
        artistListMutable = new MutableLiveData<>();
        _artist = new MutableLiveData<>();
        _groupArtist = new MutableLiveData<>();
        artistList = new ArrayList<>();
        alreadyReviewed = new MutableLiveData<>(false);
    }

    public MutableLiveData<List<Artist>> getArtistListMutable() {
        findAll();
        /*if(artistList.isEmpty()) {
            findAll(orderPosition);
        }else{
            sortList(orderPosition);
        }*/
        //artistListMutable.postValue(artistList);
        return artistListMutable;
    }

    public void findAll() {
        artistList.clear();
        db.collection("solo").addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                Log.w(TAG, "Listen Failed", e);
            }
            if (snapshot != null) {
                for (DocumentSnapshot document : snapshot) {
                    Artist artist = document.toObject(Artist.class);
                    assert artist != null;
                    if (!Objects.equals(currentId, artist.getId())) {
                        artistList.add(artist);
                    }
                }
            }
        });
        db.collection("group").addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                Log.w(TAG, "Listen Failed", e);
            }
            if (snapshot != null) {
                for (DocumentSnapshot document : snapshot) {
                    GroupArtist groupArtist = document.toObject(GroupArtist.class);
                    assert groupArtist != null;
                    if (!Objects.equals(currentId, groupArtist.getId())) {
                        artistList.add(groupArtist);
                    }
                }
            }
            artistListMutable.postValue(artistList);
        });
    }

    public void sortList(int orderPosition) {
        switch (orderPosition) {
            case 1:
                //Ascending
                Collections.sort(artistList, Comparator.comparing(Artist::getStageName));
                break;
            case 2:
                //Rating (High-Low)
                Collections.sort(artistList, Comparator.comparing(Artist::getAvgOverallRating).reversed());
                break;
            case 3:
                //Rating (Low-High)
                Collections.sort(artistList, Comparator.comparing(Artist::getAvgOverallRating));
                break;
            case 4:
                //Price (High-Low)
                Collections.sort(artistList, Comparator.comparing(Artist::getPrice).reversed());
                break;
            case 5:
                Collections.sort(artistList, Comparator.comparing(Artist::getPrice));
                break;
            default:
                // case 0; Descending
                Collections.sort(artistList, Comparator.comparing(Artist::getStageName).reversed());
                break;
        }
        artistListMutable.postValue(artistList);
    }

    public LiveData<Artist> findSoloById(String artistId) {
        db.collection("solo").document(artistId).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                _artist.postValue(documentSnapshot.toObject(Artist.class));
                Log.d(TAG, "Retrieved artist");
            } else {
                Log.w(TAG, "Listen Failed");
            }
        });
        return _artist;
    }

    public LiveData<GroupArtist> findByGroupId(String artistId) {
        db.collection("group").document(artistId).get().addOnSuccessListener(snapshot -> {
            if (snapshot.exists()) {
                _groupArtist.postValue(snapshot.toObject(GroupArtist.class));
                Log.d(TAG, "Retrieved artist");
            } else {
                Log.d(TAG, "Failed to get document");
            }
        });
        return _groupArtist;
    }

    public MutableLiveData<Boolean> alreadyReviewed(String userId, String userType) {
        assert currentId != null;
        db.collection(userType).document(currentId).collection("writtenReviews").addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                Log.w(TAG, "error retrieving written reviews" + e);
            }
            if (snapshot != null) {
                for (DocumentSnapshot document : snapshot) {
                    if (document.getId().equalsIgnoreCase(userId)) {
                        alreadyReviewed.postValue(true);
                        break;
                    } else {
                        alreadyReviewed.postValue(false);
                    }
                }
            }
        });
        return alreadyReviewed;
    }
}
