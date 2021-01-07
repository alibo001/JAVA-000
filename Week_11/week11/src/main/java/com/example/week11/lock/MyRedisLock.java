package com.example.week11.lock;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.time.Duration;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * author: alibobo
 **/
public class MyRedisLock {

    private final StringRedisTemplate redisTemplate;

    // 锁的key
    private final String lockKey = "DISTRIBUT_LOCK_KEY";
    // 锁的value
    private String lockValue;
    // 解锁脚本
    private final String unlockScript = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then return redis.call(\"del\",KEYS[1]) else return 0 end";

    public MyRedisLock(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     *  获取锁
     * @param second 超时时间
     * @return 是否成功
     */
    public boolean getLock(int second) {
        // 生成随机value
        lockValue = UUID.randomUUID().toString();
        // 请求锁超时时间
        long timeout = TimeUnit.SECONDS.toNanos(second);
        // 系统当前时间，纳秒
        long nowTime = System.nanoTime();
        while ((System.nanoTime() - nowTime) < timeout) {
            if (redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, Duration.ofSeconds(30))) {
                // 上锁成功结束请求
                return true;
            }
            try {
                // 每次请求等待10毫秒
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     *  阻塞获取锁,直到成功
     * @return 是否成功
     */
    public boolean getLock() {
        // 生成随机value
        lockValue = UUID.randomUUID().toString();
        while (true) {
            if (redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, Duration.ofSeconds(30))) {
                // 上锁成功结束请求
                return true;
            }
            try {
                // 每次请求等待10毫秒
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 释放锁
     *
     * @return 是否成功
     */
    public Boolean unlock() {
        Long execute = redisTemplate.execute(new DefaultRedisScript<>(unlockScript,Long.class), Collections.singletonList(lockKey), lockValue);
        return execute != null && execute.intValue() == 1;
    }

}
