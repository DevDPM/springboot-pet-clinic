package com.springframework.springbootpetclinic.controllers;

import com.springframework.springbootpetclinic.model.Owner;
import com.springframework.springbootpetclinic.service.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/owners")
@Controller
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @RequestMapping({"","/"})
    public String listOwners(Model model) {

        model.addAttribute("owners", ownerService.findAll());
        return "owners/index";
    }

    @GetMapping
    public String processFindForm(Owner owner, BindingResult result, Model model) {

        if (owner.getLastName() == null) {
            owner.setLastName(""); // empty string
        }

        // find owners by LastName
        List<Owner> results = ownerService.findAllByLastNameLike(owner.getLastName());
        if (results.isEmpty()) {
            // no owners found
            result.rejectValue("lastName","notFound","Not found");
            return "owners/findOwners";
        } else if (results.size() == 1) {
            // 1 owner found
            owner = results.get(0);
            return "redirect:/owners/" + owner.getId();
        } else {
            // multiple owners with identical lastname found
            model.addAttribute("selections", results);
            return "owners/ownersList";
        }
    }

    @GetMapping("/find")
    public String findOwners(Model model) {
        model.addAttribute("owner", Owner.builder().build());
        return "owners/findOwners";
    }

    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable("ownerId") Long ownerId) {
        ModelAndView mav = new ModelAndView("owners/ownersDetails");
        mav.addObject(ownerService.findById(ownerId));
        return mav;
    }

    @InitBinder
    public void setAllowFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }


}
