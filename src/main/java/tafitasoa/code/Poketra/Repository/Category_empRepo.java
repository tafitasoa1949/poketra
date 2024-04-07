package tafitasoa.code.Poketra.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tafitasoa.code.Poketra.Models.Category_employe;
import tafitasoa.code.Poketra.Models.Composition;

public interface Category_empRepo extends JpaRepository<Category_employe,String> {
    Category_employe findFirstByOrderByIdDesc();
    @Query(value = "select * from category_employe where anciente >= :anciente or anciente > 5 order by anciente asc limit 1", nativeQuery = true)
    Category_employe getByAnciente(@Param("anciente") double anciente);
}
