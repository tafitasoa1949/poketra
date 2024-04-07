package tafitasoa.code.Poketra.Models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "produit")
@Getter
@Setter
public class Produit {
    @Id
    private String id;
    @OneToOne
    @JoinColumn(name = "type_id")
    private Type type;
    @OneToOne
    @JoinColumn(name = "look_id")
    private Look look;
    @Column(name = "nom")
    private String nom;
}
