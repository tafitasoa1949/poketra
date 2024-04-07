package tafitasoa.code.Poketra.Models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "mat_premiere")
@Getter
@Setter
public class MatPremiere {
    @Id
    private String id;
    @OneToOne
    @JoinColumn(name = "unite_id")
    private Unite unite;
    @Column(name = "nom")
    private String nom;
}
