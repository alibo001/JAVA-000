
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Test {

    public static void main(String[] args) throws Exception {
        method1();
        method2();
    }

    /**
     * 方法一: 读取处理过的文件,修正字节后,写入新的文件,
     * 然后再加载新的文件
     */
    private static void method1() throws Exception {
        // 读取被处理过的 hello.xlass
        try (FileInputStream fileIn = new FileInputStream("本地路径/Hello.xlass");
             FileOutputStream fileOut = new FileOutputStream("本地路径/newHello.class")) {
            // 读取字节,并修复,写入到另一个 newHello.class
            int i;
            while ((i = fileIn.read()) != -1) {
                i ^= -1;
                fileOut.write(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    /**
     * 方法二: 把处理后的文件一次性读入,修复所有字节,
     * 直接使用所有字节
     */
    private static void method2() throws Exception {
        // 读取被处理过的 hello.xlass,并修复
        byte[] allBytes = Files.readAllBytes(Paths.get("本地路径/Hello.xlass"));
        for (int i = 0; i < allBytes.length; i++) {
            allBytes[i] ^= -1;
        }
        // 使用自定义类加载器,放入修复后的字节,并调用方法
        MyClassLoader myClassLoader = new MyClassLoader();
        // 设置字节
        myClassLoader.setData(allBytes);
        // 加载Hello
        Class<?> hello = myClassLoader.findClass("Hello");
        Object o = hello.newInstance();
        // 获取方法,并执行
        Method helloMethod = hello.getMethod("hello");
        helloMethod.invoke(o);
    }

}
