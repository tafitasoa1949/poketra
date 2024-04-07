package tafitasoa.code.Poketra.Models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "detail_composition")
@Getter
@Setter
public class DetailComposition {
    @Id
    private String id;
    @OneToOne
    @JoinColumn(name = "composition_id")
    private Composition composition;
    @OneToOne
    @JoinColumn(name = "mat_premiere_id")
    private MatPremiere matPremiere;
    @Column(name = "quantite")
    private double quantite;
}
