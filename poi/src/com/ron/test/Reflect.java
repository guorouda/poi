package com.ron.test;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Method;
import java.lang.reflect.Array;

public class Reflect {
 
    public static void main(String[] args) throws Exception {
        Class<?> clazz = Single.class;
        Single single = (Single) clazz.newInstance();
        List<String> list = new ArrayList<String>(){
            private static final long serialVersionUID = 1L;
            {
                add("fuck");
                add("ni");
                add("mei");
            }
        };
         
        //获取method两种方式， 在method中 数组的的空间大小是可以随便写的不一定使用0
        /* 1 */
        Method method = clazz.getDeclaredMethod("method", Array.newInstance(Object.class, 0).getClass());
        /* 2 */
        method = clazz.getDeclaredMethod("method", (new Object[0]).getClass());
 
 
         
        //初始化参数
        /* 1 */
        Object objs = Array.newInstance(Object.class, 2);
        Array.set(objs, 0, list);
        Array.set(objs, 1, "23");
        method.invoke(single, objs);
         
        /* 2 */
        Object[] objects = {1, "fuck", list};
        method.invoke(single, new Object[]{ objects });
    }
}
 
class Single {
    public void method(Object... objs) {
        System.out.println(Arrays.deepToString(objs));
    }
}