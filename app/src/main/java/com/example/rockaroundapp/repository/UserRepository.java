package com.example.rockaroundapp.repository;

import androidx.lifecycle.LiveData;

import com.example.rockaroundapp.dao.ArtistDaoImpl;
import com.example.rockaroundapp.dao.VenueDaoImpl;

import java.util.List;

public class UserRepository {

    private ArtistDaoImpl artistDAO;
    private VenueDaoImpl venueDAO;

    public UserRepository(ArtistDaoImpl artistDAO, VenueDaoImpl venueDAO) {
        this.artistDAO = artistDAO;
        this.venueDAO = venueDAO;
    }

    /*public LiveData<List<String>> checkCredentials(List<String> credentials) {

    }*/
}
