package tafitasoa.code.Poketra.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tafitasoa.code.Poketra.Models.Type;

public interface TypeRepo extends JpaRepository<Type,String> {
    Type findFirstByOrderByIdDesc();
}
