package com.emergency.fire.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.emergency.fire.model.Vehicle;
import com.project.model.dto.FireDto;
import com.project.model.dto.VehicleDto;

@Service
public class RemotevehiculeService {
	RestTemplate restTemplate ;
	
	
	//récupérer les véhicule du simulateur en json
	public VehicleDto[] getAllVehicleJson () {
		RestTemplate restTemplate = new RestTemplate();
		VehicleDto[] listvehicule = restTemplate.getForObject("http://localhost:8081/vehicle",VehicleDto[].class);
		return listvehicule;

	}
	
	//récupérer les feux du simulateur en json
	public FireDto[] getAllFireJson () {
		RestTemplate restTemplate = new RestTemplate();
		FireDto[] listfire = restTemplate.getForObject("http://localhost:8081/fire",FireDto[].class);
		return listfire;

	}
	
	//obtenir tous les véhicules sous forme de liste du simulateur
	public List<VehicleDto> getlallvehiculeList() {
		VehicleDto[] list = getAllVehicleJson();
		List<VehicleDto> listVehicle = new ArrayList<VehicleDto>();
		if (list == null) {
			return null;
		}
		else {
			for(VehicleDto f:list) {
				listVehicle.add(f);
			}
		}
		
		return listVehicle ;
	}
	
	public List<FireDto> getlallfireList () { 
		FireDto[] list = getAllFireJson();
		List<FireDto> fireList = new ArrayList<FireDto>();
		if (list == null) {
			return null;
		}
		else {
			for(FireDto f:list) {
				fireList.add(f);
			}
		}
		System.out.println(fireList); 
		return fireList;
	}
	
	
	//ajout d'un véhicule à la simulation
	public void addsimulation(Vehicle v) {
		restTemplate= new RestTemplate();
		Vehicle v1 = restTemplate.postForObject("http://localhost:8081/vehicle", v, Vehicle.class);
		v.setRemoteid(v1.getId());

	}
	
	public void updatesimulation(Vehicle v) {
		restTemplate= new RestTemplate();
		HttpEntity<Vehicle> requestUpdate = new HttpEntity<>(v);
		ResponseEntity<Vehicle> reponse = restTemplate.exchange("http://localhost:8081/vehicle/"+v.getRemoteid(), HttpMethod.PUT, requestUpdate, Vehicle.class);
		Vehicle v1 = reponse.getBody();
		v.setRemoteid(v1.getId());

	}

	

}
