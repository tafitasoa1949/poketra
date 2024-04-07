package tafitasoa.code.Poketra.Controller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tafitasoa.code.Poketra.Data.EmpOuvrier;
import tafitasoa.code.Poketra.Models.Category_employe;
import tafitasoa.code.Poketra.Models.Employe;
import tafitasoa.code.Poketra.Models.Poste;
import tafitasoa.code.Poketra.Repository.Category_empRepo;
import tafitasoa.code.Poketra.Repository.EmployeRepo;
import tafitasoa.code.Poketra.Service.Category_empService;
import tafitasoa.code.Poketra.Service.EmployeService;
import tafitasoa.code.Poketra.Service.PosteService;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/employe")
public class EmployeController {
    private final EmployeService employeService;
    private final PosteService posteService;
    private final EmployeRepo employeRepo;
    private final Category_empRepo categoryEmpRepo;
    private final Category_empService categoryEmpService;
    public EmployeController(EmployeService employeService, PosteService posteService,EmployeRepo employeRepo,Category_empRepo categoryEmpRepo,Category_empService categoryEmpService){
        this.employeService = employeService;
        this.posteService = posteService;
        this.employeRepo = employeRepo;
        this.categoryEmpRepo = categoryEmpRepo;
        this.categoryEmpService = categoryEmpService;
    }
    @GetMapping("/list")
    public String getTaillesPage(Model model) {
        /*Page<Employe> employeurPage = employeService.getAllEmployer(pageable);
        model.addAttribute("employeurPage", employeurPage);
        List<Poste> posteList = posteService.getList();
        model.addAttribute("posteList", posteList);*/
        List<EmpOuvrier> list_empOuvriers = new ArrayList<>();
        List<Object[]> listEmp = employeRepo.getListIdwithAnciennte();
        for(int i=0 ; i < listEmp.size() ; i++){
            EmpOuvrier empOuvrier = new EmpOuvrier();
            Employe employe = employeService.getById(listEmp.get(i)[0].toString());
            empOuvrier.setEmploye(employe);
            double anciennte = Double.valueOf(listEmp.get(i)[1].toString());
            double taux_horaire = 0;
            if(anciennte == 5 || anciennte > 5){
                empOuvrier.setAnciennte(anciennte);
                Category_employe categoryEmploye = categoryEmpService.getById("CTEP003");
                empOuvrier.setCategoryEmploye(categoryEmploye);
                taux_horaire = employe.getTaux_horaire() * 3;
            }else{
                empOuvrier.setAnciennte(anciennte);
                Category_employe categoryEmploye = categoryEmpRepo.getByAnciente(anciennte);
                empOuvrier.setCategoryEmploye(categoryEmploye);
                taux_horaire = employe.getTaux_horaire() * anciennte;
            }
            empOuvrier.setTaux_horaire(taux_horaire);
            list_empOuvriers.add(empOuvrier);
        }
        model.addAttribute("list_empOuvriers", list_empOuvriers);
        List<Poste> posteList = posteService.getList();
        model.addAttribute("posteList", posteList);
        return "employe/list";
    }
    @PostMapping("/insert")
    public String insertNew(@RequestParam Map<String, String> params) throws Exception {
        String nom = params.get("nom");
        String posteId = params.get("posteId");
        String dtn = params.get("dtn");
        Timestamp dtn_timestamp = employeService.convertStringToTimestamp(dtn);
        //System.out.println(dtn_timestamp);
        double taux_horaire = Double.parseDouble(params.get("taux_horaire"));
        String date_embauche = params.get("date_embauche");
        Timestamp date_embauche_timestamp = employeService.convertStringToTimestamp(date_embauche);
        Employe employeur = new Employe();
        employeur.setNom(nom);
        Poste poste = posteService.getById(posteId);
        employeur.setPoste(poste);
        employeur.setTaux_horaire(taux_horaire);
        employeur.setDate_naissance(dtn_timestamp);
        employeur.setDate_embauche(date_embauche_timestamp);
        this.employeService.create(employeur);
        return "redirect:/employe/list";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id){
        employeService.delete(id);
        return "redirect:/employe/list";
    }
}
