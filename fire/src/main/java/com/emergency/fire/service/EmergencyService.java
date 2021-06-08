package com.emergency.fire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.emergency.fire.model.Vehicle;
import com.emergency.fire.repository.VehiculeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.model.dto.FireDto;
import com.project.model.dto.VehicleDto;

@Service
public class EmergencyService {
	@Autowired
	VehiculeService vServ;
	
	@Autowired
	VehiculeRepository vRepository;

	public void teleportation(VehicleDto v, FireDto f) {
		// TODO Auto-generated method stub
		double lon = f.getLon();
		double lat = f.getLat();
		v.setLat(lat);
		v.setLon(lon);	
	}
	
	public void ligneDroite ( VehicleDto v, FireDto f) {
		
	}
	
	public JsonNode route ( Vehicle v, FireDto f) throws JsonMappingException, JsonProcessingException {
		String coords = v.getLon()+","+v.getLat()+";"+f.getLon()+","+f.getLat();
		String MAPBOX_ACCESS_TOKEN = "pk.eyJ1IjoiYXNpYXNpYXNpIiwiYSI6ImNrcGUxcmZxYzBmZ3oybm8xNmpvd2x1ODMifQ.N5SSom0UfDaBo55kRn2FBw";
		String APIRequest = "https://api.mapbox.com/directions/v5/mapbox/driving/"+coords
							+"?alternatives=true&geometries=geojson&steps=false&access_token="
							+MAPBOX_ACCESS_TOKEN;
		
		RestTemplate restTemplate= new RestTemplate();
		String APIResponse = restTemplate.getForObject(APIRequest,String.class);
		
	    ObjectMapper mapper = new ObjectMapper();
	    JsonNode json = mapper.readTree(APIResponse);
	    
	    JsonNode route = json.get("routes").get(0);
	    JsonNode geom = route.get("geometry");
	    JsonNode coordinates = geom.get("coordinates");
	    return coordinates;
	}
	
	public void seDeplacer(Vehicle v, FireDto f) throws JsonMappingException, JsonProcessingException {
		JsonNode coord = route(v, f);
		System.out.println("vehicule déplacé"); 

	}
	
	public void assigner(FireDto f) throws JsonMappingException, JsonProcessingException  {
		for (Vehicle v : vRepository.findAll()) {
			if ( v.isDispo() ) {
				v.setDispo(false);
				vRepository.save(v);
				System.out.println("vehicule " + v.getId() + " assigné");
				return;
			}
		}
	}	
}
