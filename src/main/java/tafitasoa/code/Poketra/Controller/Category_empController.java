package tafitasoa.code.Poketra.Controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tafitasoa.code.Poketra.Models.Category_employe;
import tafitasoa.code.Poketra.Models.Look;
import tafitasoa.code.Poketra.Service.Category_empService;
import tafitasoa.code.Poketra.Service.LookService;
import tafitasoa.code.Poketra.Service.MPLookService;
import tafitasoa.code.Poketra.Service.MatPremiereService;
@Controller
@RequestMapping("/category_emp")
public class Category_empController {
    private final Category_empService categoryEmpService;
    public Category_empController(Category_empService categoryEmpService){
        this.categoryEmpService = categoryEmpService;
    }
    @GetMapping("/list")
    public String getCatePage(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<Category_employe> categoryEmployePage = categoryEmpService.getAllCatEmp(pageable);
        model.addAttribute("categoryEmployePage", categoryEmployePage);
        return "employe/category/list";
    }
    @PostMapping("/insert")
    public String insertNew(@RequestParam("nom") String nom, @RequestParam("anciente") String anciente,@RequestParam("decalage") String decalage) {
        Category_employe categoryEmploye = new Category_employe();
        categoryEmploye.setNom(nom);
        categoryEmploye.setAnciente(Double.parseDouble(anciente));
        categoryEmploye.setDecalage(Double.parseDouble(decalage));
        this.categoryEmpService.create(categoryEmploye);
        return "redirect:/category_emp/list";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id){
        categoryEmpService.delete(id);
        return "redirect:/category_emp/list";
    }
}
