package tafitasoa.code.Poketra.Data;

import lombok.Getter;
import lombok.Setter;
import tafitasoa.code.Poketra.Models.Category_employe;
import tafitasoa.code.Poketra.Models.Employe;
import tafitasoa.code.Poketra.Models.Produit_Finie;

@Getter
@Setter
public class EmpOuvrier {
    private Employe employe;
    private Category_employe categoryEmploye;
    private double taux_horaire;
    private double anciennte;
}
