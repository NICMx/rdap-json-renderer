package mx.nic.rdap.renderer.json.writer;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import mx.nic.rdap.core.db.Link;
import mx.nic.rdap.renderer.util.RendererUtil;

public class LinkJsonWriter {

	public static JsonArray getJsonArray(List<Link> links) {
		JsonArrayBuilder builder = Json.createArrayBuilder();

		for (Link link : links) {
			builder.add(getJsonObject(link));
		}

		return builder.build();
	}

	private static JsonObject getJsonObject(Link link) {
		JsonObjectBuilder builder = Json.createObjectBuilder();

		String key = "value";
		String value = link.getValue();
		if (RendererUtil.isObjectVisible(value))
			builder.add(key, value);

		key = "rel";
		value = link.getRel();
		if (RendererUtil.isObjectVisible(value))
			builder.add(key, value);

		key = "href";
		value = link.getHref();
		if (RendererUtil.isObjectVisible(value))
			builder.add(key, value);

		key = "hreflang";
		List<String> hreflangs = link.getHreflang();
		if (RendererUtil.isObjectVisible(hreflangs)) {
			JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
			for (String lang : hreflangs) {
				arrayBuilder.add(lang);
			}
			builder.add(key, arrayBuilder.build());
		}

		key = "title";
		value = link.getTitle();
		if (RendererUtil.isObjectVisible(value))
			builder.add(key, value);

		key = "media";
		value = link.getMedia();
		if (RendererUtil.isObjectVisible(value))
			builder.add(key, value);

		key = "type";
		value = link.getType();
		if (RendererUtil.isObjectVisible(value))
			builder.add(key, value);

		return builder.build();
	}

	/**
	 * There is no privacy settings for links in notices, the server will show
	 * every data of remarks
	 */
	public static JsonArray getNoticeLinksJsonArray(List<Link> links) {
		JsonArrayBuilder builder = Json.createArrayBuilder();

		for (Link link : links) {
			builder.add(getNoticesLinksJsonObject(link));
		}

		return builder.build();
	}

	/**
	 * There is no privacy settings for links in notices, the server will show
	 * every data of remarks
	 */
	private static JsonValue getNoticesLinksJsonObject(Link link) {
		JsonObjectBuilder builder = Json.createObjectBuilder();

		String key = "value";
		String value = link.getValue();
		if (value != null && !value.isEmpty())
			builder.add(key, value);

		key = "rel";
		value = link.getRel();
		if (value != null && !value.isEmpty())
			builder.add(key, value);

		key = "href";
		value = link.getHref();
		if (value != null && !value.isEmpty())
			builder.add(key, value);

		key = "hreflang";
		List<String> hreflangs = link.getHreflang();
		if (hreflangs != null && !hreflangs.isEmpty()) {
			JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
			for (String lang : hreflangs) {
				arrayBuilder.add(lang);
			}
			builder.add(key, arrayBuilder.build());
		}

		key = "title";
		value = link.getTitle();
		if (value != null && !value.isEmpty())
			builder.add(key, value);

		key = "media";
		value = link.getMedia();
		if (value != null && !value.isEmpty())
			builder.add(key, value);

		key = "type";
		value = link.getType();
		if (value != null && !value.isEmpty())
			builder.add(key, value);

		return builder.build();
	}

}
