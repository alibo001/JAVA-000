package com.alibo.week07.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * author: alibobo
 * create: 2020-12-02 00:28
 * description:
 **/
public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<String> DATASOURCE_CONTEXT = new ThreadLocal<>();

    public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources) {
        // 默认数据源, 主库
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }


    @Override
    protected Object determineCurrentLookupKey() {
        return getDateSoureType();
    }

    /**
     * 设置数据源
     */
    public void setDateSoureType(String dateSoureType) {
        DATASOURCE_CONTEXT.set(dateSoureType);
    }

    /**
     * 获得数据源
     */
    public String getDateSoureType() {
        return DATASOURCE_CONTEXT.get();
    }

}
