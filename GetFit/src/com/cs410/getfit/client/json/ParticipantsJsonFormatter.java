package com.cs410.getfit.client.json;

import java.util.ArrayList;
import java.util.List;

import com.cs410.getfit.shared.IncomingParticipantJsonModel;
import com.cs410.getfit.shared.OutgoingParticipantJsonModel;
import com.cs410.getfit.shared.ParticipantInfoJsonModel;
import com.cs410.getfit.shared.ResourceLink;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Cookies;

public class ParticipantsJsonFormatter {
	
	public enum ParticipantsJsonFields {
		participants,
		info,
		userId,
		isAdmin,
		dateJoined, 
		links;
	}

	/**
	 * Formats json to send to server containing info for challenge participants
	 * 
	 * @param models - participant model(s) to parse into json
	 * @return string of json of participant(s) info
	 */
	public static String formatParticipantsJsonInfo(List<IncomingParticipantJsonModel> models) {
		JSONArray participantsJson = new JSONArray();
		// create array of individual challenge json
		for(IncomingParticipantJsonModel model: models) {
		
			JSONObject participantJson = new JSONObject();
			JSONObject info = new JSONObject();
	
			// challenge info
			info.put(ParticipantsJsonFields.userId.toString(), new JSONNumber(model.getUserId()));
			info.put(ParticipantsJsonFields.isAdmin.toString(), JSONBoolean.getInstance(model.isAdmin()));
			
			participantJson.put(ParticipantsJsonFields.info.toString(), info);
			
			participantsJson.set(participantsJson.size(), participantJson);
		}
		
		// create json with the array of individual challenge json
		JSONObject requestJson = new JSONObject();
		requestJson.put(ParticipantsJsonFields.participants.toString(), participantsJson);
		requestJson.put("accessToken", new JSONString(Cookies.getCookie("accessToken")));
		
		return requestJson.toString();
	}
	
	/**
	 * Parses json from the server containing participant info
	 * 
	 * @param json - json returned from server
	 * @return list of OutgoingParticipantJsonModel - each model contains info 
	 * for a participant and rels for related HTTP requests
	 */
	public static List<OutgoingParticipantJsonModel> parseParticipantsJsonInfo(String json) {
		List<OutgoingParticipantJsonModel> models = new ArrayList<OutgoingParticipantJsonModel>();
		// entire json string
		JSONValue value = JSONParser.parseLenient(json);
		// json string as a value (superclass)
		JSONObject participants = value.isObject();
		// json array of participants
		JSONArray participantsArray = participants.get(ParticipantsJsonFields.participants.toString()).isArray();

		for (int i=0; i < participantsArray.size(); i++) {
			OutgoingParticipantJsonModel model = new OutgoingParticipantJsonModel();
			ParticipantInfoJsonModel infoModel = new ParticipantInfoJsonModel();
			
			// get single challenge from json
			JSONObject participant = participantsArray.get(i).isObject();
			// get info related to challenge
			JSONObject info = participant.get(ParticipantsJsonFields.info.toString()).isObject();
			// set infoModel with challenge info
			infoModel.setUserId((long) info.get(ParticipantsJsonFields.userId.toString()).isNumber().doubleValue());
			infoModel.setAdmin(info.get(ParticipantsJsonFields.isAdmin.toString()).isBoolean().booleanValue());
			infoModel.setDateJoined((long) info.get(ParticipantsJsonFields.dateJoined.toString()).isNumber().doubleValue());
			
			JSONArray linksArray = participant.get(ParticipantsJsonFields.links.toString()).isArray(); 
			List<ResourceLink> links = LinksJsonParser.getLinks(linksArray);
			
			model.setInfo(infoModel);
			model.setLinks(links);
			
			models.add(model);
		}
		return models;
	}
	
}
