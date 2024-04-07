package tafitasoa.code.Poketra.Models;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
@Entity
@Table(name = "look")
@Getter
@Setter
public class Look {
    @Id
    private String id;
    @Column(name = "nom")
    private String nom;
    @Column(name = "description")
    private String description;
}
