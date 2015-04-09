package com.ron.test.threadlocal;

class ThreadLocalTest {

    class Index {
        int num;
 
        public void increase() {
            num++;
        }
    }

    public static Index num = new ThreadLocalTest().new Index();
        //创建一个Index类型的本地变量 
    public static ThreadLocal<Index> local = new ThreadLocal<Index>() {
        @Override
        protected Index initialValue() {
            return new ThreadLocalTest().new Index();
        }
    };
}

public class Test3{
    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[5];
        for (int j = 0; j < 5; j++) {
            threads[j] = new Thread(new Runnable() {
                @Override
                public void run() {
                                        //取出当前线程的本地变量，并累加1000次
                    ThreadLocalTest.Index index = ThreadLocalTest.local.get();
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
 

}