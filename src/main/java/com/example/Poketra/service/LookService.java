package com.example.Poketra.service;

import com.example.Poketra.models.Look;
import com.example.Poketra.models.Unite;
import com.example.Poketra.repository.LookRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LookService {
    private LookRepo lookRepo;
    public LookService(LookRepo lookRepo){
        this.lookRepo = lookRepo;
    }
    public String generateUniqueId() {
        String prefix = "LOOK";
        int paddingSize = 3;
        Look look = lookRepo.findFirstByOrderByIdDesc();
        int lastId = (look != null) ? Integer.parseInt(look.getId().substring(prefix.length())) : 0;
        int nextId = lastId + 1;
        String formattedId = String.format("%s%0" + paddingSize + "d", prefix, nextId);
        return formattedId;
    }
    public void create(Look look){
        look.setId(generateUniqueId());
        this.lookRepo.save(look);
    }
    public List<Look> getList(){
        return this.lookRepo.findAll();
    }
    public Look getById(String id){
        Optional<Look> optionalLook = lookRepo.findById(id);
        if(optionalLook.isPresent()){
            return optionalLook.get();
        }
        return null;
    }
    public void delete(String id){
        lookRepo.deleteById(id);
    }
    public void update(String id,Look look){
        Look look1 = this.getById(id);
        if(look1.getId() == look.getId()){
            look1.setNom(look.getNom());
            lookRepo.save(look1);
        }
    }
}
