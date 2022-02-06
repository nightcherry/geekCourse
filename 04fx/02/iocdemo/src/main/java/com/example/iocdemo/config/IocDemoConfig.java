package com.example.iocdemo.config;

import com.example.iocdemo.bean.ConfiguredBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan(basePackages = { "com.example.iocdemo.bean", "com.example.iocdemo.controller" })
@ImportResource("classpath*:bean.xml")
public class IocDemoConfig {

    @Bean
    public ConfiguredBean configuredBean(){
        return new ConfiguredBean();
    }
}
