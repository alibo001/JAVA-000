package com.example.week08tx.service;

import org.apache.shardingsphere.infra.database.type.DatabaseType;
import org.apache.shardingsphere.infra.database.type.dialect.MySQLDatabaseType;
import org.apache.shardingsphere.transaction.ShardingTransactionManagerEngine;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.apache.shardingsphere.transaction.core.TransactionTypeHolder;
import org.apache.shardingsphere.transaction.xa.XAShardingTransactionManager;
import org.apache.shardingsphere.transaction.xa.atomikos.manager.AtomikosTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * author: alibobo
 * create: 2020-12-09 11:27
 * description: 订单service
 **/
@Service(value = "service2")
public class OrderServiceImpl2 implements IOrderService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 表方法
    public void tableMethod() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS t_order");
        jdbcTemplate.execute("CREATE TABLE t_order (order_id int, order_name varchar(20), PRIMARY KEY (order_id))");
    }

    // 清除表中数据
    @Override
    @ShardingTransactionType(TransactionType.XA)
    public void cleanData() {
        jdbcTemplate.execute("truncate table t_order");
    }

    @Override
    public void insert() {

        jdbcTemplate.execute("INSERT INTO t_order (order_id, order_name) VALUES (?, ?)", (PreparedStatementCallback<TransactionType>) preparedStatement -> {
            doInsert(6, preparedStatement);
            return TransactionTypeHolder.get();
        });
    }

    @Autowired
    private XAShardingTransactionManager manager;
    @Override
    public void testRollback() throws SQLException {
        Connection connection = manager.getConnection("datasource");
        connection.setAutoCommit(false);

    }


    @Override
    @ShardingTransactionType(TransactionType.XA)
    public void delete() {
        jdbcTemplate.execute("delete from t_order where order_id = 0;");
        int i = 1/0;
        jdbcTemplate.execute("delete from t_order where order_id = 1;");
    }

    @Override
    public void query() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from t_order order by order_id");
        maps.forEach(System.out::println);
    }


    private void doInsert(final int count, final PreparedStatement preparedStatement) throws SQLException {
        for (int i = 0; i < count; i++) {
            preparedStatement.setObject(1, i);
            preparedStatement.setObject(2, "orderName"+i);
            preparedStatement.executeUpdate();
        }
    }


}
