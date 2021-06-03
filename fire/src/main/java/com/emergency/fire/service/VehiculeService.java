package com.emergency.fire.service;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.emergency.fire.model.Vehicle;
import com.emergency.fire.repository.VehiculeRepository;

@Service
public class VehiculeService {
	
	
	private VehiculeRepository vRepository;
	private RemotevehiculeService remotevehiculeService;
	DisplayRunnable dRunnable;
	private Thread displayThread;
	
	public VehiculeService(VehiculeRepository vRepository , RemotevehiculeService remotevehiculeService) {
		//Replace the @Autowire annotation....
		this.vRepository=vRepository;
		
		//Create a Runnable is charge of executing cyclic actions 
		this.dRunnable=new DisplayRunnable(this.vRepository);
		
		this.remotevehiculeService= remotevehiculeService ;
		
		// A Runnable is held by a Thread which manage lifecycle of the Runnable
		displayThread=new Thread(dRunnable);
		
		// The Thread is started and the method run() of the associated DisplayRunnable is launch
		displayThread.start();
	}
	
	public void addVehicule(Vehicle v) {
		Vehicle createdVehicule= vRepository.save(v);
		remotevehiculeService.addsimulation(v);
		System.out.println(createdVehicule);
	}
	
	public Vehicle getVehicule(int id) {
		Optional<Vehicle> vOpt =vRepository.findById(id);
		if (vOpt.isPresent()) {
			return vOpt.get();
		}else {
			return null;
		}
	}
	
	public void stopDisplay() {
		//Call the user defined stop method of the runnable
		this.dRunnable.stop();
		try {
			//force the thread to stop
			this.displayThread.join(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Bean(initMethod="init")
	public void init() {
		Vehicle v1=new Vehicle();
		Vehicle v2=new Vehicle();
		vRepository.save(v1);
		vRepository.save(v2);
	}
	
	
	
	
}