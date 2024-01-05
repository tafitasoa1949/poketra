package com.example.Poketra.repository;

import com.example.Poketra.models.Look;
import com.example.Poketra.models.Unite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LookRepo extends JpaRepository<Look,String> {
    Look findFirstByOrderByIdDesc();
}
