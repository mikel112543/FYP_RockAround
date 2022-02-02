package com.example.rockaroundapp.dao;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.rockaroundapp.model.Artist;
import com.example.rockaroundapp.source.remoteConnection;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ArtistDaoImpl implements ArtistDao {

    static Logger log = Logger.getLogger(ArtistDaoImpl.class);

    private static final String DELETE = "DELETE FROM artist WHERE Artist_ID=?";
    private static final String INSERT_ARTIST = "INSERT INTO artist(Firstname, Lastname, StageName, ProfileImage, ArtistType, Bio, Price, Email, ContactNumber) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_ADDRESS = "INSERT INTO artistaddress(Artist_ID, AddressLineOne, AddressLineTwo, City, County, Country) VALUES(?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_ARTIST = "UPDATE artist SET Firstname=?, Lastname=?, StageName=?, ProfileImage=?, ArtistType=?, Bio=?, Price=?, Email=?, ContactNumber=? WHERE Artist_ID=?";
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
            conn.close();
        }

    }

    @Override
    public void insert(Artist artist) throws SQLException {
        Connection conn = remoteConnection.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_ARTIST, Statement.RETURN_GENERATED_KEYS))  {
            stmt.setString(1, artist.getFirstname());
            stmt.setString(2, artist.getLastname());
            stmt.setString(3, artist.getStageName());
            stmt.setString(4, artist.getProfileImgURL());
            stmt.setString(5, artist.getUserType());
            stmt.setString(6, artist.getBio());
            stmt.setInt(7, artist.getPrice());
            stmt.setString(8, artist.getEmail());
            stmt.setString(9, artist.getContactNumber());
            stmt.execute();
        } catch (SQLException e) {
            log.error(e.getMessage());
            conn.close();
        }
    }

    @Override
    public void update(Artist artist) throws SQLException {
        Connection conn = remoteConnection.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(UPDATE_ARTIST)) {
            stmt.setString(1, artist.getFirstname());
            stmt.setString(2, artist.getLastname());
            stmt.setString(3, artist.getStageName());
            stmt.setString(4, artist.getProfileImgURL());
            stmt.setString(5, artist.getUserType());
            stmt.setString(6, artist.getBio());
            stmt.setInt(7, artist.getPrice());
            stmt.setString(8, artist.getEmail());
            stmt.setString(9, artist.getContactNumber());
            stmt.setInt(10, artist.getArtistId());
            stmt.executeUpdate();
        }catch (SQLException e) {
            log.error(e.getMessage());
            conn.close();
        }

    }

    @Override
    public List<Artist> findAll() {
        return null;
    }

    @Override
    public Artist findById(int id) {
        return null;
    }

    @Override
    public Artist findByEmail(String email) {
        Connection conn = remoteConnection.getConnection();
        try(PreparedStatement stmt = conn.prepareStatement(FIND_BY_EMAIL)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                Artist artist = new Artist();
                artist.setArtistId(rs.getInt("Artist_ID"));
                artist.setFirstname(rs.getString("Firstname"));
                artist.setLastname(rs.getString("Lastname"));
                artist.setEmail(rs.getString("Email"));
                return artist;
            }else{
                return null;
            }
        }catch (SQLException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Artist> findByStageName(String stageName) {
        return null;
    }

    @Override
    public List<Artist> findByFirstname(String firstname) {
        return null;
    }
}
