package com.pet.petstore.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pet.petstore.model.Pet;
import com.pet.petstore.repository.PetStoreRepository;

@RestController
@RequestMapping(value="/pet")
public class PetStoreController {
	
	@Autowired
	PetStoreRepository petRepository;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Pet> get(@PathVariable("id") String id) {
		
		if(StringUtils.isBlank(id) || !StringUtils.isNumeric(id))
			return new ResponseEntity<Pet>(HttpStatus.BAD_REQUEST);
		
		Pet pet = petRepository.findById(new Integer(id));
		Optional<Pet> petOptional = Optional.ofNullable(pet);
		
		if (!petOptional.isPresent()) {
			return new ResponseEntity<Pet>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Pet>(pet, HttpStatus.OK);
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ResponseEntity<Pet> create(@RequestBody Pet pet) {
		Pet petResult = petRepository.save(pet);
		return new ResponseEntity<Pet>(petResult, HttpStatus.OK);
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public @ResponseBody List<Pet> getAllAvailable() {
		
		List<Pet> resultStream = petRepository.findAllAvailable();
		return resultStream;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable("id") String id) {
		
		if(StringUtils.isBlank(id) || !StringUtils.isNumeric(id))
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		
		try {
			petRepository.delete(Integer.valueOf(id));
		} catch(EmptyResultDataAccessException e) {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

}
