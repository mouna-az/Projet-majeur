package com.emergency.fire.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.emergency.fire.model.Caserne;
import com.emergency.fire.model.Vehicle;
import com.emergency.fire.repository.VehiculeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.model.dto.VehicleDto;


@Service
@Transactional
public class VehiculeService {
	
	
	private VehiculeRepository vRepository;
	private RemotevehiculeService remotevehiculeService;
	DisplayRunnable dRunnable;
	private Thread displayThread;
	private EmergencyService emergServ;
	
	public VehiculeService(VehiculeRepository vRepository , RemotevehiculeService remotevehiculeService,EmergencyService emergServ) {
		//Replace the @Autowire annotation....
		this.vRepository=vRepository;
		this.remotevehiculeService= remotevehiculeService ;
		this.emergServ=emergServ;
		
		//Create a Runnable is charge of executing cyclic actions 
		this.dRunnable=new DisplayRunnable(this, this.vRepository, this.remotevehiculeService,this.emergServ);
		
		// A Runnable is held by a Thread which manage lifecycle of the Runnable
		displayThread=new Thread(dRunnable);
		
		// The Thread is started and the method run() of the associated DisplayRunnable is launch
		displayThread.start();
	}
	
	public void addVehicule(Vehicle v) {
		//Ajout du véhicule à la simulation
		remotevehiculeService.addsimulation(v);
		//Après récupération du remoteId (id dans la simulation), on save dans notre bdd
		
		vRepository.save(v);
	}
	
	public Vehicle getVehicule(int id) {
		Optional<Vehicle> vOpt =vRepository.findById(id);
		if (vOpt.isPresent()) {
			return vOpt.get();
		}else {
			return null;
		}
	}
	
	
	public void putVehicule(Vehicle v) throws JsonMappingException, JsonProcessingException {
		//Update in local bdd
		vRepository.save(v);
		//Conversion to DTO type
		VehicleDto vToSend = convertToDto(v);
		//Update VehicleDto in simulation's bdd
		remotevehiculeService.updatesimulation(vToSend);
		
	}
	
	//supprimer un véhicule de notre base de données
		public Vehicle deleteVehicule(Integer id) {
			Optional<Vehicle> vOpt =vRepository.findById(id);
			System.out.println("trouvée dans notre base with id");
			if (vOpt.isPresent()) {
				VehicleDto vToDelete = convertToDto(vOpt.get());
				System.out.println("voiture convertie" + vToDelete);
				 remotevehiculeService.deleteVehicleSimulation(vToDelete);
				 vRepository.deleteById(id);
				 System.out.println("deleted dans notre base");

			}
			return null;
			
		}
	
	//Conversion Vehicle to VehicleDto
	public VehicleDto convertToDto(Vehicle v) {
		VehicleDto vtosend = new VehicleDto(v.getRemoteid(),v.getLon() ,v.getLat(), v.getType() , v.getEfficiency(),
				v.getLiquidType(), v.getLiquidQuantity(), v.getLiquidConsumption(), v.getFuel(),
				v.getFuelConsumption(), v.getCrewMember(), v.getCrewMemberCapacity(), v.getFacilityRefID());
		return vtosend;
	}
	
	public void update() {
		System.out.println(vRepository.findAll());
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
		
		//List of enum values
		List<com.project.model.dto.VehicleType> types = 
				new ArrayList<com.project.model.dto.VehicleType>(Arrays.asList(com.project.model.dto.VehicleType.values()));
		
		//Création casernes
		Caserne caserne1= new Caserne(1,4.857845, 45.750125, 8);
		Caserne caserne2= new Caserne(2, 5.015251,45.778411, 8);
		Caserne caserne3= new Caserne(3, 4.810286,45.719353, 9);
		
		List<Caserne> Casernes = new ArrayList<Caserne> () ;
		Casernes.add(caserne3);
		Casernes.add(caserne2);
		Casernes.add(caserne1);
		System.out.println(Casernes);
		
		int a = 0;
		
		for (Caserne c:Casernes) {
			//Creation of one vehicle per VehicleType
			for (int i=0; i<types.size();i++) {
				
				Vehicle v = new Vehicle( i+1+a, 0 , c.getLon(), c.getLat(), types.get(i) , 10,
										com.project.model.dto.LiquidType.ALL , 100, 1,
										100, 2, types.get(i).getVehicleCrewCapacity(), 
										types.get(i).getVehicleCrewCapacity(), c.getId()) ;
				addVehicule(v);
				int nbre = c.getVehicpresent();
				c.setVehicpresent(nbre+1);
			}
			a+=6;
			
			
			
		}
		
	}

}