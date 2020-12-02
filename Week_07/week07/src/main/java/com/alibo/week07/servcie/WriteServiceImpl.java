package com.alibo.week07.servcie;

import com.alibo.week07.config.DynamicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * author: alibobo
 * create: 2020-12-02 16:50
 * description: 写
 **/
@Service
public class WriteServiceImpl implements IWriteService{

    // @Autowired
    // private DataSource dataSource; // 主库数据源

    @Autowired
    private DynamicDataSource dynamicDataSource;

    @Override
    public Object write(String... data) {
        // 默认数据源, 主库
        DataSource dataSource = dynamicDataSource.getResolvedDefaultDataSource();
        System.out.println(dataSource);// HikariPool-1
        QueryRunner qr = new QueryRunner(dataSource);
        List<Object[]> params = new ArrayList<>();
        for (String datum : data) {
            Object[] oa = {datum};
            params.add(oa);
        }
        int[] batch;
        try {
            batch = qr.batch("insert into t1 (id) values (?)", params.toArray(new Object[0][0]));
        } catch (SQLException e) {
            e.printStackTrace();
            return "主库插入失败:" + e.getMessage();
        }
        return "主库插入成功,插入条数:"+batch.length;
    }
}
