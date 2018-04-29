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
public class IA_SlimeBleu extends IA{
   boolean direction = true;
   
    public IA_SlimeBleu(Entite _entite) {
        super(_entite);
    }

    
    @Override
    public Type_Action action() {
       Type_Action action;

        if (turn%2==0){
           action = Type_Action.attendre;
       }
        else {
            if (direction == true){
                action = Type_Action.deplacement_haut;
                direction = false;
            }
            else {
                direction = true;
                action = Type_Action.deplacement_bas;
            }
        }
        turn ++;
        return action;
    }
    
    
}
