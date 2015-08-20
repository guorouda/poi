package com.ron.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.coobird.thumbnailator.Thumbnails;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.selectors.FilenameSelector;
import org.apache.tools.ant.types.selectors.OrSelector;
import org.artofsolving.jodconverter.office.OfficeException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ron.Emsxxfb;
import com.ron.controller.converter.pdfConverter.JacobPDFConverter;
import com.ron.controller.converter.pngConverter.XpdfPNGConverter;
import com.ron.dao.CityDAO;
import com.ron.dao.ContainerDAO;
import com.ron.dao.DAOFactory;
import com.ron.dao.FileUploadDAO;
import com.ron.dao.UserDAO;
import com.ron.model.City;
import com.ron.model.Container;
import com.ron.model.FileUpload;
import com.ron.model.FileUploadBean;
import com.ron.model.User;
import com.ron.pereference.SystemGlobals;
import com.ron.utils.FileUtils;
import com.ron.utils.ReadVideo;

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
	public @ResponseBody String create(HttpServletRequest req, FileUploadBean uploadItem, BindingResult result){
		if (result.hasErrors()){
			for(ObjectError error : result.getAllErrors()){
				System.err.println("Error: " + error.getCode() +  " - " + error.getDefaultMessage());
			}
		}
		
		String results = "";
		String type = "VIDEO";
		long time = Long.parseLong(SystemGlobals.getDefaultsValue("duration"));
		
		String username = Emsxxfb.authen(req);
		if(username.equals("")){
			return 	"{\"message\":\"上传文件失败，用户未登录或超时，请重新登录\", \"success\":\"false\"}";
		}
		
        UserDAO userDAO = DAOFactory.getInstance().getDAOImpl(UserDAO.class);
        User user = new User(username);
        String right = userDAO.getValue(user, "right");
        String filename;
        String uuid;

		try {
			String appPath = SystemGlobals.getDefaultsValue("application.path");
			filename = uploadItem.getFile().getOriginalFilename();
			String extension = filename.substring(filename.lastIndexOf(".") + 1);
			String extension1 = filename.substring(filename.lastIndexOf("."));
			
			uuid = UUID.randomUUID().toString();
			//TODO download temp need to read from config
			String filepath = appPath + File.separator + "download" + File.separator + "temp" + File.separator + uuid + "." + extension;
			File file = new File(filepath);
			uploadItem.getFile().transferTo(file);
				
			String isimg = FileUtils.ImageTypeCheck(filepath);
			if(isimg.equals("8950") || isimg.equals("ffd8") || isimg.equals("4749") || isimg.equals("424d")) {
				String destinationFilePath = FileUtils.getFilePrefix(filepath);
				File destinationFile = new File(destinationFilePath);
				if (!destinationFile.exists()) {
		            destinationFile.mkdir();
		        }
				FileUtils.copyFile(filepath, destinationFilePath + File.separator + uuid + "." + extension);
				type = "IMG";
			}
			
			if(extension.equalsIgnoreCase("ppt") || extension.equalsIgnoreCase("pptx")){
				String pdfFile = FileUtils.getFilePrefix(filepath) + ".pdf";
				JacobPDFConverter.ppt2PDF(filepath, pdfFile);
				extension = "pdf";
			}
			
			if(extension.equalsIgnoreCase("doc") || extension.equalsIgnoreCase("docx")){
				String pdfFile = FileUtils.getFilePrefix(filepath) + ".pdf";
				JacobPDFConverter.word2PDF(filepath, pdfFile);
				extension = "pdf";
			}		
			
			if(extension.equalsIgnoreCase("xls") || extension.equalsIgnoreCase("xlsx")){
				String pdfFile = FileUtils.getFilePrefix(filepath) + ".pdf";
				JacobPDFConverter.excel2PDF(filepath, pdfFile);
				extension = "pdf";
			}
			
			if(extension.equals("pdf")){
				XpdfPNGConverter xpdf = new XpdfPNGConverter(FileUtils.getFilePrefix(filepath) + ".pdf");
				xpdf.setCONVERTOR_STORED_PATH(SystemGlobals.getDefaultsValue("xpdfPath"));
				String pngDir = FileUtils.getFilePrefix(filepath);
				Process p = xpdf.toPNG(pngDir);
				p.waitFor();

				type = "IMGS";
			}
			
			if(type.equals("VIDEO")){
				time = ReadVideo.getTime(filepath);
				if(time == 0) {
					type = "None";
				}else{
					String destinationFilePath = FileUtils.getFilePrefix(filepath);
					File destinationFile = new File(destinationFilePath);
					if (!destinationFile.exists()) {
			            destinationFile.mkdir();
			        }
					FileUtils.copyFile(appPath + File.separator + "icons" + File.separator + "video.png", destinationFilePath + File.separator + "video.png");
				}
			}
			if(type.equals("IMGS")){
				time = 0;
			}
			
			FileUpload fileUpload = new FileUpload(filename, uuid, username, type, time);
			FileUploadDAO fileUploadDAO = DAOFactory.getInstance().getDAOImpl(FileUploadDAO.class);
			fileUploadDAO.create(fileUpload);
			
			ContainerDAO containerDAO = DAOFactory.getInstance().getDAOImpl(ContainerDAO.class);
			String pngDir = FileUtils.getFilePrefix(filepath);
			List<Container> list = fileList(pngDir); 
			for(Container c:list){
				Container container = new Container(c.getFilename(),c.getUuid(), c.getDuration());
				containerDAO.create(container);
			}
			
			results = tellFront(containerDAO.find(uuid)).toString();
			
			//thumbnail
			if(!extension.equalsIgnoreCase("gif")){
				thumbnailAll(pngDir);
			}
			
	        if(Integer.parseInt(right) >= 10){
	        	//transfer file to  city
        		ftpzip(uuid, extension1, appPath + File.separator + "download" + File.separator + "temp");
        		String ftppath = appPath + File.separator + "download" + File.separator + "temp" + File.separator + uuid + ".zip";
        		FileUpload fileupload = new FileUpload();
        		fileupload.setFilename(filename);
        		fileupload.setUuid(uuid);
	        	List<City> citylist = getCityAll();
	        	for(City city:citylist){
	        		if(city.isActive()){
	        			log.info(city.getName() + " ftp put starting...");
	        			new Thread(new MyTransferFile(city, fileupload, ftppath)).start();
	        		}
	        	}
	        }
			
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
		
		return results;
	}
	
	private void thumbnailAll(String pngDir) {
       	try {
			File file = new File(pngDir);
	        if (file.isDirectory()) {  
	            File[] files = file.listFiles();  
	            for (int i = 0; i < files.length; i++) {  
	            	if(files[i].isFile()){
						FileUploadController.thumbnail(files[i]);
	            	}
	            }
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<Container> fileList(String pngDir){
        List<Container> list = new ArrayList<Container>();
        
        File file = new File(pngDir);  
        if (file.isDirectory()) {  
            File[] files = file.listFiles();  
            for (int i = 0; i < files.length; i++) {  
                String name = files[i].getName();  
               	String uuid = pngDir.substring(pngDir.lastIndexOf("\\") + 1, pngDir.length());
               	Container container = new Container(name, uuid, Long.parseLong(SystemGlobals.getDefaultsValue("duration")));
                	
                list.add(container);
            }  
        }
        
        return list;
	}
	
	private JSONObject tellFront(List<Container> list){
		
		for(Container c:list){
			c.setPath(SystemGlobals.getDefaultsValue("srcPath"));
		}
        JSONArray ja = JSONArray.fromObject(list);
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("success", true);
        m.put("count", list.size());
        m.put("user", ja);
        m.put("message", " " + list.size() + " ");
        
        return JSONObject.fromObject(m);
	}
	
	public List<City> getCityAll(){
		List<City> list = new ArrayList<City>();
        CityDAO cityDAO = DAOFactory.getInstance().getDAOImpl(CityDAO.class);
        list = cityDAO.listAll();
		
		return list;
	}
	
	public static void thumbnail(File file) throws IOException{  
		String filePath = file.getAbsolutePath();
		String fileDir = filePath.substring(0, filePath.lastIndexOf(File.separator));
		
		File thumbnailFileDir = new File(fileDir + File.separator + "thumb");
		if(!thumbnailFileDir.exists()){
			thumbnailFileDir.mkdir();
		}
		
		String thumbnailFilePath = fileDir + File.separator + "thumb" + File.separator + "thumb-" + file.getName();
		
        Thumbnails.of(filePath)  
        /*  
         * forceSize,size和scale必须且只能调用一个  
         */ 
//      .forceSize(400, 400)  //生成的图片一定为400*400  
        /*  
         * 若图片横比200小，高比300小，不变  
         * 若图片横比200小，高比300大，高缩小到300，图片比例不变  
         * 若图片横比200大，高比300小，横缩小到200，图片比例不变  
         * 若图片横比200大，高比300大，图片按比例缩小，横为200或高为300  
         */ 
        .size(60, 80)     
        .outputFormat("png") //生成图片的格式为png  
        .outputQuality(0.8f) //生成质量为80%  
//      .scale(0.5f)  //缩小50%  
        .toFile(thumbnailFilePath);  
	}  
	
	private static void ftpzip(String uuid, String extension, String path) throws BuildException, RuntimeException{
        Project pj = new Project();
        Zip zip = new Zip();
        zip.setProject(pj);
        zip.setDestFile(new File(path + File.separator + uuid + ".zip"));//打包完的目标文件
        
        FileSet fileSet = new FileSet();
        fileSet.setProject(pj);
        fileSet.setDir(new File(path));//需要打包的路径
//        fileSet.setIncludes("*.jpg");//文件过滤  只 包含所有.doc文件
        
        FilenameSelector a = new FilenameSelector();  
        String name = uuid + extension;
        a.setName(name);  
          
        FilenameSelector b = new FilenameSelector();  
        b.setName(uuid + "/**/*");  
          
        OrSelector or = new OrSelector();   
        or.addFilename(a);  
        or.addFilename(b);  
          
        fileSet.addOr(or);  
        zip.addFileset(fileSet);
        
        zip.execute();
	}
	

}
