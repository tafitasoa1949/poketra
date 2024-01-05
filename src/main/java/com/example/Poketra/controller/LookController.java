package com.example.Poketra.controller;

import com.example.Poketra.service.LookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/look")
public class LookController {
    private final LookService lookService;
    public LookController(LookService lookService){
        this.lookService = lookService;
    }
}
