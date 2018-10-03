package cz.metacentrum.perunsearch.persistence.models.entities;

import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import org.json.JSONObject;

import java.util.Map;

public class User extends PerunEntity {

	private String firstName;
	private String lastName;
	private String middleName;
	private String titleBefore;
	private String titleAfter;
	private Boolean serviceAcc;
	private Boolean sponsoredAcc;

	public User(Long id, String firstName, String middleName, String lastName, String titleBefore, String titleAfter,
				Boolean serviceAcc, Boolean sponsoredAcc, Map<String, PerunAttribute> attributes, Long foreignId) {
		super(id, attributes, foreignId);
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

	public Boolean isServiceAcc() {
		return serviceAcc;
	}

	public void setServiceAcc(Boolean serviceAcc) {
		this.serviceAcc = serviceAcc;
	}

	public Boolean isSponsoredAcc() {
		return sponsoredAcc;
	}

	public void setSponsoredAcc(Boolean sponsoredAcc) {
		this.sponsoredAcc = sponsoredAcc;
	}

	@Override
	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		json.put("id", this.getId());
		json.put("first_name", this.firstName);
		json.put("middle_name", this.middleName);
		json.put("last_name", this.lastName);
		json.put("title_before", this.titleBefore);
		json.put("title_after", this.titleAfter);
		json.put("service", this.serviceAcc);
		json.put("sponsored", this.sponsoredAcc);
		json.put("attributes", this.attributesToJson());

		return json;
	}
}
