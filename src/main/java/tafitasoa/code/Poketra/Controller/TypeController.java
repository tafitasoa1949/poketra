package tafitasoa.code.Poketra.Controller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tafitasoa.code.Poketra.Models.Type;
import tafitasoa.code.Poketra.Service.TypeService;

@Controller
@RequestMapping("/type")
public class TypeController {
    private final TypeService typeService;
    public TypeController(TypeService typeService){
        this.typeService = typeService;
    }
    @GetMapping("/list")
    public String getTypesPage(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<Type> typePage = typeService.getAllTypes(pageable);
        model.addAttribute("typePage", typePage);
        return "type/list";
    }
    @PostMapping("/insert")
    public String insertNew(@RequestParam("nom") String nom) {
        Type type = new Type();
        type.setNom(nom);
        this.typeService.create(type);
        return "redirect:/type/list";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id){
        typeService.delete(id);
        return "redirect:/type/list";
    }
}
