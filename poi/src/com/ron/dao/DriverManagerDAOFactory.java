package com.ron.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

// Default DAOFactory implementations -------------------------------------------------------------

/**
 * The DriverManager based DAOFactory.
 */
public class DriverManagerDAOFactory extends DAOFactory {
    private String url;
    private String username;
    private String password;

    DriverManagerDAOFactory(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    Connection getConnection() throws SQLException {
    	// System.out.println("...connecting...");
        return DriverManager.getConnection(url, username, password);
    }
}