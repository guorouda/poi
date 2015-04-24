package com.ron.model;

public class PNG {
	String id;
	String filename;
	String uuid;
	int duration;
	
	public PNG(String id, String filename, String uuid, int duration) {
		this.id = id;
		this.filename = filename;
		this.uuid = uuid;
		this.duration = duration;
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
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}

}
