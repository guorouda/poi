package com.ron.dao;

import static com.ron.dao.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ron.exceptions.DAOException;
import com.ron.model.Dept;
import com.ron.model.User;

public class DeptDAOJDBC extends BaseDAOJDBC implements DeptDAO {
	
    private static final String SQL_GET_CHILDREN = "select t.*, connect_by_isleaf leaf from post_jg t where t.uporgcode = ? connect by prior orgcode = uporgcode  start with orgcode = ? ";
    private static final String SQL_GET_ONCHARGE = "select t.*, connect_by_isleaf leaf from post_jg t where t.orgcode = ? connect by prior orgcode = uporgcode  start with orgcode = ? ";
	private static final String SQL_FIND_BY_ORGCODE = "select * from post_jg t where t.orgcode = ?";
	private static final String SQL_CREATE = "insert into post_jg (orgcode, uporgcode, orgjc, candelete) values (?, ?, ?, 1)";
	private static final String SQL_UPDATE = "update post_jg t set t.uporgcode = ?, t.orgjc = ? where t.orgcode = ?";
	private static final String SQL_DELETE = "delete from post_jg t where t.orgcode = ? and t.candelete = 1";
    
	@Override
	public String getChildren(String node, String username) throws DAOException {
		String value = node.equals("0") ? getDepid(username) : node.substring(node.lastIndexOf('/') + 1, node.length());
		
        Object[] values = { 
              value,
              value 
        };
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        StringBuffer sb = new StringBuffer("");
        
        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, node.equals("0") ? SQL_GET_ONCHARGE : SQL_GET_CHILDREN, false, values);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
            	String id = resultSet.getString("orgcode"); 
            	String text = resultSet.getString("orgjc") ;
            	Boolean leaf = resultSet.getBoolean("leaf");
           		sb.append("{"  + "\"id\":\"" + node + "/"+ id +  "\"," + "\"leaf\":" + leaf + ",\"text\":\"" + text + "\"},");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "0";
        } finally {
            close(connection, preparedStatement, resultSet) ;
        }
        
        String s = "[" + sb.substring(0, sb.length()-1) + "]";
		return s;
	}
	
	private String getDepid(String username){
		UserDAO userDAO = DAOFactory.getInstance().getDAOImpl(UserDAO.class);
		User user = new User();
		user.setId(username);
		
		return userDAO.getValue(user, "depid");
	}

	@Override
	public Dept getDeptByOrgcode(String _orgcode){
        Object[] values = { 
              _orgcode 
        };
        
        Dept dept = new Dept();
        
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
		
        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_FIND_BY_ORGCODE, false, values);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
            	String orgcode = resultSet.getString("orgcode"); 
            	String orgjc  = resultSet.getString("orgjc") ;
            	String uporgcode = resultSet.getString("uporgcode");
            	
            	dept.setOrgcode(orgcode);
            	dept.setOrgjc(orgjc);
            	dept.setUporgcode(uporgcode);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            close(connection, preparedStatement, resultSet) ;
        }		
        
        return dept;
	}

	@Override
	public boolean create(Dept dept) throws DAOException {
	      Object[] values = { 
	              dept.getOrgcode(), dept.getUporgcode(), dept.getOrgjc() 
	        };
	      boolean result = false;
	        
			Connection connection = null;
	        PreparedStatement preparedStatement = null;
			
	        try {
	            connection = daoFactory.getConnection();
	            preparedStatement = prepareStatement(connection, SQL_CREATE, false, values);
	            int i = preparedStatement.executeUpdate();
	            if(i > 0) {
	            	result = true;
	            }
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        } finally {
	            close(connection, preparedStatement) ;
	        }		
	        
	        return result;
	}

	@Override
	public boolean update(Dept dept, String uporgcode, String orgjc) throws DAOException {
		Object[] values = { 
				uporgcode, orgjc, dept.getOrgcode()
	    };
		boolean result = false;
	        
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	
	    try {
	        connection = daoFactory.getConnection();
	        preparedStatement = prepareStatement(connection, SQL_UPDATE, false, values);
	        int i = preparedStatement.executeUpdate();
	        if(i > 0) {
	        	result = true;
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    } finally {
	        close(connection, preparedStatement) ;
	    }
	    return result;
	}

	@Override
	public boolean delete(Dept dept) throws DAOException {
		Object[] values = { 
			dept.getOrgcode()
	    };
	        
		boolean result = false;
		
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	
	    try {
	        connection = daoFactory.getConnection();
	        preparedStatement = prepareStatement(connection, SQL_DELETE, false, values);
	        int i = preparedStatement.executeUpdate();
	        if(i > 0) {
	        	result = true;
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    } finally {
	        close(connection, preparedStatement) ;
	    }
		
	    return result;
	}

}
