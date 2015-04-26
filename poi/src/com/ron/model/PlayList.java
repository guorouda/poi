package com.ron.model;

public class PlayList {
	
	String id;
	String listname;
	String listid;
	String createtime;
	String createuser;
	
	public PlayList() {
	}
	
	public PlayList(String listname, String createtime,
			String createuser) {
		this.listname = listname;
		this.createtime = createtime;
		this.createuser = createuser;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getListname() {
		return listname;
	}
	public void setListname(String listname) {
		this.listname = listname;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getCreateuser() {
		return createuser;
	}
	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}
	
}
