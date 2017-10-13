package mx.nic.rdap.renderer.json.writer;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import mx.nic.rdap.core.db.IpNetwork;
import mx.nic.rdap.renderer.util.RendererUtil;

public class IpNetworkJsonWriter {

	public static JsonArray getJsonArray(List<IpNetwork> ips) {
		JsonArrayBuilder builder = Json.createArrayBuilder();

		for (IpNetwork ip : ips) {
			builder.add(getJson(ip));
		}

		return builder.build();
	}

	public static JsonObject getJson(IpNetwork ipNetwork) {

		JsonObjectBuilder builder = Json.createObjectBuilder();
		builder.add("objectClassName", "ip network");

		String key = "startAddress";
		String value = ipNetwork.getStartAddress() != null ? ipNetwork.getStartAddress().getHostAddress() : null;
		if (RendererUtil.isObjectVisible(value))
			builder.add(key, value);

		key = "endAddress";
		value = ipNetwork.getEndAddress() != null ? ipNetwork.getEndAddress().getHostAddress() : null;
		if (RendererUtil.isObjectVisible(value))
			builder.add(key, value);

		key = "ipVersion";
		value = ipNetwork.getIpVersion() != null ? ipNetwork.getIpVersion().getVersionName() : null;
		if (RendererUtil.isObjectVisible(value))
			builder.add(key, value);

		key = "name";
		value = ipNetwork.getName();
		if (RendererUtil.isObjectVisible(value))
			builder.add(key, value);

		key = "type";
		value = ipNetwork.getType();
		if (RendererUtil.isObjectVisible(value))
			builder.add(key, value);

		key = "country";
		value = ipNetwork.getCountry();
		if (RendererUtil.isObjectVisible(value))
			builder.add(key, value);

		JsonUtil.fillCommonRdapJsonObject(builder, ipNetwork);

		key = "parentHandle";
		value = ipNetwork.getParentHandle();
		if (RendererUtil.isObjectVisible(value))
			builder.add(key, value);

		return builder.build();
	}

}
