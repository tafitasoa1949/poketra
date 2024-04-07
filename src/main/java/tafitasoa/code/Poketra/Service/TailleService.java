package tafitasoa.code.Poketra.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tafitasoa.code.Poketra.Models.Taille;
import tafitasoa.code.Poketra.Repository.TailleRepo;

import java.util.List;
import java.util.Optional;

@Service
public class TailleService {
    private TailleRepo tailleRepo;
    public TailleService(TailleRepo tailleRepo){
        this.tailleRepo = tailleRepo;
    }
    public String generateUniqueId() {
        String prefix = "TAIL";
        int paddingSize = 3;
        Taille latestUnite = tailleRepo.findFirstByOrderByIdDesc();
        int lastId = (latestUnite != null) ? Integer.parseInt(latestUnite.getId().substring(prefix.length())) : 0;
        int nextId = lastId + 1;
        String formattedId = String.format("%s%0" + paddingSize + "d", prefix, nextId);
        return formattedId;
    }
    public void create(Taille taille){
        taille.setId(generateUniqueId());
        this.tailleRepo.save(taille);
    }
    public List<Taille> getList(){
        return this.tailleRepo.findAll();
    }
    public Page<Taille> getAllTailles(Pageable pageable) {
        return tailleRepo.findAll(pageable);
    }
    public Taille getById(String id){
        Optional<Taille> optionalTaille = tailleRepo.findById(id);
        if(optionalTaille.isPresent()){
            return optionalTaille.get();
        }
        return null;
    }
    public void delete(String id){
        tailleRepo.deleteById(id);
    }
    public void update(String id,Taille taille){
        Taille taille1 = this.getById(id);
        if(taille1.getId() == taille.getId()){
            taille1.setValeur(taille.getValeur());
            tailleRepo.save(taille1);
        }
    }
}
