package tafitasoa.code.Poketra.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tafitasoa.code.Poketra.Data.StatVente;
import tafitasoa.code.Poketra.Models.Produit;
import tafitasoa.code.Poketra.Models.Vente;
import tafitasoa.code.Poketra.Repository.ProduitFinieRepo;
import tafitasoa.code.Poketra.Repository.VenteRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class VenteService {
    private VenteRepo venteRepo;
    private ProduitService produitService;
    private ClientService clientService;
    private final ProduitFinieRepo produitFinieRepo;
    public VenteService(VenteRepo venteRepo, ProduitService produitService, ClientService clientService, ProduitFinieRepo produitFinieRepo){
        this.venteRepo = venteRepo;
        this.produitService = produitService;
        this.clientService = clientService;
        this.produitFinieRepo = produitFinieRepo;
    }
    public String generateUniqueId() {
        String prefix = "VENT";
        int paddingSize = 3;
        Vente lastVente= venteRepo.findFirstByOrderByIdDesc();
        int lastId = (lastVente != null) ? Integer.parseInt(lastVente.getId().substring(prefix.length())) : 0;
        int nextId = lastId + 1;
        String formattedId = String.format("%s%0" + paddingSize + "d", prefix, nextId);
        return formattedId;
    }
    public void create(Vente vente){
        vente.setId(generateUniqueId());
        this.venteRepo.save(vente);
    }
    public List<Vente> getList(){
        return this.venteRepo.findAll();
    }
    public Page<Vente> getAllVentes(Pageable pageable) {
        return venteRepo.findAll(pageable);
    }
    public Vente getById(String id){
        Optional<Vente> optionalVente = venteRepo.findById(id);
        if(optionalVente.isPresent()){
            return optionalVente.get();
        }
        return null;
    }
    public void delete(String id){
        venteRepo.deleteById(id);
    }
    public void update(String id,Vente vente){
        Vente vente1 = this.getById(id);
        if(vente1.getId() == vente.getId()){
            vente1.setProduit(vente.getProduit());
            vente1.setClient(vente.getClient());
            vente1.setQuantite(vente.getQuantite());
            vente1.setDate(vente.getDate());
            venteRepo.save(vente1);
        }
    }
    public List<StatVente> statistique(){
        List<String[]> listStat = venteRepo.getStatistiqueVente();
        List<StatVente> statVentes = new ArrayList<>();
        for(int i=0 ; i < listStat.size() ; i++){
            StatVente statVente = new StatVente();
            Produit produit = produitService.getById(listStat.get(i)[0]);
            statVente.setProduit(produit);
            statVente.setHomme(Double.parseDouble(listStat.get(i)[2]));
            statVente.setFemme(Double.parseDouble(listStat.get(i)[3]));
            statVente.setPourcentage_homme(Double.parseDouble(listStat.get(i)[4]));
            statVente.setPourcentage_femme(Double.parseDouble(listStat.get(i)[5]));
            statVentes.add(statVente);
        }
        return statVentes;
    }
    public StatVente getStatOne(String produit_id){
        String statVenteOne = venteRepo.getStatistiqueVenteOne(produit_id);
        String[] data = statVenteOne.split(",");
        StatVente statVente = new StatVente();
        statVente.setProduit(produitService.getById(data[0]));
        statVente.setHomme(Double.parseDouble(data[2]));
        statVente.setFemme(Double.parseDouble(data[3]));
        statVente.setPourcentage_homme(Double.parseDouble(data[4]));
        statVente.setPourcentage_femme(Double.parseDouble(data[5]));
        return statVente;
    }
    public void checkSoldeProduit(String produit_id,double quantite)throws Exception{
        double solde = produitFinieRepo.getReste(produit_id); // Utilisez le nom de la méthode correcte
        if (solde - quantite < 0) {
            throw new Exception("Stock insuffisant. On a besoin de " + Math.abs(solde - quantite) + " en plus.");
        }
    }
}
