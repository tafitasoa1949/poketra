package tafitasoa.code.Poketra.Controller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tafitasoa.code.Poketra.Models.Look;
import tafitasoa.code.Poketra.Models.MatPremiere;
import tafitasoa.code.Poketra.Models.MatPremiereLook;
import tafitasoa.code.Poketra.Service.LookService;
import tafitasoa.code.Poketra.Service.MPLookService;
import tafitasoa.code.Poketra.Service.MatPremiereService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/look")
public class LookController {
    private final LookService lookService;
    private final MPLookService mpLookService;
    private final MatPremiereService matPremiereService;
    public LookController(LookService lookService,MPLookService mpLookService,MatPremiereService matPremiereService){
        this.lookService = lookService;
        this.mpLookService =mpLookService;
        this.matPremiereService = matPremiereService;
    }
    @GetMapping("/list")
    public String getLookPage(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<Look> lookPage = lookService.getAllLook(pageable);
        model.addAttribute("lookPage", lookPage);
        return "look/list";
    }
    @PostMapping("/insert")
    public String insertNew(@RequestParam("nom") String nom,@RequestParam("description") String description) {
        Look look = new Look();
        look.setNom(nom);
        look.setDescription(description);
        this.lookService.create(look);
        return "redirect:/look/list";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id){
        lookService.delete(id);
        return "redirect:/look/list";
    }
    @GetMapping("/details/{id}")
    public String details(@PathVariable String id,Model model){
        model.addAttribute("look_id",id);
        List<MatPremiereLook> matPremiereLooks = mpLookService.getListByLook(id);
        /*for(int i=0 ; i< matPremiereLooks.size();i++){
            System.out.println("look"+matPremiereLooks.get(i).getLook().getNom()+"mat ppp"+matPremiereLooks.get(i).getMatPremiere().getNom());
        }*/
        model.addAttribute("matPremiereLooks",matPremiereLooks);
        return "/look/details/list";
    }
    @GetMapping("ajouterDetails/{id}")
    public String ajouter_detail(@PathVariable String id,Model model){
        Look look = lookService.getById(id);
        model.addAttribute("look",look);
        List<MatPremiere> matPremiereList = matPremiereService.getList();
        model.addAttribute("matPremiereList",matPremiereList);
        return "/look/details/ajouter";
    }
    @PostMapping("/creation_details")
    public String creation_details(@RequestParam Map<String, String> params){
        String lookIdValue = params.get("look_id");
        //matiere premiere
        Map<String, String> matPremiereParams = new HashMap<>();
        Pattern pattern_matP = Pattern.compile("^matPremiere\\[(\\d+)\\]$");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            Matcher matcher = pattern_matP.matcher(entry.getKey());
            if (matcher.matches()) {
                matPremiereParams.put(matcher.group(1), entry.getValue());
            }
        }
        String[] matPremiereArray = new String[matPremiereParams.size()];
        for (Map.Entry<String, String> entry : matPremiereParams.entrySet()) {
            int index = Integer.parseInt(entry.getKey());
            matPremiereArray[index] = entry.getValue();
        }
        //
        for(int i=0 ; i < matPremiereArray.length ; i++){
            MatPremiereLook matPremiereLook = new MatPremiereLook();
            Look look = lookService.getById(lookIdValue);
            MatPremiere matPremiere = matPremiereService.getById(matPremiereArray[i]);
            matPremiereLook.setLook(look);
            matPremiereLook.setMatPremiere(matPremiere);
            //System.out.println("look"+matPremiereLook.getLook().getNom()+"mat ppp"+matPremiereLook.getMatPremiere().getNom());
            mpLookService.create(matPremiereLook);
        }
        return "redirect:/look/list";
    }
}
