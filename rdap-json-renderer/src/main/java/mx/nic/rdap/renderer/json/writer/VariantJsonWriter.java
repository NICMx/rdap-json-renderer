package mx.nic.rdap.renderer.json.writer;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import mx.nic.rdap.core.catalog.VariantRelation;
import mx.nic.rdap.core.db.Variant;
import mx.nic.rdap.core.db.VariantName;
import mx.nic.rdap.renderer.util.RendererUtil;

public class VariantJsonWriter {
	public static JsonArray getJsonArray(List<Variant> variants) {
		JsonArrayBuilder builder = Json.createArrayBuilder();

		for (Variant variant : variants) {
			builder.add(getJsonObject(variant));
		}

		return builder.build();
	}

	private static JsonObject getJsonObject(Variant variant) {
		JsonObjectBuilder builder = Json.createObjectBuilder();

		String key = "relation";
		if (RendererUtil.isObjectVisible(variant.getRelations())) {
			builder.add(key, getDomainRelationsJsonArray(variant.getRelations()));
		}

		key = "idnTable";
		String value = variant.getIdnTable();
		if (RendererUtil.isObjectVisible(value))
			builder.add(key, value);

		key = "variantNames";
		if (RendererUtil.isObjectVisible(variant.getVariantNames()))
			builder.add(key, getVariantNamesJsonArray(variant.getVariantNames()));

		return builder.build();
	}

	private static JsonArray getDomainRelationsJsonArray(List<VariantRelation> relations) {
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		for (VariantRelation relation : relations) {
			arrayBuilder.add(relation.getValue());
		}
		return arrayBuilder.build();
	}

	private static JsonArray getVariantNamesJsonArray(List<VariantName> variantNames) {
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

		for (VariantName variantName : variantNames) {
			JsonObjectBuilder builder = Json.createObjectBuilder();
			builder.add("ldhName", variantName.getLdhName());
			builder.add("unicodeName", variantName.getUnicodeName());
			arrayBuilder.add(builder);
		}

		return arrayBuilder.build();
	}

}
