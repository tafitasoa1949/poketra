package tafitasoa.code.Poketra.Data;

import lombok.Getter;
import lombok.Setter;
import tafitasoa.code.Poketra.Models.Produit_Finie;

@Getter
@Setter
public class DetailProduit {
    private Produit_Finie produitFinie;
    private double prix_total_matiere;
    private double prix_total_employe;
    private double benefice;
}
