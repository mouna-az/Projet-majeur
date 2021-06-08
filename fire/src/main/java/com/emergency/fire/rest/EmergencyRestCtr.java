package com.emergency.fire.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.project.model.dto.VehicleDto;
import com.emergency.fire.service.EmergencyService;
import com.emergency.fire.service.RemotevehiculeService;
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
		public VehicleDto[] getvehicule() {
			VehicleDto[] listvehicule = remotevehiculeService.getAllVehicleJson () ;
			System.out.println(listvehicule);
			return listvehicule; 
		}

		
		//affecter un vehicule sur un feu 
	  @RequestMapping("/affectation")
	    public int affectation () {
	        List<VehicleDto> vehiculelist = remotevehiculeService.getlallvehiculeList ();
	        List<FireDto> fireList = remotevehiculeService.getlallfireList();
	        for(VehicleDto v:vehiculelist) {
	            for (FireDto f:fireList) {
	                emergencyService.teleportation(v, f);   
	            }   
	        }   
	        return 1;
    }
	
}


