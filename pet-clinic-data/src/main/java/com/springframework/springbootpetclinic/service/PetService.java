package com.springframework.springbootpetclinic.service;

import com.springframework.springbootpetclinic.model.Pet;

public interface PetService extends CrudService<Pet, Long> {

    Pet updatePetById(Long id, Pet petDto);

}
