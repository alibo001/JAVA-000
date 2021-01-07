package com.example.week11.controller;

import com.example.week11.lock.MyRedisLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * author: alibobo
 **/
@Slf4j
@RestController
public class TestLockController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    ExecutorService executorService = Executors.newFixedThreadPool(16);

    /**
     *  测试分布式锁
     * @return
     */
    @RequestMapping("test")
    public Object test() {
        // 模拟20个客户端
        for (int i = 0; i < 20; i++) {
            executorService.submit(()->{
                MyRedisLock lock = new MyRedisLock(redisTemplate);
                // 不阻塞获取锁, 等待1秒
                boolean isLock = lock.getLock(1);
                // 阻塞获取锁
                // boolean isLock = lock.getLock();
                if (isLock) {
                    //获取锁成功,减库存
                    try {
                        log.info("获取锁成功...处理业务...");
                        // 处理业务
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        // 释放锁
                        lock.unlock();
                    }
                    return "处理成功";
                } else {
                    log.info("获取锁失败...");
                    // 获取失败,返回
                    return "获取锁失败";
                }
            });
        }
        return "";
    }

    /**
     *  模拟减库存
     * @return
     */
    @RequestMapping("decrementNum")
    public void decrementNum() {
        String s = "if redis.call('get',KEYS[1]) == '0' then return 0 else return redis.call('decr',KEYS[1]) end";
        // 初始设置总数为100
        redisTemplate.opsForValue().set("num","100");

        // 模拟20个客户端抢购
        for (int i = 0; i < 120; i++) {
            executorService.submit(()->{
                Long num = redisTemplate.execute(new DefaultRedisScript<>(s,Long.class), Collections.singletonList("num"));
                if (num != null && num != 0) {
                    log.info("抢购成功");
                } else {
                    log.info("抢购失败");
                }
            });
        }
        System.out.println("ok");

    }

}
