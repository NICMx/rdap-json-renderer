package mx.nic.rdap.renderer.json.writer;

import java.util.Date;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import mx.nic.rdap.core.catalog.EventAction;
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
		EventAction action = event.getEventAction();
		if (RendererUtil.isObjectVisible(action))
			builder.add(key, action.getValue());

		key = "eventActor";
		String value = event.getEventActor();
		if (RendererUtil.isObjectVisible(value))
			builder.add(key, value);

		key = "eventDate";
		Date eventDate = event.getEventDate();
		if (RendererUtil.isObjectVisible(eventDate))
			builder.add(key, eventDate.toInstant().toString());

		key = "links";
		if (RendererUtil.isObjectVisible(event.getLinks()))
			builder.add(key, LinkJsonWriter.getJsonArray(event.getLinks()));

		return builder.build();
	}
}
