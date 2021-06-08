package com.emergency.fire.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

import com.emergency.fire.model.Vehicle;

public interface VehiculeRepository extends CrudRepository<Vehicle, Integer> {

	public List<Vehicle> findByid(String id);
	public Optional<Vehicle> findByRemoteid(Integer remoteid);
	public List<Vehicle> deleteByRemoteid(Integer remoteid);
}
