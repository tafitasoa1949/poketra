package tafitasoa.code.Poketra.Models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "mat_premiere_look")
@Getter
@Setter
public class MatPremiereLook {
    @Id
    private String id;
    @OneToOne
    @JoinColumn(name = "look_id")
    private Look look;
    @OneToOne
    @JoinColumn(name = "mat_premiere_id")
    private MatPremiere matPremiere;
}
