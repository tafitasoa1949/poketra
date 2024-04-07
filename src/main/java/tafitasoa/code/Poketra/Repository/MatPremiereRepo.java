package tafitasoa.code.Poketra.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tafitasoa.code.Poketra.Models.MatPremiere;

import java.util.List;

public interface MatPremiereRepo extends JpaRepository<MatPremiere,String>{
    MatPremiere findFirstByOrderByIdDesc();
    @Query(value = "SELECT mat_premiere_id,entre,sortie,reste FROM stock WHERE mat_premiere_id = :mat_premiere_id", nativeQuery = true)
    String getEtatstock(@Param("mat_premiere_id") String mat_premiere_id);
}
