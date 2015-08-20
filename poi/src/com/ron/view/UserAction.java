package com.ron.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.http.Cookie;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.ron.Command;
import com.ron.dao.DAOFactory;
import com.ron.dao.UserDAO;
import com.ron.model.User;
import com.ron.utils.REncrypt;
import com.ron.utils.StringUtil;

public class UserAction extends Command {
	public static Logger log = Logger.getLogger(UserAction.class);

	public String list() {
		String depid = req.getParameter("depid");
		depid = StringUtil.getLeaf(depid);
		
        UserDAO userDAO = DAOFactory.getInstance().getDAOImpl(UserDAO.class);
        List<User> list = userDAO.getUserByDepid(depid);
        
        JSONArray ja = JSONArray.fromObject(list);
		
		return ja.toString();
	}

	public String changepwd(){
		String result;
		
		String oldpwd = req.getParameter("oldpwd");
		String newpwd = req.getParameter("newpwd");
        
        User user = new User();
        user.setPassword(newpwd);
        user.setId(username);
        
        UserDAO userDAO = DAOFactory.getInstance().getDAOImpl(UserDAO.class);
        String pwd = userDAO.getValue(user, "password");
        log.info(pwd);
        if(pwd.equals(oldpwd)){
        	userDAO.changePassword(user);
	        result = "{'success': true, 'username':'" + username + "', 'message':'" + "成功改密" + "'}";
        }else{
	        result = "{'success': false}";
        }
		
		return result;
	}
	
	public String login(){
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
        UserDAO userDAO = DAOFactory.getInstance().getDAOImpl(UserDAO.class);
        String s = userDAO.login(username, password);
        
        if(s.substring(0, 1).equals("1")){   
            int seconds = 5 * 60 * 60;   

            String content = username + "==" + password;
            String key = "guoguo";
            try {
				String encrypt = REncrypt.aesEncrypt(content, key);
                Cookie cookie = new Cookie("data", encrypt); 
                cookie.setMaxAge(seconds);                      
                resp.addCookie(cookie);   
			} catch (Exception e) {
				log.error("error: ", e);
			}  
	        s = "{'success': true, 'username':'" + username + "', 'typeid':" + s.substring(2,3) + "}";
        }else{   
	        s = "{'success': false}";
        } 
        return s;
	}

	public String create(){
		String result = "{'success': false}";
		
	    List<User> list = Json2List();
        UserDAO userDAO = DAOFactory.getInstance().getDAOImpl(UserDAO.class);
       	boolean flag = userDAO.create(list);
       	if(flag){
	        result = "{'success': true, 'message': '成功添加'}";
        }
		
		return result;
	}
	
	public String destroy(){
		String result = "{'success': false}";
		String userid = req.getParameter("userid");
		
	    List<User> list = Json2List();
        UserDAO userDAO = DAOFactory.getInstance().getDAOImpl(UserDAO.class);
        User user = new User();
        user.setId(userid);
       	boolean flag = userDAO.destroy(list);
       	if(flag){
	        result = "{'success': true, 'message': '成功删除'}";
        }
		
		return result;
	}
	
	private List<User> Json2List(){
		String temp;  
		StringBuffer sb = new StringBuffer();
		BufferedReader br;
		
		try {
			br = new BufferedReader(new InputStreamReader(req.getInputStream(), "utf-8"));
			while ((temp = br.readLine()) != null) {  
			    sb.append(temp);  
			}  
			br.close();
		} catch (IOException e) {
    		log.error("error: ", e);
		}  
		
		String item = sb.toString();
    	if(!item.startsWith("[")){
    		item = "[" + item + "]";
    	}	
    	
	    List<User> list = null;
    	try{
			Gson gson = new Gson(); 
			Type type = new TypeToken<List<User>>(){}.getType();
		    list = gson.fromJson(item, type);
    	}catch(JsonSyntaxException e){
    		log.error("error: ", e);
    	}
    	
    	return list;
	}
	
	
}
