package tafitasoa.code.Poketra.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tafitasoa.code.Poketra.Models.Client;
import tafitasoa.code.Poketra.Models.Taille;
import tafitasoa.code.Poketra.Repository.ClientRepo;
import tafitasoa.code.Poketra.Repository.TailleRepo;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private ClientRepo clientRepo;
    public ClientService(ClientRepo clientRepo){
        this.clientRepo = clientRepo;
    }
    public String generateUniqueId() {
        String prefix = "CLIT";
        int paddingSize = 3;
        Client latesClient = clientRepo.findFirstByOrderByIdDesc();
        int lastId = (latesClient != null) ? Integer.parseInt(latesClient.getId().substring(prefix.length())) : 0;
        int nextId = lastId + 1;
        String formattedId = String.format("%s%0" + paddingSize + "d", prefix, nextId);
        return formattedId;
    }
    public void create(Client client){
        client.setId(generateUniqueId());
        this.clientRepo.save(client);
    }
    public List<Client> getList(){
        return this.clientRepo.findAll();
    }
    public Page<Client> getAllTClients(Pageable pageable) {
        return clientRepo.findAll(pageable);
    }
    public Client getById(String id){
        Optional<Client> optionalClient = clientRepo.findById(id);
        if(optionalClient.isPresent()){
            return optionalClient.get();
        }
        return null;
    }
    public void delete(String id){
        clientRepo.deleteById(id);
    }
    public void update(String id,Client client){
        Client client1 = this.getById(id);
        if(client1.getId() == client.getId()){
            client1.setNom(client.getNom());
            client1.setGenre(client.getGenre());
            client1.setDate_naissance(client.getDate_naissance());
            clientRepo.save(client1);
        }
    }
}
