/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA.BlueSlime_Automaton;

import cryptofthejavadancer.Model.Carte.Cases.Type_Case;
import cryptofthejavadancer.Model.Entites.Entite;
import cryptofthejavadancer.Model.IA.IA;
import cryptofthejavadancer.Model.IA.State;
import cryptofthejavadancer.Model.IA.Type_Action;

/**
 *
 * @author yv066840
 */
public class IA_SlimeBleu extends IA {

    private State state;
    private boolean FirstTurn;

    public IA_SlimeBleu(Entite _entite) {
        super(_entite);
        FirstTurn = true;
        state = null;

    }

    @Override
    public Type_Action action() {
        if (FirstTurn) {
            FirstTurn = false;
            if (this.getMap().getCase(this.entite.getCase().getLigne() - 1, this.entite.getCase().getColonne()).getType() != Type_Case.Sol) {
                this.state = new State_Wait1(this);
            } else {
                this.state = new State_Wait2(this);
            }
        }
        Type_Action retour = state.agir();
        this.state = state.transition();
        return retour;
    }

    public Type_Action aled(Type_Action SALUT) {
        Type_Action retour = action();
        return retour;
    }
}
