package tafitasoa.code.Poketra.Controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tafitasoa.code.Poketra.Models.Client;
import tafitasoa.code.Poketra.Models.Produit;
import tafitasoa.code.Poketra.Models.Produit_Finie;
import tafitasoa.code.Poketra.Models.Vente;
import tafitasoa.code.Poketra.Service.*;

import java.util.List;

@Controller
@RequestMapping("/vente")
public class VenteController {
    private final VenteService venteService;
    private final ProduitService produitService;
    private final ClientService clientService;
    private final EmployeService employeService;

    public VenteController(VenteService venteService, ProduitService produitService, ClientService clientService, EmployeService employeService){
        this.venteService = venteService;
        this.produitService = produitService;
        this.clientService = clientService;
        this.employeService = employeService;
    }

    @GetMapping("/list")
    public String getVentePage(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<Vente> ventePage = venteService.getAllVentes(pageable);
        model.addAttribute("ventePage", ventePage);
        List<Produit> produitList = produitService.getList();
        model.addAttribute("produitList", produitList);
        List<Client> clients = clientService.getList();
        model.addAttribute("clients", clients);
        return "vente/list";
    }
    @PostMapping("/insert")
    public String insertNew(@RequestParam("produit_id") String produit_id,@RequestParam("client_id") String client_id,@RequestParam("quantite") double quantite,@RequestParam("date") String date,Model model,@PageableDefault(size = 10) Pageable pageable) throws Exception{
        try {
            venteService.checkSoldeProduit(produit_id,quantite);
            Vente vente = new Vente();
            Produit produit = produitService.getById(produit_id);
            vente.setProduit(produit);
            Client client = clientService.getById(client_id);
            vente.setClient(client);
            if(quantite <= 0){
                throw new Exception("Quantité Invalid");
            }
            vente.setQuantite(quantite);
            vente.setDate(employeService.convertStringToTimestamp(date));
            this.venteService.create(vente);
            return "redirect:/vente/list";
        }catch (Exception e){
            model.addAttribute("error", e.getMessage());
            Page<Vente> ventePage = venteService.getAllVentes(pageable);
            model.addAttribute("ventePage", ventePage);
            List<Produit> produitList = produitService.getList();
            model.addAttribute("produitList", produitList);
            List<Client> clients = clientService.getList();
            model.addAttribute("clients", clients);
            return "vente/list";
        }
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id){
        venteService.delete(id);
        return "redirect:/vente/list";
    }
}
