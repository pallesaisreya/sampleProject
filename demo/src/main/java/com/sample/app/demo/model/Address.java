package com.sample.app.demo.model;

import java.util.List;

public class Address {
	
	private String city;
	
	private List<String> line;
	
	private String postalCode;
	
	private String state;
	
	private String type;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public List<String> getLine() {
		return line;
	}

	public void setLine(List<String> line) {
		this.line = line;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	

}
