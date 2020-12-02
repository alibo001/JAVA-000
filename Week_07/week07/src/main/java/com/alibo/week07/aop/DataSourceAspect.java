package com.alibo.week07.aop;

import com.alibo.week07.config.DynamicDataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * author: alibobo
 * create: 2020-12-02 12:43
 * description: 读取的方法使用从库, 并随机选择一个数据源
 **/
@Component
@Aspect
@Slf4j
public class DataSourceAspect {

    @Autowired
    private DynamicDataSource dynamicDataSource;

    // readOnly注解的方法都使用slave数据源
    @Before("@annotation(com.alibo.week07.anno.ReadOnly)")
    public void changeDataSource() {
        // 从库名称
        String[] slaveNames = {"slave1", "slave2"};
        // 随机选择一个从库
        String slave = slaveNames[new Random().nextInt(2)];
        dynamicDataSource.setDateSoureType(slave);
        log.info("ReadOnly....使用从库:{} ........",slave);
    }
}