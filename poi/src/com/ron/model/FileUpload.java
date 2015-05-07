package com.ron.model;

public class FileUpload {
	String id;
	String filename;
	String uuid;
	int count;
	String url;
	String uploadtime;
	String uploaduser;
	String type;
	
	public FileUpload(String filename, String uuid,
			String uploaduser, String type) {
		this.filename = filename;
		this.uuid = uuid;
		this.uploaduser = uploaduser;
		this.type = type;
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
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
