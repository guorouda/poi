package com.ron.dao;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The DataSource-with-Login based DAOFactory.
 */
public class DataSourceWithLoginDAOFactory extends DAOFactory {
    private DataSource dataSource;
    private String username;
    private String password;

    DataSourceWithLoginDAOFactory(DataSource dataSource, String username, String password) {
        this.dataSource = dataSource;
        this.username = username;
        this.password = password;
    }

    @Override
    Connection getConnection() throws SQLException {
        return dataSource.getConnection(username, password);
    }
}