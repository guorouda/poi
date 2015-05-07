package com.ron.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

import com.ron.controller.converter.pdfConverter.JacobPDFConverter;
import com.ron.controller.converter.pdfConverter.OpenOfficePDFConverter;
import com.ron.controller.converter.pdfConverter.PDFConverter;
import com.ron.controller.converter.pngConverter.XpdfPNGConverter;
import com.ron.dao.ContainerDAO;
import com.ron.dao.DAOFactory;
import com.ron.dao.FileUploadDAO;
import com.ron.model.Container;
import com.ron.model.ExtJSFormResult;
import com.ron.model.FileUpload;
import com.ron.model.FileUploadBean;
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
			String extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
			String type = "VIDEO";
			
			String uuid = UUID.randomUUID().toString();
			log.info(uuid);
			String filepath = appPath + File.separator + "download" + File.separator + "temp" + File.separator + uuid + "." + extension;
			File file = new File(filepath);
			uploadItem.getFile().transferTo(file);
			
			String isimg = FileUtils.ImageTypeCheck(filepath);
			if(isimg.equals("8950") || isimg.equals("ffd8") || isimg.equals("4749") || isimg.equals("424d")){
				results = "{\"message\":\"成功上传文件\", \"success\":\"true\", \"count\":1, \"user\":[{\"duration\":5000,\"filename\":\"" + uuid + "." + extension + "\",\"id\":\"2678\",\"uuid\":\"\"}]}";
				type = "IMG";
			}else{
				results = "{\"message\":\"成功上传文件\", \"success\":\"true\", \"count\":1, \"user\":[{\"duration\":5000,\"filename\":\"video.png\",\"id\":\"2678\",\"uuid\":\"\"}]}";
			}
			
			if(extension.equalsIgnoreCase("ppt") || extension.equalsIgnoreCase("pptx")){
//				PDFConverter pdfConverter = new OpenOfficePDFConverter();
//				pdfConverter.convert2PDF(filepath);
				String pdfFile = FileUtils.getFilePrefix(filepath)+".pdf";
				JacobPDFConverter.ppt2PDF(filepath, pdfFile);
				extension = "pdf";
			}
			
			if(extension.equals("pdf")){
				XpdfPNGConverter xpdf = new XpdfPNGConverter(FileUtils.getFilePrefix(filepath)+".pdf");
				String pngDir = FileUtils.getFilePrefix(filepath);
				Process p = xpdf.toPNG(pngDir);
				p.waitFor();
				
				ContainerDAO containerDAO = DAOFactory.getInstance().getDAOImpl(ContainerDAO.class);
				List<Container> list = FileList(pngDir); 
				for(Container c:list){
					Container container = new Container(c.getFilename(),c.getUuid(), c.getDuration());
					containerDAO.create(container);
				}
				
				results = TellFront(containerDAO.find(uuid)).toString();
				type = "IMGS";
			}
			
			FileUpload fileUpload = new FileUpload(filename, uuid, "username", type);
			FileUploadDAO fileUploadDAO = DAOFactory.getInstance().getDAOImpl(FileUploadDAO.class);
			fileUploadDAO.create(fileUpload);
			
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
	
	private List<Container> FileList(String pngDir){
        List<Container> list = new ArrayList<Container>();
        
        File file = new File(pngDir);  
        if (file.isDirectory()) {  
            File[] files = file.listFiles();  
            for (int i = 0; i < files.length; i++) {  
                String name = files[i].getName();  
                if (name.trim().toLowerCase().endsWith(".png")) {  
                	String uuid = pngDir.substring(pngDir.lastIndexOf("\\") + 1, pngDir.length());
                	Container container = new Container(name, uuid, 5000);
                    list.add(container);
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
        m.put("message", " " + list.size() + " ");
        
        return JSONObject.fromObject(m);
	}

}
