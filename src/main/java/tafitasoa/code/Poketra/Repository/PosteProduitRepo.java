package tafitasoa.code.Poketra.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tafitasoa.code.Poketra.Models.Poste_Produit;

import java.util.List;

public interface PosteProduitRepo extends JpaRepository<Poste_Produit,String> {
    Poste_Produit findFirstByOrderByIdDesc();
    List<Poste_Produit> findByProduitId(String produitId);
}
