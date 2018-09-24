package cz.metacentrum.perunsearch.persistence.models.entities;

import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.PerunEntity;

import java.util.List;

public class Service extends PerunEntity {

	private String name;
	private String description;
	private Integer delay;
	private Integer reccurrence;
	private boolean enabled;
	private String script;

	public Service(Long id, List<PerunAttribute> attributes, String name, String description, Integer delay, Integer reccurrence, boolean enabled, String script) {
		super(id, attributes);
		this.name = name;
		this.description = description;
		this.delay = delay;
		this.reccurrence = reccurrence;
		this.enabled = enabled;
		this.script = script;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getDelay() {
		return delay;
	}

	public void setDelay(Integer delay) {
		this.delay = delay;
	}

	public Integer getReccurrence() {
		return reccurrence;
	}

	public void setReccurrence(Integer reccurrence) {
		this.reccurrence = reccurrence;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}
}
