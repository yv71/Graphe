/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA.Skeleton_Automaton;

import cryptofthejavadancer.Model.Entites.Entite;
import cryptofthejavadancer.Model.IA.IA;
import cryptofthejavadancer.Model.IA.State;
import cryptofthejavadancer.Model.IA.Type_Action;

/**
 *
 * @author yv066840
 */
public class IA_Skeleton extends IA{
    private boolean firstTurn;
    private State etat;
    
    public IA_Skeleton(Entite _entite) {
        super(_entite);
        this.etat = null;
        firstTurn = true;
    }

    @Override
    public Type_Action action() {
        Type_Action retour = Type_Action.attendre;
        if (firstTurn){
            
        }
        return retour;
    }
    
}
