/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA;

import cryptofthejavadancer.Model.Entites.Entite;
import cryptofthejavadancer.Model.Objet.Objet;
import cryptofthejavadancer.Model.Objet.Type_Objet;
import java.util.ArrayList;

/**
 *
 * @author Beelzed
 */
public class IA_DiamondsOpti extends IA{

    private ArrayList<Objet> diamonds;
    
    public IA_DiamondsOpti(Entite _entite) {
        super(_entite);
        diamonds = new ArrayList<>();
        for (Objet o : this.entite.getMap().getListeObjet()){
            if (o.getType() == Type_Objet.Diamant){
                this.diamonds.add(o);
            }
        }
    }

    public Type_Action action() {
        return Type_Action.acheter;
    } 
    
    public Objet closestDiamond(){
        Objet diam = null;
        if (!this.diamonds.isEmpty()){
            
        }
       return diam;
    }
    
}
