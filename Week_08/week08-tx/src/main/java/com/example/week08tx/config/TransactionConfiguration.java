package com.example.week08tx.config;

import org.apache.shardingsphere.infra.database.type.dialect.MySQLDatabaseType;
import org.apache.shardingsphere.transaction.core.ResourceDataSource;
import org.apache.shardingsphere.transaction.xa.XAShardingTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Spring boot tx configuration.
 */
@Configuration
public class TransactionConfiguration {

    @Autowired
    private DataSource dataSource;

    @Bean
    public XAShardingTransactionManager manager() {
        XAShardingTransactionManager manager = new XAShardingTransactionManager();
        List<ResourceDataSource> sourceList = new ArrayList<>();
        System.out.println("=======");
        System.out.println(dataSource);
        sourceList.add(new ResourceDataSource("datasource",dataSource));
        manager.init(new MySQLDatabaseType(),sourceList);
        return manager;
    }


}
