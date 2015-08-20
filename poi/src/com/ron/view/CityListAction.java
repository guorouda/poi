package com.ron.view;

import java.util.List;

import com.ron.Command;
import com.ron.dao.CityDAO;
import com.ron.dao.DAOFactory;
import com.ron.model.City;

public class CityListAction extends Command{

	public String listAll() {
		
        CityDAO cityDAO = DAOFactory.getInstance().getDAOImpl(CityDAO.class);
        List<City> list = cityDAO.listAll();
        
		return TellFront(list, "rows").toString();
	}
	
	public String list(){
        CityDAO cityDAO = DAOFactory.getInstance().getDAOImpl(CityDAO.class);
        List<City> list = cityDAO.list(username);
        
		return TellFront(list, "rows").toString();
	}
	
	public String save(){
		String id = req.getParameter("id");
		String ipaddress = req.getParameter("ipaddress");
		String username = req.getParameter("username");
		String path = req.getParameter("path");
		String name = req.getParameter("name");
		String port = req.getParameter("port");
		String password = req.getParameter("password");
		
		City city = new City();
		city.setId(Long.parseLong(id));
		city.setIpaddress(ipaddress);
		city.setName(name);
		city.setPassword(password);
		city.setPath(path);
		city.setPort(Integer.parseInt(port));
		city.setUsername(username);
		
        CityDAO cityDAO = DAOFactory.getInstance().getDAOImpl(CityDAO.class);
        if(cityDAO.update(city)){
	        return "{'success': true, 'title': '正确', 'message':'ftp信息修改成功！'}";
        };
        
        return "{'success': false, 'title': '错误', 'message':'ftp信息修改失败！'}";
	}

}
