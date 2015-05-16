package com.ron.dao;

import static com.ron.dao.DAOUtil.close;
import static com.ron.dao.DAOUtil.setValues;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ron.exceptions.DAOException;
import com.ron.model.City;
import com.ron.model.PlayListFile;

public class CityDAOJDBC extends BaseDAOJDBC implements CityDAO {
	
	public static Logger log = Logger.getLogger(CityDAOJDBC.class);
	
	private static final String SQL_LIST_ORDER_BY_ID = "select * from xxfb_city t order by t.id";

	@Override
	public List<City> list() throws DAOException {
  	    
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<City> p = new ArrayList<City>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(SQL_LIST_ORDER_BY_ID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                p.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return p;
	}
	
    private static City map(ResultSet resultSet) throws SQLException {
        City city = new City();

        city.setId(resultSet.getInt("id"));
        city.setName(resultSet.getString("name"));
        city.setIpAddress(resultSet.getString("ipaddress"));
        city.setPort(resultSet.getInt("port"));
        city.setUsername(resultSet.getString("username"));
        city.setPassword(resultSet.getString("password"));
        city.setFtppath(resultSet.getString("ftppath"));
        
        return city;
    }
}
