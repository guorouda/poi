package com.ron.view;

import com.ron.Command;
import com.ron.dao.DAOFactory;
import com.ron.dao.MenuDAO;
import com.ron.dao.UserDAO;
import com.ron.model.User;

public class MenuAction extends Command {

	@Override
	public String list() {
		String node = req.getParameter("node");
        MenuDAO menuDAO = DAOFactory.getInstance().getDAOImpl(MenuDAO.class);
        String s = menuDAO.getMenu(node);
        System.out.println(s);
		return s;
	}

	@Override
	public int myadd() {
		int i = Integer.parseInt(req.getParameter("i"));
		int j = Integer.parseInt(req.getParameter("j"));
		return i + j;
	}
	
	public String pr(){
		String s = req.getParameter("s");
		return s;
	}

	@Override
	public String list2() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
