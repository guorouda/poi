package com.ron.controller;

import java.io.File;
import java.io.IOException;

import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;


public class MyTransferFile implements Runnable{
	
	private String ftp_user;
	private String ftp_password;
	private String ftp_host;
	private int ftp_port;
	
	private String filePath;
	
	public MyTransferFile(String ftp_user, String ftp_password,
			String ftp_host, int ftp_port, String filePath) {
		this.ftp_user = ftp_user;
		this.ftp_password = ftp_password;
		this.ftp_host = ftp_host;
		this.ftp_port = ftp_port;
		this.filePath = filePath;
	}

	@Override
	public void run() {
		
		FTPClient client = null;
		
		try {
			client = new FTPClient();
			client.connect(ftp_host, ftp_port);
			client.login(ftp_user, ftp_password);
			client.changeDirectory("/");
			client.setPassive(true);
			client.upload(new File(filePath), new MyTransferFileListener());
			
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPIllegalReplyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPDataTransferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPAbortedException e) {
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
	}	

}
