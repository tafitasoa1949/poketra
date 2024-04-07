package tafitasoa.code.Poketra.Controller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tafitasoa.code.Poketra.Data.DetailProduit;
import tafitasoa.code.Poketra.Data.StockProduit;
import tafitasoa.code.Poketra.Models.*;
import tafitasoa.code.Poketra.Repository.*;
import tafitasoa.code.Poketra.Service.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/produit")
public class ProduitController {
    private final ProduitService produitService;
    private final TypeService typeService;
    private final LookService lookService;
    private final MPLookService mpLookService;
    private final PosteService posteService;
    private final CompositionService compositionService;
    private final MatPremiereService matPremiereService;
    private final DetailCompoService detailCompoService;
    private final PosteProduitService posteProduitService;
    private final CompositionRepo compositionRepo;
    private final DetailCompoRepo detailCompoRepo;
    private final PosteProduitRepo posteProduitRepo;
    private final ProduitFinieService produitFinieService;
    private final TailleService tailleService;
    private final ProduitFinieRepo produitFinieRepo;

    public ProduitController(ProduitService produitService, TypeService typeService, LookService lookService, MPLookService mpLookService
            , PosteService posteService, CompositionService compositionService, MatPremiereService matPremiereService, DetailCompoService detailCompoService
            , PosteProduitService posteProduitService, CompositionRepo compositionRepo, DetailCompoRepo detailCompoRepo, PosteProduitRepo posteProduitRepo
            , ProduitFinieService produitFinieService, TailleService tailleService, ProduitFinieRepo produitFinieRepo) {
        this.produitService = produitService;
        this.typeService = typeService;
        this.lookService = lookService;
        this.mpLookService = mpLookService;
        this.posteService = posteService;
        this.compositionService = compositionService;
        this.matPremiereService = matPremiereService;
        this.detailCompoService = detailCompoService;
        this.posteProduitService = posteProduitService;
        this.compositionRepo = compositionRepo;
        this.detailCompoRepo = detailCompoRepo;
        this.posteProduitRepo = posteProduitRepo;
        this.produitFinieService = produitFinieService;
        this.tailleService = tailleService;
        this.produitFinieRepo = produitFinieRepo;
    }

    @GetMapping("/list")
    public String getProduitsPage(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<Produit> produitPage = produitService.getAllProduit(pageable);
        model.addAttribute("produitPage", produitPage);
        return "produit/list";
    }
    @GetMapping("/list_produit")
    public String getProduitsPageList(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<Produit> produitPage = produitService.getAllProduit(pageable);
        model.addAttribute("produitPage", produitPage);
        return "produit/list";
    }
    @GetMapping("/ajouter")
    public String index(Model model){
        List<Type> typeList = typeService.getList();
        model.addAttribute("typeList",typeList);
        List<Look> lookList = lookService.getList();
        model.addAttribute("lookList",lookList);
        return "produit/creation/ajouter";
    }
    @PostMapping("/creation")
    public String creation_produit(@RequestParam Map<String, String> params, Model model){
        String nom = params.get("nom");
        model.addAttribute("nom",nom);
        String typeIdValue = params.get("typeId");
        Type type = typeService.getById(typeIdValue);
        model.addAttribute("type",type);
        String lookIdValue = params.get("lookId");
        Look look = lookService.getById(lookIdValue);
        model.addAttribute("look",look);
        String description = params.get("description");
        model.addAttribute("description",description);
        List<MatPremiereLook> matPremiereLooks = mpLookService.getListByLook(lookIdValue);
        model.addAttribute("matPremiereLooks",matPremiereLooks);
        List<Poste> posteList = posteService.getList();
        model.addAttribute("posteList",posteList);
        return "produit/creation/detail";
    }
    @Transactional
    @PostMapping("/creation_details")
    public String creation_tous(@RequestParam Map<String, String> params, Model model)throws Exception{
        String produit_nom = params.get("produit");
        String typeId = params.get("typeId");
        Type type = typeService.getById(typeId);
        String lookId = params.get("lookId");
        Look look = lookService.getById(lookId);
        String description = params.get("description");
        double duree = Double.parseDouble(params.get("duree"));
        try {
            //Matiere premiere
            String[] matPremiereArray = produitService.extractParams(params, "^matPremiere\\[(\\d+)\\]$");
            //quantite
            double[] quantiteArray = produitService.convertToDoubleArray(produitService.extractParams(params, "^quantite\\[(\\d+)\\]$"));
            //employe
            String[] posteArray = produitService.extractParams(params,"^employeur\\[(\\d+)\\]$");
            //nombre
            int[] nombreArray = produitService.convertToIntArray(produitService.extractParams(params, "^nombre\\[(\\d+)\\]$"));
            for(int i=0 ; i < matPremiereArray.length ; i++){
                if(quantiteArray[i] <= 0){
                    throw new Exception("Quantité invalid");
                }
            }
            for(int i=0 ; i < nombreArray.length ; i++){
                if(nombreArray[i] <= 0){
                    throw new Exception("Nombre invalid");
                }
            }
            if(duree <= 0){
                throw new Exception("Durée Invalid");
            }
            //
            Produit produit = new Produit();
            String produitId = produitService.generateUniqueId();
            produit.setId(produitId);
            produit.setNom(produit_nom);
            produit.setType(type);
            produit.setLook(look);
            produitService.createOne(produit);
            //composition
            Composition composition = new Composition();
            String compositionId = compositionService.generateUniqueId();
            composition.setId(compositionId);
            composition.setProduit(produit);
            composition.setDescription(description);
            compositionService.createOne(composition);
            //
            for(int i=0 ; i < matPremiereArray.length ; i++){
                DetailComposition detailComposition = new DetailComposition();
                Composition compositionOne = compositionService.getById(compositionId);
                detailComposition.setComposition(compositionOne);
                MatPremiere matPremiere = matPremiereService.getById(matPremiereArray[i]);
                detailComposition.setMatPremiere(matPremiere);
                detailComposition.setQuantite(quantiteArray[i]);
                detailCompoService.create(detailComposition);
            }
            System.out.println("mat fffff");
            //employeur leh poste
            for(int i=0 ; i < posteArray.length ; i++){
                Poste_Produit employeurProduit = new Poste_Produit();
                employeurProduit.setProduit(produit);
                Poste poste = posteService.getById(posteArray[i]);
                employeurProduit.setPoste(poste);
                employeurProduit.setNombre(nombreArray[i]);
                employeurProduit.setDuree(duree);
                posteProduitService.create(employeurProduit);
            }
            System.out.println("poste");
            System.out.println("huhuhhhuhuhuhuhuhuhuhuhuhuhuhu");
            return "redirect:/produit/list_produit";
        }catch (Exception e){
            model.addAttribute("error",e.getMessage());
            model.addAttribute("nom",produit_nom);
            String typeIdValue = params.get("typeId");
            Type type1 = typeService.getById(typeIdValue);
            model.addAttribute("type",type1);
            String lookIdValue = params.get("lookId");
            Look look1 = lookService.getById(lookIdValue);
            model.addAttribute("look",look1);
            String description1 = params.get("description");
            model.addAttribute("description",description1);
            List<MatPremiereLook> matPremiereLooks = mpLookService.getListByLook(lookIdValue);
            model.addAttribute("matPremiereLooks",matPremiereLooks);
            List<Poste> posteList = posteService.getList();
            model.addAttribute("posteList",posteList);
            System.out.println(e.getMessage());
            return "/produit/creation/error";
        }
    }
    @GetMapping("/voir_details/{id}")
    public String voirDetails(@PathVariable String id,Model model){
        Composition composition = compositionRepo.findByProduitId(id);
        List<DetailComposition> detailCompositions = detailCompoRepo.findByCompositionId(composition.getId());
        List<Poste_Produit> posteProduits = posteProduitRepo.findByProduitId(id);
        Produit produit = produitService.getById(id);
        model.addAttribute("produit",produit);
        model.addAttribute("composition",composition);
        model.addAttribute("detailCompositions",detailCompositions);
        model.addAttribute("posteProduits",posteProduits);
        return "/produit/details/list";
    }
    @GetMapping("/fabriquer")
    public String fabriquer(Model model){
        List<Produit> produitList = produitService.getList();
        model.addAttribute("produitList",produitList);
        List<Taille> tailleList = tailleService.getList();
        model.addAttribute("tailleList",tailleList);
        return "/produit/fabriquer/index";
    }
    @PostMapping("/ajouter_fabriquer")
    public String ajouter_fabriquer(@RequestParam Map<String, String> params, Model model)throws Exception {
        String produitId = params.get("produitId");
        String tailleId = params.get("tailleId");
        double quantite = Double.parseDouble(params.get("quantite"));
        double prix_unitaire = Double.parseDouble(params.get("prix_unitaire"));
        String date = params.get("date");
        try {
            if(quantite <= 0 || prix_unitaire <= 0){
                throw new Exception("Quantité Invalid");
            }
            Produit produit = produitService.getById(produitId);
            Taille taille = tailleService.getById(tailleId);
            Composition composition = compositionRepo.findByProduitId(produitId);
            List<DetailComposition> detailCompositions= detailCompoRepo.findByCompositionId(composition.getId());
            for(int i=0 ; i < detailCompositions.size() ; i++){
                if(taille.getId().equals("TAIL001")){
                    double quantiteBesoin = detailCompositions.get(i).getQuantite()*quantite;
                    matPremiereService.checkSotck(detailCompositions.get(i).getMatPremiere(),quantiteBesoin);
                }else{
                    double quantiteBesoin = (detailCompositions.get(i).getQuantite()*quantite)*2;
                    matPremiereService.checkSotck(detailCompositions.get(i).getMatPremiere(),quantiteBesoin);
                }

            }
            Produit_Finie produitFinie = new Produit_Finie();
            produitFinie.setProduit(produit);
            produitFinie.setQuantite(quantite);
            produitFinie.setPrix_unitaire(prix_unitaire);
            //date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = dateFormat.parse(date);
            Timestamp timestamp = new Timestamp(parsedDate.getTime());
            produitFinie.setDate(timestamp);
            produitFinieService.create(produitFinie);
            return "redirect:/produit/produit_finie";
        }catch (Exception e){
            List<Produit> produitList = produitService.getList();
            model.addAttribute("produitList",produitList);
            model.addAttribute("error",e.getMessage());
            List<Taille> tailleList = tailleService.getList();
            model.addAttribute("tailleList",tailleList);
            return "/produit/fabriquer/index";
        }
    }
    @GetMapping("/produit_finie")
    public String produit_finie(@PageableDefault(size = 10) Pageable pageable,Model model){
        /*Page<Produit_Finie> produitFiniePage = produitFinieService.getAllProduitFinie(pageable);
        model.addAttribute("produitFiniePage",produitFiniePage);*/
        List<StockProduit> stockProduits = produitFinieService.getStockProduit();
        model.addAttribute("stockProduits",stockProduits);
        return "/produit_finie/list";
    }
    @GetMapping("/recherche")
    public String voir_recherche(){
        return "produit_finie/recherche/index";
    }
    @GetMapping("/resultat_recherche")
    public String resultat_recherche(@RequestParam("min") double min,@RequestParam("max") double max,Model model)throws Exception{
        List<DetailProduit> listInclus = new ArrayList<>();
        List<Produit_Finie> produitFinies = produitFinieService.getList();
        try {
            if(min < 0 || max <=0 || max < min){
                throw new Exception("Quantité Invalid");
            }
            for(int i=0 ; i < produitFinies.size() ; i++){
                double prixTotalMatP = produitService.getPrixTotalMatP(produitFinies.get(i).getProduit());
                System.out.println("Prix total matiree"+prixTotalMatP);
                double prixTotalEmploye = produitFinieRepo.getPrixTotalEmploye(produitFinies.get(i).getProduit().getId());
                System.out.println("Prix total employer"+prixTotalEmploye);
                double benefice = produitFinies.get(i).getPrix_unitaire()-(prixTotalMatP+prixTotalEmploye);
                System.out.println("Prix unitaire"+produitFinies.get(i).getPrix_unitaire());
                System.out.println("Benefice"+benefice);
                if(benefice >= min && benefice <= max){
                    //produitFinies.get(i).setBenefice(benefice);
                    DetailProduit detailProduit = new DetailProduit();
                    detailProduit.setProduitFinie(produitFinies.get(i));
                    detailProduit.setPrix_total_matiere(prixTotalMatP);
                    detailProduit.setPrix_total_employe(prixTotalEmploye);
                    detailProduit.setBenefice(benefice);
                    listInclus.add(detailProduit);
                    //System.out.println("tafiditra"+produitFinies.get(i).getProduit().getNom());
                }
            }
            model.addAttribute("listInclus",listInclus);
            return "produit_finie/recherche/resultat";
        }catch (Exception e){
            model.addAttribute("error",e.getMessage());
            return "produit_finie/recherche/index";
        }
    }
}
