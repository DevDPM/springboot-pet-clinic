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
    public String processUpdateForm(@PathVariable("petId") Long petId,
                                    @Valid Pet pet,
                                    BindingResult result,
                                    Owner owner,
                                    Model model) {

        Owner petOwner = petService.findById(petId).getOwner();

        // verify error OR petId -> ownerId == (InitBound) ownerId
        if(result.hasErrors() || !petOwner.getId().equals(owner.getId())) {
            pet.setOwner(owner);

            model.addAttribute("pet", pet);
            return  VIEW_PETS_CREATE_OR_UPDATE;
        } else {

            petService.updatePetById(petId, pet);
            return "redirect:/owners/" + owner.getId();
        }
    }
}
