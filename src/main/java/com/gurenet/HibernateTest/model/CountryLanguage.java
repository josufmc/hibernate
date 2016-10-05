package com.gurenet.HibernateTest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="countrylanguage")
public class CountryLanguage {
	
	@Id
	@Column(name="CountryCode")
	private String countryCode;
	
	@Column(name="Language")
	private String language;

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	
}
