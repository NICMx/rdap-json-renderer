package mx.nic.rdap.renderer.json.writer;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import mx.nic.rdap.core.db.SecureDNS;
import mx.nic.rdap.renderer.util.RendererUtil;

public class SecureDNSJsonWriter {

	public static JsonObject getJsonObject(SecureDNS secureDNS) {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		String key = "zoneSigned";
		if (RendererUtil.isObjectVisible(secureDNS.getZoneSigned()))
			builder.add(key, secureDNS.getZoneSigned());

		key = "delegationSigned";
		if (RendererUtil.isObjectVisible(secureDNS.getDelegationSigned()))
			builder.add(key, secureDNS.getDelegationSigned());

		key = "maxSigLife";
		if (RendererUtil.isObjectVisible(secureDNS.getMaxSigLife()))
			builder.add(key, secureDNS.getMaxSigLife());

		key = "dsData";
		if (RendererUtil.isObjectVisible(secureDNS.getDsData()))
			builder.add(key, DsDataJsonWriter.getJsonArray(secureDNS.getDsData()));

		key = "keyData";
		if (RendererUtil.isObjectVisible(secureDNS.getKeyData()))
			builder.add(key, KeyDataJsonWriter.getJsonArray(secureDNS.getKeyData()));

		return builder.build();
	}

}
