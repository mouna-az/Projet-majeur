package com.emergency.fire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.emergency.fire.model.Vehicle;
import com.project.model.dto.VehicleDto;

@Service
public class RemotevehiculeService {
	RestTemplate restTemplate ;
	
	//ajout d'un véhicule à la simulation
	public void addsimulation(Vehicle v) {
		restTemplate= new RestTemplate();
		Vehicle v1 = restTemplate.postForObject("http://localhost:8081/vehicle", v, Vehicle.class);
		v.setRemoteid(v1.getId());
		System.out.println(v1.getId());
		System.out.println(v.getRemoteid());
	}
	
	public void updatesimulation(VehicleDto v) {
		restTemplate= new RestTemplate();
		HttpEntity<VehicleDto> requestUpdate = new HttpEntity<>(v);
		ResponseEntity<Boolean> reponse = restTemplate.exchange("http://localhost:8081/vehicle/"+v.getId(), HttpMethod.PUT, requestUpdate, Boolean.class);
		System.out.println(v.getId());
	}
	

}
