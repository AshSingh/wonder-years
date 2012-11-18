package com.cs410.getfit.client.json;

import java.util.ArrayList;
import java.util.List;

import com.cs410.getfit.shared.ResourceLink;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;

public class LinksJsonParser {
	public enum LinksJsonFields {
		REL,
		URI,
		TYPE;

		/**
		 * Converts string value to lowercase
		 * 
		 * @return string in lowercase
		 */
		@Override public String toString() {
			return super.toString().toLowerCase();
		}
	}
	
	/**
	 * Parses json from the server containing rels for other HTTP requests
	 * 
	 * @param json - rels json returned from server
	 * @return list of ResourceLink - each link contains info 
	 * for the rel, uri, and type
	 */
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
