package com.example.iocdemo.controller;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IocDemoController implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @RequestMapping("/online")
    public String sendQueue(@RequestBody String str) {
        String[] beans = applicationContext.getBeanDefinitionNames();
        return "success";
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
