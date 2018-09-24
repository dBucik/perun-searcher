package cz.metacentrum.perunsearch.persistence.models.entities;

import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.PerunEntity;

import java.util.List;

public class User extends PerunEntity {

	private String firstName;
	private String lastName;
	private String middleName;
	private String titleBefore;
	private String titleAfter;
	private boolean serviceAcc;
	private boolean sponsoredAcc;

	public User(Long id, List<PerunAttribute> attributes, String firstName, String lastName, String middleName, String titleBefore, String titleAfter, boolean serviceAcc, boolean sponsoredAcc) {
		super(id, attributes);
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.titleBefore = titleBefore;
		this.titleAfter = titleAfter;
		this.serviceAcc = serviceAcc;
		this.sponsoredAcc = sponsoredAcc;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getTitleBefore() {
		return titleBefore;
	}

	public void setTitleBefore(String titleBefore) {
		this.titleBefore = titleBefore;
	}

	public String getTitleAfter() {
		return titleAfter;
	}

	public void setTitleAfter(String titleAfter) {
		this.titleAfter = titleAfter;
	}

	public boolean isServiceAcc() {
		return serviceAcc;
	}

	public void setServiceAcc(boolean serviceAcc) {
		this.serviceAcc = serviceAcc;
	}

	public boolean isSponsoredAcc() {
		return sponsoredAcc;
	}

	public void setSponsoredAcc(boolean sponsoredAcc) {
		this.sponsoredAcc = sponsoredAcc;
	}
}
