package mx.nic.rdap.renderer.json.writer;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import mx.nic.rdap.core.catalog.Status;
import mx.nic.rdap.core.db.RdapObject;
import mx.nic.rdap.renderer.util.RendererUtil;

/**
 * Utilities for json renderer
 */
public class JsonUtil {

	public static JsonArray getRdapConformance(List<String> others) {
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		if (others != null) {
			for (String s : others) {
				arrayBuilder.add(s);
			}
		}
		return arrayBuilder.build();
	}
	
	public static JsonArray getRdapConformance(String... others) {
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		arrayBuilder.add("rdap_level_0");
		if (others != null) {
			for (String s : others) {
				arrayBuilder.add(s);
			}
		}
		return arrayBuilder.build();
	}

	public static JsonObjectBuilder fillCommonRdapJsonObject(JsonObjectBuilder builder, RdapObject object) {


		String key = "handle";
		if (RendererUtil.isObjectVisible(object.getHandle()))
			builder.add(key, object.getHandle());

		key = "remarks";
		if (RendererUtil.isObjectVisible(object.getRemarks()))
			builder.add(key, RemarkJsonWriter.getJsonArray(object.getRemarks()));

		key = "links";
		if (RendererUtil.isObjectVisible(object.getLinks()))
			builder.add(key,
					LinkJsonWriter.getJsonArray(object.getLinks()));

		key = "events";
		if (RendererUtil.isObjectVisible(object.getEvents()))
			builder.add(key, EventJsonWriter.getJsonArray(object.getEvents()));

		key = "status";
		if (RendererUtil.isObjectVisible(object.getStatus()))
			builder.add(key, getStatusJsonArray(object.getStatus()));

		key = "port43";
		if (RendererUtil.isObjectVisible(object.getPort43()))
			builder.add(key, object.getPort43());

		key = "entities";
		if (RendererUtil.isObjectVisible(object.getEntities()))
			builder.add(key, EntityJsonWriter.getJsonArray(object.getEntities()));

		key = "lang";
		if (RendererUtil.isObjectVisible(object.getLang())) {
			builder.add(key, object.getLang());
		}

		return builder;
	}



	private static JsonArray getStatusJsonArray(List<Status> statusList) {
		JsonArrayBuilder builder = Json.createArrayBuilder();

		for (Status s : statusList) {
			builder.add(s.getValue());
		}

		return builder.build();
	}

}
