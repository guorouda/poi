package com.ron.model;

public class PlayListFile {
	String listid;
	int seq;
	String filename;
	String uuid;
	String id;
	String type;
	
	public PlayListFile(String listid, int seq, String filename, String uuid) {
		this.listid = listid;
		this.seq = seq;
		this.filename = filename;
		this.uuid = uuid;
	}

	public PlayListFile() {
	}
	
	public PlayListFile(String listid, int seq, String filename, String uuid,
			String id) {
		this.listid = listid;
		this.seq = seq;
		this.filename = filename;
		this.uuid = uuid;
		this.id = id;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
