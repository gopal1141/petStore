package com.pet.petstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pet.petstore.model.Pet;

public interface PetStoreRepository extends CrudRepository<Pet, Integer>{
	
	List<Pet> findByName(String name);
	
	@Query(value="select p from Pet p where status='Available'")
	List<Pet> findAllAvailable();
	
	Pet findById(Integer Id);
	
	Pet save(Pet pet);
	
	void delete(Integer id);

}
