package tafitasoa.code.Poketra.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "vente")
@Getter
@Setter
public class Vente {
    @Id
    private String id;
    @OneToOne
    @JoinColumn(name = "produit_id")
    private Produit produit;
    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @Column(name = "quantite")
    private double quantite;
    @Column(name = "date")
    private Timestamp date;
}
