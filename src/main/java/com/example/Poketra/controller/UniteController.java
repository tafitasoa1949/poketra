package com.example.Poketra.controller;

import com.example.Poketra.models.Unite;
import com.example.Poketra.service.UniteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/unite")
public class UniteController {
    private final UniteService uniteService;
    public UniteController(UniteService uniteService){
        this.uniteService = uniteService;
    }
    @GetMapping("/list")
    public String getUnitesPage(@PageableDefault(size = 3) Pageable pageable, Model model) {
        Page<Unite> unitePage = uniteService.getAllUnites(pageable);
        model.addAttribute("unitePage", unitePage);
        return "unite/list";
    }
    @PostMapping("/insert")
    public String insertNew(@RequestParam("nom") String nom) {
        Unite unite = new Unite();
        unite.setNom(nom);
        this.uniteService.create(unite);
        System.out.println("succeeeeee ioioioio");
        return "redirect:/unite/list";
    }
}
