package cz.metacentrum.perunsearch.persistence.models.inputEntities;

public class InputUtils {

	public static String getQuery(boolean isSimple, String select, String join, String entityTable) {
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT to_json(ent) AS entity");
		if (select != null) {
			queryString.append(", ").append(select);
		}

		if (! isSimple) {
			queryString.append(", attributes.data AS attributes");
		}
		queryString.append(" FROM ").append(entityTable).append(" ent");
		if (join != null) {
			queryString.append(' ').append(join);
		}

		return queryString.toString();
	}

	public static String getQueryForRelation(boolean isSimple, String select, String join, String relationTable) {
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT ").append(select);
		if (!isSimple) {
			queryString.append(", attributes.data AS attributes");
		}
		queryString.append(" FROM ").append(relationTable).append(" ent");
		if (join != null) {
			queryString.append(' ').append(join);
		}

		return queryString.toString();

	}
}
