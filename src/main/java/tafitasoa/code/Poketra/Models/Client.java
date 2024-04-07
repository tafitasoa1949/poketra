package tafitasoa.code.Poketra.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "client")
@Getter
@Setter
public class Client {
    @Id
    private String id;
    @Column(name = "nom")
    private String nom;
    @Column(name = "genre")
    private String genre;
    @Column(name = "date_naissance")
    private Timestamp date_naissance;
}
