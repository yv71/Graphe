/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA.BlueSlime_Automaton;

import cryptofthejavadancer.Model.Carte.Cases.Case;
import cryptofthejavadancer.Model.Carte.Cases.Type_Case;
import cryptofthejavadancer.Model.Entites.Type_Entite;
import cryptofthejavadancer.Model.IA.IA;
import cryptofthejavadancer.Model.IA.State;
import cryptofthejavadancer.Model.IA.Type_Action;

/**
 *
 * @author yv066840
 */
public class State_Down extends State{

    public State_Down(IA ia) {
        super(ia);
    }

    @Override
    public Type_Action agir() {
        Type_Action retour = Type_Action.deplacement_bas;
        Case caseSuivante =this.getMap().getCase(this.getCase().getLigne()+1, this.getCase().getColonne());
        if (caseSuivante.getType()==Type_Case.Sol){
            if(caseSuivante.getEntite()!=null){
                if (caseSuivante.getEntite().getType() == Type_Entite.Cadence){
                    retour = Type_Action.interagir_bas;
                }
                else {
                    retour = Type_Action.attendre;
                }
            }
        }
        else {
            retour = Type_Action.attendre;
        }
        
        return retour;
    }

    @Override
    public State transition() {
        State retour = null;
        if (this.agir()==Type_Action.deplacement_bas){
            retour = new State_Wait2(this.getIA());
        }
        else {
            retour = this;
        }
        return retour;
    }
    
}
