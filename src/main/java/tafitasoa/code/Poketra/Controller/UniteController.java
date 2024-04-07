package tafitasoa.code.Poketra.Controller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tafitasoa.code.Poketra.Models.Unite;
import tafitasoa.code.Poketra.Service.UniteService;

import java.util.List;
@Controller
@RequestMapping("/unite")
public class UniteController {
    private final UniteService uniteService;

    public UniteController(UniteService uniteService){
        this.uniteService = uniteService;
    }

    @GetMapping("/list")
    public String getUnitesPage(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<Unite> unitePage = uniteService.getAllUnites(pageable);
        model.addAttribute("unitePage", unitePage);
        return "unite/list";
    }
    @PostMapping("/insert")
    public String insertNew(@RequestParam("nom") String nom) {
        Unite unite = new Unite();
        unite.setNom(nom);
        this.uniteService.create(unite);
        return "redirect:/unite/list";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id){
        uniteService.delete(id);
        return "redirect:/unite/list";
    }
}
