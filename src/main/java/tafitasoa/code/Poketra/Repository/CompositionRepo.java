package tafitasoa.code.Poketra.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tafitasoa.code.Poketra.Models.Composition;

public interface CompositionRepo extends JpaRepository<Composition,String> {
    Composition findFirstByOrderByIdDesc();
    Composition findByProduitId(String produit_id);
}
