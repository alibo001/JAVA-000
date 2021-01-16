**作业**

> 1. 搭建 ActiveMQ 服务，基于 JMS，写代码分别实现对于 queue 和 topic 的消息生产和消费，代码提交到 github。

​		代码见demo --> week13

> 2. 搭建一个 3 节点 Kafka 集群，测试功能和性能；实现 spring kafka 下对 kafka 集群的操作，将代码提交到 github。

​		代码见demo --> week13

集群性能测试

```java
// 生产者
.\bin\windows\kafka-producer-perf-test.bat --topic test00 --num-records 100000 --record-size 1000 --throughput 2000 --producer-props bootstrap.servers=localhost:9091,localhost:9092,localhost:9093

9976 records sent, 1993.6 records/sec (1.90 MB/sec), 3.3 ms avg latency, 284.0 ms max latency.
10032 records sent, 2003.2 records/sec (1.91 MB/sec), 1.4 ms avg latency, 17.0 ms max latency.
10032 records sent, 2000.8 records/sec (1.91 MB/sec), 1.1 ms avg latency, 4.0 ms max latency.
10006 records sent, 2000.8 records/sec (1.91 MB/sec), 1.2 ms avg latency, 13.0 ms max latency.
10030 records sent, 2000.4 records/sec (1.91 MB/sec), 1.3 ms avg latency, 12.0 ms max latency.
10026 records sent, 2000.8 records/sec (1.91 MB/sec), 0.9 ms avg latency, 10.0 ms max latency.
10028 records sent, 2002.0 records/sec (1.91 MB/sec), 1.4 ms avg latency, 24.0 ms max latency.
10005 records sent, 2000.2 records/sec (1.91 MB/sec), 1.1 ms avg latency, 14.0 ms max latency.
10027 records sent, 2001.4 records/sec (1.91 MB/sec), 0.8 ms avg latency, 11.0 ms max latency.
100000 records sent, 1999.320231 records/sec (1.91 MB/sec), 1.38 ms avg latency, 284.00 ms max latency, 1 ms 50th, 3 ms 95th, 7 ms 99th, 32 ms 99.9th.

9976 records sent, 1994.8 records/sec (1.90 MB/sec), 3.2 ms avg latency, 289.0 ms max latency.
10027 records sent, 2000.2 records/sec (1.91 MB/sec), 2.5 ms avg latency, 150.0 ms max latency.
10033 records sent, 2001.4 records/sec (1.91 MB/sec), 1.4 ms avg latency, 38.0 ms max latency.
10004 records sent, 2000.4 records/sec (1.91 MB/sec), 0.9 ms avg latency, 13.0 ms max latency.
10017 records sent, 2001.0 records/sec (1.91 MB/sec), 1.2 ms avg latency, 5.0 ms max latency.
10005 records sent, 2001.0 records/sec (1.91 MB/sec), 1.5 ms avg latency, 7.0 ms max latency.
10019 records sent, 2001.8 records/sec (1.91 MB/sec), 1.3 ms avg latency, 18.0 ms max latency.
10012 records sent, 2000.8 records/sec (1.91 MB/sec), 1.3 ms avg latency, 12.0 ms max latency.
10013 records sent, 2001.0 records/sec (1.91 MB/sec), 1.5 ms avg latency, 65.0 ms max latency.
100000 records sent, 1998.960541 records/sec (1.91 MB/sec), 1.60 ms avg latency, 289.00 ms max latency, 1 ms 50th, 3 ms 95th, 13 ms 99th, 47 ms 99.9th.

    
    
    
    
// 消费者
.\bin\windows\kafka-consumer-perf-test.bat --bootstrap-server lolocalhost:9091,localhost:9092,localhost:9093 --topic test00 --fetch-size 1048576 --messages 100000
    
start.time, end.time, data.consumed.in.MB, MB.sec, data.consumed.in.nMsg, nMsg.sec, rebalance.time.ms, fetch.time.ms, fetch.MB.sec, fetch.nMsg.sec
2021-01-16 22:36:30:724, 2021-01-16 22:36:31:294, 95.4199, 167.4033, 100057, 175538.5965, 1610807791022, -1610807790452, -0.0000, -0.0001

start.time, end.time, data.consumed.in.MB, MB.sec, data.consumed.in.nMsg, nMsg.sec, rebalance.time.ms, fetch.time.ms, fetch.MB.sec, fetch.nMsg.sec
2021-01-16 22:37:44:229, 2021-01-16 22:37:44:826, 95.4123, 159.8195, 100049, 167586.2647, 1610807864523, -1610807863926, -0.0000, -0.0001
```

