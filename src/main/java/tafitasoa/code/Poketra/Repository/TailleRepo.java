package tafitasoa.code.Poketra.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tafitasoa.code.Poketra.Models.Taille;

public interface TailleRepo extends JpaRepository<Taille,String> {
    Taille findFirstByOrderByIdDesc();
}
