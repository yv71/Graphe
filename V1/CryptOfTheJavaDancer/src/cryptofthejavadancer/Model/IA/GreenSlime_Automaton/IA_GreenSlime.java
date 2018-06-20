/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA.GreenSlime_Automaton;

import cryptofthejavadancer.Model.Entites.Entite;
import cryptofthejavadancer.Model.IA.IA;
import cryptofthejavadancer.Model.IA.State;
import cryptofthejavadancer.Model.IA.Type_Action;

/**
 *
 * @author yv066840
 */
public class IA_GreenSlime extends IA{
    private State etat;
    public IA_GreenSlime(Entite _entite) {
        super(_entite);
        this.etat = new State_Wait(this);
    }

    @Override
    public Type_Action action() {
        Type_Action retour = etat.agir();
        this.etat = etat.transition();
        return retour;
    }
    
}
