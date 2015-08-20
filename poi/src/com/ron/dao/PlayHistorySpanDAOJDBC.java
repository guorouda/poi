package com.ron.dao;

import static com.ron.dao.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import oracle.net.aso.i;

import org.apache.log4j.Logger;

import com.ron.exceptions.DAOException;
import com.ron.model.City;
import com.ron.model.PlayHistoryProvinceSpan;
import com.ron.model.PlayHistoryRecord;
import com.ron.model.PlayHistorySpan;
import com.ron.model.Stats;

public class PlayHistorySpanDAOJDBC extends BaseDAOJDBC implements PlayHistorySpanDAO {
	
	public static Logger log = Logger.getLogger(PlayHistorySpanDAOJDBC.class);
	
	private static final String SQL_LIST_BY_ID = "select distinct t.playlistfile, b.name, t.username from xxfb_playhistory t, xxfb_city b where b.id = t.username and t.begintime between to_date(?, 'yyyy/mm/dd hh24:mi:ss') and to_date(?,'yyyy/mm/dd hh24:mi:ss') and t.username = ?";

	private static final String SQL_RECORDLIST_BY_ID = "select * from xxfb_playhistory t where t.begintime between  to_date(?, 'yyyy/mm/dd hh24:mi:ss')  and  to_date(?, 'yyyy/mm/dd hh24:mi:ss')  and t.username = ? and t.playlistfile like ?";

	private static final String SQL_PROVINCELIST_BY_ID = "select * from xxfb_fileupload t where t.uploadtime between to_date(?, 'yyyy/mm/dd hh24:mi:ss') and to_date(?, 'yyyy/mm/dd hh24:mi:ss') and t.uploaduser = ?";

	@Override
	public List<PlayHistorySpan> list(String begintime, String endtime, String username) throws DAOException {
  	    Object[] values = {
  	    		begintime,
  	    		endtime,
  	    		username
  	    };
  	                     
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<PlayHistorySpan> p = new ArrayList<PlayHistorySpan>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(SQL_LIST_BY_ID);
            setValues(preparedStatement, values);
            resultSet = preparedStatement.executeQuery();
           	List<String> list = new ArrayList<String>();
           	String _name ="";
           	String _username = "";
            while (resultSet.next()) {
                String playlistfile[] = resultSet.getString("playlistfile").split("\\|");
                for(String file:playlistfile){
                	list.add(file);
                }
                _name = resultSet.getString("name");
                _username =resultSet.getString("username"); 
            }
            HashSet<String> h = new HashSet<String>(list);
            Iterator<String> iterator = h.iterator();
            while(iterator.hasNext()){
            	PlayHistorySpan phs = new PlayHistorySpan();
            	phs.setName(_name);
            	phs.setUsername(_username);
            	phs.setPlaylistfile(iterator.next());
            	
            	p.add(phs);
            }
            
            
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return p;
	}
	
	
	@Override
	public List<PlayHistoryRecord> recordlist(String begintime, String endtime, String username, String playlistfile) throws DAOException {
  	    Object[] values = {
  	    		begintime,
  	    		endtime,
  	    		username,
  	    		"%" + playlistfile + "%"
  	    };
  	                     
  	    log.info(username+ "|" + playlistfile);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<PlayHistoryRecord> p = new ArrayList<PlayHistoryRecord>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(SQL_RECORDLIST_BY_ID);
            setValues(preparedStatement, values);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
            	p.add(map(resultSet, playlistfile));
            }
            
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return p;
	}
	
    private static PlayHistoryRecord map(ResultSet resultSet, String playlistfile) throws SQLException {
        PlayHistoryRecord phr = new PlayHistoryRecord();

        phr.setPlaylistfile(playlistfile);
        phr.setBegintime(resultSet.getString("begintime"));
        phr.setEndtime(resultSet.getString("endtime"));
        
        return phr;
    }
    
	@Override
	public List<PlayHistoryProvinceSpan> provincelist(String begintime, String endtime, String username) throws DAOException {
  	    Object[] values = {
  	    		begintime,
  	    		endtime,
  	    		username
  	    };
  	                     
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<PlayHistoryProvinceSpan> p = new ArrayList<PlayHistoryProvinceSpan>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(SQL_PROVINCELIST_BY_ID);
            setValues(preparedStatement, values);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
            	p.add(provinceMap(resultSet));
            }
            
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return p;
	}
    
    private static PlayHistoryProvinceSpan provinceMap(ResultSet resultSet) throws SQLException {
        PlayHistoryProvinceSpan phps = new PlayHistoryProvinceSpan();

        phps.setFilename(resultSet.getString("filename"));
        phps.setUuid(resultSet.getString("uuid"));
        phps.setUploadtime(resultSet.getString("uploadtime"));
        phps.setUploaduser(resultSet.getString("uploaduser"));
        
        return phps;
    }

}
