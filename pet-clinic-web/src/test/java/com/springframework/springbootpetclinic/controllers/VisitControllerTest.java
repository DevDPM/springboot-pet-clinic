package com.springframework.springbootpetclinic.controllers;

import com.springframework.springbootpetclinic.model.Owner;
import com.springframework.springbootpetclinic.model.Pet;
import com.springframework.springbootpetclinic.model.PetType;
import com.springframework.springbootpetclinic.model.Visit;
import com.springframework.springbootpetclinic.service.OwnerService;
import com.springframework.springbootpetclinic.service.PetService;
import com.springframework.springbootpetclinic.service.PetTypeService;
import com.springframework.springbootpetclinic.service.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    @Mock
    VisitService visitService;

    @Mock
    PetService petService;

    @InjectMocks
    VisitController visitController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();
    }

    @Test
    void initNewVisitForm() throws Exception {
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setOwner(Owner.builder().id(1L).build());
        pet.getVisits().add(Visit.builder().pet(pet).build());

        when(petService.findById(anyLong())).thenReturn(pet);

        mockMvc.perform(MockMvcRequestBuilders.get("/owners/1/pets/1/visits/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("visit","pet"))
                .andExpect(MockMvcResultMatchers.view().name("pets/createOrUpdateVisitForm"));
    }

    @Test
    void processNewVisitForm() throws Exception {
        // todo
    }
}