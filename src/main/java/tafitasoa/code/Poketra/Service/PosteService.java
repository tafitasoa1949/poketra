package tafitasoa.code.Poketra.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tafitasoa.code.Poketra.Models.Composition;
import tafitasoa.code.Poketra.Models.Poste;
import tafitasoa.code.Poketra.Models.Produit;
import tafitasoa.code.Poketra.Repository.CompositionRepo;
import tafitasoa.code.Poketra.Repository.DetailCompoRepo;
import tafitasoa.code.Poketra.Repository.PosteRepo;

import java.util.List;
import java.util.Optional;

@Service
public class PosteService {
    private PosteRepo posteRepo;

    public PosteService(PosteRepo posteRepo){
        this.posteRepo = posteRepo;
    }
    public String generateUniqueId() {
        String prefix = "POST";
        int paddingSize = 3;
        Poste poste = posteRepo.findFirstByOrderByIdDesc();
        int lastId = (poste != null) ? Integer.parseInt(poste.getId().substring(prefix.length())) : 0;
        int nextId = lastId + 1;
        String formattedId = String.format("%s%0" + paddingSize + "d", prefix, nextId);
        return formattedId;
    }
    public void create(Poste poste){
        poste.setId(generateUniqueId());
        this.posteRepo.save(poste);
    }
    public List<Poste> getList(){
        return this.posteRepo.findAll();
    }
    public Page<Poste> getAllPoste(Pageable pageable) {
        return posteRepo.findAll(pageable);
    }
    public Poste getById(String id){
        Optional<Poste> optionalPoste = posteRepo.findById(id);
        if(optionalPoste.isPresent()){
            return optionalPoste.get();
        }
        return null;
    }
    public void delete(String id){
        posteRepo.deleteById(id);
    }
    public void update(String id,Poste poste){
        Poste poste1 = this.getById(id);
        if(poste1.getId() == poste.getId()){
            poste1.setNom(poste.getNom());
            poste1.setSalaire(poste.getSalaire());
            posteRepo.save(poste1);
        }
    }

}
