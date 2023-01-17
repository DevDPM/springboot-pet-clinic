package com.springframework.springbootpetclinic.service.map;

import com.springframework.springbootpetclinic.model.Owner;
import com.springframework.springbootpetclinic.model.Pet;
import com.springframework.springbootpetclinic.model.PetType;
import com.springframework.springbootpetclinic.service.OwnerService;
import com.springframework.springbootpetclinic.service.PetService;
import com.springframework.springbootpetclinic.service.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({"default","map"})
public class OwnerMapService extends AbstractMapService<Owner, Long> implements OwnerService {

    private final PetTypeService petTypeService;
    private final PetService petService;

    public OwnerMapService(PetTypeService petTypeService, PetService petService) {


        System.out.println("$(OwnerMapService): ###########   MAP profile active ###########");


        this.petTypeService = petTypeService;
        this.petService = petService;
    }

    @Override
    public Set<Owner> findAll() {
        return super.findAll();
    }

    @Override
    public Owner findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Owner save(Owner object) {

        Owner saveOwner = null;

        if (object != null) {
            saveOwner = object;

            if (saveOwner.getPets() != null) {
                saveOwner.getPets().forEach(pet -> {
                    //*
                    // Saving owner requires a list of Pets, each Pet requires a PetType
                    // <1> Check each Pet for required PetType.
                    // <2> If petType not exists, add new relational petType.
                    // <3> If requirements successfully: add Pet
                    // *//

                    // <1>
                    if (pet.getPetType() != null) {

                        // <2>
                        if (pet.getPetType().getId() == null) {
                            PetType savePetType = pet.getPetType();
                            pet.setPetType(petTypeService.save(savePetType));
                        }

                    } else {
                        throw new RuntimeException("PetType is required.");
                    }

                    // <3>
                    if (pet.getId() == null) {
                        Pet savePet = petService.save(pet);
                        pet.setId(savePet.getId());
                    }
                });
            }
        }

        return super.save(saveOwner);
    }

    @Override
    public void delete(Owner object) {
        super.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public Owner findByLastName(String lastName) {
        // TO-DO
        return null;
    }
}
