package com.ron.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.artofsolving.jodconverter.office.OfficeException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ron.controller.converter.pdfConverter.OpenOfficePDFConverter;
import com.ron.controller.converter.pdfConverter.PDFConverter;
import com.ron.controller.converter.pngConverter.XpdfPNGConverter;
import com.ron.model.ExtJSFormResult;
import com.ron.model.FileUploadBean;
import com.ron.model.PNG;
import com.ron.pereference.SystemGlobals;
import com.ron.utils.FileUtils;

/**
 * Controller - Spring
 * 
 * @author Loiane Groner
 * http://loiane.com
 * http://loianegroner.com
 */
@Controller
@RequestMapping(value = "/upload.action")
public class FileUploadController {
	
	public static Logger log = Logger.getLogger(FileUploadController.class);

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody String create(FileUploadBean uploadItem, BindingResult result){

		String results = "";
		ExtJSFormResult extjsFormResult = new ExtJSFormResult();
		
		if (result.hasErrors()){
			for(ObjectError error : result.getAllErrors()){
				System.err.println("Error: " + error.getCode() +  " - " + error.getDefaultMessage());
			}
			
			//set extjs return - error
			extjsFormResult.setSuccess(false);
			
			return extjsFormResult.toString();
		}

		// Some type of file processing...
		try {
			String appPath = SystemGlobals.getDefaultsValue("application.path");
			String filename = uploadItem.getFile().getOriginalFilename();
			String filepath = appPath + File.separator + "download" + File.separator + "temp" + File.separator + filename;
			File file = new File(filepath);
			uploadItem.getFile().transferTo(file);
			PDFConverter pdfConverter = new OpenOfficePDFConverter();
			pdfConverter.convert2PDF(filepath);
			XpdfPNGConverter xpdf = new XpdfPNGConverter(FileUtils.getFilePrefix(filepath)+".pdf");
			String pngDir = FileUtils.getFilePrefix(filepath)+"Dir";
			Process p = xpdf.toPNG(pngDir);
			p.waitFor();
			results = TellFront(FileList(pngDir)).toString();
			log.info(results);
			
		} catch (IllegalStateException e) {
			e.printStackTrace();
			log.error("error:", e);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("error:", e);
		}catch(OfficeException e){
			log.error("error: ",e);
		} catch (InterruptedException e) {
			e.printStackTrace();
			log.error("error:", e);
		}
		
		//set extjs return - sucsess
		extjsFormResult.setSuccess(true);
		
//		return extjsFormResult.toString();
		return results;
	}
	
	private List FileList(String pngDir){
        List<PNG> list = new ArrayList<PNG>();
        
        File file = new File(pngDir);  
        if (file.isDirectory()) {  
            File[] files = file.listFiles();  
            for (int i = 0; i < files.length; i++) {  
                String name = files[i].getName();  
                if (name.trim().toLowerCase().endsWith(".png")) {  
                	PNG png = new PNG(name, "<a href='/poi/download/temp/73Dir/" + name + "'>a</a>");
                    list.add(png);
                }  
            }  
        }
        
        return list;
	
	}
	
	private JSONObject TellFront(List list){
		
        JSONArray ja = JSONArray.fromObject(list);
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("success", true);
        m.put("count", list.size());
        m.put("user", ja);
        
        return JSONObject.fromObject(m);
	}

}
