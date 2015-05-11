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

	@Override
	public String list2() {
		// TODO Auto-generated method stub
		String result = "[ { \"name\": \"Kitchen Sink\", \"thumb\": \"sink.png\", \"url\": \"kitchensink\", \"type\": \"Application\" }, { \"name\": \"Twitter app\", \"thumb\": \"twitter.png\", \"url\": \"twitter\", \"type\": \"Application\" }, { \"name\": \"Kiva app\", \"thumb\": \"kiva.png\", \"url\": \"kiva\", \"type\": \"Application\" }, { \"name\": \"Geocongress\", \"thumb\": \"geocongress.png\", \"url\": \"geocongress\", \"type\": \"Application\" }, { \"name\": \"AJAX\", \"thumb\": \"ajax.png\", \"url\": \"ajax\", \"type\": \"Example\" }, { \"name\": \"Carousel\", \"thumb\": \"carousel.png\", \"url\": \"carousel\", \"type\": \"Example\" }, { \"name\": \"Drag &amp; Drop\", \"thumb\": \"DnD.png\", \"url\": \"dragdrop\", \"type\": \"Example\" }, { \"name\": \"Forms\", \"thumb\": \"forms.png\", \"url\": \"forms\", \"type\": \"Example\" }, { \"name\": \"Guide\", \"thumb\": \"guide.png\", \"url\": \"guide\", \"type\": \"Example\" }, { \"name\": \"Icons\", \"thumb\": \"icons.png\", \"url\": \"icons\", \"type\": \"Example\" }, { \"name\": \"Map\", \"thumb\": \"map.png\", \"url\": \"map\", \"type\": \"Example\" }, { \"name\": \"Nested List\", \"thumb\": \"nestedList.png\", \"url\": \"nestedlist\", \"type\": \"Example\" }, { \"name\": \"Overlays\", \"thumb\": \"overlays.png\", \"url\": \"overlays\", \"type\": \"Example\" }, { \"name\": \"Picker\", \"thumb\": \"picker.png\", \"url\": \"picker\", \"type\": \"Example\" }, { \"name\": \"Sortable\", \"thumb\": \"sortable.png\", \"url\": \"sortable\", \"type\": \"Example\" }, { \"name\": \"Tabs\", \"thumb\": \"tabs.png\", \"url\": \"tabs\", \"type\": \"Example\" }, { \"name\": \"Tabs 2\", \"thumb\": \"tabs2.png\", \"url\": \"tabs2\", \"type\": \"Example\" }, { \"name\": \"Toolbars\", \"thumb\": \"toolbars.png\", \"url\": \"toolbars\", \"type\": \"Example\" }, { \"name\": \"YQL\", \"thumb\": \"yql.png\", \"url\": \"yql\", \"type\": \"Example\" }]";
		return result;
	}

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
	
	@Override
	public String list(){
        PlayListFileDAO playListFileDAO = DAOFactory.getInstance().getDAOImpl(PlayListFileDAO.class);
        List list = playListFileDAO.list();
        
		return TellFront(list, "user").toString();
	}
	
}
