package com.ron.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.ron.Command;
import com.ron.dao.DAOFactory;
import com.ron.dao.FileUploadDAO;
import com.ron.exceptions.DAOException;
import com.ron.model.FileUpload;
import com.ron.model.PlayListFile;
import com.ron.model.Uuid;

public class FileUploadAction extends Command {

	public String list2() {
        FileUploadDAO fileUploadDAO = DAOFactory.getInstance().getDAOImpl(FileUploadDAO.class);
        List<FileUpload> list = fileUploadDAO.listAll(username);
        
        return TellFront(list, "rows").toString();
	}

	public String listall(){
        FileUploadDAO fileUploadDAO = DAOFactory.getInstance().getDAOImpl(FileUploadDAO.class);
        List<FileUpload> list = fileUploadDAO.listAll(username);
        
        return TellFront(list, "rows").toString();
	}
	
	public String listcity(){
        FileUploadDAO fileUploadDAO = DAOFactory.getInstance().getDAOImpl(FileUploadDAO.class);
        List<FileUpload> list = fileUploadDAO.listCity(username, false);
        
        return TellFront(list, "rows").toString();
	}
	
	public String listPR(){
        FileUploadDAO fileUploadDAO = DAOFactory.getInstance().getDAOImpl(FileUploadDAO.class);
        List<FileUpload> list = fileUploadDAO.listPR(username, false);
        
        return TellFront(list, "rows").toString();
	}
	
	public String listcity5(){
        FileUploadDAO fileUploadDAO = DAOFactory.getInstance().getDAOImpl(FileUploadDAO.class);
        List<FileUpload> list = fileUploadDAO.listCity(username, true);
        
        return TellFront(list, "rows").toString();
	}
	
	public String listPR5(){
        FileUploadDAO fileUploadDAO = DAOFactory.getInstance().getDAOImpl(FileUploadDAO.class);
        List<FileUpload> list = fileUploadDAO.listPR(username, true);
        
        return TellFront(list, "rows").toString();
	}
	
	public void create(FileUpload fileupload) throws IllegalArgumentException, DAOException{
        FileUploadDAO fileUploadDAO = DAOFactory.getInstance().getDAOImpl(FileUploadDAO.class);
        fileUploadDAO.create(fileupload);
	}
	
	public String destroycity(){
		String temp;  
		StringBuffer sb = new StringBuffer();
		BufferedReader br;
		
		try {
			br = new BufferedReader(new InputStreamReader(req.getInputStream(), "utf-8"));
			while ((temp = br.readLine()) != null) {  
			    sb.append(temp);  
			}  
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.info("error: ", e);
		}  
		
		String item = sb.toString();
    	if(!item.startsWith("[")){
    		item = "[" + item + "]";
    	}		
    	
	    List<Uuid> list = null;
    	try{
			Gson gson = new Gson(); 
			Type type = new TypeToken<List<Uuid>>(){}.getType();
		    list = gson.fromJson(item, type);
    	}catch(JsonSyntaxException e){
    		log.error("error: ", e);
    	}
    	
        FileUploadDAO fileUploadDAO = DAOFactory.getInstance().getDAOImpl(FileUploadDAO.class);
        fileUploadDAO.destroy(list);
        
        return "{success: true, message: \"成功删除\", count: " + list.size() + ", user:[{\"message\": \"ok!\"}]}";
	}

}
