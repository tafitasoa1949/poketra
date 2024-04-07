package tafitasoa.code.Poketra.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tafitasoa.code.Poketra.Models.DetailComposition;
import tafitasoa.code.Poketra.Repository.DetailCompoRepo;

import java.util.List;
import java.util.Optional;

@Service
public class DetailCompoService {
    private DetailCompoRepo detailCompoRepo;
    public DetailCompoService(DetailCompoRepo detailCompoRepo){
        this.detailCompoRepo = detailCompoRepo;
    }
    public String generateUniqueId() {
        String prefix = "DETC";
        int paddingSize = 3;
        DetailComposition detailComposition = detailCompoRepo.findFirstByOrderByIdDesc();
        int lastId = (detailComposition != null) ? Integer.parseInt(detailComposition.getId().substring(prefix.length())) : 0;
        int nextId = lastId + 1;
        String formattedId = String.format("%s%0" + paddingSize + "d", prefix, nextId);
        return formattedId;
    }
    public void create(DetailComposition detailComposition){
        detailComposition.setId(this.generateUniqueId());
        this.detailCompoRepo.save(detailComposition);
    }
    public List<DetailComposition> getList(){
        return this.detailCompoRepo.findAll();
    }
    public Page<DetailComposition> getAllDetailComposition(Pageable pageable) {
        return detailCompoRepo.findAll(pageable);
    }
    public DetailComposition getById(String id){
        Optional<DetailComposition> optionalDetailComposition = detailCompoRepo.findById(id);
        if(optionalDetailComposition.isPresent()){
            return optionalDetailComposition.get();
        }
        return null;
    }
    public void delete(String id){
        detailCompoRepo.deleteById(id);
    }
    public void update(String id,DetailComposition detailComposition){
        DetailComposition detailComposition1 = this.getById(id);
        if(detailComposition1.getId() == detailComposition.getId()){
            detailComposition1.setComposition(detailComposition.getComposition());
            detailComposition1.setMatPremiere(detailComposition.getMatPremiere());
            detailComposition1.setQuantite(detailComposition.getQuantite());
            detailCompoRepo.save(detailComposition1);
        }
    }
}
