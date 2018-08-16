package mx.nic.rdap.renderer.json;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map.Entry;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.json.JsonWriter;

import mx.nic.rdap.core.db.Autnum;
import mx.nic.rdap.core.db.Domain;
import mx.nic.rdap.core.db.Entity;
import mx.nic.rdap.core.db.IpNetwork;
import mx.nic.rdap.core.db.Nameserver;
import mx.nic.rdap.core.db.RdapObject;
import mx.nic.rdap.core.db.Remark;
import mx.nic.rdap.renderer.Renderer;
import mx.nic.rdap.renderer.json.writer.AutnumJsonWriter;
import mx.nic.rdap.renderer.json.writer.DomainJsonWriter;
import mx.nic.rdap.renderer.json.writer.EntityJsonWriter;
import mx.nic.rdap.renderer.json.writer.IpNetworkJsonWriter;
import mx.nic.rdap.renderer.json.writer.JsonUtil;
import mx.nic.rdap.renderer.json.writer.NameserverJsonWriter;
import mx.nic.rdap.renderer.json.writer.RemarkJsonWriter;
import mx.nic.rdap.renderer.object.ExceptionResponse;
import mx.nic.rdap.renderer.object.HelpResponse;
import mx.nic.rdap.renderer.object.RdapResponse;
import mx.nic.rdap.renderer.object.RequestResponse;
import mx.nic.rdap.renderer.object.SearchResponse;

public class JsonRenderer implements Renderer {

	@Override
	public void renderEntity(RequestResponse<Entity> response, PrintWriter printWriter) {
		Entity rdapObject = response.getRdapObject();
		JsonObject json = EntityJsonWriter.getJson(rdapObject);
		render(response, json, printWriter);
	}

	@Override
	public void renderDomain(RequestResponse<Domain> response, PrintWriter printWriter) {
		JsonObject json = DomainJsonWriter.getJson(response.getRdapObject());
		render(response, json, printWriter);
	}

	@Override
	public void renderNameserver(RequestResponse<Nameserver> response, PrintWriter printWriter) {
		JsonObject json = NameserverJsonWriter.getJson(response.getRdapObject());
		render(response, json, printWriter);
	}

	@Override
	public void renderAutnum(RequestResponse<Autnum> response, PrintWriter printWriter) {
		JsonObject json = AutnumJsonWriter.getJson(response.getRdapObject());
		render(response, json, printWriter);
	}

	@Override
	public void renderIpNetwork(RequestResponse<IpNetwork> response, PrintWriter printWriter) {
		JsonObject json = IpNetworkJsonWriter.getJson(response.getRdapObject());
		render(response, json, printWriter);
	}

	@Override
	public void renderException(ExceptionResponse response, PrintWriter printWriter) {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		builder.add("rdapConformance", JsonUtil.getRdapConformance(response.getRdapConformance()));

		String errorCode = response.getErrorCode();
		if (errorCode != null) {
			builder.add("errorCode", errorCode);
		}

		String errorTitle = response.getErrorTitle();
		if (errorTitle != null) {
			builder.add("title", errorTitle);
		}

		List<String> errorDescription = response.getDescription();
		if (errorCode != null && errorDescription != null && !errorDescription.isEmpty()) {
			if (errorCode.compareTo("500") != 0) {
				JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
				for (String description : errorDescription) {
					arrayBuilder.add(description);
				}
				builder.add("description", arrayBuilder.build());
			}
		}

		List<Remark> notices = response.getNotices();
		if (notices != null && !notices.isEmpty()) {
			JsonArray jsonArray = RemarkJsonWriter.getJsonArray(notices);
			builder.add("notices", jsonArray);
		}

		JsonWriter jsonWriter = Json.createWriter(printWriter);
		jsonWriter.writeObject(builder.build());

	}

	@Override
	public void renderHelp(HelpResponse response, PrintWriter printWriter) {
		JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
		objectBuilder.add("rdapConformance", JsonUtil.getRdapConformance(response.getRdapConformance()));

		List<Remark> notices = response.getNotices();
		if (notices != null && !notices.isEmpty()) {
			JsonArray jsonArray = RemarkJsonWriter.getJsonArray(notices);
			objectBuilder.add("notices", jsonArray);
		}

		JsonWriter jsonWriter = Json.createWriter(printWriter);
		jsonWriter.writeObject(objectBuilder.build());
	}

	private void render(RdapResponse response, JsonObject resultJson, PrintWriter writer) {

		JsonObjectBuilder object = Json.createObjectBuilder();
		object.add("rdapConformance", JsonUtil.getRdapConformance(response.getRdapConformance()));

		if (response.getNotices() != null && !response.getNotices().isEmpty()) {
			object.add("notices", noticesToJsonArray(response.getNotices()));
		}

		for (Entry<String, JsonValue> entry : resultJson.entrySet()) {
			object.add(entry.getKey(), entry.getValue());
		}

		JsonWriter jsonWriter = Json.createWriter(writer);
		jsonWriter.writeObject(object.build());
	}

	@Override
	public void renderEntities(SearchResponse<Entity> response, PrintWriter printWriter) {
		JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		List<Entity> rdapObjects = response.getRdapObjects();
		for (RdapObject entity : rdapObjects) {
			arrayBuilder.add(EntityJsonWriter.getJson((Entity) entity));
		}

		objectBuilder.add("entitySearchResults", arrayBuilder);
		JsonObject build = objectBuilder.build();
		render(response, build, printWriter);
	}

	@Override
	public void renderDomains(SearchResponse<Domain> response, PrintWriter printWriter) {
		List<Domain> rdapObjects = response.getRdapObjects();

		JsonObjectBuilder builder = Json.createObjectBuilder();
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

		for (Domain domain : rdapObjects) {
			arrayBuilder.add(DomainJsonWriter.getJson(domain));
		}

		builder.add("domainSearchResults", arrayBuilder);
		JsonObject resultJsonObject = builder.build();

		render(response, resultJsonObject, printWriter);

	}

	@Override
	public void renderNameservers(SearchResponse<Nameserver> response, PrintWriter printWriter) {
		List<Nameserver> rdapObjects = response.getRdapObjects();

		JsonObjectBuilder builder = Json.createObjectBuilder();
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

		for (Nameserver nameserver : rdapObjects) {
			arrayBuilder.add(NameserverJsonWriter.getJson(nameserver));
		}

		builder.add("nameserverSearchResults", arrayBuilder);
		JsonObject resultJsonObject = builder.build();

		render(response, resultJsonObject, printWriter);
	}

	private JsonArray noticesToJsonArray(List<Remark> notices) {
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		for (Remark notice : notices) {
			arrayBuilder.add(RemarkJsonWriter.getNoticeJsonObject(notice));
		}
		return arrayBuilder.build();
	}

}
