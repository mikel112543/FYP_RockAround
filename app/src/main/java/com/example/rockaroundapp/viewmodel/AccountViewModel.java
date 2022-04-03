package com.example.rockaroundapp.viewmodel;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rockaroundapp.R;
import com.example.rockaroundapp.model.Artist;
import com.example.rockaroundapp.model.GroupArtist;
import com.example.rockaroundapp.model.Venue;
import com.example.rockaroundapp.repository.ArtistRepository;
import com.example.rockaroundapp.repository.UserRepository;
import com.example.rockaroundapp.repository.VenueRepository;
import com.google.firebase.auth.FirebaseAuth;

public class AccountViewModel extends ViewModel {

    private MutableLiveData<String> userType;
    private UserRepository repository;
    private String currentUid = FirebaseAuth.getInstance().getUid();

    public AccountViewModel() {
        repository = new UserRepository();
        userType = new MutableLiveData<>();
    }

    public MutableLiveData<String> getUserType() {
        return repository.findUserType(currentUid);
    }

    public LiveData<Artist> getSoloUser(String currentUid) {
        return new ArtistRepository().findSoloById(currentUid);
    }

    public LiveData<GroupArtist> getGroupUser(String currentUid) {
        return new ArtistRepository().findByGroupId(currentUid);
    }

    public LiveData<Venue> getVenueUser(String currentUid) {
        return new VenueRepository().findById(currentUid);
    }
}
