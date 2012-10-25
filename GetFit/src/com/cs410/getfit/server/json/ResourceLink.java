package com.cs410.getfit.server.json;

public class ResourceLink {
	String rel;
	String uri;
	String type;
	public ResourceLink(String rel, String uri, String type){
		this.rel = rel;
		this.uri = uri;
		this.type = type;
	}
}
