package com.ron.view;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;

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
		fileList(SystemGlobals.getDefaultsValue("ftproot")); 
	}
	
	private static void fileList(String filePath){
    	final String downloadpath = SystemGlobals.getDefaultsValue("application.path") + File.separator + "download" + File.separator + "temp";
    	final String warpath = FileUtils.getParentPath(SystemGlobals.getDefaultsValue("application.path"));
    	
    	Path target = null;    	
		
		Path base = Paths.get(filePath);
		if(Files.isDirectory(base)){
			try {
				DirectoryStream<Path> paths = Files.newDirectoryStream(base);  
					for(Path p : paths){  
		                log.info(p.toString());

						String filename = p.getFileName().toString();
						switch(FileUtils.getFileExtension(filename).toLowerCase()){
							case "zip":
								target = Paths.get(downloadpath + File.separator + p.getFileName());
								break;
							case "war":
								target = Paths.get(warpath + File.separator + p.getFileName());
								break;
							default:
								log.info("unknow file type!");
						}   
							
						if(Files.move(p, target, StandardCopyOption.REPLACE_EXISTING)==target){
							log.info(filename + " 读取文件成功！！！！！");
							if(FileUtils.getFileExtension(filename).equalsIgnoreCase("zip")){
								unzip(downloadpath + File.separator + filename, downloadpath);
							}
						}else{
							log.info(filename + " 读取文件失败");
						}
					} 
						
			} catch (IOException e) {
				e.printStackTrace();
				
			}  
			
		}
		
		
	}
	
	
	
	private static void fileList3(String filePath){
    	final String path = SystemGlobals.getDefaultsValue("application.path") + File.separator + "download" + File.separator + "temp" + File.separator + "1.gif";
    	final String warpath = FileUtils.getParentPath(SystemGlobals.getDefaultsValue("application.path"));
    	
    	Path warsource = Paths.get(warpath);    	
		
		Path base = Paths.get(filePath);
		if(Files.isDirectory(base)){
			try {
				Files.walkFileTree(base, new FileVisitor<Object>(){

					@Override
					public FileVisitResult preVisitDirectory(Object dir,
							BasicFileAttributes attrs) throws IOException {
						log.info(dir);
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFile(Object file,
							BasicFileAttributes attrs) throws IOException {
						log.info(file);
						Path source = Paths.get((String)file);
						if(Files.exists(source)){
							log.info(".....................................");
						}else{
							log.info("#######################################");
						}
						Path target = Paths.get("c:\\tmp\\1111.gif");
						Files.copy(source, target);
						log.info(path);
						
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFileFailed(Object file,
							IOException exc) throws IOException {
						exc.printStackTrace();
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult postVisitDirectory(Object dir,
							IOException exc) throws IOException {
						return FileVisitResult.CONTINUE;
					}
					
				});
			} catch (IOException e) {
				e.printStackTrace();
				
			}  

			
			
		}
		
	 /*    File file = new File(filePath);  
	     if (file.isDirectory()) {  
	         File[] files = file.listFiles();  
	         for (int i = 0; i < files.length; i++) {  
	            String filename = files[i].getName();  
	           	File newFile =  new File(path + File.separator + filename);
				if(!files[i].renameTo(newFile)){
					log.info(filename + " 读取文件失败，:-(.....");
				}else{
					log.info(filename + " 读取文件成功！！！！！");
					switch(FileUtils.getFileExtension(filename).toLowerCase()){
						case "zip":
							unzip(path + File.separator + filename, path);
							break;
						case "war":
							
							break;
					}
						
				}
	         }  
	     }*/
		
	}
	
	private static void unzip(String zipFilepath, String destDir){
		
		while(true){
			try{
				Project proj = new Project();
				Expand expand = new Expand();
				expand.setProject(proj);
				expand.setTaskType("unzip");
				expand.setTaskName("unzip");
				expand.setEncoding("utf-8");
		
				expand.setSrc(new File(zipFilepath));
				expand.setDest(new File(destDir));
				expand.execute();
				break;
			}catch(BuildException e){
				log.info("expand error!");
			}
			
			try{
				Thread.sleep(5000);
			}catch(InterruptedException e){
				
			}
		}
			
	}
	
	//XXX
	public static void fileList1(String filePath){
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
//               	log.info(targetPath);
//         	    	File targetFile = new File(targetPath);
               	
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
	
	public static void moveFile(File oldFile, File newFile){
		oldFile.renameTo(newFile);
	}

	public String list() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args){
		Path base = Paths.get("C:\\apache-tomcat-7.0.35\\wtpwebapps\\poi\\download\\temp\\1.bak.gif");
		Path base2 = Paths.get("c:\\tmp\\aaa.gif");
		Path base3 = Paths.get("C:\\inetpub\\ftproot\\xxfb\\12.gif");
		
		try {
			Files.move(base3, base);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	
}
