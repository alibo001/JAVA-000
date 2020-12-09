package com.example.week08tx;

import com.example.week08tx.service.IOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Week08TxApplicationTests {

    @Autowired
    private IOrderService orderService;

    // 创建表
    @Test
    void table() {
        orderService.tableMethod();
    }

    // 清除数据
    @Test
    void clean() {
        orderService.cleanData();
    }

    // 测试插入数据成功
    @Test
    void testInsert() {
        orderService.insert();
    }

    // 测试插入数据失败
    @Test
    void testRollback() {
        orderService.testRollback();
    }

    // 查询
    @Test
    void testQuery() {
        orderService.query();
    }

    // 测试删除数据失败
    @Test
    void testDelete() {
        orderService.delete();
    }


}
