package com.alibo.week07.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * author: alibobo
 * create: 2020-12-01 22:13
 * description:数据源头配置
 **/
@Configuration
public class DataSourceConfig {


    //主数据源 master 本地3307
    @Primary
    @Bean(name = "master")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }


    //从库1 本地3308
    @Bean(name = "slave1")
    @ConfigurationProperties(prefix = "spring.datasource.slave1")
    public DataSource slaveDataSource1() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    //从库2 本地3309
    @Bean(name = "slave2")
    @ConfigurationProperties(prefix = "spring.datasource.slave2")
    public DataSource slaveDataSource2() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    // 动态数据源管理
    @Bean(name = "dynamicDataSource")
    public DynamicDataSource dataSource(@Qualifier("master") DataSource master,
                                        @Qualifier("slave1") DataSource slave1,
                                        @Qualifier("slave2") DataSource slave2) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("master", master);
        targetDataSources.put("slave1", slave1);
        targetDataSources.put("slave2", slave2);
        return new DynamicDataSource(master, targetDataSources);

    }
}