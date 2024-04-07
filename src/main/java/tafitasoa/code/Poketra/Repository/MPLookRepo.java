package tafitasoa.code.Poketra.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tafitasoa.code.Poketra.Models.MatPremiereLook;

import java.util.List;

public interface MPLookRepo extends JpaRepository<MatPremiereLook,String> {
    MatPremiereLook findFirstByOrderByIdDesc();
    List<MatPremiereLook> findByLookId(String lookId);
}
