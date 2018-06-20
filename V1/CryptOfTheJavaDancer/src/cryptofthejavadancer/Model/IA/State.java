/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA;

import cryptofthejavadancer.Model.Carte.Cases.Case;
import cryptofthejavadancer.Model.Carte.Map;
import cryptofthejavadancer.Model.IA.Type_Action;

/**
 *
 * @author yv066840
 */
public abstract class State {
    private IA ia;
    
    public State(IA ia){
        this.ia = ia;
    }
    public abstract Type_Action agir();
    public abstract State transition();
    
    public IA getIA(){
        return this.ia;
    }
    
    public Case getCase(){
        return this.ia.getCase();
    }
    
    public Map getMap(){
        return this.ia.getMap();
    }
}
