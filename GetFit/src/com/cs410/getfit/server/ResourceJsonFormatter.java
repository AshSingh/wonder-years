package com.cs410.getfit.server;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ResourceJsonFormatter {
	Type clazz;
	Type clazzArray;
	String encompassingString;
	
	protected ResourceJsonFormatter(Type clazz, Type clazzArray, String encompassingString) {
		this.clazz = clazz;
		this.clazzArray = clazzArray;
		this.encompassingString = encompassingString;
	}
	
	public String getJSONFormattedStringOfResource(List<? extends Object> objects) {
		JsonObject jsonFormattedChallenges = new JsonObject();

		JsonArray challengeObjects = new JsonArray();
		
		for (Object object: objects) {
			String objectJsonString = new Gson().toJson(object, clazz);
			JsonObject jsonObject = (JsonObject) new JsonParser().parse(objectJsonString);
			challengeObjects.add(jsonObject);	
		}
		jsonFormattedChallenges.add(encompassingString, challengeObjects);
		System.out.println(jsonFormattedChallenges.toString());
		return jsonFormattedChallenges.toString();
	}

	public List<? extends Object> getResourcesFromJSONFormattedString(String jsonFormattedResourceString) {
		JsonObject object = (JsonObject)new JsonParser().parse(jsonFormattedResourceString);
		Object[] array = new Gson().fromJson(object.get(encompassingString), clazzArray);
		return new ArrayList<Object>(Arrays.asList(array));
	}
}
