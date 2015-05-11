package com.ron.view;

import java.util.List;

import com.ron.Command;
import com.ron.dao.CityDAO;
import com.ron.dao.DAOFactory;
import com.ron.model.City;

public class CityListAction extends Command{

	@Override
	public String list2() {
		return null;
	}

	@Override
	public String list() {
		
        CityDAO cityDAO = DAOFactory.getInstance().getDAOImpl(CityDAO.class);
        List<City> list = cityDAO.list();
        
		return TellFront2(list).toString();
	}
	

}
