package tafitasoa.code.Poketra.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tafitasoa.code.Poketra.Models.Employe;
import tafitasoa.code.Poketra.Repository.EmployeRepo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeService {
    private EmployeRepo employeRepo;
    private PosteService posteService;
    public EmployeService(EmployeRepo employeRepo, PosteService posteService) {
        this.employeRepo = employeRepo;
        this.posteService = posteService;
    }
    public String generateUniqueId() {
        String prefix = "EMPL";
        int paddingSize = 3;
        Employe employeur = employeRepo.findFirstByOrderByIdDesc();
        int lastId = (employeur != null) ? Integer.parseInt(employeur.getId().substring(prefix.length())) : 0;
        int nextId = lastId + 1;
        String formattedId = String.format("%s%0" + paddingSize + "d", prefix, nextId);
        return formattedId;
    }
    public void create(Employe employeur){
        employeur.setId(generateUniqueId());
        this.employeRepo.save(employeur);
    }
    public List<Employe> getList(){
        return this.employeRepo.findAll();
    }
    public Page<Employe> getAllEmployer(Pageable pageable) {
        return employeRepo.findAll(pageable);
    }
    public Employe getById(String id){
        Optional<Employe> optionalEmployeur = employeRepo.findById(id);
        if(optionalEmployeur.isPresent()){
            return optionalEmployeur.get();
        }
        return null;
    }
    public void delete(String id){
        employeRepo.deleteById(id);
    }
    public void update(String id,Employe employeur){
        Employe employeur1 = this.getById(id);
        if(employeur1.getId() == employeur.getId()){
            employeur1.setNom(employeur.getNom());
            employeur1.setPoste(employeur.getPoste());
            employeur1.setDate_naissance(employeur.getDate_naissance());
            employeur1.setTaux_horaire(employeur.getTaux_horaire());
            employeur1.setDate_embauche(employeur.getDate_embauche());
            employeRepo.save(employeur1);
        }
    }
    public Timestamp convertStringToTimestamp(String dateString) throws Exception{
        String dateFormatPattern = "yyyy-MM-dd";

        // Utilisation de DateTimeFormatter pour parser la chaîne
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormatPattern);

        try {
            // Conversion de la chaîne en objet LocalDate
            LocalDate localDate = LocalDate.parse(dateString, dateFormatter);

            // Création d'un objet Timestamp à partir de l'objet LocalDate
            return Timestamp.valueOf(localDate.atStartOfDay());

        } catch (Exception e) {
            // Gérer l'exception si la conversion échoue
            e.printStackTrace();
            return null; // Ou lancez une exception personnalisée si nécessaire
        }
    }
}
