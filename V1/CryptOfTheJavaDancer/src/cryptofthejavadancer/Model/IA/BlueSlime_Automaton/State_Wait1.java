/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA.BlueSlime_Automaton;

import cryptofthejavadancer.Model.IA.State;
import cryptofthejavadancer.Model.IA.Type_Action;

/**
 *
 * @author yv066840
 */
public class State_Wait1 extends State{

    @Override
    public Type_Action agir() {
        return Type_Action.attendre;
    }

    @Override
    public State transition() {
        return new State_Down();
    }
    
}
