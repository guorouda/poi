package com.ron.test.threadlocal;

import com.ron.test.threadlocal.Test1.Index;

public class ThreadLocalTest1 {
    public static Index num = new Index();
        //创建一个Index类型的本地变量 
    public static ThreadLocal<Index> local = new ThreadLocal<Index>() {
        @Override
        protected Index initialValue() {
            return new Index();
        }
    };
}