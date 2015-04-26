package com.ron.model;

public class PlayListFile {
	String listid;
	int seq;
	String filename;
	String uuid;
	
	public PlayListFile(String listid, int seq, String filename, String uuid) {
		this.listid = listid;
		this.seq = seq;
		this.filename = filename;
		this.uuid = uuid;
	}

	public PlayListFile() {
	}
	
	public String getListid() {
		return listid;
	}
	public void setListid(String listid) {
		this.listid = listid;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
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

}
