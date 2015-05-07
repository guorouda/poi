package com.ron.dao;

import java.util.List;

import com.ron.exceptions.DAOException;
import com.ron.model.PlayList;
import com.ron.model.PlayListFile;

public interface PlayListDAO extends BaseDAO {

	public void create(PlayList playList) throws IllegalArgumentException,
			DAOException;

	public List<PlayList> list() throws DAOException; 
}
