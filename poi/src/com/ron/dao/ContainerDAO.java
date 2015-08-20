package com.ron.dao;

import java.util.List;

import com.ron.exceptions.DAOException;
import com.ron.model.Container;

public interface ContainerDAO extends BaseDAO{
	
	public void create(Container Container) throws IllegalArgumentException, DAOException;
	
	public void update(List<Container> list) throws IllegalArgumentException, DAOException;
	
	public List<Container> find(String uuid) throws IllegalArgumentException, DAOException;

}
