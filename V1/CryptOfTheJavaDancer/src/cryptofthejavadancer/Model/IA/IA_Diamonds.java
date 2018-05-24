/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA;

import cryptofthejavadancer.Model.Carte.Cases.Case;
import cryptofthejavadancer.Model.Entites.Entite;

/**
 *
 * @author Beelzed
 */
public class IA_Diamonds extends IA{

    public IA_Diamonds(Entite _entite) {
        super(_entite);
    }
    
    public Type_Action noeudToAction (Case CaseSuivante){
        Type_Action retour = Type_Action.acheter;
        
    }

    @Override
    public Type_Action action() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
