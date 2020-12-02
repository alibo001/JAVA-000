package com.alibo.week07sharding;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@SpringBootTest
class Week07ShardingApplicationTests {


    @Autowired
    private DataSource dataSource;

    @Test
    public void test() throws SQLException {
        QueryRunner qr = new QueryRunner(dataSource);

        // 插入数据测试,t1在主库里,可以插入成功
        Object insert = qr.insert(connection,"insert into t1 (id) values (22)", new ScalarHandler<>());

        // 查询数据测试,t2 在从库里,可以查询出来
        List<Map<String, Object>> mapList = qr.query("select * from t2", new MapListHandler());
        mapList.forEach(System.out::println);

        // =========开启手动事务,插入后再查询======

        Connection connection =null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            // 插入数据
            Object insert1 = qr.insert(connection,"insert into t1 (id) values (22)", new ScalarHandler<>());
            // 再次查询,报错, 主库里没有t2,说明此次查询使用的是主库.
            mapList = qr.query(connection,"select * from t2", new MapListHandler());
            mapList.forEach(System.out::println);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                connection.rollback();
            }
        }
    }



}
