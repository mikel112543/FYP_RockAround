package com.example.rockaroundapp.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.rockaroundapp.model.Artist;
import com.example.rockaroundapp.model.GroupArtist;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
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
        db.collection("solo").get().addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                for(DocumentSnapshot document : task.getResult()) {
                    Artist artist = document.toObject(Artist.class);
                    artistList.add(artist);
                    //TODO correct setters and getters for binding
                    //TODO Add Reviews for binding
                }
            }
            artistListMutable.postValue(artistList);
        });
       /* db.collection("group").get().addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                for(DocumentSnapshot document : task.getResult()) {
                    GroupArtist groupArtist = document.toObject(GroupArtist.class);
                    artistList.add(groupArtist);
                }
            }
        });*/
    }
}
