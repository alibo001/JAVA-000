package com.alibo.demo.jdbc;

import java.sql.*;

/**
 * 使用事务，PrepareStatement 方式，批处理方式，改进上述操作。
 **/
public class MyJdbc2 {

    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/sys?serverTimezone=UTC";
        String username = "root";
        String pass = "root";
        Connection connection = null;
        PreparedStatement p = null;
        try {
            connection = DriverManager.getConnection(url, username, pass);
            // 自己管理事务
            connection.setAutoCommit(false);
            p = connection.prepareStatement("INSERT INTO clouddb01.dept VALUES(?,?,?)");
            // 插入2条
            for (int i = 0; i < 2; i++) {
                // 3列数据
                for (int j = 0; j < 3; j++) {
                    p.setObject(j + 1,"V"+i+j);
                }
                p.addBatch();
            }
            // 或者用下列方式
            // p.addBatch("INSERT INTO clouddb01.dept VALUES('7','aa','bb')");
            // p.addBatch("INSERT INTO clouddb01.dept VALUES('8','aa','bb')");
            // p.addBatch("INSERT INTO clouddb01.dept VALUES('9','aa','bb')");
            int[] ints = p.executeBatch();
            System.out.println(ints.length);
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            rollback(connection);
        }finally {
            close(connection);
        }
    }

    // 关闭
    private static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    // 回滚
    private static void rollback(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

}
