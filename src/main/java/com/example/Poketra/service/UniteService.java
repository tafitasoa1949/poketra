package com.example.Poketra.service;

import com.example.Poketra.models.Look;
import com.example.Poketra.models.Unite;
import com.example.Poketra.repository.UniteRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UniteService {
    private UniteRepo uniteRepo;
    public UniteService(UniteRepo uniteRepo){
        this.uniteRepo = uniteRepo;
    }
    public String generateUniqueId() {
        String prefix = "UNIT";
        int paddingSize = 3;
        Unite latestUnite = uniteRepo.findFirstByOrderByIdDesc();
        int lastId = (latestUnite != null) ? Integer.parseInt(latestUnite.getId().substring(prefix.length())) : 0;
        int nextId = lastId + 1;
        String formattedId = String.format("%s%0" + paddingSize + "d", prefix, nextId);
        return formattedId;
    }
    public void create(Unite unite){
        unite.setId(generateUniqueId());
        this.uniteRepo.save(unite);
    }
    public List<Unite> getList(){
        return this.uniteRepo.findAll();
    }
    public Page<Unite> getAllUnites(Pageable pageable) {
        return uniteRepo.findAll(pageable);
    }
    public Unite getById(String id){
        Optional<Unite> uniteOptional = uniteRepo.findById(id);
        if(uniteOptional.isPresent()){
            return uniteOptional.get();
        }
        return null;
    }
    public void delete(String id){
        uniteRepo.deleteById(id);
    }
    public void update(String id,Unite unite){
        Unite unite1 = this.getById(id);
        if(unite1.getId() == unite.getId()){
            unite1.setNom(unite.getNom());
            uniteRepo.save(unite1);
        }
    }
}
