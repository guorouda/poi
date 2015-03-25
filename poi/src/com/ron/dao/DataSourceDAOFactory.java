package com.ron.dao;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The DataSource based DAOFactory.
 */
public class DataSourceDAOFactory extends DAOFactory {
    private DataSource dataSource;

    DataSourceDAOFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}