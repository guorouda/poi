package com.ron.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ron.Command;
import com.ron.dao.DAOFactory;
import com.ron.dao.FileUploadDAO;
import com.ron.dao.PlayListDAO;
import com.ron.exceptions.DAOException;
import com.ron.model.FileUpload;

public class FileUploadAction extends Command {

	@Override
	public String list2() {
        FileUploadDAO fileUploadDAO = DAOFactory.getInstance().getDAOImpl(FileUploadDAO.class);
        List list = fileUploadDAO.list();
        
        return TellFront(list).toString();
	}

	@Override
	public String list(){
        FileUploadDAO fileUploadDAO = DAOFactory.getInstance().getDAOImpl(FileUploadDAO.class);
        List list = fileUploadDAO.list();
        
        return TellFront(list).toString();
//		return "{\"user\":[{\"name\":\"sara_pink.jpg\",\"size\":2154,\"lastmod\":1407243286000,\"url\":\"/poi/download/temp/73Dir/image-000001.png\"},{\"name\":\"sara_pumpkin.jpg\",\"size\":2588,\"lastmod\":1407243286000,\"url\":\"resources/images/sara_pumpkin.jpg\"},{\"name\":\"zack.jpg\",\"size\":2901,\"lastmod\":1407243286000,\"url\":\"resources/images/zack.jpg\"},{\"name\":\"sara_smile.jpg\",\"size\":2410,\"lastmod\":1407243286000,\"url\":\"resources/images/sara_smile.jpg\"},{\"name\":\"zack_sink.jpg\",\"size\":2303,\"lastmod\":1407243286000,\"url\":\"resources/images/zack_sink.jpg\"},{\"name\":\"up_to_something.jpg\",\"size\":2120,\"lastmod\":1407243286000,\"url\":\"resources/images/up_to_something.jpg\"},{\"name\":\"kids_hug.jpg\",\"size\":2477,\"lastmod\":1407243286000,\"url\":\"resources/images/kids_hug.jpg\"},{\"name\":\"dance_fever.jpg\",\"size\":2067,\"lastmod\":1407243286000,\"url\":\"resources/images/dance_fever.jpg\"},{\"name\":\"kids_hug2.jpg\",\"size\":2476,\"lastmod\":1407243286000,\"url\":\"resources/images/kids_hug2.jpg\"},{\"name\":\"gangster_zack.jpg\",\"size\":2115,\"lastmod\":1407243286000,\"url\":\"resources/images/gangster_zack.jpg\"},{\"name\":\"zacks_grill.jpg\",\"size\":2825,\"lastmod\":1407243286000,\"url\":\"resources/images/zacks_grill.jpg\"},{\"name\":\"zack_dress.jpg\",\"size\":2645,\"lastmod\":1407243286000,\"url\":\"resources/images/zack_dress.jpg\"},{\"name\":\"zack_hat.jpg\",\"size\":2323,\"lastmod\":1407243286000,\"url\":\"resources/images/zack_hat.jpg\"}]}";		
	}
	
	
	public void create(FileUpload fileupload) throws IllegalArgumentException, DAOException{
        FileUploadDAO fileUploadDAO = DAOFactory.getInstance().getDAOImpl(FileUploadDAO.class);
        fileUploadDAO.create(fileupload);
	}

	private JSONObject TellFront(List list){
		
        JSONArray ja = JSONArray.fromObject(list);
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("success", true);
        m.put("total", list.size());
        m.put("rows", ja);
        m.put("message", " " + list.size() + " ");
        
        return JSONObject.fromObject(m);
	}
	
	
}
