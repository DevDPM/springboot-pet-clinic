package com.springframework.springbootpetclinic.service.SpringDataJPA;

import com.springframework.springbootpetclinic.model.Pet;
import com.springframework.springbootpetclinic.repository.PetRepository;
import com.springframework.springbootpetclinic.service.PetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class PetJpaService implements PetService {

    private final PetRepository petRepository;

    public PetJpaService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public Set<Pet> findAll() {
        Set<Pet> pets = new HashSet<>();
        petRepository.findAll().forEach(pet -> {
            pets.add(pet);
        });
        return pets;
    }

    @Override
    public Pet findById(Long id) {
        return petRepository.findById(id).isPresent() ? petRepository.findById(id).get() : null;
    }

    @Override
    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }

    @Override
    public void delete(Pet pet) {
        petRepository.delete(pet);
    }

    @Override
    public void deleteById(Long id) {
        petRepository.deleteById(id);
    }

    @Override
    public Pet updatePetById(Long id, Pet petDto) {
        return petRepository.findById(id).map(updatePet -> {
            updatePet.setBirthDate(petDto.getBirthDate());
            updatePet.setVisits(petDto.getVisits());
            updatePet.setPetType(petDto.getPetType());
            updatePet.setName(petDto.getName());
            return updatePet;
        }).orElse(null);

    }
}
