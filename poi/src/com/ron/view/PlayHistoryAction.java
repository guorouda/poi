package com.ron.view;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.ron.Command;
import com.ron.dao.DAOFactory;
import com.ron.dao.PlayHistorySpanDAO;
import com.ron.model.PlayHistoryProvinceSpan;
import com.ron.model.PlayHistoryRecord;
import com.ron.model.PlayHistorySpan;

public class PlayHistoryAction extends Command {

	public String list() {
		String starttime = req.getParameter("starttime").replaceAll("T", " ").replaceAll("-", "");
		String endtime = req.getParameter("endtime").replaceAll("T", " ").replaceAll("-", "");
		String username = req.getParameter("id");
        PlayHistorySpanDAO phsDAO = DAOFactory.getInstance().getDAOImpl(PlayHistorySpanDAO.class);
        List<PlayHistorySpan> list = phsDAO.list(starttime, endtime, username);
        
		return TellFront(list, "rows").toString();
	}
	
	
	public String recordlist() {
		String starttime = req.getParameter("starttime").replaceAll("T", " ").replaceAll("-", "");
		String endtime = req.getParameter("endtime").replaceAll("T", " ").replaceAll("-", "");
		String username = req.getParameter("id");
		String playlistfile = "";
		try {
			playlistfile = new String(req.getParameter("playlistfile").getBytes("iso-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        PlayHistorySpanDAO phsDAO = DAOFactory.getInstance().getDAOImpl(PlayHistorySpanDAO.class);
        List<PlayHistoryRecord> list = phsDAO.recordlist(starttime, endtime, username, playlistfile);
        
		return TellFront(list, "rows").toString();
	}
	
	
	public String provincelist() {
		String starttime = req.getParameter("starttime").replaceAll("T", " ").replaceAll("-", "");
		String endtime = req.getParameter("endtime").replaceAll("T", " ").replaceAll("-", "");
		String username = req.getParameter("id");

        PlayHistorySpanDAO phsDAO = DAOFactory.getInstance().getDAOImpl(PlayHistorySpanDAO.class);
        List<PlayHistoryProvinceSpan> list = phsDAO.provincelist(starttime, endtime, username);
        
		return TellFront(list, "rows").toString();
	}
	
	
}
