package com.example.rockaroundapp.dao;

import androidx.lifecycle.LiveData;

import com.example.rockaroundapp.model.Artist;

import java.util.List;

public class ArtistDaoImpl implements ArtistDao {

    @Override
    public void delete(int id) {

    }

    @Override
    public void insert(Artist artist) {

    }

    @Override
    public void update(Artist artist) {

    }

    @Override
    public List<Artist> findAll() {
        return null;
    }

    @Override
    public LiveData<Artist> findById(int id) {
        return null;
    }

    @Override
    public LiveData<Artist> findByEmail(String email) {
        return null;
    }

    @Override
    public LiveData<Artist> findByStageName(String stageName) {
        return null;
    }

    @Override
    public LiveData<Artist> findByFirstname(String firstname) {
        return null;
    }
}
