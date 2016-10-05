package com.gurenet.HibernateTest.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="country")
public class Country {
	
	@Id
	@Column(name="Code")
	private String code;
	
	@Column(name="Name")
	private String name;
	
	@OneToMany(mappedBy="id")
	private Set<City> cities;
	
	@OneToOne
	@JoinColumn(name = "Capital")
	private City capital;
	
	@OneToMany(mappedBy="countryCode")
	private Set<CountryLanguage> countryLanguages;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<City> getCities() {
		return cities;
	}

	public void setCities(Set<City> cities) {
		this.cities = cities;
	}

	public City getCapital() {
		return capital;
	}

	public void setCapital(City capital) {
		this.capital = capital;
	}

	public Set<CountryLanguage> getCountryLanguages() {
		return countryLanguages;
	}

	public void setCountryLanguages(Set<CountryLanguage> countryLanguages) {
		this.countryLanguages = countryLanguages;
	}
}
