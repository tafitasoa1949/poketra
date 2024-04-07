package tafitasoa.code.Poketra.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tafitasoa.code.Poketra.Models.Produit_Finie;

import java.util.List;

public interface ProduitFinieRepo extends JpaRepository<Produit_Finie,String> {
    Produit_Finie findFirstByOrderByIdDesc();
    @Query(value = "SELECT prix FROM get_tarif_employe WHERE produit_id = :produit_id", nativeQuery = true)
    double getPrixTotalEmploye(@Param("produit_id") String produit_id);
    @Query(value = "SELECT * FROM stock_produit_final", nativeQuery = true)
    List<String[]> getStockProduit();
    @Query(value = "select solde_ve from stock_produit_final where produit_id = :produit_id", nativeQuery = true)
    double getReste(@Param("produit_id") String produit_id);
}
