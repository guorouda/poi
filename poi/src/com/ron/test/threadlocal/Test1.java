package com.ron.test.threadlocal;

public class Test1{
    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[5];
        for (int j = 0; j < 5; j++) {
            threads[j] = new Thread(new Runnable() {
                @Override
                public void run() {
                    //取出当前线程的本地变量，并累加1000次
                    Index index = ThreadLocalTest1.local.get();
                    for (int i = 0; i < 1000; i++) {                                          
                        index.increase();
                    }
                    System.out.println(Thread.currentThread().getName() + " : "+ index.num);
 
                }
            }, "Thread-" + j);
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }
 
    static class Index {
        int num;
 
        public void increase() {
            num++;
        }
    }
}