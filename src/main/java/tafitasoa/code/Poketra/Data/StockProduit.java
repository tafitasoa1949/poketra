package tafitasoa.code.Poketra.Data;

import lombok.Getter;
import lombok.Setter;
import tafitasoa.code.Poketra.Models.Produit;

@Getter
@Setter
public class StockProduit {
    private Produit produit;
    private double quantite;
    private double prix_moyenne;
}
