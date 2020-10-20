package com.alibo.demo.aaa;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

public class Test {

    public static void main(String[] args) throws Exception {

        // ===== Part 1 ===========================

        // 读取被处理过的 hello.xlass
        try (FileInputStream fileIn = new FileInputStream("本地路径/Hello.xlass");
             FileOutputStream fileOut = new FileOutputStream("本地路径/newHello.class")){
            // 读取字节,并修复,写入到另一个 newHello.class
            int i;
            while ((i = fileIn.read()) != -1) {
                i = 255 -i;
                fileOut.write(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ===== Part 2 ===========================

        // 使用自定义类加载器,加载修复后的文件,并调用方法
        MyClassLoader myClassLoader = new MyClassLoader();
        // 设置字节加载路径为修复后的文件
        myClassLoader.setFilePath("本地路径/newHello.class");
        // 加载Hello
        Class<?> hello = myClassLoader.findClass("Hello");
        Object o = hello.newInstance();
        // 获取方法,并执行
        Method helloMethod = hello.getMethod("hello");
        helloMethod.invoke(o);
    }
}
