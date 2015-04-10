package com.ron.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.ron.model.Dept;

public class FileConvertController {
	
	private static Logger log = Logger.getLogger(FileConvertController.class);
	
	public static JSONObject tellfront(String srcFilePath) {  
		BufferedReader reader = null; 
        List<Dept> list = new ArrayList<Dept>();
        JSONObject result = new JSONObject();
        int iCount = 0;	

        try {
			File file = new File(srcFilePath);
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "GBK");   
	    	reader = new BufferedReader(isr);	    	 
	    	String tempString = null; 
    	
			while ((tempString = reader.readLine()) != null){ 
				String[] tempStrings = tempString.split("\t");
				if(tempStrings.length<2){
//					log.debug(tempString);
					continue;
				}
//				log.debug(tempStrings[1]);
				Dept sb = new Dept(tempStrings[0], tempStrings[1], "", false);
				list.add(sb);
				iCount++;
			}
			
            JSONArray ja = JSONArray.fromObject(list);
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("success", true);
            m.put("count", iCount);
            m.put("user", ja);
            result = JSONObject.fromObject(m);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
       		try {
       			if (reader != null){ 
       				reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			 } 
		}
		
		return result;
    }

	
}
