package com.ron.test.threadlocal.goodsample;

import java.util.Random;

public class ThreadLocalDemo extends Thread {

    public static void main(String[] args) {
//            Thread threadOne = new ThreadLocalDemo();
//            threadOne.start();
//            Thread threadTwo = new ThreadLocalDemo();
//            threadTwo.start();
            for(int i = 0; i < 20; i++){
            	new ThreadLocalDemo().start();
            }
    }
    
    @Override
    public void run() {
            // 线程
            Context context = new Context();
            Random random = new Random();
            int age = random.nextInt(100);
            context.setTransactionId(String.valueOf(age));
            
            System.out.println("set thread ["+getName()+"] contextid to " + String.valueOf(age));
            // 在ThreadLocal中设置context
            ExecutionContext.set(context);
            /* note that we are not explicitly passing the transaction id */
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) 
              {  
                e.printStackTrace();
            }
            
            new BusinessService().businessMethod(getName());
            ExecutionContext.unset();
            }
    }
