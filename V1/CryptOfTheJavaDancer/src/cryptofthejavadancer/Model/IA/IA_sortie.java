/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA;

import cryptofthejavadancer.Model.Carte.Cases.Case;
import cryptofthejavadancer.Model.Carte.Graphes.Algorithmes.Dijkstra;
import cryptofthejavadancer.Model.Carte.Graphes.Vertex;
import cryptofthejavadancer.Model.Entites.Entite;

/**
 *
 * @author yv066840
 */
public class IA_sortie extends IA {

    private Dijkstra algo;
    public IA_sortie(Entite _entite) {
        super(_entite);
    }

    
    public Type_Action noeudToAction (Case CaseSuivante){
        
    }
    @Override
    public Type_Action action() {
        if (this.algo.getPath().isEmpty()){
            this.algo.calcul(_debug, _fin);
        }
    }
    
}
