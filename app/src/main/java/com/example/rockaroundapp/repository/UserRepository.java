package com.example.rockaroundapp.repository;

import com.example.rockaroundapp.dao.ArtistDaoImpl;
import com.example.rockaroundapp.dao.VenueDaoImpl;


public class UserRepository {

    private ArtistDaoImpl artistDAO;
    private VenueDaoImpl venueDAO;

    public UserRepository() {
        artistDAO = new ArtistDaoImpl();
        venueDAO = new VenueDaoImpl();
    }


    public boolean checkEmail(String email) {
        //TRUE IF EMAIL EXISTS
        //FALSE IF NO EMAIL IS FOUND
        return artistDAO.findByEmail(email) != null;
    }
}
