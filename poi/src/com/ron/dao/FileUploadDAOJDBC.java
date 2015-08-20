package com.ron.dao;

import static com.ron.dao.DAOUtil.close;
import static com.ron.dao.DAOUtil.prepareStatement;
import static com.ron.dao.DAOUtil.setValues;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ron.exceptions.DAOException;
import com.ron.model.FileUpload;
import com.ron.model.PlayListFile;
import com.ron.model.Uuid;
import com.ron.pereference.SystemGlobals;
import com.ron.utils.FileUtils;

public class FileUploadDAOJDBC extends BaseDAOJDBC implements FileUploadDAO {

    private static final String SQL_INSERT = "INSERT INTO xxfb_fileupload (id, filename, uuid, uploadtime, uploaduser, type, duration) VALUES (fileupload_id_seq.nextval, ?, ?, sysdate, ?, ?, ?)";
	private static final String SQL_LISTALL_ORDER_BY_ID = "select t.uploaduser, t.uploadtime, t.uuid, t.filename, t.type, t.duration, count(b.filename) count from xxfb_fileupload t, xxfb_container b where t.uuid = b.uuid(+) and (t.uploaduser = ? or t.uploaduser in (select id from xxfb_user where  right >= 10)) group by t.uuid, t.filename, t.type, t.duration, t.uploaduser, t.uploadtime order by t.uploadtime desc";
	private static final String SQL_LISTCITY5_ORDER_BY_ID = "select * from (select t.uuid, t.filename, t.uploadtime, t.uploaduser, t.type, t.duration, count(b.filename) count from xxfb_fileupload t, xxfb_container b where t.uuid = b.uuid(+) and (t.uploaduser = ?) group by t.uuid, t.filename, t.type, t.duration,t.uploaduser,t.uploadtime order by t.uploadtime desc) where rownum <=5";
	private static final String SQL_LISTPR5_ORDER_BY_ID = "select * from (select t.uuid, t.filename, t.uploadtime, t.uploaduser, t.type, t.duration, count(b.filename) count from xxfb_fileupload t, xxfb_container b where t.uuid = b.uuid(+) and t.uploaduser in (select id from xxfb_user where  right >= 10) group by t.uuid, t.filename, t.type, t.duration, t.uploaduser, t.uploadtime order by t.uploadtime desc) where rownum <= 5";
	private static final String SQL_LISTCITY_ORDER_BY_ID = "select t.uuid, t.filename, t.uploadtime, t.uploaduser, t.type, t.duration, count(b.filename) count from xxfb_fileupload t, xxfb_container b where t.uuid = b.uuid(+) and (t.uploaduser = ?) group by t.uuid, t.filename, t.type, t.duration,t.uploaduser,t.uploadtime order by t.uploadtime desc";
	private static final String SQL_LISTPR_ORDER_BY_ID = "select t.uuid, t.filename, t.uploadtime, t.uploaduser, t.type, t.duration, count(b.filename) count from xxfb_fileupload t, xxfb_container b where t.uuid = b.uuid(+) and t.uploaduser in (select id from xxfb_user where  right >= 10) group by t.uuid, t.filename, t.type, t.duration, t.uploaduser, t.uploadtime order by t.uploadtime desc";
	private static final String SQL_DELETE_CITY_BY_UUID = "delete from xxfb_fileupload t where t.uuid = ?";
	
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
	public void destroy(List<Uuid> list) throws IllegalArgumentException,
			DAOException {
	    Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_CITY_BY_UUID);
            for(Uuid p:list){
        	    Object[] values = {
        	    		p.getUuid()
        	    };
        	    setValues(preparedStatement, values);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }
		
	}
	
	
	@Override
	public List<FileUpload> listAll(String username) throws IllegalArgumentException, DAOException{
	    Object[] values = {
	    		username
	    };		
	    
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    List<FileUpload> fileUploads = new ArrayList<FileUpload>();

	    try {
	        connection = daoFactory.getConnection();
	        preparedStatement = connection.prepareStatement(SQL_LISTALL_ORDER_BY_ID);
       	    setValues(preparedStatement, values);
	        
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
        String shortFilename = filename.substring(0, 2) + "?." + FileUtils.getFileExtension(filename);
//        log.info(shortFilename + ":" + FileUtils.getFilePrefix(filename).length() + ":" + filename + ":" + FileUtils.getFilePrefix(filename));
        String uuid = resultSet.getString("uuid");
        String type = resultSet.getString("type");
        int count = resultSet.getInt("count");
        String url = "";

        fileUpload.setFilename(filename);
        fileUpload.setShortFilename(shortFilename);
        fileUpload.setUuid(resultSet.getString("uuid"));
        fileUpload.setUploaduser(resultSet.getString("uploaduser"));
        fileUpload.setUploadtime(resultSet.getTimestamp("uploadtime"));
        fileUpload.setCount(count);
        url = SystemGlobals.getDefaultsValue("srcPath");
        if(type.equalsIgnoreCase("video")){
        	url += "/" + uuid + "/video.png";
        }else if(type.equalsIgnoreCase("imgs")){
        	url += "/" + uuid + "/image-000001.png";
        }else{
        	url += uuid + filename.substring(filename.lastIndexOf("."), filename.length());
        }
        fileUpload.setUrl(url);
        fileUpload.setType(resultSet.getString("type"));
        fileUpload.setDuration(resultSet.getLong("duration"));
        
        return fileUpload;
    }
    
    
	@Override
	public List<FileUpload> listCity(String username, boolean top5) throws IllegalArgumentException, DAOException{
	    Object[] values = {
	    		username
	    };		
	    
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    List<FileUpload> fileUploads = new ArrayList<FileUpload>();

	    try {
	        connection = daoFactory.getConnection();
	        preparedStatement = connection.prepareStatement(top5?SQL_LISTCITY5_ORDER_BY_ID:SQL_LISTCITY_ORDER_BY_ID);
       	    setValues(preparedStatement, values);
	        
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
	
	@Override
	public List<FileUpload> listPR(String username, boolean top5) throws IllegalArgumentException, DAOException{
	    Object[] values = {
	    		username
	    };		
	    
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    List<FileUpload> fileUploads = new ArrayList<FileUpload>();

	    try {
	        connection = daoFactory.getConnection();
	        preparedStatement = connection.prepareStatement(top5?SQL_LISTPR5_ORDER_BY_ID:SQL_LISTPR_ORDER_BY_ID);
//       	    setValues(preparedStatement, values);
	        
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

}
