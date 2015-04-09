package com.ron;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


public class Emsxxfb extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String extension = "do";
	public static Logger log = Logger.getLogger(Emsxxfb.class);
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String result = "";
		String action = req.getParameter("action");
		String _module = req.getRequestURI();
		String _contextPath = req.getContextPath();
		String module = _module.substring(_contextPath.length() + 1, _module.length() - extension.length() - 1);//+1 剔除'/'号， -1剔除'.'号
		
		log.info(_contextPath);
		log.info(_module);
		log.info(module + "|" + action);
		
		try{
			this.processCommand(req, resp, module, action);
		}catch(Exception e){
			e.printStackTrace();
		}
		

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(result);
		
	}
	
	private void processCommand(HttpServletRequest req, HttpServletResponse resp, String module, String action) throws Exception {
		Command c = this.retrieveCommand(module);
		Object object = c.process(req, resp, module, action);
	}
	
	private Command retrieveCommand(String module) throws Exception
	{
		return (Command)Class.forName("com.ron.view."+ module).newInstance();
	}
	

}
