package tafitasoa.code.Poketra.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tafitasoa.code.Poketra.Models.Composition;
import tafitasoa.code.Poketra.Models.DetailComposition;
import tafitasoa.code.Poketra.Models.Produit;
import tafitasoa.code.Poketra.Repository.CompositionRepo;
import tafitasoa.code.Poketra.Repository.DetailCompoRepo;
import tafitasoa.code.Poketra.Repository.ProduitRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;

@Service
public class ProduitService {
    private ProduitRepo produitRepo;
    private TailleService tailleService;
    private MatPremiereService matPremiereService;
    private CompositionRepo compositionRepo;
    private DetailCompoRepo detailCompoRepo;
    private AchatMPService achatMPService;
    public ProduitService(ProduitRepo produitRepo,TailleService tailleService,MatPremiereService matPremiereService
    ,CompositionRepo compositionRepo,DetailCompoRepo detailCompoRepo,AchatMPService achatMPService){
        this.produitRepo = produitRepo;
        this.tailleService = tailleService;
        this.matPremiereService = matPremiereService;
        this.compositionRepo = compositionRepo;
        this.detailCompoRepo = detailCompoRepo;
        this.achatMPService = achatMPService;
    }
    public String generateUniqueId() {
        String prefix = "PROD";
        int paddingSize = 3;
        Produit latestProduit = produitRepo.findFirstByOrderByIdDesc();
        int lastId = (latestProduit != null) ? Integer.parseInt(latestProduit.getId().substring(prefix.length())) : 0;
        int nextId = lastId + 1;
        String formattedId = String.format("%s%0" + paddingSize + "d", prefix, nextId);
        return formattedId;
    }
    public void create(Produit produit){
        produit.setId(generateUniqueId());
        this.produitRepo.save(produit);
    }
    public void createOne(Produit produit){
        this.produitRepo.save(produit);
    }
    public List<Produit> getList(){
        return this.produitRepo.findAll();
    }
    public Page<Produit> getAllProduit(Pageable pageable) {
        return produitRepo.findAll(pageable);
    }
    public Produit getById(String id){
        Optional<Produit> optionalProduit = produitRepo.findById(id);
        if(optionalProduit.isPresent()){
            return optionalProduit.get();
        }
        return null;
    }
    public void delete(String id){
        produitRepo.deleteById(id);
    }
    public void update(String id,Produit produit){
        Produit produit1 = this.getById(id);
        if(produit1.getId() == produit.getId()){
            produit1.setLook(produit.getLook());
            produit1.setType(produit.getType());
            produit1.setNom(produit.getNom());
            produitRepo.save(produit1);
        }
    }
    public String[] extractParams(Map<String, String> params, String regex) {
        Map<String, String> extractedParams = new HashMap<>();
        Pattern pattern = Pattern.compile(regex);

        for (Map.Entry<String, String> entry : params.entrySet()) {
            Matcher matcher = pattern.matcher(entry.getKey());
            if (matcher.matches()) {
                extractedParams.put(matcher.group(1), entry.getValue());
            }
        }

        String[] resultArray = new String[extractedParams.size()];
        for (Map.Entry<String, String> entry : extractedParams.entrySet()) {
            int index = Integer.parseInt(entry.getKey());
            resultArray[index] = entry.getValue();
        }

        return resultArray;
    }
    public double[] convertToDoubleArray(String[] stringArray) {
        double[] doubleArray = new double[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            doubleArray[i] = Double.parseDouble(stringArray[i]);
        }
        return doubleArray;
    }
    public int[] convertToIntArray(String[] stringArray) {
        int[] intArray = new int[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            intArray[i] = Integer.parseInt(stringArray[i]);
        }
        return intArray;
    }
    public double getPrixTotalMatP(Produit produit)throws Exception{
        double prix = 0;
        try {
            Composition composition = compositionRepo.findByProduitId(produit.getId());
            List<DetailComposition> detailCompositions = detailCompoRepo.findByCompositionId(composition.getId());
            for(int i=0 ; i < detailCompositions.size() ; i++){
                double pump = achatMPService.getPUMPMatPremiere(detailCompositions.get(i).getMatPremiere().getId());
                //System.out.println("Mat"+detailCompositions.get(i).getMatPremiere().getNom()+"Pump"+pump+"Quantite"+detailCompositions.get(i).getQuantite());
                prix = prix + (pump*detailCompositions.get(i).getQuantite());
                //System.out.println("egale "+(pump*detailCompositions.get(i).getQuantite()));
            }
        }catch (Exception e){
            throw e;
        }
        return prix;
    }
}
