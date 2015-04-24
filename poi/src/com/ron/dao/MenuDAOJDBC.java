package com.ron.dao;

import static com.ron.dao.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.ron.exceptions.DAOException;
import com.ron.model.User;

public class MenuDAOJDBC extends BaseDAOJDBC implements MenuDAO {
	
	public static Logger log = Logger.getLogger(MenuDAOJDBC.class);
    private static final String SQL_MEMU = "select t.*, connect_by_isleaf leaf from xxfb_menu t where t.right <= ? and pid = ? connect by prior id = pid start with id = ?";

	@Override
	public String getMenu(String node, String username) throws DAOException {
		String value = getRight(username);
		String dept = node.substring(node.lastIndexOf('/') + 1, node.length());
		
        Object[] values = {        	  
              value,
              dept,
              dept
        };
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        StringBuffer sb = new StringBuffer();
        
        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_MEMU, false, values);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
            	String id = resultSet.getString("id"); 
            	String text = resultSet.getString("name");
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
	
	private String getRight(String username){
		UserDAO userDAO = DAOFactory.getInstance().getDAOImpl(UserDAO.class);
		User user = new User();
		user.setId(username);
		
		return userDAO.getValue(user, "right");
		
	}


}
