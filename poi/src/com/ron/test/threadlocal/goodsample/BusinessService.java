package com.ron.test.threadlocal.goodsample;


public class BusinessService {
	
    public void businessMethod(String threadname) {
        Context context = ExecutionContext.get();
        System.out.println("Thread[" + threadname + "]: " + context.getTransactionId());
        }
}
