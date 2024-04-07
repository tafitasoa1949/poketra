package tafitasoa.code.Poketra.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "category_employe")
@Getter
@Setter
public class Category_employe {
    @Id
    private String id;
    @Column(name = "nom")
    private String nom;
    @Column(name = "anciente")
    private double anciente;
    @Column(name = "decalage")
    private double decalage;
}
