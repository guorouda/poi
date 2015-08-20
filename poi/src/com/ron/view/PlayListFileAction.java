package com.ron.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.validator.ValidateWith;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.ron.Command;
import com.ron.dao.DAOFactory;
import com.ron.dao.PlayListFileDAO;
import com.ron.model.PlayListFile;

public class PlayListFileAction extends Command {

	public String create() {
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
    	
	    List<PlayListFile> list = null;
    	try{
			Gson gson = new Gson(); 
			Type type = new TypeToken<List<PlayListFile>>(){}.getType();
		    list = gson.fromJson(item, type);
    	}catch(JsonSyntaxException e){
    		log.error("error: ", e);
    	}
    	
    	for(PlayListFile plf:list){
    		plf.setListid(username);
    	}
	    
        PlayListFileDAO playListFileDAO = DAOFactory.getInstance().getDAOImpl(PlayListFileDAO.class);
        playListFileDAO.create(list);
		
		return "{success: true, message: \"成功插入\", count: " + list.size() + ", user:[{\"message\": \"ok!\"}]}";
	}
	
	public String destroy() {
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
    	
	    List<PlayListFile> list = null;
    	try{
			Gson gson = new Gson(); 
			Type type = new TypeToken<List<PlayListFile>>(){}.getType();
		    list = gson.fromJson(item, type);
    	}catch(JsonSyntaxException e){
    		log.error("error: ", e);
    	}
	    
        PlayListFileDAO playListFileDAO = DAOFactory.getInstance().getDAOImpl(PlayListFileDAO.class);
        playListFileDAO.destroy(list);
		
		return "{success: true, message: \"成功删除\", count: " + list.size() + ", user:[{\"message\": \"ok!\"}]}";
	}
	
	public String list(){
        PlayListFileDAO playListFileDAO = DAOFactory.getInstance().getDAOImpl(PlayListFileDAO.class);
        List list = playListFileDAO.list(username);
        
		return TellFront(list, "user").toString();
	}
	
}
