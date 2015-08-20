package com.ron.controller;

import org.apache.log4j.Logger;

import com.ron.dao.CityDAO;
import com.ron.dao.DAOFactory;
import com.ron.model.City;
import com.ron.model.FileUpload;

import it.sauronsoftware.ftp4j.FTPDataTransferListener;

public class MyTransferFileListener implements FTPDataTransferListener {
	
	public static Logger log = Logger.getLogger(MyTransferFileListener.class);
	
	private City city;
	private FileUpload fileupload;

	public MyTransferFileListener(City city, FileUpload fileupload) {
		this.city = city;
		this.fileupload = fileupload;
	}

	@Override
	public void aborted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void completed() {
		// TODO Auto-generated method stub
		log.info(city.getName() + "传输成功！！");
		CityDAO cityDAO = DAOFactory.getInstance().getDAOImpl(CityDAO.class);
		cityDAO.addTransferHisotry(city, fileupload);
	}

	@Override
	public void failed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void started() {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void transferred(int arg0) {
		// TODO Auto-generated method stub
		
	}

}
