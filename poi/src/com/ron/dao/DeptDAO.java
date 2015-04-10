package com.ron.dao;

import com.ron.exceptions.DAOException;

public interface DeptDAO extends BaseDAO {

	public String getChildren(String node) throws DAOException;

}
