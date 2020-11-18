作业:

1. 写代码实现 Spring Bean 的装配，方式越多越好（XML、Annotation 都可以）, 提交到 Github。

   代码在demo项目中DemoApplicationTests类中,使用的test类进行的测试

2.  给前面课程提供的 Student/Klass/School 实现自动配置和 Starter。

   自定义starter:  custom-spring-boot-starter代码如上,

   调用starter的方法也在demo项目的DemoApplicationTests类中

作业1,2 测试代码

```java
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
```



3.  研究一下 JDBC 接口和数据库连接池，掌握它们的设计和用法：
   1）使用 JDBC 原生接口，实现数据库的增删改查操作。
   2）使用事务，PrepareStatement 方式，批处理方式，改进上述操作。
   3）配置 Hikari 连接池，改进上述操作。提交代码到 Github。

代码在demo项目中com.alibo.demo.jdbc包中三种方式