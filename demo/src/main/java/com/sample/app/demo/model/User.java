package com.sample.app.demo.model;

import org.springframework.data.annotation.Id;
import javax.persistence.*;

@Entity
public class User {
	
	@Id
	private String id;
	private String name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public User() {
	}
	
	

}
