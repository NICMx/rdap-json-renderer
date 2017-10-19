package mx.nic.rdap.renderer.json.writer;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import mx.nic.rdap.core.db.Domain;
import mx.nic.rdap.renderer.util.RendererUtil;

public class DomainJsonWriter {

	public static JsonObject getJson(Domain domain) {
		JsonObjectBuilder builder = Json.createObjectBuilder();

		builder.add("objectClassName", "domain");
		JsonUtil.fillCommonRdapJsonObject(builder, domain);

		String key = "ldhName";
		String value = domain.getFQDN();
		if (RendererUtil.isObjectVisible(domain.getLdhName()))
			builder.add(key, value);

		key = "unicodeName";
		value = domain.getUnicodeFQDN();
		if (RendererUtil.isObjectVisible(domain.getUnicodeName()))
			builder.add(key, value);

		key = "variants";
		if (RendererUtil.isObjectVisible(domain.getVariants()))
			builder.add(key, VariantJsonWriter.getJsonArray(domain.getVariants()));

		key = "publicIds";
		if (RendererUtil.isObjectVisible(domain.getPublicIds())) {
			builder.add(key, PublicIdJsonWriter.getJsonArray(domain.getPublicIds()));
		}

		key = "nameservers";
		if (RendererUtil.isObjectVisible(domain.getNameServers()))
			builder.add(key, NameserverJsonWriter.getJsonArray(domain.getNameServers()));

		key = "secureDNS";
		if (RendererUtil.isObjectVisible(domain.getSecureDNS()))
			builder.add(key, SecureDNSJsonWriter.getJsonObject(domain.getSecureDNS()));

		key = "network";
		if (RendererUtil.isObjectVisible(domain.getIpNetwork())) {
			builder.add(key, IpNetworkJsonWriter.getJson(domain.getIpNetwork()));
		}

		return builder.build();
	}

}
