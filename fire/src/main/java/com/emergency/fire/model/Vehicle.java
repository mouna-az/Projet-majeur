package com.emergency.fire.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.project.model.dto.LiquidType;
import com.project.model.dto.VehicleType;

//https://www.amitph.com/spring-entity-to-dto/
@Entity
public class Vehicle {
	public static final int CREW_MEMBER_START_VALUE=-1;

	@Id
	@GeneratedValue
	private Integer id;
	private Integer remoteid;
	private double lon;
	private double lat;
	private VehicleType type;
	private float efficiency; // need all crew member to reach full efficiency value between 0 and 10
	private LiquidType liquidType; // type of liquid effective to type of fire
	private float liquidQuantity; // total quantity of liquid
	private float liquidConsumption; // per second when use
	private float fuel;		// total quantity of fuel
	private float fuelConsumption; // per km
	private int crewMember;
	private int crewMemberCapacity;
	private Integer facilityRefID;
	private boolean dispo = true;
	private int fireAssigned = 0;
	private int coordNow = 0;
	
	public Vehicle() {
		crewMember= CREW_MEMBER_START_VALUE;
		liquidType=LiquidType.ALL;
	}

	public Vehicle(int id, int remoteid,double lon, double lat, com.project.model.dto.VehicleType type, float efficiency,
			com.project.model.dto.LiquidType liquidType, float liquidQuantity, float liquidConsumption, float fuel,
			float fuelConsumption, int crewMember, int crewMemberCapacity, Integer facilityRefID) {
		super();
		this.id=id;
		this.setRemoteid(remoteid);
		this.lon = lon;
		this.lat = lat;
		this.type = type;
		this.efficiency = efficiency;
		this.liquidType = liquidType;
		this.liquidQuantity = liquidQuantity;
		this.liquidConsumption = liquidConsumption;
		this.fuel = fuel;
		this.fuelConsumption = fuelConsumption;
		this.crewMember = crewMember;
		this.crewMemberCapacity = crewMemberCapacity;
		this.facilityRefID = facilityRefID;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public VehicleType getType() {
		return type;
	}

	public void setType(VehicleType type) {
		this.type = type;
	}

	public float getEfficiency() {
		return efficiency;
	}

	public void setEfficiency(float efficiency) {
		this.efficiency = efficiency;
	}

	public LiquidType getLiquidType() {
		return liquidType;
	}

	public void setLiquidType(LiquidType liquidType) {
		this.liquidType = liquidType;
	}

	public float getLiquidQuantity() {
		return liquidQuantity;
	}

	public void setLiquidQuantity(float liquidQuantity) {
		this.liquidQuantity = liquidQuantity;
	}

	public float getLiquidConsumption() {
		return liquidConsumption;
	}

	public void setLiquidConsumption(float liquidConsumption) {
		this.liquidConsumption = liquidConsumption;
	}

	public float getFuel() {
		return fuel;
	}

	public void setFuel(float fuel) {
		this.fuel = fuel;
	}

	public float getFuelConsumption() {
		return fuelConsumption;
	}

	public void setFuelConsumption(float fuelConsumption) {
		this.fuelConsumption = fuelConsumption;
	}

	public int getCrewMember() {
		return crewMember;
	}

	public void setCrewMember(int crewMember) {
		this.crewMember = crewMember;
	}

	public int getCrewMemberCapacity() {
		return crewMemberCapacity;
	}

	public void setCrewMemberCapacity(int crewMemberCapacity) {
		this.crewMemberCapacity = crewMemberCapacity;
	}

	public Integer getFacilityRefID() {
		return facilityRefID;
	}

	public void setFacilityRefID(Integer facilityRefID) {
		this.facilityRefID = facilityRefID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRemoteid() {
		return remoteid;
	}

	public void setRemoteid(Integer remoteid) {
		this.remoteid = remoteid;
	}

	public boolean isDispo() {
		return dispo;
	}

	public void setDispo(boolean dispo) {
		this.dispo = dispo;
	}

	public int getFireAssigned() {
		return fireAssigned;
	}

	public void setFireAssigned(int fireAssigned) {
		this.fireAssigned = fireAssigned;
	}

	public int getCoordNow() {
		return coordNow;
	}

	public void setCoordNow(int coordNow) {
		this.coordNow = coordNow;
	}
	

}
