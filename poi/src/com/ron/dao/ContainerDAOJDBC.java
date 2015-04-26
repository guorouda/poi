package com.ron.dao;

import static com.ron.dao.DAOUtil.close;
import static com.ron.dao.DAOUtil.prepareStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ron.exceptions.DAOException;
import com.ron.model.Container;

public class ContainerDAOJDBC extends BaseDAOJDBC implements ContainerDAO {
	
	private static final String SQL_INSERT = "INSERT INTO xxfb_container (id, filename, uuid, duration) VALUES (container_id_seq.nextval, ?, ?, ?)";
	private static final String SQL_LIST_CONTAINER_BY_UUID = "select * from xxfb_container t where t.uuid = ?";
	
	public static Logger log = Logger.getLogger(ContainerDAOJDBC.class);
	
	@Override
	public void create(Container container) throws IllegalArgumentException,
			DAOException {
	
	    if (container.getId() != null) {
	        throw new IllegalArgumentException(
	                "User is already created, the user ID is not null.");
	    }
	
	    Object[] values = {
	    		container.getFilename(), container.getUuid(), container.getDuration()
	    };
	
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet generatedKeys = null;
	
	    try {
	        connection = daoFactory.getConnection();
	        preparedStatement = prepareStatement(connection, SQL_INSERT, true,
	                values);
	        int affectedRows = preparedStatement.executeUpdate();
	
	        if (affectedRows == 0) {
	            throw new DAOException("Creating fileupload failed, no rows affected.");
	        }
	        generatedKeys = preparedStatement.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            container.setId(generatedKeys.getLong(1) + "");
	        } else {
	            throw new DAOException(
	                    "Creating fileupload failed, no generated key obtained.");
	        }
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        close(connection, preparedStatement, generatedKeys);
	    }

	}

	@Override
	public List<Container> find(String uuid) throws IllegalArgumentException,
			DAOException {
		
        Object[] values = {
        		uuid
            };
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Container> containers = new ArrayList<Container>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_LIST_CONTAINER_BY_UUID, false, values);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                containers.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return containers;
	}
	
	
    private static Container map(ResultSet resultSet) throws SQLException {
        Container container = new Container();

        container.setId(resultSet.getString("id"));
        container.setFilename(resultSet.getString("filename"));
        container.setUuid(resultSet.getString("uuid"));
        container.setDuration(resultSet.getInt("duration"));
        return container;
    }

}
