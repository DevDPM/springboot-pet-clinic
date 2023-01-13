package com.springframework.springbootpetclinic.bootstrap;

import com.springframework.springbootpetclinic.model.Owner;
import com.springframework.springbootpetclinic.model.Pet;
import com.springframework.springbootpetclinic.model.PetType;
import com.springframework.springbootpetclinic.model.Vet;
import com.springframework.springbootpetclinic.services.OwnerService;
import com.springframework.springbootpetclinic.services.PetTypeService;
import com.springframework.springbootpetclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;


    public DataLoader(OwnerService ownerService,
                      VetService vetService,
                      PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
    }

    @Override
    public void run(String... args) throws Exception {

        // --------------------------------------------------
        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogType = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        PetType savedCatType = petTypeService.save(cat);

        // ---------------------------------------------------


        Owner owner1 = new Owner();
        owner1.setFirstName("FirstPerson1");
        owner1.setLastName("LastPerson1");
        owner1.setAddress("Address1");
        owner1.setCity("City1");
        owner1.setPhoneNumber("0654321098");

        Pet pet1 = new Pet();
        pet1.setPetType(savedDogType);
        pet1.setOwner(owner1);
        pet1.setName("Pet1");
        pet1.setBirthDate(LocalDate.of(2005, 5, 12));

        owner1.getPets().add(pet1);
        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("FirstPerson2");
        owner2.setLastName("LastPerson2");
        owner2.setAddress("Address2");
        owner2.setCity("City2");
        owner2.setPhoneNumber("0654321098");

        Pet pet2 = new Pet();
        pet2.setPetType(savedCatType);
        pet2.setOwner(owner2);
        pet2.setName("Pet2");
        pet2.setBirthDate(LocalDate.of(2020, 7, 24));

        owner2.getPets().add(pet2);
        ownerService.save(owner2);

        System.out.println("Loaded Owners....");

        Vet vet1 = new Vet();
        vet1.setFirstName("FirstVet1");
        vet1.setLastName("LastVet1");

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("FirstVet2");
        vet2.setLastName("LastVet2");

        vetService.save(vet2);

        System.out.println("Loaded Vets....");
    }
}
