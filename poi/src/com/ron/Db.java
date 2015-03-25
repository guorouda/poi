package com.ron;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Db {
    public static Connection getsmsConnection() {
		Connection conn = null;
		try {//属性文件
	    	Properties prop = new Properties();
	    	InputStream is = Db.class.getResourceAsStream("/systemconfig.properties");
	    	//从输入流中读取属性列表
	    	prop.load(is);	    	   
	        Class.forName(prop.getProperty("driver"));
	        conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return conn;
    }
    
    
    public static void closeConnection(Connection conn){
    	try {
		   	if(null != conn){
				conn.close();
		   	}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

}
