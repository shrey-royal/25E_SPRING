package com.royal.core.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class Employee {
	private int id;
	private String name;
	private double salary;
	
	public Employee(int id, String name, double salary) {
		super();
		this.id = id;
		this.name = name;
		this.salary = salary;
	}

	public int getId() {
		return id;
	}

	@Autowired
	public void setId(@Value("22") int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	@Autowired
	public void setName(@Value("mohak") String name) {
		this.name = name;
	}

	public double getSalary() {
		return salary;
	}

	@Autowired
	public void setSalary(@Value("6786786") double salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", salary=" + salary + "]";
	}
}
