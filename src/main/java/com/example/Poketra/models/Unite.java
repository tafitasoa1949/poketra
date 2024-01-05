package com.example.Poketra.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "unite")
@Getter
@Setter
public class Unite {
    @Id
    private String id;
    @Column(name = "nom")
    private  String nom;
}
