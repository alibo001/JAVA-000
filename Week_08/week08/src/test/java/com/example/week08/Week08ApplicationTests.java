package com.example.week08;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootTest
class Week08ApplicationTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void table() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS t_order");
        // jdbcTemplate.execute("CREATE TABLE t_order (id int,name varchar(20))");
    }

    @Test
    void testInsert() {
        // 测试插入数据
        List<Object[]> params = new ArrayList<>();
        // 每个库16张表,添加32条数据,查看是否每个表都有1条数据
        for (int i = 0; i < 32; i++) {
            Object[] param = new Object[2];
            param[0] = i;
            param[1] = "orderName"+i;
            params.add(param);
        }
        for (Object[] param : params) {
            System.out.println(Arrays.asList(param));
        }
        jdbcTemplate.batchUpdate("insert into t_order(id, name) values (?,?)",params);
    }

    @Test
    void testUpdate() {
        //  修改数据
        jdbcTemplate.execute("update t_order set name = 'newName' where id = 17;");
    }
    @Test
    void testQuery() {
        //  查询数据
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from t_order where id = 7");
        System.out.println(maps);

    }
    @Test
    void testDelete() {
        //  删除数据
        // jdbcTemplate.execute("delete from t_order where id = 3");
        jdbcTemplate.execute("delete from t_order");
    }

}
