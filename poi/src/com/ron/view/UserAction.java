package com.ron.view;

import com.ron.Command;

public class UserAction extends Command {

	@Override
	public String list() {
		// TODO Auto-generated method stub
		return "Login";
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
	
	private void validateLogin(String username, String password){
		
	}

}
