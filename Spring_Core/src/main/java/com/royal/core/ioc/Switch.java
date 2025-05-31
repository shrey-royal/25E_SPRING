package com.royal.core.ioc;

public class Switch {
	private Light light;
	
	public Switch(Light light) {
		this.light = light;
	}
	
	public void operate() {
		light.turnOn();
	}
}
