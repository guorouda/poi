package com.ron.dao;

import java.util.List;

import com.ron.exceptions.DAOException;
import com.ron.model.PlayHistoryProvinceSpan;
import com.ron.model.PlayHistoryRecord;
import com.ron.model.PlayHistorySpan;

public interface PlayHistorySpanDAO extends BaseDAO {

	public abstract List<PlayHistorySpan> list(String begintime, String endtime, String username) throws DAOException;
	
	public abstract List<PlayHistoryRecord> recordlist(String begintime, String endtime, String username, String playlistfile) throws DAOException;
	
	public abstract List<PlayHistoryProvinceSpan> provincelist(String begintime, String endtime, String username) throws DAOException;

}
