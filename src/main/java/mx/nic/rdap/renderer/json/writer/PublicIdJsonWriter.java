package mx.nic.rdap.renderer.json.writer;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import mx.nic.rdap.core.db.PublicId;
import mx.nic.rdap.renderer.util.RendererUtil;

public class PublicIdJsonWriter {

	public static JsonArray getJsonArray(List<PublicId> publicIds) {
		JsonArrayBuilder builder = Json.createArrayBuilder();

		for (PublicId publicId : publicIds) {
			builder.add(getJson(publicId));
		}

		return builder.build();
	}

	private static JsonObject getJson(PublicId publicId) {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		String key = "type";
		if (RendererUtil.isObjectVisible(publicId.getType())) {
			builder.add(key, publicId.getType());
		}

		key = "identifier";
		if (RendererUtil.isObjectVisible(publicId.getPublicId())) {
			builder.add(key, publicId.getPublicId());
		}

		return builder.build();
	}

}
