/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA;

import cryptofthejavadancer.Model.Carte.Cases.Case;
import cryptofthejavadancer.Model.Carte.Cases.Type_Case;
import cryptofthejavadancer.Model.Carte.Map;
import cryptofthejavadancer.Model.Entites.Entite;

/**
 *
 * @author yv066840
 */
public class IA_droite extends IA{
    private int turn;
    
    public IA_droite(Entite _entite) {
        super(_entite);
    }

    @Override
    public Type_Action action() {
        
        // return Type_Action.attendre
        Type_Action action = Type_Action.attendre;
        Map mapEntite = entite.getMap();
        Case caseEntite = mapEntite.getCase(entite.getCase().getLigne(), entite.getCase().getColonne());
        Case caseDroite = mapEntite.getCase(entite.getCase().getLigne(), entite.getCase().getColonne()+1);
        Type_Case typeCaseDroite = caseDroite.getType();
        if (typeCaseDroite==Type_Case.Sol && caseDroite.getEntite()==null){
            action = Type_Action.deplacement_droite;
        }
        else if (typeCaseDroite == Type_Case.Mur){
            action = Type_Action.interagir_droite;
        }
        else if (typeCaseDroite == Type_Case.MurDur ){
        }
        else if (typeCaseDroite == Type_Case.Sol && caseDroite.getEntite()!=null){
            action = Type_Action.interagir_droite;
    }
        else if (typeCaseDroite == Type_Case.MurIndestructible){
        }
        if (caseEntite.getObjet()!=null){
            action = Type_Action.ramasser;
        }
        if (turn ==0){
            turn ++;
            action = Type_Action.deplacement_bas;
        }
        
        return action; //To change body of generated methods, choose Tools | Templates.
    }
    
}

/*
        // return Type_Action.attendre
        Type_Action action;
        if (turn==0){
            turn ++;
            action = Type_Action.deplacement_droite;
        }
        else if (turn ==1){
            turn ++;
            action = Type_Action.deplacement_bas;
        }
        else {
            turn ++;
            action = Type_Action.attendre;
        }
        return action; //To change body of generated methods, choose Tools | Templates.

*/


/*
Type_Action action = Type_Action.attendre;
        switch (turn){
            case 0:
               ;
            action = Type_Action.deplacement_haut;
            break;
            case 1:
            action = Type_Action.deplacement_gauche;
            break;
            case 2:
            action = Type_Action.interagir_gauche;
            break;
            case 3:
            action = Type_Action.deplacement_bas;
            break;
            case 4:
            action = Type_Action.deplacement_bas;
            break;
            case 5:
            action = Type_Action.deplacement_bas;
            break;
            case 6:
            action = Type_Action.interagir_bas;
            break;
            case 7:
            action = Type_Action.deplacement_droite;
            break;
            case 8:
            action = Type_Action.interagir_droite;
            break;
            case 9:
            action = Type_Action.interagir_droite;
            break;
            case 10:
            action = Type_Action.deplacement_droite;
            break;
            case 11:
            action = Type_Action.deplacement_droite;
            break;
            case 12:
            action = Type_Action.deplacement_haut;
            break;
            case 13:
            action = Type_Action.ramasser;
            break;
            default :
                action = Type_Action.attendre;
            
        }
        turn ++;
        return action; //To change body of generated methods, choose Tools | Templates.
    }
*/