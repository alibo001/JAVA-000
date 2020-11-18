package com.alibo.demo;

import com.example.starter.vo.ISchool;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private ApplicationContext context;

    @Test
    void contextLoads() {

        // 测试bean装配
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
            // 1: @Component方式
            // 2: 启动类加@Import(Student.class)方式   // 方式1和方式2冲突, 如果都配置,则使用了方式1
            // 3: @Configuration +bean方式
            // 4: xml方式

        }

        // =================================

        // 测试自定义starter
        ISchool school = context.getBean(ISchool.class);
        school.ding();

    }

}
