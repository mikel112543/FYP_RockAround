package com.example.rockaroundapp.dao;

import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.example.rockaroundapp.model.Artist;
import com.example.rockaroundapp.source.remoteConnection;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ArtistDaoImpl implements ArtistDao {

    static Logger log = Logger.getLogger(ArtistDaoImpl.class);

    private static final String DELETE = "DELETE FROM artist WHERE Artist_ID=?";
    private static final String INSERT_ARTIST = "INSERT INTO artist(Firstname, Lastname, StageName, ProfileImage, ArtistType, Bio, Price, Email, ContactNumber) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_ADDRESS = "INSERT INTO artistaddress(Artist_ID, AddressLineOne, AddressLineTwo, City, County, Country) VALUES(?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE artist SET Firstname=?, Lastname=?, StageName=?, ProfileImage=?, ArtistType=?, Bio=?, Price=?, Email=?, ContactNumber=? WHERE Artist_ID=?";
    private static final String FIND_ALL = "SELECT * FROM artist ORDER BY Artist_ID";
    private static final String FIND_BY_ID = "SELECT * FROM artist WHERE Artist_ID=?";
    private static final String FIND_BY_EMAIL = "SELECT * FROM artist WHERE Email=?";
    private static final String FIND_BY_STAGE_NAME = "SELECT * FROM artist WHERE StageName=?";
    private static final String FIND_BY_FIRSTNAME = "SELECT * FROM artist WHERE Firstname=?";


    @Override
    public void delete(int id) throws SQLException {
        Connection conn = remoteConnection.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(DELETE)) {
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            log.debug(e.getMessage());
        }
        conn.close();
    }

    @Override
    public void insert(Artist artist) throws SQLException {
        Connection conn = remoteConnection.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(INSERT_ARTIST, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, artist.getFirstname());
            stmt.setString(2, artist.getLastname());
            stmt.setString(3, artist.getStageName());
            stmt.setString(4, artist.getProfileImgURL());
            stmt.setString(5, artist.getUserType());
            stmt.setString(6, artist.getBio());
            stmt.setInt(7, artist.getPrice());
            stmt.setString(8, artist.getEmail());
            stmt.setString(9, artist.getContactNumber());
        }

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
