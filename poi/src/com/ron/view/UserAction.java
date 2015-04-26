package com.ron.view;

import javax.servlet.http.Cookie;

import com.ron.Command;
import com.ron.dao.DAOFactory;
import com.ron.dao.UserDAO;
import com.ron.utils.REncrypt;

public class UserAction extends Command {

	@Override
	public String list() {
		// TODO Auto-generated method stub
		return "Login";
	}

	public int myadd() {
		int i = Integer.parseInt(req.getParameter("i"));
		int j = Integer.parseInt(req.getParameter("j"));
		return i + j;
	}
	
	public String pr(){
		String s = req.getParameter("s");
		return s;
	}
	
	public String login(){
		String username = req.getParameter("username");
		String password = req.getParameter("password");
        UserDAO userDAO = DAOFactory.getInstance().getDAOImpl(UserDAO.class);
        String s = userDAO.login(username, password);
        log.info(s);
        
        if(s.substring(0, 1).equals("1")){   
            int seconds = 5*60*60;   

            String content = username+"=="+password;
            String key = "guoguo";
            try {
				String encrypt = REncrypt.aesEncrypt(content, key);
				log.info(encrypt);
                Cookie cookie = new Cookie("data", encrypt); 
                cookie.setMaxAge(seconds);                      
                resp.addCookie(cookie);   
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	        s = "{'success': true, 'username':'" + username + "', 'typeid':" + s.substring(2,3) + "}";
        }else{   
	        s = "{'success': false}";
        } 
        return s;
	}

	@Override
	public String list2() {
		// TODO Auto-generated method stub
		return null;
	}

}
