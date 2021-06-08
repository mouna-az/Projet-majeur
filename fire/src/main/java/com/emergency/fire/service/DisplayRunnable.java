package com.emergency.fire.service;

import org.springframework.web.client.RestTemplate;

import com.emergency.fire.model.Vehicle;
import com.emergency.fire.repository.VehiculeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.project.model.dto.FireDto;

public class DisplayRunnable implements Runnable {
	
	private VehiculeService vService;
	private EmergencyService emergServ;
	private VehiculeRepository vrepo;
	private RemotevehiculeService remoteService;
	
	boolean isEnd = false;

	public DisplayRunnable(VehiculeService vService, VehiculeRepository vRepo, RemotevehiculeService remoteService,EmergencyService emergServ) {
		this.vService = vService;
		this.vrepo = vRepo;
		this.remoteService = remoteService;
		this.emergServ=emergServ;
	}

	@Override
	public void run() {
		while (!this.isEnd) {
			try {
				Thread.sleep(1000); //10 seconde
				//appeler les fonctions update déplacement des voitures
				FireDto[] listfire = this.remoteService.getAllFireJson();
				if (listfire != null) {
					for (FireDto f : listfire) {
						emergServ.assigner(f);
						
					}
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
		System.out.println("Runnable DisplayRunnable ends.... ");
	}

	public void stop() {
		this.isEnd = true;
	}
	
	

}