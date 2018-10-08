package cz.metacentrum.perunsearch.persistence.mappers;

import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.GroupResource;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class MemberResourceMapper implements RowMapper<GroupResource> {

	@Override
	public GroupResource mapRow(ResultSet resultSet, int i) throws SQLException {
		Long memberId = resultSet.getLong("member_id");
		Long resourceId = resultSet.getLong("resource_id");

		Map<String, PerunAttribute> attributes = MappersUtils.getAttributes(resultSet);
		Long foreignId = MappersUtils.getForeignId(resultSet);

		return new GroupResource(null, memberId, resourceId, attributes, foreignId);
	}
}
