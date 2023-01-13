package com.springframework.springbootpetclinic.bootstrap;

import com.springframework.springbootpetclinic.model.*;
import com.springframework.springbootpetclinic.services.OwnerService;
import com.springframework.springbootpetclinic.services.PetTypeService;
import com.springframework.springbootpetclinic.services.SpecialtyService;
import com.springframework.springbootpetclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialtyService specialtyService;


    public DataLoader(OwnerService ownerService,
                      VetService vetService,
                      PetTypeService petTypeService,
                      SpecialtyService specialtyService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialtyService = specialtyService;
    }

    @Override
    public void run(String... args) throws Exception {

        int count = petTypeService.findAll().size();
        if (count == 0)
            loadData();
    }

    private void loadData() {
        // --------------------------------------------------
        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogType = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        PetType savedCatType = petTypeService.save(cat);

        // ---------------------------------------------------
        Specialty radiology = new Specialty();
        radiology.setDescription("Radiology");
        Specialty savedRadiology = specialtyService.save(radiology);

        Specialty surgery = new Specialty();
        surgery.setDescription("Surgery");
        Specialty savedSurgery = specialtyService.save(surgery);

        Specialty dentistry = new Specialty();
        dentistry.setDescription("Dentistry");
        Specialty savedDentistry = specialtyService.save(dentistry);

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

        // ---------------------------------------------------
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


        // ---------------------------------------------------
        Vet vet1 = new Vet();
        vet1.setFirstName("VetPerson1");
        vet1.setLastName("VetLastName1");
        vet1.getSpecialties().add(savedRadiology);

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("VetPerson2");
        vet2.setLastName("VetLastName2");
        vet2.getSpecialties().add(dentistry);

        vetService.save(vet2);

        System.out.println("Loaded Vets....");
    }
}
