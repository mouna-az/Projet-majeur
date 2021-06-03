package com.emergency.fire.service;

import org.springframework.stereotype.Service;

import com.project.model.dto.FireDto;
import com.project.model.dto.VehicleDto;

@Service
public class EmergencyService {

	public void teleportation(VehicleDto v, FireDto f) {
		// TODO Auto-generated method stub
		double lon = f.getLon();
		double lat = f.getLat();
		v.setLat(lat);
		v.setLon(lon);	
	}
	
	public void ligneDroite ( VehicleDto v, FireDto f) {
		
	}
	
	public void depplacementReseau ( VehicleDto v, FireDto f) {
		
	}
	


}
