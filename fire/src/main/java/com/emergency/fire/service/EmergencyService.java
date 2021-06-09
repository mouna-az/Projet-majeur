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
import com.project.model.dto.Coord;
import com.project.model.dto.FireDto;

@Service
public class EmergencyService {
	@Autowired
	VehiculeService vServ;
	
	@Autowired
	VehiculeRepository vRepository;
	
	@Autowired
	RemotevehiculeService remotevehiculeService;

	public void teleportation(Vehicle v, FireDto f) throws JsonMappingException, JsonProcessingException {
		// TODO Auto-generated method stub
		double lon = f.getLon();
		double lat = f.getLat();
		v.setLat(lat);
		v.setLon(lon);
		vServ.putVehicule(v);
	}
	
	public JsonNode getRoute(String d1, String d2, String a1, String a2) throws JsonMappingException, JsonProcessingException {
		String coords = d1+","+d2+";"+a1+","+a2;
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
	
	
	//Déplacer un véhicule selon la route fourni par getRoute
	public void seDeplacer(Vehicle v, FireDto f) throws JsonMappingException, JsonProcessingException {
		
		String d1 = Double. toString(v.getLon());
		String d2=Double. toString(v.getLat());
		String a1=Double. toString(f.getLon());
		String a2=Double. toString(f.getLat());
		JsonNode routeNode = getRoute(d1, d2, a1, a2);
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode pointNode = routeNode.get(1);
		
		if (routeNode.size()>2) {
			String lonStr = pointNode.get(0).toString();
			String latStr = pointNode.get(1).toString();
			System.out.println(lonStr + " " + latStr);
		    double lon = Double.parseDouble(lonStr);  
		    double lat = Double.parseDouble(latStr); 
		    System.out.println(lon + " " + lat);
			v.setLat(lat);
			v.setLon(lon);
			vServ.putVehicule(v);
		}
		else {
			v.setLat(f.getLat());
			v.setLon(f.getLon());
			vServ.putVehicule(v);
			FireDto[] listfire = this.remotevehiculeService.getAllFireJson();
			int c = 0;
			if (listfire != null) {
				for (FireDto fire : listfire) {
					if (fire.getId()==v.getFireAssigned()) {
						c = 1;
					}
					
				}
			}
			
			if (c!=1) {
				v.setDispo(true);
				v.setFireAssigned(0);
			}
		}

	}
	
	public void retourCaserne() {
		
	}
	
	public void assigner(FireDto f) throws JsonMappingException, JsonProcessingException  {
		for (Vehicle v : vRepository.findAll()) {
			if ( v.isDispo() ) {
				System.out.println(v.getLiquidType() + " " +  v.getType());
				v.setDispo(false);
				v.setFireAssigned(f.getId());
				vRepository.save(v);
				seDeplacer( v, f);
				System.out.println("Vehicule " + v.getId() + " assigné");
				return;
			}
			else if (f.getId() == v.getFireAssigned()){
				
				seDeplacer(v,f);
			}
		}
	}

}
