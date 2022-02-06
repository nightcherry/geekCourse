package com.example.iocdemo;

import com.example.iocdemo.bean.AbstractBean;
import com.example.iocdemo.config.IocDemoConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class IocDemoApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(IocDemoApplication.class, args);
		String[] beans = context.getBeanNamesForType(AbstractBean.class);
		for(String bean : beans){
				System.out.println(bean);
		}
	}

}
