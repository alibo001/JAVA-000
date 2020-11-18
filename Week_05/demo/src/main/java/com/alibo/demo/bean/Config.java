package com.alibo.demo.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean(name = "student2")
    public Student getStudent() {
        return new Student();
    }
}
