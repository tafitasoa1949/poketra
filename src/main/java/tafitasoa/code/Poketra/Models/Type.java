package tafitasoa.code.Poketra.Models;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "type")
@Getter
@Setter
public class Type {
    @Id
    private String id;
    @Column(name = "nom")
    private String nom;
}
