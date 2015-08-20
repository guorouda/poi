package com.ron.dao;

import static com.ron.dao.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ron.exceptions.DAOException;
import com.ron.model.City;
import com.ron.model.FileUpload;
import com.ron.model.Stats;

public class CityDAOJDBC extends BaseDAOJDBC implements CityDAO {
	
	public static Logger log = Logger.getLogger(CityDAOJDBC.class);
	
	private static final String SQL_LIST_ORDER_BY_ID = "select * from xxfb_city t order by t.id";
	private static final String SQL_LIST_BY_ID = "select * from xxfb_city t where t.id = ?";
	private static final String SQL_UPDATE = "update xxfb_city set port = ?, username = ?, password = ?, ftppath = ? where id = ?";
	private static final String SQL_STATS = "select t.username username, b.name name, case when t.username is null then 0 else counts end counts from (select t.username, count(1) counts from xxfb_playhistory t where t.begintime between to_date(?, 'yyyy/mm/dd hh24:mi:ss') and to_date(?, 'yyyy/mm/dd hh24:mi:ss') group by t.username) t right outer join xxfb_city b on to_char(t.username) = to_char(b.id) order by name";

	private static final String SQL_ADD_TRANSFERHISTORY = "insert into xxfb_transferhistory(id, filename, uuid, cityid, transfertime) values(transferhistory_id_seq.nextval, ?, ?, ?, sysdate)";
	

	@Override
	public List<City> listAll() throws DAOException {
  	    
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
	
	@Override
	public List<City> list(String username) throws DAOException {
  	    Object[] values = {
  	    		username
  	    };
  	                     
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<City> p = new ArrayList<City>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(SQL_LIST_BY_ID);
            setValues(preparedStatement, values);
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

        city.setId(resultSet.getLong("id"));
        city.setName(resultSet.getString("name"));
        city.setIpaddress(resultSet.getString("ipaddress"));
        city.setPort(resultSet.getInt("port"));
        city.setUsername(resultSet.getString("username"));
        city.setPassword(resultSet.getString("password"));
        city.setPath(resultSet.getString("ftppath"));
        city.setActive((resultSet.getInt("active") != 0));
        city.setPassivemode(resultSet.getInt("ftpmode") != 0);
        
        return city;
    }

	@Override
	public boolean update(City city) throws DAOException {
		 Object[] values = {
				 city.getPort(), 
				 city.getUsername(), 
				 city.getPassword(),
				 city.getPath(),
				 city.getId(),
	  	    };
	  	                     
	     Connection connection = null;
	     PreparedStatement preparedStatement = null;
	     ResultSet resultSet = null;

	     try {
	         connection = daoFactory.getConnection();
	         preparedStatement = prepareStatement(connection, SQL_UPDATE, false, values);
	         int affectedRows = preparedStatement.executeUpdate();

	         if (affectedRows == 0) {
	        	 throw new DAOException("Updating user failed, no rows affected.");
	         }
	     } catch (SQLException e) {
	         throw new DAOException(e);
	     } finally {
	         close(connection, preparedStatement, resultSet);	         
	     }

		
		return true;
	}
	
	
	@Override
	public boolean addTransferHisotry(City city, FileUpload fileupload) throws DAOException {
		 Object[] values = {
				 fileupload.getFilename(),
				 fileupload.getUuid(),
				 city.getId()
	  	    };
	  	                     
	     Connection connection = null;
	     PreparedStatement preparedStatement = null;
	     ResultSet resultSet = null;

	     try {
	         connection = daoFactory.getConnection();
	         preparedStatement = prepareStatement(connection, SQL_ADD_TRANSFERHISTORY, false, values);
	         int affectedRows = preparedStatement.executeUpdate();

	         if (affectedRows == 0) {
	        	 throw new DAOException("Updating user failed, no rows affected.");
	         }
	     } catch (SQLException e) {
	         throw new DAOException(e);
	     } finally {
	         close(connection, preparedStatement, resultSet);	         
	     }

		
		return true;
	}
	
	public List<Stats> listStats(String starttime, String endtime) throws DAOException{
		 Object[] values = {
				 starttime,
				 endtime
	  	    };
		
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Stats> p = new ArrayList<Stats>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(SQL_STATS);
       	    setValues(preparedStatement, values);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                p.add(mapStats(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return p;
		
	}
	
    private static Stats mapStats(ResultSet resultSet) throws SQLException {
        Stats stats = new Stats();

        stats.setName(resultSet.getString("name"));
        stats.setUsername(resultSet.getString("username"));
        stats.setCounts(resultSet.getLong("counts"));
        
        return stats;
    }
    
    
}
