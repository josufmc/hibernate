package com.gurenet.HibernateTest.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="countrylanguage")
public class CountryLanguage  implements Serializable{
	
	private static final long serialVersionUID = -2229471888742168169L;

	@EmbeddedId
    private CountryLanguagePK countryLanguagePK;
	
	@Column(name="IsOfficial")
	private String isOfficial;

	public CountryLanguagePK getCountryLanguagePK() {
		return countryLanguagePK;
	}

	public void setCountryLanguagePK(CountryLanguagePK countryLanguagePK) {
		this.countryLanguagePK = countryLanguagePK;
	}

	public String getIsOfficial() {
		return isOfficial;
	}

	public void setIsOfficial(String isOfficial) {
		this.isOfficial = isOfficial;
	}
	
	
	
}
