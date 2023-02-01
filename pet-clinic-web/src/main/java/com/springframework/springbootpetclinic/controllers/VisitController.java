package com.springframework.springbootpetclinic.controllers;

import com.springframework.springbootpetclinic.model.Owner;
import com.springframework.springbootpetclinic.model.Pet;
import com.springframework.springbootpetclinic.model.Visit;
import com.springframework.springbootpetclinic.service.PetService;
import com.springframework.springbootpetclinic.service.VisitService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
public class VisitController {

    private static final String VIEW_CREATE_AND_UPDATE_VISIT_FORM = "pets/createOrUpdateVisitForm";
    private final PetService petService;
    private final VisitService visitService;


    public VisitController(PetService petService, VisitService visitService) {
        this.petService = petService;
        this.visitService = visitService;
    }

    @InitBinder
    public void setAllFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("visit")
    public Visit loadPetWithVisit(@PathVariable("petId") Long petId, Model model) {
        Pet pet = petService.findById(petId);
        model.addAttribute("pet", pet);
        Visit visit = new Visit();
        pet.getVisits().add(visit);
        visit.setPet(pet);
        return visit;
    }

    @GetMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String initNewVisitForm(@PathVariable("ownerId") Long ownerId,
                                   @PathVariable("petId") Long petId,
                                   Model model) {

        return VIEW_CREATE_AND_UPDATE_VISIT_FORM;
    }

    @PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String processNewVisitForm(@PathVariable("ownerId") Long ownerId,
                                      @PathVariable("petId") Long petId,
                                      @Valid Visit visit,
                                      BindingResult result) {
        if (result.hasErrors()) {
            return VIEW_CREATE_AND_UPDATE_VISIT_FORM;
        } else {
            Visit saveVisit = visitService.save(visit);
            Owner visitPetOwner = saveVisit.getPet().getOwner();
            return "redirect:/owners/" + visitPetOwner.getId();
        }
    }







}
