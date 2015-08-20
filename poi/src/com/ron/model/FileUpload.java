package com.ron.model;

import java.sql.Timestamp;

public class FileUpload {
	String id;
	String filename;
	String shortFilename;
	String uuid;
	int count;
	String url;
	Timestamp uploadtime;
	String uploaduser;
	String type;
	long duration;
	
	public FileUpload(String filename, String uuid,
			String uploaduser, String type, long duration) {
		this.filename = filename;
		this.uuid = uuid;
		this.uploaduser = uploaduser;
		this.type = type;
		this.duration = duration;
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
	public String getShortFilename() {
		return shortFilename;
	}
	public void setShortFilename(String shortFilename) {
		this.shortFilename = shortFilename;
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
	public Timestamp getUploadtime() {
		return uploadtime;
	}
	public void setUploadtime(Timestamp timestamp) {
		this.uploadtime = timestamp;
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
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}

}
