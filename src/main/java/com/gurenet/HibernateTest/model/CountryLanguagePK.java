package com.gurenet.HibernateTest.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class CountryLanguagePK implements Serializable{

	
	private static final long serialVersionUID = 6764088904472411903L;
	
	private String language;
	@ManyToOne
	@JoinColumn(name="CountryCode")
	private Country country;
	
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
		
}
