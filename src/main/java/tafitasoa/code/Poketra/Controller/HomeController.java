package tafitasoa.code.Poketra.Controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tafitasoa.code.Poketra.Models.Unite;
import tafitasoa.code.Poketra.Service.UniteService;

@Controller
@RequestMapping("/")
public class HomeController {
    private final UniteService uniteService;

    public HomeController(UniteService uniteService) {
        this.uniteService = uniteService;
    }
    @GetMapping("/")
    public String index(@PageableDefault(size = 3) Pageable pageable, Model model){
        Page<Unite> unitePage = uniteService.getAllUnites(pageable);
        model.addAttribute("unitePage", unitePage);
        return "unite/list";
    }
}
