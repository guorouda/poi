package com.ron.dao;

import java.util.List;

import com.ron.exceptions.DAOException;
import com.ron.model.PlayListFile;

public interface PlayListFileDAO extends BaseDAO {


	public void create(List<PlayListFile> list) throws IllegalArgumentException,
			DAOException;
	
	public void destroy(List<PlayListFile> list) throws IllegalArgumentException,
			DAOException;
	
	public List<PlayListFile> list() throws DAOException;

}
