package tafitasoa.code.Poketra.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tafitasoa.code.Poketra.Models.Look;

public interface LookRepo extends JpaRepository<Look,String> {
    Look findFirstByOrderByIdDesc();
}
