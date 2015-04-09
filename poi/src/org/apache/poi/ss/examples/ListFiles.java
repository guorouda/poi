package org.apache.poi.ss.examples;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class ListFiles {
	public static Logger log = Logger.getLogger(ListFiles.class);
	public static List<String> listFiles(String path){
		List<String> filenames = new ArrayList<String>();
		File file = new File(path);
		String[] files = file.list();
		for(int i = 0; i < files.length; i++){
			File readfile = new File(path + "\\" + files[i]);
			if(readfile.isFile()){
				filenames.add(readfile.getPath());
//				System.out.println(i + ":" + readfile.getPath());
			}
		}
		return filenames;
		
	}
	
	public static TreeMap<String, String> getPicPath(List<String> list){
		TreeMap<String, String> treemap = new TreeMap<String, String>();
    	InputStreamReader isr = null;   
    	BufferedReader reader = null;	    	 
    	
		try{
			int i = 0;
			for(String s:list){
				i++;
				String[] Ss = s.split("\\_");
				File file = new File(s);
		    	isr = new InputStreamReader(new FileInputStream(file), "GBK");   
		    	reader = new BufferedReader(isr);	    	 
		    	String tempString = null; 
		    	StringBuffer sb = new StringBuffer();
		    	
		    	while ((tempString = reader.readLine()) != null){ 
		    		sb.append(tempString);
		    	}
		    	
				Pattern pattern = Pattern.compile("href=\"([^>]*)\">");
				Matcher matcher = pattern.matcher(sb.toString());
//				System.out.print( Ss[1] );
				if(matcher.find()){
//					System.out.println( "|" + matcher.group(1));
					treemap.put(Ss[1], matcher.group(1));
				}else{
					treemap.put(Ss[1], "");
//					System.out.println();
				}
				
			}
			
		}catch(Exception e){
			
		}finally{
			try{
				reader.close();
				isr.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}

		return treemap;
	}
	
	
	public static void main(String[] args){
		String path = "e:\\1\\1";
		List<String> list = ListFiles.listFiles(path);
		TreeMap<String, String> treemap = ListFiles.getPicPath(list);
	}
}
