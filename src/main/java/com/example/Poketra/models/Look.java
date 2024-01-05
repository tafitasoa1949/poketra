package com.example.Poketra.models;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
@Entity
@Table(name = "look")
@Getter
@Setter
public class Look {
    @Id
    private String id;
    @Column(name = "nom")
    private  String nom;
}
