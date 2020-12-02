package com.alibo.week07.anno;

import java.lang.annotation.*;

/**
 * author: alibobo
 * create: 2020-12-02 11:46
 * description:
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)// 运行时有效
@Documented
public @interface ReadOnly {

    // 自定义注解
}
