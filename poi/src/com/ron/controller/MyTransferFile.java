package com.ron.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.ron.model.City;
import com.ron.model.FileUpload;

import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPConnector;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPFile;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import it.sauronsoftware.ftp4j.FTPListParseException;
import it.sauronsoftware.ftp4j.connectors.DirectConnector;


public class MyTransferFile implements Runnable{
	
	public static Logger log = Logger.getLogger(MyTransferFile.class);
	
	private City city;
	private FileUpload fileupload;
	private String filePath;
	
	public MyTransferFile(City city, FileUpload fileupload, String filePath) {
		this.city = city;
		this.fileupload = fileupload;
		this.filePath = filePath;
	}

	@Override
	public void run() {
		
		FTPClient client = null;
//		FTPFile[] files= null;
		boolean flag = false;
		
/*		try {
			FTPConnector ftpconnector = new DirectConnector();
			ftpconnector.setConnectionTimeout(20000);
			ftpconnector.setReadTimeout(30000);
			
			client = new FTPClient();
			client.setConnector(ftpconnector);
			client.connect(city.getIpaddress(), city.getPort());
			client.setAutoNoopTimeout(300);
			client.login(city.getUsername(), city.getPassword());
			
			files = client.list("/");
			for(FTPFile f:files){
				if(f.getName().equals("xxfb")){
					flag = true;
					if(f.getType() == 0){
						client.rename("xxfb", "xxfb.bak");
						client.createDirectory("xxfb");
					}
				}
			}
			if(!flag){
				client.createDirectory("xxfb");
			} 
			
			client.changeDirectory("/xxfb");
			client.setPassive(true);
			
		} catch (IllegalStateException e) {
			log.error(city.getName() + "ftp error: ", e);	
		} catch (IOException e) {
			log.error(city.getName() + "ftp error: ", e);	
		} catch (FTPIllegalReplyException e) {
			log.error(city.getName() + "ftp error: ", e);	
		} catch (FTPException e) {
			log.error(city.getName() + "ftp error: ", e);	
		}*/
		
		int count = 0;
		while(count < 5){
			count++;
			try {
				
				FTPConnector ftpconnector = new DirectConnector();
				ftpconnector.setConnectionTimeout(0);
				ftpconnector.setReadTimeout(0);
				
				client = new FTPClient();
				client.setConnector(ftpconnector);
				client.connect(city.getIpaddress(), city.getPort());
				client.setAutoNoopTimeout(300);
				client.login(city.getUsername(), city.getPassword());
				client.changeDirectory("/xxfb");
				client.setPassive(city.isPassivemode());
				
				client.upload(new File(filePath), new MyTransferFileListener(city, fileupload));
				
				Thread.sleep(10000);
				
				flag = true;
			} catch (IllegalStateException e) {
				log.error(city.getName() + "ftp error: ", e);	
			} catch (FileNotFoundException e) {
				log.error(city.getName() + "ftp error: ", e);	
			} catch (IOException e) {
				log.error(city.getName() + "ftp error: ", e);	
			} catch (FTPIllegalReplyException e) {
				log.error(city.getName() + "ftp error: ", e);	
			} catch (FTPException e) {
				log.error(city.getName() + "ftp error: ", e);	
			} catch (FTPDataTransferException e) {
				log.error(city.getName() + "ftp error: ", e);	
			} catch (FTPAbortedException e) {
				log.error(city.getName() + "ftp error: ", e);	
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(client!=null && client.isConnected()) {
				      try {
				          client.disconnect(true); 
				      } catch (Throwable t) {
				          t.printStackTrace();
				      }
				}
			}
			if(flag){
				break;
			}
		}
		
	}	
	
}
