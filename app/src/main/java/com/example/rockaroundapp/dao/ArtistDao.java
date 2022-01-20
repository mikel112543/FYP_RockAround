package com.example.rockaroundapp.dao;

import androidx.lifecycle.LiveData;

import com.example.rockaroundapp.model.Artist;

import java.sql.SQLException;
import java.util.List;

public interface ArtistDao {

    public void delete(int id) throws SQLException;
    public void insert(Artist artist) throws SQLException;
    public void update(Artist artist);
    public List<Artist> findAll();
    public LiveData<Artist> findById(int id);
    public LiveData<Artist> findByEmail(String email);
    public LiveData<Artist> findByStageName(String stageName);
    public LiveData<Artist> findByFirstname(String firstname);

}
