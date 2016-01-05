package com.ron.model;

public class PlayListFilePIC {
	String p_username;
	String p_type;
	String p_uuid;
	String c_uuid;
	String p_filename;
	String c_filename;
	String p_duration;
	String c_duration;	
	
	@Override
	public String toString() {
		return "PlayListFilePIC [p_username=" + p_username + ", p_type="
				+ p_type + ", p_uuid=" + p_uuid + ", c_uuid=" + c_uuid
				+ ", p_filename=" + p_filename + ", c_filename=" + c_filename
				+ ", p_duration=" + p_duration + ", c_duration=" + c_duration
				+ "]";
	}
	public String getP_username() {
		return p_username;
	}
	public void setP_username(String p_username) {
		this.p_username = p_username;
	}
	public String getP_type() {
		return p_type;
	}
	public void setP_type(String p_type) {
		this.p_type = p_type;
	}
	public String getP_uuid() {
		return p_uuid;
	}
	public void setP_uuid(String p_uuid) {
		this.p_uuid = p_uuid;
	}
	public String getC_uuid() {
		return c_uuid;
	}
	public void setC_uuid(String c_uuid) {
		this.c_uuid = c_uuid;
	}
	public String getP_filename() {
		return p_filename;
	}
	public void setP_filename(String p_filename) {
		this.p_filename = p_filename;
	}
	public String getC_filename() {
		return c_filename;
	}
	public void setC_filename(String c_filename) {
		this.c_filename = c_filename;
	}
	public String getP_duration() {
		return p_duration;
	}
	public void setP_duration(String p_duration) {
		this.p_duration = p_duration;
	}
	public String getC_duration() {
		return c_duration;
	}
	public void setC_duration(String c_duration) {
		this.c_duration = c_duration;
	}
	
	
}
