package tafitasoa.code.Poketra.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tafitasoa.code.Poketra.Models.Poste;

public interface PosteRepo extends JpaRepository<Poste,String> {
    Poste findFirstByOrderByIdDesc();
}
