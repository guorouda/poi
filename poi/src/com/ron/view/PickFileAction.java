package com.ron.view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.ron.Command;
import com.ron.controller.FileUploadController;
import com.ron.controller.converter.pdfConverter.JacobPDFConverter;
import com.ron.controller.converter.pngConverter.XpdfPNGConverter;
import com.ron.dao.ContainerDAO;
import com.ron.dao.DAOFactory;
import com.ron.dao.FileUploadDAO;
import com.ron.model.Container;
import com.ron.model.FileUpload;
import com.ron.pereference.SystemGlobals;
import com.ron.utils.FileUtils;
import com.ron.utils.ReadVideo;

public class PickFileAction extends Command{
	public static Logger log = Logger.getLogger(PickFileAction.class);
	
	public static void fileList(){
		fileList("C:\\inetpub\\ftproot"); 
	}
	
	private static void fileList(String filePath){
        List<Container> list = new ArrayList<Container>();
        
        long time = 5000;
        String type = "";
        
        File file = new File(filePath);  
        if (file.isDirectory()) {  
            File[] files = file.listFiles();  
            for (int i = 0; i < files.length; i++) {  
            	time = 500;
                String name = files[i].getName();  
                String uuid = UUID.randomUUID().toString();
               	String extension = name.substring(name.lastIndexOf(".") + 1, name.length());
               	
               	String targetPath = SystemGlobals.getDefaultsValue("application.path") + File.separator + "download" + File.separator + "temp" + File.separator + uuid + "." + extension;
               	log.info(targetPath);
//               	File targetFile = new File(targetPath);
               	
               	File newDirectory =  new File(SystemGlobals.getDefaultsValue("application.path") + File.separator + "download" + File.separator + "temp");
               	String newName =  uuid + "." + extension;
				moveFile(files[i], newDirectory, newName);
               	
               	String isimg = "";
				try {
					targetPath = SystemGlobals.getDefaultsValue("application.path") + File.separator + "download" + File.separator + "temp" + File.separator + newName;
					isimg = FileUtils.ImageTypeCheck(targetPath);
	    			if(isimg.equals("8950") || isimg.equals("ffd8") || isimg.equals("4749") || isimg.equals("424d")){
	    				extension = "img";
	    			}else{
	    			}
				} catch (IOException e) {
					e.printStackTrace();
				}
               	
               	switch(extension){
               		case "pptx":
               		case "ppt":
               			String pdfFile = FileUtils.getFilePrefix(targetPath) + ".pdf";
        				JacobPDFConverter.ppt2PDF(targetPath, pdfFile);
               		case "pdf":
						try {
							XpdfPNGConverter xpdf = new XpdfPNGConverter(FileUtils.getFilePrefix(targetPath)+".pdf");
	        				String pngDir = FileUtils.getFilePrefix(targetPath);
							Process p = xpdf.toPNG(pngDir);
							p.waitFor();
							
	        				ContainerDAO containerDAO = DAOFactory.getInstance().getDAOImpl(ContainerDAO.class);
	        				List<Container> containerList = FileUploadController.fileList(pngDir); 
	        				for(Container c:containerList){
	        					Container container = new Container(c.getFilename(),c.getUuid(), c.getDuration());
	        					containerDAO.create(container);
	        				}
						} catch (IOException e) {
							e.printStackTrace();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						type = "IMGS";
        				break;
               		case "img":
               			type = "IMG";
               			break;
               		default:
               			time = ReadVideo.getTime(targetPath);
               			if(time > 0){
               				type = "VIDEO";
               			}
               			break;
               	}
               	
				FileUpload fileUpload = new FileUpload(name, uuid, "admin", type, time);
				FileUploadDAO fileUploadDAO = DAOFactory.getInstance().getDAOImpl(FileUploadDAO.class);
				fileUploadDAO.create(fileUpload);
            }  
        }
	}

	public static void moveFile(File oldFile, File newDirectory, String newName){
		File newFile = new File(newDirectory, newName);
		oldFile.renameTo(newFile);
	}

	public static void main(String[] args){
		fileList();
	}
	
	@Override
	public String list2() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String list() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
