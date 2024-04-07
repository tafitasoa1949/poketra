package tafitasoa.code.Poketra.Models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "personne_produit")
@Getter
@Setter
public class Poste_Produit {
    @Id
    private String id;
    @OneToOne
    @JoinColumn(name = "produit_id")
    private Produit produit;
    @OneToOne
    @JoinColumn(name = "poste_id")
    private Poste poste;
    @Column(name = "nombre")
    private int nombre;
    @Column(name = "duree")
    private double duree;
}
