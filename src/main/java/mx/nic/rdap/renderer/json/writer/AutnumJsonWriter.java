/**
 * 
 */
package mx.nic.rdap.renderer.json.writer;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import mx.nic.rdap.core.db.Autnum;
import mx.nic.rdap.renderer.util.RendererUtil;

public class AutnumJsonWriter {

	public static JsonArray getJsonArray(List<Autnum> autnums) {
		JsonArrayBuilder builder = Json.createArrayBuilder();

		for (Autnum autnum : autnums) {
			builder.add(getJson(autnum));
		}
		return builder.build();
	}

	public static JsonObject getJson(Autnum autnum) {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		builder.add("objectClassName", "autnum");
		JsonUtil.fillCommonRdapJsonObject(builder, autnum);

		String key = "startAutnum";
		if (RendererUtil.isObjectVisible(autnum.getStartAutnum()))
			builder.add(key, autnum.getStartAutnum());

		key = "endAutnum";
		if (RendererUtil.isObjectVisible(autnum.getEndAutnum()))
			builder.add(key, autnum.getEndAutnum());

		key = "name";
		if (RendererUtil.isObjectVisible(autnum.getName()))
			builder.add(key, autnum.getName());

		key = "type";
		if (RendererUtil.isObjectVisible(autnum.getType()))
			builder.add(key, autnum.getType());

		key = "country";
		if (RendererUtil.isObjectVisible(autnum.getCountryCode()))
			builder.add(key, autnum.getCountryCode());

		return builder.build();
	}

}
