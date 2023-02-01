package com.springframework.springbootpetclinic.controllers;

import com.springframework.springbootpetclinic.model.Owner;
import com.springframework.springbootpetclinic.model.Pet;
import com.springframework.springbootpetclinic.model.PetType;
import com.springframework.springbootpetclinic.service.OwnerService;
import com.springframework.springbootpetclinic.service.PetService;
import com.springframework.springbootpetclinic.service.PetTypeService;
import jakarta.validation.Valid;
import org.hibernate.Session;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {

    private static final String VIEW_PETS_CREATE_OR_UPDATE = "pets/createOrUpdatePetForm";
    private final PetTypeService petTypeService;
    private final OwnerService ownerService;
    private final PetService petService;

    public PetController(PetTypeService petTypeService, OwnerService ownerService, PetService petService) {
        this.petTypeService = petTypeService;
        this.ownerService = ownerService;
        this.petService = petService;
    }

    @ModelAttribute("types")
    public Collection<PetType> populatePetType() {
        return petTypeService.findAll();
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable("ownerId") Long ownerId) {
        return ownerService.findById(ownerId);
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/pets/new")
    public String initCreationForm(Owner owner,
                                   Model model) {
        Pet pet = new Pet();
        pet.setOwner(owner);
        owner.getPets().add(pet);
        model.addAttribute("pet", pet);
        return VIEW_PETS_CREATE_OR_UPDATE;
    }

    @PostMapping("/pets/new")
    public String processCreationForm(Owner owner,
                                      @Valid Pet pet,
                                      BindingResult result,
                                      Model model) {
        if (!StringUtils.isEmpty(pet.getName()) && pet.isNew() && owner.getPet(pet.getName(), true) != null) {
            result.rejectValue("name", "duplicate", "already exists");
        }
        pet.setOwner(owner);
        owner.getPets().add(pet);
        if (result.hasErrors()) {
            model.addAttribute("pet", pet);
            return VIEW_PETS_CREATE_OR_UPDATE;
        } else {
            petService.save(pet);
            return "redirect:/owners/" + owner.getId();
        }
    }

    @GetMapping("/pets/{petId}/edit")
    public String initUpdateForm(@PathVariable("petId") Long petId,
                                 Owner owner,
                                 Model model) {
        Pet pet = petService.findById(petId);

        model.addAttribute("pet", pet);
        return VIEW_PETS_CREATE_OR_UPDATE;
    }

    @PostMapping("/pets/{petId}/edit")
    public String processUpdateForm(@Valid Pet pet,
                                    BindingResult result,
                                    Owner owner,
                                    Model model) {

        if(result.hasErrors()) {
            pet.setOwner(owner);

            model.addAttribute("pet", pet);
            return  VIEW_PETS_CREATE_OR_UPDATE;
        } else {
            List<Pet> ownerPet = owner.getPets().stream()
                    .filter(e -> e.getId().equals(pet.getId()))
                    .toList();

            if (ownerPet.isEmpty())
                throw new RuntimeException("error");

            Pet convertPet = ownerPet.get(0);
            convertPet.setBirthDate(pet.getBirthDate());
            convertPet.setVisits(pet.getVisits());
            convertPet.setPetType(pet.getPetType());
            convertPet.setName(pet.getName());

            ownerService.save(owner);
            return "redirect:/owners/" + owner.getId();
        }
    }
}
