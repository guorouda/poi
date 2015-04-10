package com.ron.dao;

import static com.ron.dao.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ron.exceptions.DAOException;
import com.ron.model.User;

public class DeptDAOJDBC extends BaseDAOJDBC implements DeptDAO {
	
    private static final String SQL_GET_CHILDREN = "select t.*, connect_by_isleaf leaf from post_jg t where t.uporgcode = ? connect by prior orgcode = uporgcode  start with orgcode = ? ";
    private static final String SQL_GET_ONCHARGE = "select t.*, connect_by_isleaf leaf from post_jg t where t.orgcode = ? connect by prior orgcode = uporgcode  start with orgcode = ? ";
    
	@Override
	public String getChildren(String node) throws DAOException {
		String value = node.equals("0") ? getDepid() : node.substring(node.lastIndexOf('/') + 1, node.length());
		
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
	
	private String getDepid(){
		UserDAO userDAO = DAOFactory.getInstance().getDAOImpl(UserDAO.class);
		User user = new User();
		user.setId("hejie");
		
		return userDAO.getValue(user, "depid");
	}


}
