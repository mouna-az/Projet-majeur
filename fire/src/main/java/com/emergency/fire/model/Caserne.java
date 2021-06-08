package com.emergency.fire.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;


import javax.persistence.Entity;
import com.project.model.dto.LiquidType;
import com.project.model.dto.VehicleType;
@Entity

public class Caserne {
	@Id
	@GeneratedValue
	private Integer id;
	
	private double lon;
	private double lat;
	private int capacity;
	private int vehicpresent;
	
	public int getVehicpresent() {
		return vehicpresent;
	}
	public void setVehicpresent(int vehicpresent) {
		this.vehicpresent = vehicpresent;
	}
	public Caserne() {
		
	}
	public Caserne(Integer id,double lon,double lat, int capacity) {
		this.id=id;
		this.lon=lon;
		this.lat=lat;
		this.capacity=capacity;
		
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	


}
