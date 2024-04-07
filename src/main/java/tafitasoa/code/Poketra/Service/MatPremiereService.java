package tafitasoa.code.Poketra.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tafitasoa.code.Poketra.Data.StockMP;
import tafitasoa.code.Poketra.Models.MatPremiere;
import tafitasoa.code.Poketra.Repository.MatPremiereRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class MatPremiereService {
    private MatPremiereRepo matPremiereRepo;
    public MatPremiereService(MatPremiereRepo matPremiereRepo){
        this.matPremiereRepo = matPremiereRepo;
    }
    public String generateUniqueId() {
        String prefix = "MATP";
        int paddingSize = 3;
        MatPremiere matPremiere = matPremiereRepo.findFirstByOrderByIdDesc();
        int lastId = (matPremiere != null) ? Integer.parseInt(matPremiere.getId().substring(prefix.length())) : 0;
        int nextId = lastId + 1;
        String formattedId = String.format("%s%0" + paddingSize + "d", prefix, nextId);
        return formattedId;
    }
    public void create(MatPremiere matPremiere){
        matPremiere.setId(generateUniqueId());
        this.matPremiereRepo.save(matPremiere);
    }
    public List<MatPremiere> getList(){
        return this.matPremiereRepo.findAll();
    }
    public Page<MatPremiere> getAllMatP(Pageable pageable) {
        return matPremiereRepo.findAll(pageable);
    }
    public MatPremiere getById(String id){
        Optional<MatPremiere> matPremiereOptional = matPremiereRepo.findById(id);
        if(matPremiereOptional.isPresent()){
            return matPremiereOptional.get();
        }
        return null;
    }
    public void delete(String id){
        matPremiereRepo.deleteById(id);
    }
    public void update(String id,MatPremiere matPremiere){
        MatPremiere matPremiere1 = this.getById(id);
        if(matPremiere1.getId() == matPremiere.getId()){
            matPremiere1.setNom(matPremiere.getNom());
            matPremiere1.setUnite(matPremiere.getUnite());
            matPremiereRepo.save(matPremiere1);
        }
    }
    public void checkSotck(MatPremiere matPremiere,double alaina)throws Exception{
        String etatStockString = matPremiereRepo.getEtatstock(matPremiere.getId());
        if(etatStockString == null){
            throw new Exception("Quantité insuffisante. On a besoin de " + alaina + " " + matPremiere.getNom());
        }else{
            String[] etatStock = etatStockString.split(",");
            double quantite_reste = Double.parseDouble(etatStock[3]);
            double reste = quantite_reste - alaina;
            if (reste < 0) {
                double manque = Math.abs(reste);
                throw new Exception("Quantité insuffisante. On a besoin de " + manque + " " + matPremiere.getNom());
            }
        }
    }
}
