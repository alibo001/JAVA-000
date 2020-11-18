package com.example.starter.pro;

import org.springframework.boot.context.properties.ConfigurationProperties;

// @Component  // 没有扫描这个注解,所以这种方式不能用
@ConfigurationProperties(prefix = "custom.student")
public class CustomStarterProperties {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
