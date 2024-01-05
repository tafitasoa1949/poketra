package com.example.Poketra.repository;

import com.example.Poketra.models.Unite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UniteRepo extends JpaRepository<Unite,String> {
    Unite findFirstByOrderByIdDesc();
}
