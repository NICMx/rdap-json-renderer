package mx.nic.rdap.renderer.json.writer;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import mx.nic.rdap.core.catalog.Role;
import mx.nic.rdap.core.db.Entity;
import mx.nic.rdap.renderer.util.RendererUtil;

public class EntityJsonWriter {

	public static JsonObject getJson(Entity entity) {

		JsonObjectBuilder builder = Json.createObjectBuilder();
		builder.add("objectClassName", "entity");

		JsonUtil.fillCommonRdapJsonObject(builder, entity);

		String key = "roles";
		if (RendererUtil.isObjectVisible(entity.getRoles())) {
			builder.add(key, getRolesJsonArray(entity.getRoles()));
		}

		key = "publicIds";
		if (RendererUtil.isObjectVisible(entity.getPublicIds())) {
			builder.add(key, PublicIdJsonWriter.getJsonArray(entity.getPublicIds()));
		}

		key = "networks";
		if (RendererUtil.isObjectVisible(entity.getIpNetworks())) {
			builder.add(key, IpNetworkJsonWriter.getJsonArray(entity.getIpNetworks()));
		}

		key = "autnums";
		if (RendererUtil.isObjectVisible(entity.getAutnums())) {
			builder.add(key, AutnumJsonWriter.getJsonArray(entity.getAutnums()));
		}

		key = "vcardArray";
		if (RendererUtil.isObjectVisible(entity.getVCardList())) {
			builder.add(key, VCardJsonWriter.getJson(entity.getVCardList().get(0)));
		}

		return builder.build();
	}

	public static JsonArray getJsonArray(List<Entity> entities) {
		JsonArrayBuilder builder = Json.createArrayBuilder();

		for (Entity entity : entities) {
			builder.add(getJson(entity));
		}

		return builder.build();
	}

	private static JsonArray getRolesJsonArray(List<Role> roles) {
		JsonArrayBuilder builder = Json.createArrayBuilder();

		for (Role role : roles) {
			builder.add(role.getValue());
		}

		return builder.build();
	}

}
