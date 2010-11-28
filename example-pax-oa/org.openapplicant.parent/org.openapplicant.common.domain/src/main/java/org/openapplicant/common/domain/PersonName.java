package org.openapplicant.common.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Embeddable
public class PersonName {
	
	@Column(name="given_name")
	private String givenName;
	
	@Column(name="middle_name")
	private String middleName;
	
	@Column(name="family_name")
	private String familyName;

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
					.append("givenName", givenName)
					.append("middleName", middleName)
					.append("familyName", familyName)
					.toString();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17,37)
						.append(givenName)
						.append(middleName)
						.append(familyName)
						.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof PersonName)) {
			return false;
		}
		PersonName rhs = (PersonName)other;
		return new EqualsBuilder()
					.append(givenName, rhs.getGivenName())
					.append(middleName, rhs.getMiddleName())
					.append(familyName, rhs.getFamilyName())
					.isEquals();
	}
}
