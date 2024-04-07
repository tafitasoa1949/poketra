package tafitasoa.code.Poketra.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tafitasoa.code.Poketra.Data.StockProduit;
import tafitasoa.code.Poketra.Models.Produit;
import tafitasoa.code.Poketra.Models.Produit_Finie;
import tafitasoa.code.Poketra.Repository.ProduitFinieRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class ProduitFinieService {
    private ProduitFinieRepo produitFinieRepo;
    private final ProduitService produitService;
    public ProduitFinieService(ProduitFinieRepo produitFinieRepo, ProduitService produitService){
        this.produitFinieRepo = produitFinieRepo ;
        this.produitService = produitService;
    }
    public String generateUniqueId() {
        String prefix = "PROF";
        int paddingSize = 3;
        Produit_Finie produitFinie = produitFinieRepo.findFirstByOrderByIdDesc();
        int lastId = (produitFinie != null) ? Integer.parseInt(produitFinie.getId().substring(prefix.length())) : 0;
        int nextId = lastId + 1;
        String formattedId = String.format("%s%0" + paddingSize + "d", prefix, nextId);
        return formattedId;
    }
    public void create(Produit_Finie produitFinie){
        produitFinie.setId(generateUniqueId());
        this.produitFinieRepo.save(produitFinie);
    }
    public List<Produit_Finie> getList(){
        return this.produitFinieRepo.findAll();
    }
    public Page<Produit_Finie> getAllProduitFinie(Pageable pageable) {
        return produitFinieRepo.findAll(pageable);
    }
    public Produit_Finie getById(String id){
        Optional<Produit_Finie> optionalProduitFinie = produitFinieRepo.findById(id);
        if(optionalProduitFinie.isPresent()){
            return optionalProduitFinie.get();
        }
        return null;
    }
    public void delete(String id){
        produitFinieRepo.deleteById(id);
    }
    public void update(String id,Produit_Finie produitFinie){
        Produit_Finie produitFinie1 = this.getById(id);
        if(produitFinie1.getId() == produitFinie.getId()){
            produitFinie1.setProduit(produitFinie.getProduit());
            produitFinie1.setQuantite(produitFinie.getQuantite());
            produitFinie1.setPrix_unitaire(produitFinie.getPrix_unitaire());
            produitFinie1.setDate(produitFinie.getDate());
            produitFinieRepo.save(produitFinie1);
        }
    }
    public  List<StockProduit> getStockProduit(){
        List<String[]> list = produitFinieRepo.getStockProduit();
        List<StockProduit> stockProduits = new ArrayList<>();
        for (int i=0 ; i < list.size() ; i++){
            StockProduit stockProduit = new StockProduit();
            Produit produit = produitService.getById(list.get(i)[0]);
            stockProduit.setProduit(produit);
            stockProduit.setQuantite(Double.parseDouble(list.get(i)[1]));
            stockProduit.setPrix_moyenne(Double.parseDouble(list.get(i)[2]));
            stockProduits.add(stockProduit);
        }
        return  stockProduits;
    }
}
