package tafitasoa.code.Poketra.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tafitasoa.code.Poketra.Models.Employe;

import java.util.List;

public interface EmployeRepo extends JpaRepository<Employe,String> {
    Employe findFirstByOrderByIdDesc();
    @Query(value = "SELECT * FROM list_emp_aciennete", nativeQuery = true)
    List<Object[]> getListIdwithAnciennte();

}
