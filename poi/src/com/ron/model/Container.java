package com.ron.model;

public class Container {

	String id;
	String filename;
	String uuid;
	int duration;
	
	public Container(String filename, String uuid, int duration) {
		this.filename = filename;
		this.uuid = uuid;
		this.duration = duration;
	}
	
	public Container() {
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
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	
}
