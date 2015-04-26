package com.ron.model;

public class FileUpload {
	String id;
	String filename;
	String uuid;
	String uploadtime;
	String uploaduser;
	
	public FileUpload(String filename, String uuid,
			String uploaduser) {
		this.filename = filename;
		this.uuid = uuid;
		this.uploaduser = uploaduser;
	}
	
	public FileUpload() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getUploadtime() {
		return uploadtime;
	}
	public void setUploadtime(String uploadtime) {
		this.uploadtime = uploadtime;
	}
	public String getUploaduser() {
		return uploaduser;
	}
	public void setUploaduser(String uploaduser) {
		this.uploaduser = uploaduser;
	}
	
}
