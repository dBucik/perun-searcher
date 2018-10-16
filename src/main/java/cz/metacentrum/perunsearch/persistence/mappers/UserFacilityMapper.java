package cz.metacentrum.perunsearch.persistence.mappers;

import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.relations.UserFacility;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class UserFacilityMapper implements RowMapper<UserFacility> {

	@Override
	public UserFacility mapRow(ResultSet resultSet, int i) throws SQLException {
		Long userId = resultSet.getLong("user_id");
		Long facilityId = resultSet.getLong("facility_id");

		Map<String, PerunAttribute> attributes = MappersUtils.getAttributes(resultSet);
		Long foreignId = MappersUtils.getForeignId(resultSet);

		return new UserFacility(userId, facilityId, attributes, foreignId);
	}
}
