package tafitasoa.code.Poketra.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tafitasoa.code.Poketra.Models.Produit;

public interface ProduitRepo extends JpaRepository<Produit,String> {
    Produit findFirstByOrderByIdDesc();
}
