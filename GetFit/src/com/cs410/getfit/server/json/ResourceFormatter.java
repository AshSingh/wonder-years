package com.cs410.getfit.server.json;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ResourceFormatter {
	Type incomingClazzArray;
	Type outgoingClazz;
	String encompassingString;
	
	protected ResourceFormatter(Type incomingClazzArray, Type outgoingClazz, String encompassingString) {
		this.incomingClazzArray = incomingClazzArray;
		this.outgoingClazz = outgoingClazz;
		this.encompassingString = encompassingString;
	}
	
	public String getJSONFormattedStringOfResource(List<? extends Object> objects) {
		JsonObject jsonFormattedChallenges = new JsonObject();

		JsonArray challengeObjects = new JsonArray();
		
		for (Object object: objects) {
			String objectJsonString = new Gson().toJson(object, outgoingClazz);
			JsonObject jsonObject = (JsonObject) new JsonParser().parse(objectJsonString);
			challengeObjects.add(jsonObject);	
		}
		jsonFormattedChallenges.add(encompassingString, challengeObjects);
		return jsonFormattedChallenges.toString();
	}

	public List<? extends Object> getResourcesFromJSONFormattedString(String jsonFormattedResourceString) {
		JsonObject object = (JsonObject)new JsonParser().parse(jsonFormattedResourceString);
		Object[] array = new Gson().fromJson(object.get(encompassingString), incomingClazzArray);
		return new ArrayList<Object>(Arrays.asList(array));
	}
}
