package tafitasoa.code.Poketra.Models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "achat_mp")
@Getter
@Setter
public class Achat_mp {
    @Id
    private String id;
    @OneToOne
    @JoinColumn(name = "mat_premiere_id")
    private MatPremiere matPremiere;
    @Column(name = "quantite")
    private double quantite;
    @Column(name = "prix")
    private double prix;
    @Column(name = "date")
    private Timestamp date;
}
