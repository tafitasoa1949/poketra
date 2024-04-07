package tafitasoa.code.Poketra.Models;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "taille")
@Getter
@Setter
public class Taille {
    @Id
    private String id;
    @Column(name = "valeur")
    private String valeur;
}
