## 作业

> **1** 设计对前面的订单表数据进行水平分库分表，拆分 2 个库，每个库 16 张表。并在新结构在演示常见的增删改查操作。代码、sql 和配置文件，上传到 Github。

**shardingsphere-proxy 的config-sharding.yaml配置如下:**

说明: 使用了两个数据库实例,端口分别为3317和3318, 每个库有16张表,分别从t_order-0到t_order_15. 以表的id来分片

t_order表的分片算法表达式: algorithm-expression: t_order_${ id.intdiv(2) % 16}  这样使每个表都能有数据.

测试增删改查代码在week08/src/test里.

```yaml
schemaName: db_mall
dataSourceCommon:
 username: root
 password:
 connectionTimeoutMilliseconds: 30000
 idleTimeoutMilliseconds: 60000
 maxLifetimeMilliseconds: 1800000
 maxPoolSize: 50
 minPoolSize: 1
 maintenanceIntervalMilliseconds: 30000
dataSources:
 ds0:
   url: jdbc:mysql://127.0.0.1:3317/db_mall?serverTimezone=UTC&useSSL=false
 ds1:
   url: jdbc:mysql://127.0.0.1:3318/db_mall?serverTimezone=UTC&useSSL=false

rules:
- !SHARDING
 tables:
   t_order:
     actualDataNodes: ds${0..1}.t_order_${0..15}
     tableStrategy:
       standard:
         shardingColumn: id
         shardingAlgorithmName: t_order_inline
     keyGenerateStrategy:
       column: id
       keyGeneratorName: snowflake
 bindingTables:
   - t_order
 defaultDatabaseStrategy:
   standard:
     shardingColumn: id
     shardingAlgorithmName: database_inline
 defaultTableStrategy:
   none:
 
 shardingAlgorithms:
   database_inline:
     type: INLINE
     props:
       algorithm-expression: ds${id % 2}
   t_order_inline:
     type: INLINE
     props:
       algorithm-expression: t_order_${id.intdiv(2) % 16} #不能使用{id/2 % 16}
 
 keyGenerators:
   snowflake:
     type: SNOWFLAKE
     props:
       worker-id: 123

```



>**2** 基于 hmily TCC 或 ShardingSphere 的 Atomikos XA 实现一个简单的分布式事务应用 demo（二选一），
>
>提交到 Github。

作业使用的是ShardingSphere  Atomikos XA

代码在week08-tx 项目里