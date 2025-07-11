package com.royal.core;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.royal.core.ioc.AppConfig;
import com.royal.core.ioc.Employee;
import com.royal.core.ioc.Switch;

@SpringBootApplication
public class SpringCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCoreApplication.class, args);
		/*
		// BeansFactory
		BeanFactory factory = new ClassPathXmlApplicationContext("beans.xml");
		Employee e = (Employee) factory.getBean("employee");
		System.out.println(e);
		
		e.setId(1089);
		e.setName("Mohak");
		e.setSalary(20);
		
		System.out.println(e);
		
		Switch switch1 = (Switch) factory.getBean("switch");
		switch1.operate();
		*/
		
		// ApplicationContext
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		
		Switch sw = context.getBean(Switch.class);
		sw.operate();
		
		System.out.println(context.getBean("emp1").toString());
		
	}

}
