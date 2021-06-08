package com.emergency.fire.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.emergency.fire.model.Caserne;
import com.emergency.fire.model.Vehicle;

public interface CaserneRepository extends CrudRepository<Caserne, Integer> {

	public List<Caserne> findByid(String id);

}