package com.ron.view;

import java.util.List;

import com.ron.Command;
import com.ron.dao.DAOFactory;
import com.ron.dao.FileUploadDAO;
import com.ron.exceptions.DAOException;
import com.ron.model.FileUpload;

public class FileUploadAction extends Command {

	@Override
	public String list2() {
        FileUploadDAO fileUploadDAO = DAOFactory.getInstance().getDAOImpl(FileUploadDAO.class);
        List<FileUpload> list = fileUploadDAO.list();
        
        return TellFront(list, "rows").toString();
	}

	@Override
	public String list(){
        FileUploadDAO fileUploadDAO = DAOFactory.getInstance().getDAOImpl(FileUploadDAO.class);
        List<FileUpload> list = fileUploadDAO.list();
        
        return TellFront(list, "rows").toString();
	}
	
	
	public void create(FileUpload fileupload) throws IllegalArgumentException, DAOException{
        FileUploadDAO fileUploadDAO = DAOFactory.getInstance().getDAOImpl(FileUploadDAO.class);
        fileUploadDAO.create(fileupload);
	}

}
