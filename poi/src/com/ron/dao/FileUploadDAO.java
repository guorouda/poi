package com.ron.dao;

import java.util.List;

import com.ron.exceptions.DAOException;
import com.ron.model.FileUpload;

public interface FileUploadDAO extends BaseDAO {
	
	public void create(FileUpload fileupload) throws IllegalArgumentException, DAOException;

	public List<FileUpload> list() throws IllegalArgumentException, DAOException;
}
