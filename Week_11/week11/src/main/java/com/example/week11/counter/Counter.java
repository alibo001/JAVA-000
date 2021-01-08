package com.example.week11.counter;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;

/**
 * @Author: alibobo
 * @Date: 2021/01/08
 * @Description: 全局计数
 **/
public class Counter {

    // 减数脚本
    private final String decr = "if redis.call('get',KEYS[1]) == '0' then return -1 else return redis.call('decr',KEYS[1]) end";

    private final StringRedisTemplate redisTemplate;

    public static final String COUNT_KEY = "count_key";

    public Counter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Long decr() {
        return redisTemplate.execute(new DefaultRedisScript<>(decr,Long.class), Collections.singletonList(COUNT_KEY));
    }
}
