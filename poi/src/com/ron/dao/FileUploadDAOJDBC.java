package com.ron.dao;

import static com.ron.dao.DAOUtil.close;
import static com.ron.dao.DAOUtil.prepareStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ron.exceptions.DAOException;
import com.ron.model.FileUpload;

public class FileUploadDAOJDBC extends BaseDAOJDBC implements FileUploadDAO {

    private static final String SQL_INSERT = "INSERT INTO xxfb_fileupload (id, filename, uuid, uploadtime, uploaduser) VALUES (fileupload_id_seq.nextval, ?, ?, sysdate, ?)";
	private static final String SQL_LIST_ORDER_BY_ID = "select * from xxfb_fileupload";


	@Override
	public void create(FileUpload fileupload) throws IllegalArgumentException,
			DAOException {
	
	    if (fileupload.getId() != null) {
	        throw new IllegalArgumentException(
	                "User is already created, the user ID is not null.");
	    }
	
	    Object[] values = {
	    		fileupload.getFilename(), fileupload.getUuid(), fileupload.getUploaduser()
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
	            fileupload.setId(generatedKeys.getLong(1) + "");
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
	public List<FileUpload> list() throws IllegalArgumentException, DAOException{
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    List<FileUpload> fileUploads = new ArrayList<FileUpload>();

	    try {
	        connection = daoFactory.getConnection();
	        preparedStatement = connection.prepareStatement(SQL_LIST_ORDER_BY_ID);
	        resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	            fileUploads.add(map(resultSet));
	        }
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        close(connection, preparedStatement, resultSet);
	    }

	    return fileUploads;
		
	}
	
    private static FileUpload map(ResultSet resultSet) throws SQLException {
        FileUpload fileUpload = new FileUpload();

        fileUpload.setId(resultSet.getString("id"));
        fileUpload.setFilename(resultSet.getString("filename"));
        fileUpload.setUuid(resultSet.getString("uuid"));
        fileUpload.setUploadtime(resultSet.getString("uploadtime"));
        fileUpload.setUploaduser(resultSet.getString("uploaduser"));
        
        
        return fileUpload;
    }

}
