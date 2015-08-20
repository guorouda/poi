package com.ron.view;

import java.util.List;

import com.ron.Command;
import com.ron.dao.CityDAO;
import com.ron.dao.DAOFactory;
import com.ron.model.Stats;

public class StatsAction extends Command {

	public String list() {
		String starttime = req.getParameter("starttime").replaceAll("T", " ").replaceAll("-", "");
		String endtime = req.getParameter("endtime").replaceAll("T", " ").replaceAll("-", "");
        CityDAO cityDAO = DAOFactory.getInstance().getDAOImpl(CityDAO.class);
        List<Stats> list = cityDAO.listStats(starttime, endtime);
        
		return TellFront(list, "rows").toString();
	}
	
}
