package com.royal.core.ioc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.royal.core.ioc")
public class AppConfig {
	
	@Bean
	public Light light() {
		return new Light();
	}
	
	@Bean
	public Switch switchBean() {
		return new Switch(light());
	}
	
	@Bean(name="emp")
	public Employee employeeBean1() {
		return new Employee(101, "Kathan", 300000000);
	}
	
	@Bean(name="emp1")
	public Employee employeeBean2() {
		return new Employee(102, "Mohak", 230);
	}
}
