/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA;

import cryptofthejavadancer.Model.Entites.Entite;

/**
 *
 * @author Beelzed
 */
public class IA_SlimeJaune extends IA{

    public IA_SlimeJaune(Entite _entite) {
        super(_entite);
    }

    @Override
    public Type_Action action() {
       Type_Action action;
       switch (turn%4){
           case 0:
               action = Type_Action.deplacement_haut;
               break;
            case 1:
               action = Type_Action.deplacement_droite;
               break;
            case 2:
               action = Type_Action.deplacement_bas;
               break;
            case 3:
               action = Type_Action.deplacement_gauche;
               break;
            default : 
                action = Type_Action.attendre;
       }
       turn ++;
       return action;
    }
    
}
