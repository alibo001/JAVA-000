作业:

1. 按自己设计的表结构，插入 100 万订单模拟数据，测试不同方式的插入效率

   1)使用原生jdbc PreparedStatement批量插入方式.  2次分别用时: 100.2968739s  100.9423909s   平均100秒左右

   2) 使用mysql function, 2次分别用时: 58秒  61秒  平均一分钟左右

   3)使用mysql procedure, 非常慢,  1000条就需要一分钟

   4)使用数据库工具导入数据,同样非常慢

   我的测试中mysql function速度最快,其次是PreparedStatement批量插入方式

   

2. 读写分离 - 动态切换数据源版本 1.0

   代码见 week07, 

   项目中使用了dbutils执行SQL,没有使用mybatis

   

3. 读写分离 - 数据库框架版本 2.0

   代码见week07-sharding,  测试代码在test包里Week07ShardingApplicationTests
