package com.ron.model;

public class User {
	String userid;
	String id;
	String name;
	String password;
	String depid;
	String right;
	String inuse;
	
	public User(String id, String name, String depid, String right, String inuse) {
		this.id = id;
		this.name = name;
		this.depid = depid;
		this.right = right;
		this.inuse = inuse;
	}
	
	public User(String userid, String name, String depid, String right) {
		this.userid = userid;
		this.name = name;
		this.depid = depid;
		this.right = right;
	}
	
	public User(String id) {
		this.id = id;
	}
	
	public User() {
	}

	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDepid() {
		return depid;
	}
	public void setDepid(String depid) {
		this.depid = depid;
	}
	public String getRight() {
		return right;
	}
	public void setRight(String right) {
		this.right = right;
	}
	public String getInuse() {
		return inuse;
	}
	public void setInuse(String inuse) {
		this.inuse = inuse;
	}

	
}
