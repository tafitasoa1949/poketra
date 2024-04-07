package tafitasoa.code.Poketra.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tafitasoa.code.Poketra.Models.Vente;

import java.util.List;

public interface VenteRepo extends JpaRepository<Vente,String> {
    Vente findFirstByOrderByIdDesc();
    @Query(value = "SELECT * FROM vente_totale", nativeQuery = true)
    List<String[]> getStatistiqueVente();

    @Query(value = "SELECT * FROM vente_totale where id = :produit_id", nativeQuery = true)
    String getStatistiqueVenteOne(@Param("produit_id") String produit_id);
    List<Vente> findByProduitId(@Param("produit_id") String produit_id);
}
