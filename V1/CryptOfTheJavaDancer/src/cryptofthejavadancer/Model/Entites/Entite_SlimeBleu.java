package cryptofthejavadancer.Model.Entites;

import cryptofthejavadancer.Model.IA.IA_Immobile;
import cryptofthejavadancer.Model.IA.IA_SlimeBleu;

/**
 * Slime Bleu
 * @author Matthieu
 */
public class Entite_SlimeBleu extends Entite_Monstre {

//---------- CONSTRUCTEURS -----------------------------------------------------

    public Entite_SlimeBleu() {
        super(2);
        this.setIA(new IA_SlimeBleu(this));
    }
    
//------------------------------------------------------------------------------

//---------- GETEUR/SETEUR -----------------------------------------------------

    @Override
    public Type_Entite getType() {
        return Type_Entite.SlimeBleu;
    }
    
//------------------------------------------------------------------------------

    @Override
    public int getGainOr() {
        return 7;
    }


}
