package com.ron;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ron.dao.DAOFactory;
import com.ron.utils.REncrypt;
import com.ron.utils.StringUtil;


public class Emsxxfb extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String extension = "do";
	public static Logger log = Logger.getLogger(Emsxxfb.class);
	

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		
        DAOFactory.init("javabase.jndi");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
//        resp.setContentType("text/html;charset=UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        
		String username = authen(req);
		
		String result = "";
		String action = req.getParameter("action");
		String _module = req.getRequestURI();
		String _contextPath = req.getContextPath();
		String module = _module.substring(_contextPath.length() + 1, _module.length() - extension.length() - 1);//+1 剔除'/'号， -1剔除'.'号
		
        if(action.equals("login") && module.equals("UserAction")){
        	//add some code
        }else{
	        if(StringUtil.isEmpty(username)){
	        	out.print("{success: false, message: '请重新登录'}");
	        	return;
	        }
        }
        
		try{
			result = (String)this.processCommand(req, resp, module, action, username);
		}catch(Exception e){
			e.printStackTrace();
		}
		
        out.print(result);
		
	}
	
	private Object processCommand(HttpServletRequest req, HttpServletResponse resp, String module, String action, String username) throws Exception {
		Command c = this.retrieveCommand(module);
		Object object = c.process(req, resp, module, action, username);
		return object;
	}
	
	private Command retrieveCommand(String module) throws Exception
	{
		return (Command)Class.forName("com.ron.view."+ module).newInstance();
	}
	
	private String authen(HttpServletRequest req){
        Cookie[] cookies = req.getCookies();  
        String[] cooks = null;   
        String username = "";   
        
        if (cookies != null) {   
            for (Cookie coo : cookies) {   
            	if(coo.getName().equals("data")){
            		String encrypt = coo.getValue(); //log.info("cookie: " + encrypt);
            		String key = "guoguo";
            		String decrypt = "";
            		
					try {
						decrypt = REncrypt.aesDecrypt(encrypt, key);log.info(decrypt);
		                cooks = decrypt.split("==");   
		                if (cooks.length == 2) {
		                    username = cooks[0];  
		                }   
					} catch (Exception e) {
						log.error("cookie: ", e);
					}             		
            		
            	}
            }   
        }
        
        return username;
	}

}
