package com.ron.controller.converter.pdfConverter;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.log4j.Logger;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeException;
import org.artofsolving.jodconverter.office.OfficeManager;

import com.ron.utils.FileUtils;

public class OpenOfficePDFConverter implements PDFConverter{
	
	public static Logger log = Logger.getLogger(OpenOfficePDFConverter.class);
	
	private static  OfficeManager officeManager;
	private static String OFFICE_HOME = "//opt//openoffice.org3";
	private static int port[] = {8100};

	

	public  void convert2PDF(String inputFile, String pdfFile) throws OfficeException {
		
		if(inputFile.endsWith(".txt")){
			String odtFile = FileUtils.getFilePrefix(inputFile)+".odt";
			if(new File(odtFile).exists()){
				System.out.println("odt文件已存在！");
				inputFile = odtFile;
			}else{
				try {
					FileUtils.copyFile(inputFile,odtFile);
					inputFile = odtFile;
				} catch (FileNotFoundException e) {
					System.out.println("文档不存在！");
					e.printStackTrace();
				}
			}
		}
		
		log.info("Starting Service...");
		startService();
		log.info("进行文档转换转换:" + inputFile + " --> " + pdfFile);
        OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
        File pptfile = new File(inputFile);
        
        if(pptfile.exists()){
        	converter.convert(new File(inputFile),new File(pdfFile));
        }else{
        	log.info("no pptfile found");
        }
		stopService();
	}


	public void convert2PDF(String inputFile) {
		String pdfFile = FileUtils.getFilePrefix(inputFile)+".pdf";
		convert2PDF(inputFile,pdfFile);
	}
	
	public static void startService(){
		DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration();
        try {
            log.info("准备启动服务....");
            configuration.setOfficeHome(OFFICE_HOME);//设置OpenOffice.org安装目录
            configuration.setPortNumbers(port); //设置转换端口，默认为8100
            configuration.setTaskExecutionTimeout(1000 * 60 * 5L);//设置任务执行超时为5分钟
            configuration.setTaskQueueTimeout(1000 * 60 * 60 * 24L);//设置任务队列超时为24小时
         
            officeManager = configuration.buildOfficeManager();
            officeManager.start();	//启动服务
            log.info("office转换服务启动成功!");
        } catch (Exception ce) {
          log.error("office转换服务启动失败!详细信息:" , ce);
        }
	}
	
	public static void stopService(){
	      log.info("关闭office转换服务....");
	      if (officeManager != null) {
	          officeManager.stop();
	      }
	      log.info("关闭office转换成功!");
	}
}
