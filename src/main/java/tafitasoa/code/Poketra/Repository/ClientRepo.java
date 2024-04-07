package tafitasoa.code.Poketra.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tafitasoa.code.Poketra.Models.Client;

public interface ClientRepo extends JpaRepository<Client,String> {
    Client findFirstByOrderByIdDesc();
}
