package com.sample.app.demo.util;

import java.util.ArrayList;
import java.util.List;

import com.sample.app.demo.model.Address;
import com.sample.app.demo.model.Beneficiary;
import com.sample.app.demo.model.Code;
import com.sample.app.demo.model.Coverage;
import com.sample.app.demo.model.FHIR;
import com.sample.app.demo.model.Identifier;
import com.sample.app.demo.model.Name;
import com.sample.app.demo.model.Patient;
import com.sample.app.demo.model.Payor;
import com.sample.app.demo.model.Period;
import com.sample.app.demo.model.Type;

public class FHIRUtil {

	private static final String ORGANIZATION = "Organization";
	private static final String BCBSNC = "BCBSNC";
	private static final String ORGANIZATION_IDENTIFIER = "Organization Identifier";
	private static final String XX = "XX";
	private static final String PATIENT = "Patient";
	private static final String POSTAL = "postal";
	private static final String CODE_SYSTEM = "http://terminology.hl7.org/CodeSystem/v2-0203";
	private static final String MB = "MB";
	private static final String IDENTIFIER_SYSTEM = "https://fhir.primetherapeutics.com/data/sid/BCBSNC";

	public FHIR constructFHIR() {

	
	String memberId="";
	List<Identifier> identifiers= new ArrayList<>();		
	Identifier identifier=new Identifier();
	identifier.setValue(memberId);
	identifier.setSystem(IDENTIFIER_SYSTEM);
	Type type=new Type();
	List<Code> codes = new ArrayList<Code>();
	Code code = new Code();
	code.setSystem(CODE_SYSTEM);
	code.setCode(MB);
	code.setDisplay("Member Number");
	codes.add(code);
	type.setCoding(codes);
	identifier.setType(type);
	identifiers.add(identifier);
	
	String line1="";
	String line2="";
	String postalCode="";	
	String birthDate="";
	String gender="";
	String city = "";
	String uniqueId="MEMBERID.STARTDATE";//memberId+.startDate
	String family="lastName";//lastName
	String given="firstName";//firstName
	String firstName="Test";
	String text= "";
	FHIR fhir= new FHIR();
	Patient patient= constructPatient(identifiers, line1, line2, postalCode, birthDate, gender, city, uniqueId, family, firstName, text);
	fhir.setPatient(patient);
	
	String start="";
	String end="";
	Coverage coverage= new Coverage();
	coverage.setIdentifier(identifiers);
	Period period= new Period();
	period.setStart(start);
	period.setEnd(end);
	coverage.setPeriod(period);
	Payor payor = new Payor();
	List<Identifier> payorIdentifiers= new ArrayList<>();		
	Identifier payorIdentifier=new Identifier();
	payorIdentifier.setValue(BCBSNC);
	payorIdentifier.setSystem(IDENTIFIER_SYSTEM);
	Type payorIdentifierType = constructType(CODE_SYSTEM,XX,ORGANIZATION_IDENTIFIER);
	payorIdentifier.setType(payorIdentifierType);
	payorIdentifiers.add(identifier);
	payor.setType(ORGANIZATION);
	Beneficiary beneficiary= new Beneficiary();
	beneficiary.setReference(uniqueId);
	beneficiary.setType(PATIENT);
	
	
	
	
	
	
	
	
	
	
		
		
	return null;
	}

	private Type constructType(String system,String code, String display) {
		Type type=new Type();
		List<Code> codes = new ArrayList<Code>();
		Code codeObject = new Code();
		codeObject.setSystem(system);
		codeObject.setCode(code);
		codeObject.setDisplay(display);
		codes.add(codeObject);
		type.setCoding(codes);
		return type;
	}

	private Patient constructPatient(List<Identifier> identifiers, String line1, String line2, String postalCode,
			String birthDate, String gender, String city, String uniqueId, String family, String firstName,
			String text) {
		Patient patient= new Patient();
		patient.setBirthDate(birthDate);
		patient.setGender(gender);
		patient.setId(uniqueId);
		patient.setIdentifier(identifiers);
		Name name= new Name();
		name.setFamily(family);
		name.setText(text);
		List<String> names=new ArrayList<String>();
		names.add(firstName);
		name.setGiven(names);
		patient.setName(name);
		List<Address> addresses=new ArrayList<Address>();
		Address address= new Address();
		address.setCity(city);
		List<String> lines = new ArrayList<String>();
		lines.add(line1);
		lines.add(line2);
		address.setLine(lines);
		address.setPostalCode(postalCode);
		address.setType(POSTAL);
		addresses.add(address);
		patient.setResourceType(PATIENT);
		return patient;
	}
	
	
}
