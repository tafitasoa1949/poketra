package com.example.Poketra.repository;

import com.example.Poketra.models.MatPremiere;
import com.example.Poketra.models.Unite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatPremiereRepo extends JpaRepository<MatPremiere,String> {
    MatPremiere findFirstByOrderByIdDesc();
}
