package com.example.rockaroundapp.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.rockaroundapp.model.Artist;

import java.sql.SQLException;
import java.util.List;

public interface ArtistDao {

    void delete(int id) throws SQLException;
    void insert(Artist artist) throws SQLException;
    void update(Artist artist) throws SQLException;
    List<Artist> findAll();
    Artist findById(int id);
    Artist findByEmail(String email);
    List<Artist> findByStageName(String stageName);
    List<Artist> findByFirstname(String firstname);

}
