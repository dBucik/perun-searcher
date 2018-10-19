package cz.metacentrum.perunsearch.persistence.mappers;

import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.basic.Resource;
import org.json.JSONObject;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class ResourceMapper implements RowMapper<Resource> {

	@Override
	public Resource mapRow(ResultSet resultSet, int i) throws SQLException {
		JSONObject entityJson = new JSONObject(resultSet.getString("entity"));

		Integer id = entityJson.getInt("id");
		Integer facilityId = MappersUtils.getInteger(entityJson, "facility_id");
		String name = MappersUtils.getString(entityJson,"name");
		String dsc = MappersUtils.getString(entityJson,"dsc");
		Integer voId = MappersUtils.getInteger(entityJson, "vo_id");

		Map<String, PerunAttribute> attributes = MappersUtils.getAttributes(resultSet);
		Integer foreignId = MappersUtils.getForeignId(resultSet);

		return new Resource(id, facilityId, name, dsc, voId, attributes, foreignId);
	}
}
