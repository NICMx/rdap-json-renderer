package mx.nic.rdap.renderer.json.writer;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import mx.nic.rdap.core.db.KeyData;
import mx.nic.rdap.renderer.util.RendererUtil;

public class KeyDataJsonWriter {

	public static JsonArray getJsonArray(List<KeyData> keyDataList) {
		JsonArrayBuilder builder = Json.createArrayBuilder();
		for (KeyData keyData : keyDataList) {
			builder.add(getJsonObject(keyData));
		}
		return builder.build();
	}

	private static JsonValue getJsonObject(KeyData keyData) {
		JsonObjectBuilder builder = Json.createObjectBuilder();

		String key = "flags";
		Integer intValue = keyData.getFlags();
		if (RendererUtil.isObjectVisible(intValue))
			builder.add(key, intValue);

		key = "protocol";
		intValue = keyData.getProtocol();
		if (RendererUtil.isObjectVisible(intValue))
			builder.add(key, intValue);

		key = "publicKey";
		String stringValue = keyData.getPublicKey();
		if (RendererUtil.isObjectVisible(stringValue))
			builder.add(key, stringValue);

		key = "algorithm";
		intValue = keyData.getAlgorithm();
		if (RendererUtil.isObjectVisible(intValue))
			builder.add(key, intValue);

		key = "events";
		if (RendererUtil.isObjectVisible(keyData.getEvents()))
			builder.add(key, EventJsonWriter.getJsonArray(keyData.getEvents()));

		key = "links";
		if (RendererUtil.isObjectVisible(keyData.getLinks()))
			builder.add(key, LinkJsonWriter.getJsonArray(keyData.getLinks()));

		return builder.build();
	}

}
