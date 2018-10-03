package cz.metacentrum.perunsearch.persistence.models.entities;

import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import org.json.JSONObject;

import java.util.Map;

public class Service extends PerunEntity {

	private String name;
	private String description;
	private Integer delay;
	private Integer recurrence;
	private boolean enabled;
	private String script;

	public Service(Long id, String name, String description, Integer delay, Integer recurrence, Boolean enabled,
				   String script, Map<String, PerunAttribute> attributes, Long foreignId) {
		super(id, attributes, foreignId);
		this.name = name;
		this.description = description;
		this.delay = delay;
		this.recurrence = recurrence;
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

	public Integer getRecurrence() {
		return recurrence;
	}

	public void setRecurrence(Integer recurrence) {
		this.recurrence = recurrence;
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	@Override
	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		json.put("id", this.getId());
		json.put("name", this.name);
		json.put("description", this.description);
		json.put("delay", this.delay);
		json.put("recurrence", this.recurrence);
		json.put("enabled", this.enabled);
		json.put("script", this.script);
		json.put("attributes", this.attributesToJson());

		return json;
	}
}
