package tafitasoa.code.Poketra.Models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "composition")
@Getter
@Setter
public class Composition {
    @Id
    private String id;
    @OneToOne
    @JoinColumn(name = "produit_id")
    private Produit produit;
    @Column(name = "description")
    private String description;
}
