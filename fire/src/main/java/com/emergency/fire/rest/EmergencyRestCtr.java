package com.emergency.fire.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.project.model.dto.VehicleDto;
import com.emergency.fire.model.Vehicle;
import com.emergency.fire.service.EmergencyService;
import com.emergency.fire.service.RemotevehiculeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.project.model.dto.FireDto;


@RestController
public class EmergencyRestCtr {
	
	@Autowired 
	EmergencyService emergencyService;
	@Autowired 
	RemotevehiculeService remotevehiculeService;

	//regarder les feux du simulateur 
		@RequestMapping("/allfire") 
		public FireDto[] getFireJson () {
			RestTemplate restTemplate = new RestTemplate();
			FireDto[] listfire = remotevehiculeService.getAllFireJson () ;
			System.out.println(listfire);
			return listfire; 
		}
		
		//regarder les véhicules présents dans le simulateur format json
		@RequestMapping("/allvehicule")  
		public List<Vehicle> getvehicule() {
			List<Vehicle> listvehicule = remotevehiculeService.getAllVehicle() ;
			System.out.println(listvehicule);
			return listvehicule; 
		}
		
		//affecter un vehicule sur un feu 
	  
		//reset Feu LISTE
		@RequestMapping("/fire/reset") 
		public void resetFire() {
			RestTemplate restTemplate = new RestTemplate();
			remotevehiculeService.resetFire() ;
		}
		
		//route
		@RequestMapping("/route/{d1}/{d2}/{a1}/{a2}") 
		public JsonNode getRoute(@PathVariable String d1,@PathVariable String d2,@PathVariable String a1,@PathVariable String a2) throws JsonMappingException, JsonProcessingException {
			RestTemplate restTemplate = new RestTemplate();
			return emergencyService.getRoute(d1,d2,a1,a2) ;
		}
	
	
	
}


