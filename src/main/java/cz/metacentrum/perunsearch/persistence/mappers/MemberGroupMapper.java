package cz.metacentrum.perunsearch.persistence.mappers;

import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.MemberGroup;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class MemberGroupMapper implements RowMapper<MemberGroup> {

	@Override
	public MemberGroup mapRow(ResultSet resultSet, int i) throws SQLException {
		Long memberId = resultSet.getLong("member_id");
		Long groupId = resultSet.getLong("group_id");

		Map<String, PerunAttribute> attributes = MappersUtils.getAttributes(resultSet);
		Long foreignId = MappersUtils.getForeignId(resultSet);

		return new MemberGroup(null, memberId, groupId, attributes, foreignId);
	}
}
