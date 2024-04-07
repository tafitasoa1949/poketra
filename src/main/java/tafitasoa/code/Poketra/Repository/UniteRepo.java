package tafitasoa.code.Poketra.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tafitasoa.code.Poketra.Models.Unite;

public interface UniteRepo extends JpaRepository<Unite,String> {
    Unite findFirstByOrderByIdDesc();
}
