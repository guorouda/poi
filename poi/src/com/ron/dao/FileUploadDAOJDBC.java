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
import com.ron.model.FileUpload;
import com.ron.pereference.SystemGlobals;
import com.ron.utils.FileUtils;

public class FileUploadDAOJDBC extends BaseDAOJDBC implements FileUploadDAO {

    private static final String SQL_INSERT = "INSERT INTO xxfb_fileupload (id, filename, uuid, uploadtime, uploaduser, type, duration) VALUES (fileupload_id_seq.nextval, ?, ?, sysdate, ?, ?, ?)";
	private static final String SQL_LIST_ORDER_BY_ID = "select t.uuid, t.filename, t.type, t.duration, count(b.filename) count from xxfb_fileupload t, xxfb_container b where t.uuid = b.uuid(+) group by t.uuid, t.filename, t.type, t.duration";
	
	public static Logger log = Logger.getLogger(FileUploadDAOJDBC.class);

	@Override
	public void create(FileUpload fileupload) throws IllegalArgumentException,
			DAOException {
	
	    if (fileupload.getId() != null) {
	        throw new IllegalArgumentException(
	                "fileupload is already created, the user ID is not null.");
	    }
	
	    Object[] values = {
	    		fileupload.getFilename(), fileupload.getUuid(), fileupload.getUploaduser(), fileupload.getType(), fileupload.getDuration()
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
        String filename = resultSet.getString("filename");
        String shortFilename = filename.substring(0, FileUtils.getFilePrefix(filename).length() > 4 ? 4 : FileUtils.getFilePrefix(filename).length()) + ".." + FileUtils.getFileExtension(filename);
//        log.info(shortFilename + ":" + FileUtils.getFilePrefix(filename).length() + ":" + filename + ":" + FileUtils.getFilePrefix(filename));
        String uuid = resultSet.getString("uuid");
        String type = resultSet.getString("type");
        int count = resultSet.getInt("count");
        String url = "";

        fileUpload.setFilename(filename);
        fileUpload.setShortFilename(shortFilename);
        fileUpload.setUuid(resultSet.getString("uuid"));
        fileUpload.setCount(count);
        url = SystemGlobals.getDefaultsValue("srcPath");
        if(type.equalsIgnoreCase("video")){
        	url += "video.png";
        }else if(type.equalsIgnoreCase("imgs")){
        	url += "image-000001.png";
        }else{
        	url += uuid + filename.substring(filename.lastIndexOf("."), filename.length());
        }
        fileUpload.setUrl(url);
        fileUpload.setType(resultSet.getString("type"));
        fileUpload.setDuration(resultSet.getLong("duration"));
        
        return fileUpload;
    }

}
