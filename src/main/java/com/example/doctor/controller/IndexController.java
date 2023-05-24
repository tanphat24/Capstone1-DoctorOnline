package com.example.doctor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = { "/" })
public class IndexController {

    @GetMapping
    public String homePage(Model model) {
        String redirectUrl = "/login";
        return "redirect:" + redirectUrl;
    }

}
