package tafitasoa.code.Poketra.Controller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tafitasoa.code.Poketra.Models.Taille;
import tafitasoa.code.Poketra.Service.TailleService;

import java.util.List;

@Controller
@RequestMapping("/taille")
public class TailleController {
    private final TailleService tailleService;
    public TailleController(TailleService tailleService){
        this.tailleService = tailleService;
    }
    @GetMapping("/list")
    public String getTaillesPage(@PageableDefault(size = 3) Pageable pageable, Model model) {
        Page<Taille> taillePage = tailleService.getAllTailles(pageable);
        model.addAttribute("taillePage", taillePage);
        return "taille/list";
    }
    @PostMapping("/insert")
    public String insertNew(@RequestParam("valeur") String valeur) {
        Taille taille = new Taille();
        taille.setValeur(valeur);
        this.tailleService.create(taille);
        return "redirect:/taille/list";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id){
        tailleService.delete(id);
        return "redirect:/taille/list";
    }
}
