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
	
	
	public Object process(HttpServletRequest req, HttpServletResponse resp, String module, String action, String username){
		this.req = req;
		this.resp = resp;
		this.username = username;
		
		Object object = null;
		
		try {
			log.info(module + ":" + action);
			object = this.getClass().getMethod(action, NO_ARGS_CLASS).invoke(this, NO_ARGS_OBJECT);
		} catch (IllegalAccessException e) {
			log.error("error: ", e);
		} catch (IllegalArgumentException e) {
			log.error("error: ", e);
		} catch (InvocationTargetException e) {
			log.error("error: ", e);
		} catch (NoSuchMethodException e) {
			log.error("error: ", e);
		} catch (SecurityException e) {
			log.error("error: ", e);
		}
		
		return object;
		
	}
	
//	public abstract int myadd();
//	public abstract String pr();
	public abstract String list2();

	public abstract String list();
//	public abstract String delete();
//	public abstract String create();
	
}
