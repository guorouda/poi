package com.ron.dao;

import com.ron.exceptions.DAOException;

public interface MenuDAO extends BaseDAO {

	public String getMenu(String node) throws DAOException;

}
