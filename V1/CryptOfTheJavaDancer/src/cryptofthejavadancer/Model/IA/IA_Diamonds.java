/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA;

import cryptofthejavadancer.Model.Carte.Cases.Case;
import cryptofthejavadancer.Model.Entites.Entite;
import cryptofthejavadancer.Model.Objet.Type_Objet;

/**
 *
 * @author Beelzed
 */
public class IA_Diamonds extends IA{

    public IA_Diamonds(Entite _entite) {
        super(_entite);
    }
    
    public Type_Action noeudToAction (Case CaseSuivante){
        Type_Action retour = Type_Action.attendre;
        return retour
    }

    @Override
    public Type_Action action() {
        Type_Action retour = Type_Action.attendre;
       if (this.entite.getCase().getObjet().getType() == Type_Objet.Diamant){
           retour = Type_Action.ramasser;
       }
       else if (turn == 0){
           
       }
    }
    
}
