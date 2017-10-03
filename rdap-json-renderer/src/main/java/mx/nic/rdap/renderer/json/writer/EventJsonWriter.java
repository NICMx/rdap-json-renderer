package mx.nic.rdap.renderer.json.writer;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import mx.nic.rdap.core.db.Event;
import mx.nic.rdap.renderer.util.RendererUtil;

public class EventJsonWriter {
	public static JsonArray getJsonArray(List<Event> events) {
		JsonArrayBuilder builder = Json.createArrayBuilder();

		for (Event event : events) {
			builder.add(getJsonObject(event));
		}

		return builder.build();
	}

	private static JsonObject getJsonObject(Event event) {
		JsonObjectBuilder builder = Json.createObjectBuilder();

		String key = "eventAction";
		String value = event.getEventAction().getValue();
		if (RendererUtil.isObjectVisible(value))
			builder.add(key, value);

		key = "eventActor";
		value = event.getEventActor();
		if (RendererUtil.isObjectVisible(value))
			builder.add(key, value);

		key = "eventDate";
		value = event.getEventDate().toInstant().toString();
		if (RendererUtil.isObjectVisible(value))
			builder.add(key, value);

		key = "links";
		if (RendererUtil.isObjectVisible(event.getLinks()))
			builder.add(key, LinkJsonWriter.getJsonArray(event.getLinks()));

		return builder.build();
	}
}
