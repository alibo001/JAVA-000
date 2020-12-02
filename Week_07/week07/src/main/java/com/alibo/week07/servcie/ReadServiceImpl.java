package com.alibo.week07.servcie;

import com.alibo.week07.anno.ReadOnly;
import com.alibo.week07.config.DynamicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * author: alibobo
 * create: 2020-12-02 16:47
 * description: 读取service,使用从库
 **/
@Service
public class ReadServiceImpl implements IReadServcie {

    // @Qualifier("slave")
    // @Autowired
    // private DataSource dataSource; // slave从库

    @Autowired
    private DynamicDataSource dynamicDataSource;

    @ReadOnly
    @Override
    public Object read() {
        // 获取从库连接
        try( Connection connection = dynamicDataSource.getConnection();) {
            // System.out.println(dataSource); //HikariPool-2
            // QueryRunner qr = new QueryRunner(dataSource);
            QueryRunner qr = new QueryRunner();
            List<Map<String, Object>> mapList = new ArrayList<>();
            try {
                mapList = qr.query(connection,"select * from t2", new MapListHandler());
            } catch (SQLException e) {
                e.printStackTrace();
                return "查询出错::" + e.getMessage();
            }
            for (Map<String, Object> map : mapList) {
                System.out.println(map);
            }
            return "查询出条数::" + mapList.size();
        } catch (SQLException e) {
            e.printStackTrace();
            return e;
        }

    }

}
