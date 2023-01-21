package com.springframework.springbootpetclinic.service.SpringDataJPA;

import com.springframework.springbootpetclinic.model.Owner;
import com.springframework.springbootpetclinic.repository.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerJpaServiceTest {

    @Mock
    OwnerRepository ownerRepository;

    @InjectMocks
    OwnerJpaService ownerJpaService;

    private final String LAST_NAME = "smith";

    @BeforeEach
    void setUp() {
//        ownerJpaService = new OwnerJpaService(ownerRepository);
    }

    @Test
    void findAll() {
        Set<Owner> returnOwners = new HashSet<>();
        returnOwners.add(Owner.builder().id(1L).build());
        returnOwners.add(Owner.builder().id(2L).build());
        returnOwners.add(Owner.builder().id(3L).build());
        returnOwners.add(Owner.builder().id(4L).build());

        when(ownerJpaService.findAll()).thenReturn(returnOwners);

        Set<Owner> receivedOwners = ownerJpaService.findAll();

        assertNotNull(receivedOwners);
        assertEquals(returnOwners, receivedOwners);
        assertEquals(returnOwners.size(), receivedOwners.size());

    }

    @Test
    void findAllNoReturn() {
        Set<Owner> returnOwners = new HashSet<>();

        when(ownerJpaService.findAll()).thenReturn(returnOwners);

        Set<Owner> receivedOwners = ownerJpaService.findAll();

        assertNull(ownerJpaService.findAll());
    }

    @Test
    void findById() {
        Owner returnOwner = Owner.builder().id(1L).build();

        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(returnOwner));

        Owner owner = ownerJpaService.findById(1L);

        assertNotNull(owner);
    }

    @Test
    void findByNotExistingId() {

        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());

        Owner owner = ownerJpaService.findById(1L);

        assertNull(owner);
    }

    @Test
    void save() {
        Owner saveOwner = Owner.builder().id(1L).build();

        when(ownerRepository.save(any())).thenReturn(saveOwner);

        Owner owner = ownerJpaService.save(saveOwner);

        assertEquals(saveOwner,owner);

    }

    @Test
    void delete() {

        ownerJpaService.delete(Owner.builder().id(1L).build());

        verify(ownerRepository).delete(any());
    }

    @Test
    void deleteById() {

        ownerJpaService.deleteById(1L);

        verify(ownerRepository).deleteById(anyLong());
    }

    @Test
    void findByLastName() {

        Set<Owner> returnOwnersByLastName = new HashSet<>();
        Owner owner = Owner.builder().id(1L).lastName(LAST_NAME).build();
        returnOwnersByLastName.add(owner);

        when(ownerRepository.findAll()).thenReturn(returnOwnersByLastName);

        Set<Owner> owners = ownerJpaService.findByLastName(LAST_NAME);

        assertEquals(returnOwnersByLastName, owners);
        assertEquals(returnOwnersByLastName.size(), owners.size());


    }
}