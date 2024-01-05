package com.example.Poketra.controller;

import com.example.Poketra.models.Unite;
import com.example.Poketra.service.UniteService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final UniteService uniteService;

    public HomeController(UniteService uniteService) {
        this.uniteService = uniteService;
    }

    @GetMapping("/")
    public String home(Model model){
        List<Unite> uniteList = uniteService.getList();
        model.addAttribute("uniteList",uniteList);
        return "index";
    }
}
