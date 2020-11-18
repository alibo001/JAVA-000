package com.alibo.demo.jdbc;

import java.sql.*;

/**
 * 使用 JDBC 原生接口，实现数据库的增删改查操作。
 **/
public class MyJdbc1 {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url ="jdbc:mysql://localhost:3306/sys?serverTimezone=UTC";
        String username = "root";
        String pass = "root";
        Connection connection = DriverManager.getConnection(url, username, pass);
        // 自动事务
        connection.setAutoCommit(true);
        PreparedStatement p = connection.prepareStatement("select * from clouddb01.dept");
        ResultSet resultSet = p.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        System.out.println(columnCount);
        while (resultSet.next()) {
            for (int i = 1; i < columnCount + 1; i++) {
                String string = resultSet.getString(i);
                System.out.println(string);
            }
        }
        p.close();
        connection.close();
    }
}
