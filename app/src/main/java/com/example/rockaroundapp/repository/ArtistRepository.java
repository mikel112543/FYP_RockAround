package com.example.rockaroundapp.repository;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.rockaroundapp.model.Artist;
import com.example.rockaroundapp.model.GroupArtist;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ArtistRepository {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private MutableLiveData<List<Artist>>  artistListMutable;
    private List<Artist> artistList;

    public ArtistRepository() {
        artistList = new ArrayList<>();
        artistListMutable = new MutableLiveData<>();
    }

    public MutableLiveData<List<Artist>> getArtistListMutable() {
        if(artistList.isEmpty()) {
            findAll();
        }

        artistListMutable.postValue(artistList);
        return artistListMutable;
    }

    public void findAll() {
        db.collection("solo").addSnapshotListener((snapshot, e) -> {
            if(e != null) {
                Log.w(TAG, "Listen Failed", e);
            }
            if(snapshot != null) {
                for(DocumentSnapshot document : snapshot) {
                    Artist artist = document.toObject(Artist.class);
                    artistList.add(artist);
                    //TODO Add Reviews for binding
                }
            }
        });
        db.collection("group").addSnapshotListener((snapshot, e) -> {
            if(e != null) {
                Log.w(TAG, "Listen Failed", e);
            }
            if(snapshot != null) {
                for(DocumentSnapshot document : snapshot) {
                    GroupArtist groupArtist = document.toObject(GroupArtist.class);
                    artistList.add(groupArtist);
                    //TODO Add Reviews for binding
                }
                artistListMutable.postValue(artistList);
            }
        });

    }
}
