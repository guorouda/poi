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
import com.ron.model.PlayList;
import com.ron.model.PlayListFile;

public class PlayListDAOJDBC extends BaseDAOJDBC implements PlayListDAO {
	
	public static Logger log = Logger.getLogger(PlayListDAOJDBC.class);

	private static final String SQL_INSERT_PLAYLIST = "insert into xxfb_playlist (id, listname, createtime, createuser) values (playlist_id_seq.nextval, ?, sysdate, ?)";

	private static final String SQL_LIST_ORDER_BY_ID = null;

	@Override
	public void create(PlayList playList) throws IllegalArgumentException,
			DAOException {
	
	    if (playList.getId() != null) {
	        throw new IllegalArgumentException(
	                "User is already created, the user ID is not null.");
	    }
	
	    Object[] values = {
	    		playList.getListname(), playList.getCreateuser()
	    };
	
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet generatedKeys = null;
	
	    try {
	        connection = daoFactory.getConnection();
	        preparedStatement = prepareStatement(connection, SQL_INSERT_PLAYLIST, true,
	                values);
	        int affectedRows = preparedStatement.executeUpdate();
	
	        if (affectedRows == 0) {
	            throw new DAOException("Creating fileupload failed, no rows affected.");
	        }
	        generatedKeys = preparedStatement.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            playList.setId(generatedKeys.getLong(1) + "");
	        } else {
	            throw new DAOException(
	                    "Creating playList failed, no generated key obtained.");
	        }
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        close(connection, preparedStatement, generatedKeys);
	    }

	}
	
    @Override
    public List<PlayList> list() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<PlayList> p = new ArrayList<PlayList>();

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
	
    private static PlayList map(ResultSet resultSet) throws SQLException {
        PlayList playlist = new PlayList();

        playlist.setId(resultSet.getString("id"));
        playlist.setListname(resultSet.getString("listname"));
        playlist.setCreatetime(resultSet.getString("createtime"));
        playlist.setCreateuser(resultSet.getString("createuser"));
        
        return playlist;
    }

}
