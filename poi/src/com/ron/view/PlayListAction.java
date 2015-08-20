package com.ron.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ron.Command;
import com.ron.dao.DAOFactory;
import com.ron.dao.PlayListDAO;

public class PlayListAction extends Command {

	public String list(){
        PlayListDAO playListDAO = DAOFactory.getInstance().getDAOImpl(PlayListDAO.class);
        List list = playListDAO.list();
        
		return TellFront(list, "user").toString();
	}
	
}
