package com.ron.view;

import com.ron.Command;
import com.ron.dao.DAOFactory;
import com.ron.dao.DeptDAO;
import com.ron.dao.UserDAO;
import com.ron.model.User;

public class DeptAction extends Command {

	@Override
	public String list() {
		String node = req.getParameter("node");
        DeptDAO deptDAO = DAOFactory.getInstance().getDAOImpl(DeptDAO.class);
        String s = deptDAO.getChildren(node);
        
		return s;
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

	@Override
	public String list2() {
		return null;
	}

}
