package tafitasoa.code.Poketra.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tafitasoa.code.Poketra.Models.Achat_mp;
import tafitasoa.code.Poketra.Models.MatPremiere;

import java.util.List;

public interface AchatMPRepo extends JpaRepository<Achat_mp,String> {
    Achat_mp findFirstByOrderByIdDesc();
    Page<Achat_mp> findAllByOrderByIdDesc(Pageable pageable);
    @Query(value = "SELECT * FROM stock", nativeQuery = true)
    List<Object[]> getStock();
    List<Achat_mp> findAllByMatPremiereId(String matPremiereId);
}
