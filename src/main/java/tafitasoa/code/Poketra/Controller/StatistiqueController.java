package tafitasoa.code.Poketra.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tafitasoa.code.Poketra.Data.StatVente;
import tafitasoa.code.Poketra.Models.Vente;
import tafitasoa.code.Poketra.Repository.VenteRepo;
import tafitasoa.code.Poketra.Service.VenteService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/statistique")
public class StatistiqueController {
    private final VenteService venteService;
    private final VenteRepo venteRepo;

    public StatistiqueController(VenteService venteService, VenteRepo venteRepo) {
        this.venteService = venteService;
        this.venteRepo = venteRepo;
    }

    @GetMapping("/produit")
    public String getVentePage(Model model) {
        List<StatVente> statVentes = venteService.statistique();
        model.addAttribute("statVentes",statVentes);
        // Préparez les données pour le graphique à barres
        List<String> labels = new ArrayList<>();
        List<Double> hommePercentages = new ArrayList<>();
        List<Double> femmePercentages = new ArrayList<>();

        for (StatVente statVente : statVentes) {
            labels.add(statVente.getProduit().getNom());
            hommePercentages.add(statVente.getPourcentage_homme());
            femmePercentages.add(statVente.getPourcentage_femme());
        }

        model.addAttribute("labels", labels);
        model.addAttribute("hommePercentages", hommePercentages);
        model.addAttribute("femmePercentages", femmePercentages);
        return "statistique/produit/list";
    }
    @GetMapping("/detail_produit/{id}")
    public String statDetailProduit(@PathVariable String id,Model model){
        StatVente statVente = venteService.getStatOne(id);
        model.addAttribute("statVente",statVente);
        //
        List<Vente> venteList = venteRepo.findByProduitId(id);
        model.addAttribute("venteList",venteList);
        return "statistique/produit/detail";
    }
}
