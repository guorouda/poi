package com.ron.dao;

import java.util.List;

import com.ron.exceptions.DAOException;
import com.ron.model.City;
import com.ron.model.FileUpload;
import com.ron.model.Stats;

public interface CityDAO extends BaseDAO {

	public abstract List<City> list(String username) throws DAOException;
	
	public abstract List<City> listAll() throws DAOException;
	
	public boolean update(City city) throws DAOException;
	
	public List<Stats> listStats(String starttime, String endtime) throws DAOException;

	public boolean addTransferHisotry(City city, FileUpload fileupload) throws DAOException;

}
