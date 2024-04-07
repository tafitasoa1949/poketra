package tafitasoa.code.Poketra.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tafitasoa.code.Poketra.Models.Type;
import tafitasoa.code.Poketra.Repository.TypeRepo;

import java.util.List;
import java.util.Optional;

@Service
public class TypeService {
    private TypeRepo typeRepo;
    public TypeService(TypeRepo typeRepo){
        this.typeRepo = typeRepo;
    }
    public String generateUniqueId() {
        String prefix = "TYPE";
        int paddingSize = 3;
        Type lastType = typeRepo.findFirstByOrderByIdDesc();
        int lastId = (lastType != null) ? Integer.parseInt(lastType.getId().substring(prefix.length())) : 0;
        int nextId = lastId + 1;
        String formattedId = String.format("%s%0" + paddingSize + "d", prefix, nextId);
        return formattedId;
    }
    public void create(Type type){
        type.setId(generateUniqueId());
        this.typeRepo.save(type);
    }
    public List<Type> getList(){
        return this.typeRepo.findAll();
    }
    public Page<Type> getAllTypes(Pageable pageable) {
        return typeRepo.findAll(pageable);
    }
    public Type getById(String id){
        Optional<Type> optionalType = typeRepo.findById(id);
        if(optionalType.isPresent()){
            return optionalType.get();
        }
        return null;
    }
    public void delete(String id){
        typeRepo.deleteById(id);
    }
    public void update(String id,Type type){
        Type type1 = this.getById(id);
        if(type1.getId() == type.getId()){
            type1.setNom(type.getNom());
            typeRepo.save(type1);
        }
    }
}
