package com.ozgursoft.vetpetapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {

    @GetMapping("")
    public String home() {
        return "home";
    }

    @GetMapping("/findOwners")
    public String findOwners() {
        return "updateOwner";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "login";
    }


}
