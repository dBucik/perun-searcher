package cz.metacentrum.perunsearch.persistence.models.inputEntities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class InputUtils {

	private static Map<String, String> translationMap;

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

	public static String translateCoreAttribute(String key) {
		if (translationMap == null) {
			translationMap = readTranslationTable();
		}

		return translationMap.get(key);
	}

	private static Map<String, String> readTranslationTable() {
		Map<String, String> table = null;

		ClassLoader classLoader = InputUtils.class.getClassLoader();
		File file = new File(Objects.requireNonNull(classLoader.getResource("coreAttrsTranslationTable.xml")).getFile());
		try (FileInputStream fis = new FileInputStream(file)) {
			Properties props = new Properties();
			props.loadFromXML(fis);
			table = new HashMap<>();
			for (Map.Entry<Object, Object> pair : props.entrySet()) {
				String key = (String) pair.getKey();
				String value = (String) pair.getValue();
				table.put(key, value);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return table;
	}
}
