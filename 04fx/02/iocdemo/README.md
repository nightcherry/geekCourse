# 项目说明

### 写代码实现 Spring Bean 的装配
本项目使用spring-boot，模拟了
* 使用xml配置装配bean
* 使用配置类中@Bean装配bean
* 使用@Component（也可以使用@Service, @Controller等）装配bean

其他说明：
* 传统spring加载xml时往往使用
```java
    ApplicationContext ac = new ClassPathXmlApplicationContext(“applicationContext.xml”);
    XMLBean xmlBean = (XMLBean)ac.getBean("XMLBean");
```
的方式装配，这里由于使用了springboot, 在configure类中又调用了@ImportResource标签来加载xml配置
* 使用配置类中@Bean装配beans时，可以使用@ConditionOnXX的标签实现条件加载，如果返回null则不加载
* 如果使用@Component注解方式进行自动注册，需要该类在包扫描路径下。@SpringBootApplication会自动扫描入口类的根路径及其子包，如果需要调整可使用scanBasePackages属性，或者在配置类中使用@ComponentScan
