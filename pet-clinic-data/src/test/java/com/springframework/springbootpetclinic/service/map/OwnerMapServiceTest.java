package com.springframework.springbootpetclinic.service.map;

import com.springframework.springbootpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
class OwnerMapServiceTest {

    OwnerMapService ownerMapService;
    private final Long ownerId = 1L;
    private Owner owner;
    private final String lastName = "Smith";
    private Owner addOwner1;
    private Owner addOwner2;

    @BeforeEach
    void setUp() {
        ownerMapService = new OwnerMapService(new PetTypeMapService(), new PetMapService());
        owner = Owner.builder().id(ownerId).lastName(lastName).build();
        addOwner1 = Owner.builder().id(ownerId+1).lastName(lastName).build();
        addOwner2 = Owner.builder().id(ownerId+2).lastName(lastName).build();
        ownerMapService.save(owner);
        ownerMapService.save(addOwner1);
        ownerMapService.save(addOwner2);
    }

    @Test
    void findAll() {
        Set<Owner> ownerSet = ownerMapService.findAll();

        assertEquals(3, ownerSet.size());
    }

    @Test
    void findById() {
        Owner receivedOwner = ownerMapService.findById(ownerId);

        assertEquals(ownerId, receivedOwner.getId());
    }

    @Test
    void saveExistingId() {
        Long SaveId = 2L;
        Owner newOwner = Owner.builder().id(SaveId).build();
        Owner savedOwner = ownerMapService.save(newOwner);

        assertEquals(SaveId, savedOwner.getId());
    }

    @Test
    void saveNoId() {
        Owner savedOwner = ownerMapService.save(Owner.builder().build());

        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
    }

    @Test
    void delete() {
        int ownerSetSize = ownerMapService.findAll().size();
        assertEquals(ownerSetSize, ownerMapService.findAll().size());
        ownerMapService.delete(owner);
        assertEquals(ownerSetSize-1, ownerMapService.findAll().size());
    }

    @Test
    void deleteById() {
        int ownerSetSize = ownerMapService.findAll().size();
        assertEquals(ownerSetSize, ownerMapService.findAll().size());
        ownerMapService.deleteById(ownerId);
        assertEquals(ownerSetSize-1, ownerMapService.findAll().size());
    }

    @Test
    void findOneByLastName() {
        Set<Owner> mockOwnersByLastName = new HashSet<>();
        mockOwnersByLastName.add(owner);
        mockOwnersByLastName.add(addOwner1);
        mockOwnersByLastName.add(addOwner2);
        Set<Owner> foundOwnersByLastName = ownerMapService.findByLastName(lastName);

        assertEquals(mockOwnersByLastName, foundOwnersByLastName);
        assertEquals(mockOwnersByLastName.size(), foundOwnersByLastName.size());
    }

    @Test
    void findMultipleByLastName() {
        Set<Owner> mockOwnersByLastName = new HashSet<>();

        mockOwnersByLastName.add(owner);
        mockOwnersByLastName.add(addOwner1);
        mockOwnersByLastName.add(addOwner2);

        Set<Owner> foundOwnersByLastName = ownerMapService.findByLastName(lastName);

        assertEquals(mockOwnersByLastName, foundOwnersByLastName);
        assertEquals(mockOwnersByLastName.size(), foundOwnersByLastName.size());
    }

    @Test
    void findLastNameNotFound() {
        Set<Owner> foundOwnersByLastName = ownerMapService.findByLastName("Not existing lastname");

        assertNull(foundOwnersByLastName);
    }
}