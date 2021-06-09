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
import com.emergency.fire.repository.VehiculeRepository;
import com.project.model.dto.FireDto;
import com.project.model.dto.VehicleDto;

@Service
public class RemotevehiculeService {
	
	@Autowired
	VehiculeRepository vRepo;
	
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
	
	public List<Vehicle> getAllVehicle() {
		Iterable<Vehicle> list = vRepo.findAll();
		List<Vehicle> listVehicle = new ArrayList<Vehicle>();
		if (list == null) {
			return null;
		}
		else {
			for(Vehicle v:list) {
				listVehicle.add(v);
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
		vRepo.save(v);
	}
	
	public ResponseEntity<Boolean> updatesimulation(VehicleDto v) {
		restTemplate= new RestTemplate();
		HttpEntity<VehicleDto> requestUpdate = new HttpEntity<>(v);
		ResponseEntity<Boolean> reponse = restTemplate.exchange("http://localhost:8081/vehicle/"+v.getId(), HttpMethod.PUT, requestUpdate, Boolean.class);
		System.out.println(v.getId());
		return reponse;
	}
	
	public void deleteVehicleSimulation(VehicleDto vdelete) {
		restTemplate= new RestTemplate( );
		HttpEntity<VehicleDto> requestUpdate = new HttpEntity<>(vdelete);
		ResponseEntity<Boolean> reponse = restTemplate.exchange("http://localhost:8081/vehicle/"+vdelete.getId(), HttpMethod.DELETE, requestUpdate, Boolean.class);		
		System.out.println(reponse + " : " + vdelete.getId() + "delete dans le serveur");
		}

	public void resetFire() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getForObject("http://localhost:8081/fire/reset",void.class);	
		System.out.println("Fire reset");
	}
	

}
