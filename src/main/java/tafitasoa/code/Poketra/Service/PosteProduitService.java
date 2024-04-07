package tafitasoa.code.Poketra.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tafitasoa.code.Poketra.Models.Poste_Produit;
import tafitasoa.code.Poketra.Repository.PosteProduitRepo;

import java.util.List;
import java.util.Optional;

@Service
public class PosteProduitService {
    private PosteProduitRepo posteProduitRepo;
    private ProduitService posteService;
    public PosteProduitService(PosteProduitRepo posteProduitRepo, ProduitService posteService) {
        this.posteProduitRepo = posteProduitRepo;
        this.posteService = posteService;
    }
    public String generateUniqueId() {
        String prefix = "POSP";
        int paddingSize = 3;
        Poste_Produit employeurProduit = posteProduitRepo.findFirstByOrderByIdDesc();
        int lastId = (employeurProduit != null) ? Integer.parseInt(employeurProduit.getId().substring(prefix.length())) : 0;
        int nextId = lastId + 1;
        String formattedId = String.format("%s%0" + paddingSize + "d", prefix, nextId);
        return formattedId;
    }
    public void create(Poste_Produit employeurProduit){
        employeurProduit.setId(generateUniqueId());
        this.posteProduitRepo.save(employeurProduit);
    }
    public List<Poste_Produit> getList(){
        return this.posteProduitRepo.findAll();
    }
    public Page<Poste_Produit> getAllEmployerPro(Pageable pageable) {
        return posteProduitRepo.findAll(pageable);
    }
    public Poste_Produit getById(String id){
        Optional<Poste_Produit> optionalEmployeurProduit = posteProduitRepo.findById(id);
        if(optionalEmployeurProduit.isPresent()){
            return optionalEmployeurProduit.get();
        }
        return null;
    }
    public void delete(String id){
        posteProduitRepo.deleteById(id);
    }
    public void update(String id,Poste_Produit employeurProduit){
        Poste_Produit employeurProduit1 = this.getById(id);
        if(employeurProduit1.getId() == employeurProduit.getId()){
            employeurProduit1.setProduit(employeurProduit.getProduit());
            employeurProduit1.setPoste(employeurProduit.getPoste());
            employeurProduit1.setNombre(employeurProduit.getNombre());
            employeurProduit1.setDuree(employeurProduit.getDuree());
            posteProduitRepo.save(employeurProduit1);
        }
    }
}
