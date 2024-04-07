package tafitasoa.code.Poketra.Controller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tafitasoa.code.Poketra.Models.Poste;
import tafitasoa.code.Poketra.Service.PosteService;

@Controller
@RequestMapping("/poste")
public class PosteController {
    private final PosteService posteService;
    public PosteController(PosteService posteService){
        this.posteService = posteService;
    }
    @GetMapping("/list")
    public String getTypesPage(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<Poste> postePage = posteService.getAllPoste(pageable);
        model.addAttribute("postePage", postePage);
        return "employe/poste/list";
    }
    @PostMapping("/insert")
    public String insertNew(@RequestParam("nom") String nom,@RequestParam("salaire") double salaire) {
        Poste poste = new Poste();
        poste.setNom(nom);
        poste.setSalaire(salaire);
        this.posteService.create(poste);
        return "redirect:/poste/list";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id){
        posteService.delete(id);
        return "redirect:/poste/list";
    }
}
