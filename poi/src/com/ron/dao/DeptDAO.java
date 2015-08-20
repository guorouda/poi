package com.ron.dao;

import com.ron.exceptions.DAOException;
import com.ron.model.Dept;

public interface DeptDAO extends BaseDAO {

	public String getChildren(String node, String username) throws DAOException;

	public Dept getDeptByOrgcode(String _orgcode) throws DAOException;

	public abstract boolean create(Dept dept) throws DAOException;

	public abstract boolean update(Dept dept, String uporgcode, String orgjc) throws DAOException;

	public abstract boolean delete(Dept dept) throws DAOException;

}
