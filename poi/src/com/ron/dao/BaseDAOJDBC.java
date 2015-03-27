package com.ron.dao;

public abstract class BaseDAOJDBC {
	
	public DAOFactory daoFactory;

	public void setDAOFactory(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
}
