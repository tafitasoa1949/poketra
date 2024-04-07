package tafitasoa.code.Poketra.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tafitasoa.code.Poketra.Models.DetailComposition;

import java.util.List;

public interface DetailCompoRepo extends JpaRepository<DetailComposition,String> {
    DetailComposition findFirstByOrderByIdDesc();
    List<DetailComposition> findByCompositionId(String composition_id);
}
