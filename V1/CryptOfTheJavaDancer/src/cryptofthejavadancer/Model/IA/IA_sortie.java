/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA;

import cryptofthejavadancer.Model.Carte.Cases.Case;
import cryptofthejavadancer.Model.Carte.Graphes.Algorithmes.Dijkstra;
import cryptofthejavadancer.Model.Carte.Graphes.Graphe;
import cryptofthejavadancer.Model.Carte.Graphes.Vertex;
import cryptofthejavadancer.Model.Carte.Map;
import cryptofthejavadancer.Model.Entites.Entite;

/**
 *
 * @author yv066840
 */
public class IA_sortie extends IA {

    private Dijkstra algo;
    public IA_sortie(Entite _entite) {
        super(_entite);
        algo = null;
    }
    

    
    public Type_Action noeudToAction (Case CaseSuivante){
        Case cC = this.entite.getCase();
        Type_Action retour = Type_Action.attendre;
        int X = cC.getColonne();
        int Y = cC.getLigne();
        if (CaseSuivante.getColonne() == (X - 1)){
            retour = Type_Action.deplacement_haut;
        }
        else if (CaseSuivante.getColonne() == (X+1)){
            retour = Type_Action.deplacement_bas;
        }
        else if (CaseSuivante.getLigne() == (Y-1)){
            retour = Type_Action.deplacement_gauche;
        }
        else if (CaseSuivante.getLigne() == (Y+1) ){
            retour = Type_Action.deplacement_droite;
        }
        else {
            
        }
        this.algo.destroyFirst();
        System.out.println(this.algo.getPath());
        return retour;
        
    }
    @Override
    public Type_Action action() {
        Type_Action retour = Type_Action.attendre;
        if (this.turn == 0){
            this.algo = new Dijkstra(this.entite.getMap().getGrapheSimple());
            Map map = this.entite.getMap();
            this.algo.calcul(map.getGrapheSimple().getVertex(this.entite.getCase()), map.getGrapheSimple().getVertex(map.getCase(map.getSortie().getLigne(), map.getSortie().getColonne())));
        }
        else {
            retour = noeudToAction(this.algo.getPath().get(0).getCase());
        }
        turn ++;
        return retour;
    }
    
}
