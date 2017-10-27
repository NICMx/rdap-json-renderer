package mx.nic.rdap.renderer.json.writer;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import mx.nic.rdap.core.db.Remark;
import mx.nic.rdap.core.db.RemarkDescription;
import mx.nic.rdap.renderer.util.RendererUtil;

public class RemarkJsonWriter {
	public static JsonArray getJsonArray(List<Remark> remarks) {
		JsonArrayBuilder builder = Json.createArrayBuilder();

		for (Remark remark : remarks) {
			JsonObject jsonObject = getJsonObject(remark);
			builder.add(jsonObject);
		}

		return builder.build();
	}

	private static JsonObject getJsonObject(Remark remark) {
		JsonObjectBuilder builder = Json.createObjectBuilder();

		String key = "title";
		String value = remark.getTitle();
		if (RendererUtil.isObjectVisible(value))
			builder.add(key, value);

		key = "description";
		if (RendererUtil.isObjectVisible(remark.getDescriptions()))
			builder.add(key, getDescriptionsJsonArray(remark.getDescriptions()));

		key = "type";
		value = remark.getType();
		if (RendererUtil.isObjectVisible(value))
			builder.add(key, value);

		key = "links";
		if (RendererUtil.isObjectVisible(remark.getLinks()))
			builder.add(key, LinkJsonWriter.getJsonArray(remark.getLinks()));

		key = "lang";
		if (RendererUtil.isObjectVisible(remark.getLanguage()))
			builder.add("lang", remark.getLanguage());

		return builder.build();
	}

	/**
	 * There is no privacy settings for notices, the server will show every data
	 * of remarks
	 */
	public static JsonObject getNoticeJsonObject(Remark remark) {
		JsonObjectBuilder builder = Json.createObjectBuilder();

		String key = "title";
		String value = remark.getTitle();
		if (value != null && !value.isEmpty())
			builder.add(key, value);

		key = "description";
		if (remark.getDescriptions() != null && !remark.getDescriptions().isEmpty())
			builder.add(key, getDescriptionsJsonArray(remark.getDescriptions()));

		key = "type";
		value = remark.getType();
		if (value != null && !value.isEmpty())
			builder.add(key, value);

		key = "links";
		if (remark.getLinks() != null && !remark.getLinks().isEmpty())
			builder.add(key, LinkJsonWriter.getNoticeLinksJsonArray(remark.getLinks()));

		return builder.build();
	}

	private static JsonArray getDescriptionsJsonArray(List<RemarkDescription> descriptions) {
		JsonArrayBuilder builder = Json.createArrayBuilder();

		for (RemarkDescription description : descriptions) {
			if (RendererUtil.isObjectVisible(description) && RendererUtil.isObjectVisible(description.getDescription())) {
				builder.add(description.getDescription());
			}
		}

		return builder.build();
	}
}
