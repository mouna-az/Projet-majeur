package com.emergency.fire.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.project.model.dto.VehicleDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.model.dto.FireDto;


@RestController
public class EmergencyRestCtr {

	@RequestMapping("/allfire") 
	public List<FireDto> getAllFire () {
		RestTemplate restTemplate = new RestTemplate();
		FireDto[] list = restTemplate.getForObject("http://localhost:8081/fire",FireDto[].class);
		System.out.println(list[0].toString()); 
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
	
	@RequestMapping("/allvehicle") 
	public List<VehicleDto> getAllVehicle () {
		
		RestTemplate restTemplate = new RestTemplate();
		VehicleDto[] list = restTemplate.getForObject("http://localhost:8081/vehicle",VehicleDto[].class);
		List<VehicleDto> listVehicle = new ArrayList<VehicleDto>();
		if (list == null) {
			return null;
		}
		else {
			for(VehicleDto f:list) {
				listVehicle.add(f);
			}
		}
		System.out.println("la liste:"+listVehicle);	
		return listVehicle;

	}
	
}


