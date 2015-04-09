package com.ron.test.threadlocal.goodsample;


public class ExecutionContext {
    public static final ThreadLocal userThreadLocal = new ThreadLocal();
    
    public static void set(Context user) {
    	userThreadLocal.set(user);
    }
     
    public static void unset() {
    	userThreadLocal.remove();
    }
     
    public static Context get() {
    	return (Context)userThreadLocal.get();
    }
}