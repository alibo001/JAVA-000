package com.alibo.demo.bean;

import org.springframework.stereotype.Component;

@Component("student1")
public class Student {

    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
