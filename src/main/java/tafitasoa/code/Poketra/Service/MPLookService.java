package tafitasoa.code.Poketra.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tafitasoa.code.Poketra.Models.MatPremiereLook;
import tafitasoa.code.Poketra.Repository.MPLookRepo;

import java.util.List;
import java.util.Optional;
@Service
public class MPLookService {
    private MPLookRepo mpLookRepo;
    public MPLookService(MPLookRepo mpLookRepo){
        this.mpLookRepo = mpLookRepo;
    }
    public String generateUniqueId() {
        String prefix = "MPLO";
        int paddingSize = 3;
        MatPremiereLook matPremiereLook = mpLookRepo.findFirstByOrderByIdDesc();
        int lastId = (matPremiereLook != null) ? Integer.parseInt(matPremiereLook.getId().substring(prefix.length())) : 0;
        int nextId = lastId + 1;
        String formattedId = String.format("%s%0" + paddingSize + "d", prefix, nextId);
        return formattedId;
    }
    public void create(MatPremiereLook matPremiereLook){
        matPremiereLook.setId(generateUniqueId());
        System.out.println("jjjjj");
        this.mpLookRepo.save(matPremiereLook);
    }
    public void createOne(MatPremiereLook matPremiereLook){
        this.mpLookRepo.save(matPremiereLook);
    }
    public String createReturnId(MatPremiereLook matPremiereLook){
        String generatedId = generateUniqueId();
        matPremiereLook.setId(generatedId);
        this.mpLookRepo.save(matPremiereLook);
        return generatedId;
    }
    public List<MatPremiereLook> getList(){
        return this.mpLookRepo.findAll();
    }
    public Page<MatPremiereLook> getAllMPLook(Pageable pageable) {
        return mpLookRepo.findAll(pageable);
    }
    public MatPremiereLook getById(String id){
        Optional<MatPremiereLook> optionalMatPremiereLook = mpLookRepo.findById(id);
        if(optionalMatPremiereLook.isPresent()){
            return optionalMatPremiereLook.get();
        }
        return null;
    }
    public void delete(String id){
        mpLookRepo.deleteById(id);
    }
    public void update(String id,MatPremiereLook matPremiereLook){
        MatPremiereLook matPremiereLook1 = this.getById(id);
        if(matPremiereLook1.getId() == matPremiereLook.getId()){
            matPremiereLook1.setLook(matPremiereLook.getLook());
            matPremiereLook1.setMatPremiere(matPremiereLook.getMatPremiere());
            mpLookRepo.save(matPremiereLook1);
        }
    }
    public List<MatPremiereLook> getListByLook(String look_id){
        List<MatPremiereLook> matPremiereLooks = mpLookRepo.findByLookId(look_id);
        return matPremiereLooks;
    }
}
