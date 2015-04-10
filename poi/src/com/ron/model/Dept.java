package com.ron.model;


public class Dept {
	String id;
	String text;
	String cls;
	String qtip;
	boolean leaf;
	
	public Dept(String id, String text, String cls, boolean leaf) {
		super();
		this.id = id;
		this.text = text;
		this.cls = cls;
		this.leaf = leaf;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getCls() {
		return cls;
	}
	public void setCls(String cls) {
		this.cls = cls;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	
	
	
}
