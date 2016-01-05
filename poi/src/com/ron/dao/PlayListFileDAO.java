package com.ron.dao;

import java.util.List;

import com.ron.exceptions.DAOException;
import com.ron.model.PlayListFile;
import com.ron.model.PlayListFilePIC;

public interface PlayListFileDAO extends BaseDAO {


	public void create(List<PlayListFile> list) throws IllegalArgumentException,
			DAOException;
	
	public void destroy(List<PlayListFile> list) throws IllegalArgumentException,
			DAOException;
	
	public List<PlayListFile> list(String username) throws DAOException;

	public List<PlayListFilePIC> listpic(String id);

}
