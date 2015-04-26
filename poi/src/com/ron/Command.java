package com.ron;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public abstract class Command {
	
	public static Logger log = Logger.getLogger(Command.class);
	
	private static Class[] NO_ARGS_CLASS = new Class[0];
	private static Object[] NO_ARGS_OBJECT = new Object[0];
	
	protected HttpServletRequest req;
	protected HttpServletResponse resp;
	protected String username;
	
	public abstract String list();
	public abstract String list2();
//	public abstract int myadd();
//	public abstract String pr();
	
	public Object process(HttpServletRequest req, HttpServletResponse resp, String module, String action, String username){
		this.req = req;
		this.resp = resp;
		this.username = username;
		
		Object object = null;
		
		try {
			object = this.getClass().getMethod(action, NO_ARGS_CLASS).invoke(this, NO_ARGS_OBJECT);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		
		return object;
		
	}

}
