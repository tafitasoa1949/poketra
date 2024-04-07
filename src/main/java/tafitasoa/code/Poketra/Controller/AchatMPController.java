package tafitasoa.code.Poketra.Controller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tafitasoa.code.Poketra.Data.StockMP;
import tafitasoa.code.Poketra.Models.Achat_mp;
import tafitasoa.code.Poketra.Models.MatPremiere;
import tafitasoa.code.Poketra.Repository.MatPremiereRepo;
import tafitasoa.code.Poketra.Service.AchatMPService;
import tafitasoa.code.Poketra.Service.MatPremiereService;

import javax.xml.crypto.Data;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/achatMP")
public class AchatMPController {
    private final AchatMPService achatMPService;
    private final MatPremiereService matPremiereService;
    private final MatPremiereRepo matPremiereRepo;
    public AchatMPController(AchatMPService achatMPService,MatPremiereService matPremiereService,MatPremiereRepo matPremiereRepo){
        this.achatMPService = achatMPService;
        this.matPremiereService = matPremiereService;
        this.matPremiereRepo = matPremiereRepo;
    }
    @GetMapping("/list")
    public String getAchatMPPage(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<Achat_mp> achatMpPage = achatMPService.getAllTAchat(pageable);
        model.addAttribute("achatMpPage", achatMpPage);
        return "matiere/achat/list";
    }
    @GetMapping("/ajouter_achat")
    public String voir_achat_Mp(Model model){
        List<MatPremiere> matPremiereList = matPremiereService.getList();
        model.addAttribute("matPremiereList",matPremiereList);
        return "matiere/achat/ajouter";
    }
    @PostMapping("/achat")
    public String achat_MP(@RequestParam Map<String, String> params){
        String mat_premiere_id = params.get("mat_premiere");
        double quantite = Double.parseDouble(params.get("quantite"));
        MatPremiere matPremiere = matPremiereService.getById(mat_premiere_id);
        //PU
        double prix = Double.parseDouble(params.get("prix"));
        Date date_string = Date.valueOf(params.get("date"));
        Timestamp date = new Timestamp(date_string.getTime());
        Achat_mp achatMp = new Achat_mp();
        achatMp.setMatPremiere(matPremiere);
        achatMp.setQuantite(quantite);
        achatMp.setPrix(prix);
        achatMp.setDate(date);
        achatMPService.create(achatMp);
        return "redirect:/achatMP/list";
    }
    @GetMapping("/stock")
    public String voir_stock(Model model){
        List<StockMP> stockList = achatMPService.getListStock();
        /*for(int i=0 ; i < stockList.size() ; i++){
            System.out.println(stockList.get(i).getMatPremiere().getNom()+stockList.get(i).getQuantite()+stockList.get(i).getPump());
        }*/
        model.addAttribute("stockList",stockList);
        return "/matiere/stock";
    }
}
