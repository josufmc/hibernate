package com.gurenet.HibernateTest.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class CountryLanguagePK implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6764088904472411903L;
	
	private String language;
	private String countryCode;
	
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
		
}
