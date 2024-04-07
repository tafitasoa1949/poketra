package tafitasoa.code.Poketra.Models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "employe")
@Getter
@Setter
public class Employe {
    @Id
    private String id;
    @Column(name = "nom")
    private String nom;
    @OneToOne()
    @JoinColumn(name = "poste_id")
    private Poste poste;
    @Column(name = "date_naissance")
    private Timestamp date_naissance;
    @Column(name = "taux_horaire")
    private double taux_horaire;
    @Column(name = "date_embauche")
    private Timestamp date_embauche;
}
