package com.example.rockaroundapp.source;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class remoteConnection {

    private static Connection conn = null;

    static {
        String url = "jdbc:mysql://127.0.0.1:3306/music_venue_db";
        String user = "root";
        String pass = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, pass);
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection()
    {
        return conn;
    }
}
