package tafitasoa.code.Poketra.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tafitasoa.code.Poketra.Models.Composition;
import tafitasoa.code.Poketra.Repository.CompositionRepo;

import java.util.List;
import java.util.Optional;

@Service
public class CompositionService {
    private CompositionRepo compositionRepo;
    public CompositionService(CompositionRepo compositionRepo){
        this.compositionRepo = compositionRepo;
    }
    public String generateUniqueId() {
        String prefix = "COMP";
        int paddingSize = 3;
        Composition composition = compositionRepo.findFirstByOrderByIdDesc();
        int lastId = (composition != null) ? Integer.parseInt(composition.getId().substring(prefix.length())) : 0;
        int nextId = lastId + 1;
        String formattedId = String.format("%s%0" + paddingSize + "d", prefix, nextId);
        return formattedId;
    }
    public void create(Composition composition){
        composition.setId(generateUniqueId());
        this.compositionRepo.save(composition);
    }
    public void createOne(Composition composition){
        this.compositionRepo.save(composition);
    }
    public String createReturnId(Composition composition){
        String generatedId = generateUniqueId();
        composition.setId(generatedId);
        this.compositionRepo.save(composition);
        return generatedId;
    }
    public List<Composition> getList(){
        return this.compositionRepo.findAll();
    }
    public Page<Composition> getAllComposition(Pageable pageable) {
        return compositionRepo.findAll(pageable);
    }
    public Composition getById(String id){
        Optional<Composition> optionalComposition = compositionRepo.findById(id);
        if(optionalComposition.isPresent()){
            return optionalComposition.get();
        }
        return null;
    }
    public void delete(String id){
        compositionRepo.deleteById(id);
    }
    public void update(String id,Composition composition){
        Composition composition1 = this.getById(id);
        if(composition1.getId() == composition.getId()){
            composition1.setProduit(composition.getProduit());
            composition1.setDescription(composition.getDescription());
            compositionRepo.save(composition1);
        }
    }
}
