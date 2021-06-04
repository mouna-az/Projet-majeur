package com.emergency.fire.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.emergency.fire.model.Vehicle;
import com.emergency.fire.service.RemotevehiculeService;
import com.emergency.fire.service.VehiculeService;
import com.project.model.dto.FireDto;

@RestController
public class VehiculeRestCtr {
	
	@Autowired
	VehiculeService vService;
	
	
	RestTemplate restTemplate ;
	
	@RequestMapping(method=RequestMethod.POST,value="/vehicle/add")
	public void addvehicule(@RequestBody Vehicle v) {
		vService.addVehicule(v);
		
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/vehicle/{id}")
	public Vehicle getvehicule(@PathVariable String id) {
		Vehicle v=vService.getVehicule(Integer.valueOf(id));
		return v;
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/vehicle/{id}")
	public Vehicle putvehicule(@RequestBody Vehicle v) {
		//vService.putVehicule(Integer.valueOf(id));
		return v;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/stopdisplay")
	public void stopDisplay() {
		vService.stopDisplay();
	}
	



}
