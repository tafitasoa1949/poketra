package tafitasoa.code.Poketra.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tafitasoa.code.Poketra.Models.Category_employe;
import tafitasoa.code.Poketra.Models.Look;
import tafitasoa.code.Poketra.Repository.Category_empRepo;
import tafitasoa.code.Poketra.Repository.LookRepo;

import java.util.List;
import java.util.Optional;

@Service
public class Category_empService {
    private Category_empRepo categoryEmpRepo;
    public Category_empService(Category_empRepo categoryEmpRepo) {
        this.categoryEmpRepo = categoryEmpRepo;
    }
    public String generateUniqueId() {
        String prefix = "CTEP";
        int paddingSize = 3;
        Category_employe categoryEmploye = categoryEmpRepo.findFirstByOrderByIdDesc();
        int lastId = (categoryEmploye != null) ? Integer.parseInt(categoryEmploye.getId().substring(prefix.length())) : 0;
        int nextId = lastId + 1;
        String formattedId = String.format("%s%0" + paddingSize + "d", prefix, nextId);
        return formattedId;
    }
    public void create(Category_employe categoryEmploye){
        categoryEmploye.setId(generateUniqueId());
        this.categoryEmpRepo.save(categoryEmploye);
    }
    public List<Category_employe> getList(){
        return this.categoryEmpRepo.findAll();
    }
    public Page<Category_employe> getAllCatEmp(Pageable pageable) {
        return categoryEmpRepo.findAll(pageable);
    }
    public Category_employe getById(String id){
        Optional<Category_employe> optionalCategoryEmploye = categoryEmpRepo.findById(id);
        if(optionalCategoryEmploye.isPresent()){
            return optionalCategoryEmploye.get();
        }
        return null;
    }
    public void delete(String id){
        categoryEmpRepo.deleteById(id);
    }
    public void update(String id,Category_employe categoryEmploye){
        Category_employe categoryEmploye1 = this.getById(id);
        if(categoryEmploye1.getId() == categoryEmploye.getId()){
            categoryEmploye1.setNom(categoryEmploye.getNom());
            categoryEmploye1.setAnciente(categoryEmploye.getAnciente());
            categoryEmploye1.setDecalage(categoryEmploye.getDecalage());
            categoryEmpRepo.save(categoryEmploye1);
        }
    }
}
