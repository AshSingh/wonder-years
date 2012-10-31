package com.cs410.getfit.client.json;

import java.util.ArrayList;
import java.util.List;

import com.cs410.getfit.server.json.ResourceLink;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;

public class LinksJsonParser {
	public enum LinksJsonFields {
		REL,
		URI,
		TYPE;

		@Override public String toString() {
			// return lower case string
			return super.toString().toLowerCase();
		}
	}
	
	public static List<ResourceLink> getLinks(JSONArray linksArray) {
		List<ResourceLink> links = new ArrayList<ResourceLink>();
		for (int i=0; i < linksArray.size(); i++) {
			JSONObject linkObj = linksArray.get(i).isObject();
			String rel = linkObj.get(LinksJsonFields.REL.toString()).isString().stringValue();
			String uri = linkObj.get(LinksJsonFields.URI.toString()).isString().stringValue();
			String type = linkObj.get(LinksJsonFields.TYPE.toString()).isString().stringValue();
			links.add(new ResourceLink(rel, uri, type));
		}
		return links;
	}
}
