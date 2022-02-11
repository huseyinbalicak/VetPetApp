package com.ozgursoft.vetpetapp.controller;

import com.ozgursoft.vetpetapp.model.Animal;
import com.ozgursoft.vetpetapp.model.Owner;
import com.ozgursoft.vetpetapp.services.AnimalService;
import com.ozgursoft.vetpetapp.services.OwnerService;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Controller
public class PetController {

    private final AnimalService animalService;
    private final OwnerService ownerService;

    public PetController(AnimalService animalService, OwnerService ownerService) {
        this.animalService = animalService;
        this.ownerService = ownerService;
    }


    @GetMapping("pet/pets")
    public String getAllPets(Model model) {

        return viewPetPage(model, 1, "name", "asc");

    }


    @GetMapping("/page_pet/{pageNum}")
    public String viewPetPage(Model model,
                           @PathVariable(name = "pageNum") int pageNum,
                           @Param("sortField") String sortField,
                           @Param("sortDir") String sortDir) {

        Page<Animal> page = animalService.getAnimals(pageNum, sortField, sortDir);

        List<Animal> petList = page.getContent();

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("petList", petList);

        return "pets";
    }








    @GetMapping("/pets/new{ownerId}")
    public String petAddForm(@PathVariable(value = "ownerId") long ownerId, Model model, @ModelAttribute("pet") Animal pet) {

        Optional<Owner> owner = ownerService.findById(ownerId);
        if (owner.isPresent()) {
            model.addAttribute("owner", owner.get());
            model.addAttribute("pet", new Animal());
            return "addPet";
        }
        return "owners";

    }


    @PostMapping("/pets/new{ownerId}")
    public String petAdd(@ModelAttribute("pet") Animal pet, @PathVariable(value = "ownerId") long ownerId, Model model) {

        Optional<Owner> owner = ownerService.findById(ownerId);
        if (owner.isPresent()) {
            pet.setOwner(owner.get());
            animalService.save(pet);
            model.addAttribute("owner", owner.get());
            model.addAttribute("pet", new Animal());
            return "redirect:/owners/" + owner.get().getId();
        }

        return "owners";
    }

    @GetMapping("/updatePet/{petId}")
    public String showFormForUpdate(@PathVariable(value = "petId") Long petId, Model model) {
        Animal animal = animalService.getPetById(petId);
        model.addAttribute("pet", animal);
        return "updatePet";
    }

    @PostMapping("pets/{ownerId}/update/{id}")
    public String updatePetPage(@ModelAttribute("pet") Animal pet, @PathVariable(value = "ownerId") long ownerId) {

        Optional<Owner> owner = ownerService.findById(ownerId);
        if (owner.isPresent()) {
            pet.setOwner(owner.get());
            animalService.save(pet);
            return "redirect:/owners/" + owner.get().getId();
        }


        return "redirect:/owners/" + owner.get().getId();
    }


    @GetMapping("/deletePet/{pet_id}")
    public String deletePet(@PathVariable long pet_id, @ModelAttribute("pet") Animal pet) {

        Optional<Animal> foundPet = animalService.findById(pet_id);

        if (foundPet.isPresent()) {

            Long ownerId = foundPet.get().getOwner().getId();
            animalService.delete(foundPet.get().getId());
            return "redirect:/owners/" + ownerId;
        }

        return "pets";


    }


    @GetMapping("/findPet")
    public String petFindForm(Model model) {
        model.addAttribute("pet", new Animal());
        return "findPets";
    }


    @GetMapping("/pets")
    public String petFindForm(Animal pet, Model model, BindingResult result) {

        Collection<Animal> results = this.animalService.findAnimalByAnimalName(pet.getName());
        if (results.size() > 0) {
            model.addAttribute("foundPets", results);
            return "foundPetList";
        } else {

            model.addAttribute("pet", new Animal());
            result.rejectValue("name", "notFound", "not found");
            return "findPets";
        }

    }

}