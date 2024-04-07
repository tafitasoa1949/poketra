package tafitasoa.code.Poketra.Models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "produit_finie")
@Getter
@Setter
public class Produit_Finie {
    @Id
    private String id;
    @OneToOne
    @JoinColumn(name = "produit_id")
    private Produit produit;
    @Column(name = "quantite")
    private double quantite;
    @Column(name = "prix_unitaire")
    private double prix_unitaire;
    @Column(name = "date")
    private Timestamp date;
}
