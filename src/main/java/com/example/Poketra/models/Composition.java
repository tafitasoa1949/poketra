package com.example.Poketra.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "composition")
@Getter
@Setter
public class Composition {
    @Column(name = "idlook")
    private Look look;
    @Column(name = "idmat_premiere")
    private MatPremiere matPremiere;
}
