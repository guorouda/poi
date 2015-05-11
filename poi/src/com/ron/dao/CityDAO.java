package com.ron.dao;

import java.util.List;

import com.ron.exceptions.DAOException;
import com.ron.model.City;

public interface CityDAO extends BaseDAO {

	public List<City> list() throws DAOException;

}
