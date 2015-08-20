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
import com.ron.dao.ContainerDAO;
import com.ron.dao.DAOFactory;
import com.ron.dao.PlayListFileDAO;
import com.ron.model.Container;
import com.ron.model.PlayListFile;

public class ContainerAction extends Command {

	public String list(){
		return "{\"count\":\"1\", \"user\":[{\"filename\":\"sara_pink.jpg\",\"duration\":\"2154\",\"id\":\"1407243286000\",\"uuid\":\"2222222222222222\"}]}";
	}
	
	public String update(){
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
			e.printStackTrace();
		}  
		
		String item = sb.toString();
    	if(!item.startsWith("[")){
    		item = "[" + item + "]";
    	}		
    	
	    List<Container> list = null;
    	try{
			Gson gson = new Gson(); 
			Type type = new TypeToken<List<Container>>(){}.getType();
		    list = gson.fromJson(item, type);
    	}catch(JsonSyntaxException e){
    		log.error("error: ", e);
    	}
	    
        ContainerDAO containerDAO = DAOFactory.getInstance().getDAOImpl(ContainerDAO.class);
        containerDAO.update(list);
		
		return "{success: true, message: \"成功插入\", count: " + list.size() + ", user:[{\"message\": \"ok!\"}]}";
	}
	
}
