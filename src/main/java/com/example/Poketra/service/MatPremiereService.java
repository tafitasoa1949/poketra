package com.example.Poketra.service;

import com.example.Poketra.models.MatPremiere;
import com.example.Poketra.models.Unite;
import com.example.Poketra.repository.MatPremiereRepo;
import com.example.Poketra.repository.UniteRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

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
        Optional<MatPremiere> uniteOptional = matPremiereRepo.findById(id);
        if(uniteOptional.isPresent()){
            return uniteOptional.get();
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
}
