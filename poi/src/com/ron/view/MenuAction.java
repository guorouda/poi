package com.ron.view;

import com.ron.Command;
import com.ron.dao.DAOFactory;
import com.ron.dao.MenuDAO;
import com.ron.dao.UserDAO;
import com.ron.model.User;

public class MenuAction extends Command {

	public String list() {
		String node = req.getParameter("node");
        MenuDAO menuDAO = DAOFactory.getInstance().getDAOImpl(MenuDAO.class);
        String s = menuDAO.getMenu(node, username);
        
		return s;
	}

}
