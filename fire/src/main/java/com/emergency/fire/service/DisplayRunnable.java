package com.emergency.fire.service;

import com.emergency.fire.model.Vehicle;
import com.emergency.fire.repository.VehiculeRepository;

public class DisplayRunnable implements Runnable {

	private VehiculeRepository vrepo;
	boolean isEnd = false;

	public DisplayRunnable(VehiculeRepository vrepo) {
		this.vrepo = vrepo;
	}

	@Override
	public void run() {
		while (!this.isEnd) {
			try {
				Thread.sleep(10000);
				for (Vehicle v : this.vrepo.findAll()) {
					System.out.println(v.toString());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Runnable DisplayRunnable ends.... ");
	}

	public void stop() {
		this.isEnd = true;
	}
	
	

}