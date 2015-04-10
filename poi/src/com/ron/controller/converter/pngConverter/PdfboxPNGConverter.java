package com.ron.controller.converter.pngConverter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import com.ron.utils.FileUtils;

public class PdfboxPNGConverter {
	
	public static Logger log = Logger.getLogger(PdfboxPNGConverter.class);
	
	 public static void converter2png(String inputFile) {
		
		 try {
	         String destinationDir  = FileUtils.getFilePrefix(inputFile) + "Dir" + File.separator; 
         
	         File pdfFile = new File(inputFile);
	         File destinationFile = new File(destinationDir);
	         if (!destinationFile.exists()) {
	             destinationFile.mkdir();
	             log.info("Folder Created -> "+ destinationFile.getAbsolutePath());
	         }
	         
	         if (pdfFile.exists()) {
	             log.info("Images copied to Folder: "+ destinationFile.getName());             
	             PDDocument document = PDDocument.load(inputFile);
	             List<PDPage> list = document.getDocumentCatalog().getAllPages();
	             log.info("Total files to be converted -> "+ list.size());
         
	             String fileName = pdfFile.getName().replace(".pdf", "");             
	             log.info(list.size());
	             int pageNumber = 1;
	             for (PDPage page : list) {
	            	 log.info(pageNumber);
	                 BufferedImage image = page.convertToImage();
	                 log.info(destinationDir + fileName +"_"+ pageNumber +".png");
	                 File outputfile = new File(destinationDir + fileName +"_"+ pageNumber +".png");
	                 log.info("Image Created -> "+ outputfile.getName());
	                 ImageIO.write(image, "png", outputfile);
	                 pageNumber++;
	             }
	             document.close();
	             log.info("Converted Images are saved at -> "+ destinationFile.getAbsolutePath());
	         } else {
	             log.info(pdfFile.getName() +" File not exists");
	         }
         
	     } catch (Exception e) {
	    	 log.error("error: ", e);
	     }
	}
	 
	 public static void main(String[] args){
		 PDDocument doc;
		    try {
		        String input  = "e:/73.pdf";
		        doc = PDDocument.load(input);
		        List<PDPage> list = doc.getDocumentCatalog().getAllPages();
		        int i = 0;
		        for(PDPage page:list){
			        BufferedImage image = page.convertToImage();
			        File outputfile = new File("e:" + File.separator + "image" + i + ".png");
			        ImageIO.write(image, "png", outputfile);
			        i++;
		        }
		        doc.close();

		    } catch (IOException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    }
	 }
}
