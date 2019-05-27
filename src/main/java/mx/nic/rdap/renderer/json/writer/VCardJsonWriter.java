package mx.nic.rdap.renderer.json.writer;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import mx.nic.rdap.core.db.VCard;
import mx.nic.rdap.core.db.VCardPostalInfo;
import mx.nic.rdap.renderer.util.RendererUtil;

public class VCardJsonWriter {

	public static JsonArray getJson(VCard vCard) {

		JsonArrayBuilder jCard = Json.createArrayBuilder();
		jCard.add("vcard");
		JsonArrayBuilder attributesArray = Json.createArrayBuilder();
		attributesArray.add(getVersion("4.0"));

		String value = vCard.getName();
		if (RendererUtil.isObjectVisible(value))
			attributesArray.add(getFN(value));

		value = vCard.getCompanyName();
		if (RendererUtil.isObjectVisible(value))
			attributesArray.add(getOrg(value));

		value = vCard.getCompanyURL();
		if (RendererUtil.isObjectVisible(value))
			attributesArray.add(getUrl(value));

		List<String> contactUri = vCard.getContactUri();
		if (RendererUtil.isObjectVisible(contactUri)) {
			if (contactUri.size() == 1) {
				attributesArray.add(getContactUri(contactUri.get(0)));
			} else {
				for (int i = 0; i < contactUri.size(); i++) {
					attributesArray.add(getContactUri(contactUri.get(i), i + 1));
				}
			}
		}
		
		value = vCard.getEmail();
		if (RendererUtil.isObjectVisible(value))
			attributesArray.add(getEmail(value));

		value = vCard.getVoice();
		if (RendererUtil.isObjectVisible(value))
			attributesArray.add(getVoice(value));

		value = vCard.getCellphone();
		if (RendererUtil.isObjectVisible(value))
			attributesArray.add(getCellphone(value));

		value = vCard.getFax();
		if (RendererUtil.isObjectVisible(value))
			attributesArray.add(getFax(value));

		value = vCard.getJobTitle();
		if (RendererUtil.isObjectVisible(value))
			attributesArray.add(getTitle(value));

		if (RendererUtil.isObjectVisible(vCard.getPostalInfo())) {
			for (VCardPostalInfo postalInfo : vCard.getPostalInfo()) {
				JsonArray vCardAddressArray = getVCardAddressArray(postalInfo);
				attributesArray.add(vCardAddressArray);
			}
		}

		jCard.add(attributesArray.build());

		return jCard.build();
	}

	private static JsonArray getVersion(String version) {
		return getVCardAttributeArray("version", "text", version);
	}

	private static JsonArray getFN(String fn) {
		return getVCardAttributeArray("fn", "text", fn);
	}

	private static JsonArray getOrg(String org) {
		return getVCardAttributeArray("org", "text", org);
	}

	private static JsonArray getUrl(String url) {
		return getVCardAttributeArray("url", "uri", url);
	}

	private static JsonArray getEmail(String email) {
		return getVCardAttributeArray("email", "text", email);
	}

	private static JsonArray getVoice(String voiceNumber) {
		return getVCardAttributeArray("tel", Json.createObjectBuilder().add("type", "voice").build(), "text",
				voiceNumber);
	}

	private static JsonArray getCellphone(String cellphone) {
		return getVCardAttributeArray("tel", Json.createObjectBuilder().add("type", "cell").build(), "text", cellphone);
	}

	private static JsonArray getFax(String fax) {
		return getVCardAttributeArray("tel", Json.createObjectBuilder().add("type", "fax").build(), "text", fax);
	}

	private static JsonArray getTitle(String title) {
		return getVCardAttributeArray("title", "text", title);
	}

	private static JsonArray getContactUri(String contactUri) {
		return getVCardAttributeArray("contact-uri", Json.createObjectBuilder().build(), "uri", contactUri);
	}

	private static JsonArray getContactUri(String contactUri, int pref) {
		return getVCardAttributeArray("contact-uri", Json.createObjectBuilder().add("pref", pref).build(), "uri",
				contactUri);
	}

	private static JsonArray getVCardAttributeArray(String attributeName, String type, String value) {
		return getVCardAttributeArray(attributeName, Json.createObjectBuilder().build(), type, value);
	}

	private static JsonArray getVCardAttributeArray(String attributeName, JsonObject jsonObject, String type,
			String value) {
		JsonArrayBuilder builder = Json.createArrayBuilder();
		builder.add(attributeName);
		builder.add(jsonObject);
		builder.add(type);
		builder.add(value);
		return builder.build();
	}

	private static JsonArray getVCardAddressArray(VCardPostalInfo postalInfo) {
		JsonArrayBuilder attributeArray = Json.createArrayBuilder();
		attributeArray.add("adr");

		/* Add parameters to the postal Info */
		String key = "type";
		String value = postalInfo.getType();

		JsonObjectBuilder paramAttr = Json.createObjectBuilder();
		if (RendererUtil.isObjectVisible(value))
			paramAttr.add(key, value);

		key = "cc";
		value = postalInfo.getCountryCode();
		if (RendererUtil.isObjectVisible(value))
			paramAttr.add(key, value);

		attributeArray.add(paramAttr.build());

		attributeArray.add("text");

		/* postal info */
		JsonArrayBuilder postalInfoArray = Json.createArrayBuilder();

		postalInfoArray.add("");
		postalInfoArray.add("");

		JsonArrayBuilder streetBuilder = Json.createArrayBuilder();
		key = "street1";
		value = postalInfo.getStreet1();
		String singleStreet = null;
		int streetCounter = 0;
		if (RendererUtil.isObjectVisible(value)) {
			streetCounter++;
			streetBuilder.add(value);
			singleStreet = value;
		} else {
			streetBuilder.add("");
		}

		key = "street2";
		value = postalInfo.getStreet2();
		if (RendererUtil.isObjectVisible(value)) {
			streetCounter++;
			streetBuilder.add(value);
			singleStreet = value;
		} else {
			streetBuilder.add("");
		}

		key = "street3";
		value = postalInfo.getStreet3();
		if (RendererUtil.isObjectVisible(value)) {
			streetCounter++;
			streetBuilder.add(value);
			singleStreet = value;
		} else {
			streetBuilder.add("");
		}

		if (streetCounter > 1) {
			postalInfoArray.add(streetBuilder.build());
		} else if (streetCounter == 1) {
			postalInfoArray.add(singleStreet);
		} else {
			postalInfoArray.add("");
		}

		key = "city";
		value = postalInfo.getCity();
		if (RendererUtil.isObjectVisible(value))
			postalInfoArray.add(value);
		else
			postalInfoArray.add("");

		key = "state";
		value = postalInfo.getState();
		if (RendererUtil.isObjectVisible(value))
			postalInfoArray.add(value);
		else
			postalInfoArray.add("");

		key = "postalCode";
		value = postalInfo.getPostalCode();
		if (RendererUtil.isObjectVisible(value))
			postalInfoArray.add(value);
		else
			postalInfoArray.add("");

		key = "country";
		value = postalInfo.getCountry();
		if (RendererUtil.isObjectVisible(value))
			postalInfoArray.add(value);
		else
			postalInfoArray.add("");

		attributeArray.add(postalInfoArray.build());

		return attributeArray.build();
	}

}
