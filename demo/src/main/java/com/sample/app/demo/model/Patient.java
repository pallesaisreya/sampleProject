package com.sample.app.demo.model;

import java.util.List;

public class Patient {
	
	private List<Identifier> identifier;
	
	private String resourceType;
	
	private String id;
	
	private String gender;
	
	private String birthDate;
	
	private List<Address> address;
	
	private Name name;

	public List<Identifier> getIdentifier() {
		return identifier;
	}

	public void setIdentifier(List<Identifier> identifier) {
		this.identifier = identifier;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}
	
	
	
	
	

}
