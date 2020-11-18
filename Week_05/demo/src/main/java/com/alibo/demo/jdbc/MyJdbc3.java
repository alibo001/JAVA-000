package com.alibo.demo.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 配置 Hikari 连接池，改进上述操作。
 **/
public class MyJdbc3 {

    public static void main(String[] args){
        HikariConfig config = new HikariConfig("/hikari.properties");
        HikariDataSource ds = new HikariDataSource(config);
        try ( Connection conn = ds.getConnection();
              PreparedStatement preparedStatement = conn.prepareStatement("select * from clouddb01.dept")){
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            int columnCount = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i < columnCount + 1; i++) {
                    System.out.println(resultSet.getString(i));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


}
