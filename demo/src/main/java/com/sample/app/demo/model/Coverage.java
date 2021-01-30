package com.sample.app.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Coverage {

	private List<Identifier> identifier;
	
	private Period period;
	
	private List<Payor> payor;
	
	private Beneficiary beneficiary;
	
	private Type type;
	
	@JsonSetter("class")
	@JsonProperty("class")
	private List<Identifier> classes;
	
	private String resourseType;
	
	private String id;
	
	private String status;
	
	public List<Identifier> getIdentifier() {
		return identifier;
	}
	public void setIdentifier(List<Identifier> identifier) {
		this.identifier = identifier;
	}
	public Period getPeriod() {
		return period;
	}
	public void setPeriod(Period period) {
		this.period = period;
	}
	public List<Payor> getPayor() {
		return payor;
	}
	public void setPayor(List<Payor> payor) {
		this.payor = payor;
	}
	public Beneficiary getBeneficiary() {
		return beneficiary;
	}
	public void setBeneficiary(Beneficiary beneficiary) {
		this.beneficiary = beneficiary;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	
	@JsonProperty("class")
	public List<Identifier> getClasses() {
		return classes;
	}
	public void setClasses(List<Identifier> classes) {
		this.classes = classes;
	}
	public String getResourseType() {
		return resourseType;
	}
	public void setResourseType(String resourseType) {
		this.resourseType = resourseType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
	
}
