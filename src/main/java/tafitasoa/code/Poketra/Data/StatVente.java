package tafitasoa.code.Poketra.Data;

import lombok.Getter;
import lombok.Setter;
import tafitasoa.code.Poketra.Models.Produit;

@Getter
@Setter
public class StatVente {
    private Produit produit;
    private double homme;
    private double femme;
    private double pourcentage_homme;
    private double pourcentage_femme;
}
