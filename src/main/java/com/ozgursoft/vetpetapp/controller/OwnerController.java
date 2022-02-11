package com.ozgursoft.vetpetapp.controller;
import com.ozgursoft.vetpetapp.model.Owner;
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
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;

    }

    @GetMapping("owner/owners")
    public String getAllOwners(Model model) {
        return viewPageOwner(model, 1, "name", "asc");
    }



    @GetMapping("/page/{pageNum}")
    public String viewPageOwner(Model model,
                           @PathVariable(name = "pageNum") int pageNum,
                           @Param("sortField") String sortField,
                           @Param("sortDir") String sortDir) {

        Page<Owner> page = ownerService.getOwners(pageNum, sortField, sortDir);

        List<Owner> ownerList = page.getContent();

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("ownerList", ownerList);

        return "owners";
    }


    @GetMapping("showNewOwnerForm")
    public String showNewOwnerForm(Model model) {
        Owner owner = new Owner();
        model.addAttribute("owner", owner);
        return "addOwner";
    }

    @PostMapping("saveOwner")
    public String saveOwner(@ModelAttribute("owner") Owner owner) {
        ownerService.save(owner);
        return "redirect:/owner/owners";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        Owner owner = ownerService.getOwnerById(id);
        model.addAttribute("owner", owner);
        return "updateOwner";
    }

    @GetMapping("/owners/{id}")
    public String ownerDetailPage(@PathVariable(value = "id") long id, Model model) {
        Optional<Owner> optionalOwner = ownerService.findById(id);

        if (optionalOwner.isPresent()) {
            Owner owner = optionalOwner.get();
            model.addAttribute("ownerInformation", owner);
            return "ownerDetails";
        }
        return "redirect:/owner/owners";
    }

    @GetMapping("/findOwner")
    public String ownerFindForm(Model model) {
        model.addAttribute("owner", new Owner());
        return "findOwners";
    }

    @GetMapping("/owners")
    public String ownerFind(Owner owner, BindingResult result, Model model) {

        Collection<Owner> results = this.ownerService.findOwnerByLastName(owner.getSurname());
        if (results.size() < 1) {
            result.rejectValue("surname", "notFound", "not found");
            return "findOwners";
        } else {
            model.addAttribute("selections", results);
            return "foundOwnerList";
        }
    }


    @GetMapping("/deleteOwner/{id}")
    public String deleteOwner(@PathVariable(value = "id") long id) {
        this.ownerService.delete(id);
        return "redirect:/owner/owners";
    }


}