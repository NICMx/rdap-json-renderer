package mx.nic.rdap.renderer.json.writer;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import mx.nic.rdap.core.db.IpAddress;
import mx.nic.rdap.core.db.Nameserver;
import mx.nic.rdap.core.db.struct.NameserverIpAddressesStruct;
import mx.nic.rdap.renderer.util.RendererUtil;

public class NameserverJsonWriter {

	public static JsonArray getJsonArray(List<Nameserver> nameserver) {
		JsonArrayBuilder builder = Json.createArrayBuilder();

		for (Nameserver ns : nameserver) {
			builder.add(getJson(ns));
		}

		return builder.build();
	}

	public static JsonObject getJson(Nameserver nameserver) {
		JsonObjectBuilder builder = Json.createObjectBuilder();

		builder.add("objectClassName", "nameserver");
		JsonUtil.fillCommonRdapJsonObject(builder, nameserver);

		String key = "ldhName";
		String value = nameserver.getLdhName();
		if (RendererUtil.isObjectVisible(value))
			builder.add(key, value);
		
		key = "unicodeName";
		value = nameserver.getUnicodeName();
		if (RendererUtil.isObjectVisible(value))
			builder.add(key, value);

		fillIpAddresses(builder, nameserver.getIpAddresses());

		return builder.build();
	}

	private static void fillIpAddresses(JsonObjectBuilder builder, NameserverIpAddressesStruct ipAdresses) {
		String key = "ipAddresses";
		if (!RendererUtil.isObjectVisible(ipAdresses))
			return;

		JsonObjectBuilder addressBuilder = Json.createObjectBuilder();
		boolean insertIpAddresses = false;
		key = "v4";
		List<IpAddress> ipv4List = ipAdresses.getIpv4Adresses();
		if (RendererUtil.isObjectVisible(ipv4List)) {
			addressBuilder.add(key, getIpAddressJsonArray(ipv4List));
			insertIpAddresses = true;
		}

		key = "v6";
		List<IpAddress> ipv6List = ipAdresses.getIpv6Adresses();
		if (RendererUtil.isObjectVisible(ipv6List)) {
			addressBuilder.add(key, getIpAddressJsonArray(ipv6List));
			insertIpAddresses = true;
		}

		if (insertIpAddresses) {
			key = "ipAddresses";
			builder.add(key, addressBuilder.build());
		}
	}

	private static JsonArray getIpAddressJsonArray(List<IpAddress> addresses) {
		JsonArrayBuilder builder = Json.createArrayBuilder();
		for (IpAddress address : addresses) {
			builder.add(address.getAddress().getHostAddress());
		}
		return builder.build();
	}

}
