package com.ron.dao;

import java.util.List;

import com.ron.exceptions.DAOException;
import com.ron.model.FileUpload;
import com.ron.model.Uuid;

public interface FileUploadDAO extends BaseDAO {
	
	public void create(FileUpload fileupload) throws IllegalArgumentException, DAOException;

	public List<FileUpload> listAll(String username) throws IllegalArgumentException, DAOException;
	
	public List<FileUpload> listCity(String username, boolean top5) throws IllegalArgumentException, DAOException;
	
	public List<FileUpload> listPR(String username, boolean top5) throws IllegalArgumentException, DAOException;
	
	public void destroy(List<Uuid> list) throws IllegalArgumentException, DAOException;
}
