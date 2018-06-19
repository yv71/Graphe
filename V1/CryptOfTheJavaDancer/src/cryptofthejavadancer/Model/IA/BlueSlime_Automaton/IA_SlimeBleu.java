/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA.BlueSlime_Automaton;

import cryptofthejavadancer.Model.Entites.Entite;
import cryptofthejavadancer.Model.IA.IA;
import cryptofthejavadancer.Model.IA.State;
import cryptofthejavadancer.Model.IA.Type_Action;

/**
 *
 * @author yv066840
 */
public class IA_SlimeBleu extends IA{

    private State state;
    
    public IA_SlimeBleu(Entite _entite) {
        super(_entite);
        this.state = new State_Wait1();
    }

    @Override
    public Type_Action action() {
        Type_Action retour= state.agir();
        this.state = state.transition();
        return retour;
    }
    
}
