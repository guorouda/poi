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
	
	public abstract String list();
	public abstract int myadd();
	public abstract String pr();
	
	public void process(HttpServletRequest req, HttpServletResponse resp, String module, String action){
		this.req = req;
		this.resp = resp;
		String result;
		
		try {
			result = this.getClass().getMethod(action, NO_ARGS_CLASS).invoke(this, NO_ARGS_OBJECT).toString();
			log.info(result);
		} catch (IllegalAccessException e) {
			log.info("IllegalAccessException ");
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
		
	}

}
