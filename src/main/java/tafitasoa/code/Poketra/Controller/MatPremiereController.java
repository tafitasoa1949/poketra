package tafitasoa.code.Poketra.Controller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tafitasoa.code.Poketra.Models.MatPremiere;
import tafitasoa.code.Poketra.Models.Unite;
import tafitasoa.code.Poketra.Repository.MatPremiereRepo;
import tafitasoa.code.Poketra.Service.MatPremiereService;
import tafitasoa.code.Poketra.Service.UniteService;

import java.util.List;
@Controller
@RequestMapping("/mat_premiere")
public class MatPremiereController {
    private final MatPremiereService matPremiereService;
    private final UniteService uniteService;

    public MatPremiereController(MatPremiereService matPremiereService,UniteService uniteService){
        this.matPremiereService = matPremiereService;
        this.uniteService = uniteService;
    }
    @GetMapping("/list")
    public String getMatPPage(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<MatPremiere> matPremierePage = matPremiereService.getAllMatP(pageable);
        model.addAttribute("matPremierePage", matPremierePage);
        List<Unite> uniteList = uniteService.getList();
        model.addAttribute("uniteList",uniteList);
        return "matiere/list";
    }
    @PostMapping("/insert")
    public String insertNew(@RequestParam("nom") String nom, @RequestParam("uniteId") String uniteId) {
        MatPremiere matPremiere = new MatPremiere();
        matPremiere.setNom(nom);
        Unite unite = uniteService.getById(uniteId);
        matPremiere.setUnite(unite);
        this.matPremiereService.create(matPremiere);
        return "redirect:/mat_premiere/list";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id){
        matPremiereService.delete(id);
        return "redirect:/mat_premiere/list";
    }
}
