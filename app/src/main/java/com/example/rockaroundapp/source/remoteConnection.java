package com.example.rockaroundapp.source;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class remoteConnection {

    public static Connection conn = null;

    static {
        String url = "jdbc:mariadb://192.168.1.17:3306/music_venue_db";
        String user = "root";
        String pass = "";
        try {
            conn = DriverManager.getConnection(url, user, pass);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection()
    {
        return conn;
    }
}
