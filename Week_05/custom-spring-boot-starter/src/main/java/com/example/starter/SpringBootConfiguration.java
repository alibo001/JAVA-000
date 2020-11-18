package com.example.starter;

import com.example.starter.pro.CustomStarterProperties;
import com.example.starter.vo.ISchool;
import com.example.starter.vo.Klass;
import com.example.starter.vo.School;
import com.example.starter.vo.Student;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
@ConditionalOnClass({Student.class, Klass.class, ISchool.class}) // 有这些类才配置
@ConditionalOnProperty(prefix = "custom.starter",name = "enable",havingValue = "true",matchIfMissing = true)
@EnableConfigurationProperties(CustomStarterProperties.class)
public class SpringBootConfiguration{

    // @Autowired   // @Component不可用,所以注入的方式不可用
    // private CustomStarterProperties config;

    private Student student;

    @Bean(name = "student100")
    public Student student(CustomStarterProperties config) {
        Student student = new Student(Integer.parseInt(config.getId()), config.getName());
        this.student = student;
        return student;
    }

    @Bean
    @ConditionalOnBean(Student.class)
    public Klass klass() {
        Klass klass = new Klass();
        klass.setStudents(Collections.singletonList(student));
        return klass;
    }

    @Bean
    @ConditionalOnBean({Student.class,Klass.class})
    public ISchool school() {
        return new School();
    }


}

