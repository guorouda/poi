package com.ron;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.impl.common.SystemCache;

import com.ron.dao.DAOFactory;
import com.ron.exceptions.DAOException;
import com.ron.pereference.SystemGlobals;

public class initServlet extends HttpServlet {
	private static Logger log = Logger.getLogger(initServlet.class);
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);

		try {
			log.info("initiating ...");
			String appPath = config.getServletContext().getRealPath("");
			SystemGlobals.initGlobals(appPath, appPath + "/WEB-INF/config/SystemGlobals.properties");
			
			DAOFactory.init("javabase.jdbc");
			
			SystemGlobals.getPlayListFile();
			SystemGlobals.getPlayListFilePIC();
		}catch (Exception e) {
			throw new DAOException("Error while starting XXFB", e);
		}
	}


}
