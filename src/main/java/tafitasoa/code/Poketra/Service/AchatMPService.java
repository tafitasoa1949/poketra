package tafitasoa.code.Poketra.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tafitasoa.code.Poketra.Data.StockMP;
import tafitasoa.code.Poketra.Models.Achat_mp;
import tafitasoa.code.Poketra.Models.MatPremiere;
import tafitasoa.code.Poketra.Repository.AchatMPRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class AchatMPService {
    private AchatMPRepo achatMPRepo;
    private MatPremiereService matPremiereService;
    public AchatMPService(AchatMPRepo achatMPRepo,MatPremiereService matPremiereService){
        this.achatMPRepo = achatMPRepo;
        this.matPremiereService = matPremiereService;
    }
    public String generateUniqueId() {
        String prefix = "ACHT";
        int paddingSize = 3;
        Achat_mp latestAchat = achatMPRepo.findFirstByOrderByIdDesc();
        int lastId = (latestAchat != null) ? Integer.parseInt(latestAchat.getId().substring(prefix.length())) : 0;
        int nextId = lastId + 1;
        String formattedId = String.format("%s%0" + paddingSize + "d", prefix, nextId);
        return formattedId;
    }
    public void create(Achat_mp achatMp){
        achatMp.setId(generateUniqueId());
        this.achatMPRepo.save(achatMp);
    }
    public List<Achat_mp> getList(){
        return this.achatMPRepo.findAll();
    }
    public Page<Achat_mp> getAllTAchat(Pageable pageable) {
        return achatMPRepo.findAllByOrderByIdDesc(pageable);
    }
    public Achat_mp getById(String id){
        Optional<Achat_mp> optionalAchatMp = achatMPRepo.findById(id);
        if(optionalAchatMp.isPresent()){
            return optionalAchatMp.get();
        }
        return null;
    }
    public void delete(String id){
        achatMPRepo.deleteById(id);
    }
    public void update(String id,Achat_mp achatMp){
        Achat_mp achatMp1 = this.getById(id);
        if(achatMp1.getId() == achatMp.getId()){
            achatMp1.setMatPremiere(achatMp.getMatPremiere());
            achatMp1.setQuantite(achatMp.getQuantite());
            achatMp1.setPrix(achatMp.getPrix());
            achatMp1.setDate(achatMp.getDate());
            achatMPRepo.save(achatMp1);
        }
    }
    public double getPUMPMatPremiere(String matPremiereId){
        double pump = 0,numerateur=0,denominateur=0;
        List<Achat_mp> achatMpList = achatMPRepo.findAllByMatPremiereId(matPremiereId);
        for (Achat_mp achatMp : achatMpList) {
            numerateur += achatMp.getQuantite() * achatMp.getPrix();
            denominateur += achatMp.getQuantite();
        }
        pump = numerateur / denominateur;
        return pump;
    }
    public List<StockMP> getListStock(){
        List<StockMP> stockList = new ArrayList<>();
        List<Object[]> list = achatMPRepo.getStock();
        for(int i=0 ; i < list.size() ; i++){
            StockMP stock_mat_premiere = new StockMP();
            MatPremiere matPremiere = matPremiereService.getById(list.get(i)[0].toString());
            stock_mat_premiere.setMatPremiere(matPremiere);
            double quantiteDouble = Double.parseDouble(list.get(i)[3].toString());
            stock_mat_premiere.setQuantite(quantiteDouble);
            double pump = this.getPUMPMatPremiere(list.get(i)[0].toString());
            stock_mat_premiere.setPump(pump);
            stockList.add(stock_mat_premiere);
        }
        return stockList;
    }
}
