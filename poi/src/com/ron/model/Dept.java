package com.ron.model;


public class Dept {
	String orgcode;
	String uporgcode;
	String orgjc;
	String cls;
	String qtip;
	boolean leaf;
	
	public String getOrgcode() {
		return orgcode;
	}
	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}
	public String getUporgcode() {
		return uporgcode;
	}
	public void setUporgcode(String uporgcode) {
		this.uporgcode = uporgcode;
	}
	public String getOrgjc() {
		return orgjc;
	}
	public void setOrgjc(String orgjc) {
		this.orgjc = orgjc;
	}
	public String getCls() {
		return cls;
	}
	public void setCls(String cls) {
		this.cls = cls;
	}
	public String getQtip() {
		return qtip;
	}
	public void setQtip(String qtip) {
		this.qtip = qtip;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	
}
