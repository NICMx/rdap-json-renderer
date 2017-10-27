package mx.nic.rdap.renderer.json.writer;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import mx.nic.rdap.core.db.DsData;
import mx.nic.rdap.renderer.util.RendererUtil;

public class DsDataJsonWriter {

	public static JsonArray getJsonArray(List<DsData> dsDataList) {
		JsonArrayBuilder builder = Json.createArrayBuilder();

		for (DsData dsData : dsDataList) {
			builder.add(getJsonObject(dsData));
		}

		return builder.build();
	}

	private static JsonObject getJsonObject(DsData dsData) {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		String key = "keyTag";
		Integer intValue = dsData.getKeytag();
		if (RendererUtil.isObjectVisible(intValue))
			builder.add(key, intValue);

		key = "algorithm";
		intValue = dsData.getAlgorithm();
		if (RendererUtil.isObjectVisible(intValue))
			builder.add(key, intValue);

		key = "digest";
		String stringValue = dsData.getDigest();
		if (RendererUtil.isObjectVisible(stringValue))
			builder.add(key, stringValue);

		key = "digestType";
		intValue = dsData.getDigestType();
		if (RendererUtil.isObjectVisible(intValue))
			builder.add(key, intValue);

		key = "events";
		if (RendererUtil.isObjectVisible(dsData.getEvents()))
			builder.add(key, EventJsonWriter.getJsonArray(dsData.getEvents()));

		key = "links";
		if (RendererUtil.isObjectVisible(dsData.getLinks()))
			builder.add(key, LinkJsonWriter.getJsonArray(dsData.getLinks()));

		return builder.build();
	}

}
