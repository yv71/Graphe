/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA;

import cryptofthejavadancer.Model.Carte.Cases.Case;
import cryptofthejavadancer.Model.Carte.Cases.Case_Sol;
import cryptofthejavadancer.Model.Carte.Cases.Type_Case;
import cryptofthejavadancer.Model.Carte.Graphes.Algorithmes.Dijkstra;
import cryptofthejavadancer.Model.Carte.Graphes.Graphe;
import cryptofthejavadancer.Model.Carte.Graphes.Vertex;
import cryptofthejavadancer.Model.Carte.Graphes.VertexCouple;
import cryptofthejavadancer.Model.Carte.Map;
import cryptofthejavadancer.Model.Entites.Entite;
import cryptofthejavadancer.Model.Entites.Type_Entite;
import cryptofthejavadancer.Model.Objet.Type_Objet;

/**
 *
 * @author yv066840
 */
public class IA_sortie extends IA {
    private boolean tour;
    private Dijkstra algo;
    public IA_sortie(Entite _entite) {
        super(_entite);
        algo = null;
        tour = false;
    }
    

    
    public Type_Action noeudToAction (Case CaseSuivante){
        Case cC = this.entite.getCase();
        Type_Action retour = Type_Action.attendre;
        int X = cC.getLigne();
        int Y = cC.getColonne();      
        if (CaseSuivante.getType() == Type_Case.Sol){
            if (CaseSuivante.getEntite() == null){
                if (CaseSuivante.getLigne() == (X - 1)){
                    retour = Type_Action.deplacement_haut;
                }
                else if (CaseSuivante.getLigne() == (X+1)){
                    retour = Type_Action.deplacement_bas;
                }
                else if (CaseSuivante.getColonne() == (Y-1)){
                    retour = Type_Action.deplacement_gauche;
                }
                else if (CaseSuivante.getColonne() == (Y+1) ){
                    retour = Type_Action.deplacement_droite;
                }
                this.algo.destroyFirst();
            }
            else if( CaseSuivante.getEntite().getType() != Type_Entite.Cadence){
                if (CaseSuivante.getLigne() == (X - 1)){
                    retour = Type_Action.interagir_haut;
                }
                else if (CaseSuivante.getLigne() == (X+1)){
                    retour = Type_Action.interagir_bas;
                }
                else if (CaseSuivante.getColonne() == (Y-1)){
                    retour = Type_Action.interagir_gauche;
                }
                else if (CaseSuivante.getColonne() == (Y+1) ){
                    retour = Type_Action.interagir_droite;
                }
            }
        }
        else {
            
                if (CaseSuivante.getLigne() == (X - 1)){
                        retour = Type_Action.interagir_haut;
                    }
                    else if (CaseSuivante.getLigne() == (X+1)){
                        retour = Type_Action.interagir_bas;
                    }
                    else if (CaseSuivante.getColonne() == (Y-1)){
                        retour = Type_Action.interagir_gauche;
                    }
                    else if (CaseSuivante.getColonne() == (Y+1) ){
                        retour = Type_Action.interagir_droite;
                    } 
                    
                    for(Vertex v : this.algo.getGraph().getVertices().values()){
                        if (v.getNeighbours().contains(this.getGraph().getVertex(CaseSuivante))){
                            VertexCouple cV = new VertexCouple(this.getGraph().getVertex(CaseSuivante),v);
                            this.getGraph().getLabels().put(cV,1);
                        }
                    }
                    algo.getGraph().getVertices().put(new Case_Sol(CaseSuivante.getLigne(),CaseSuivante.getColonne(),getMap()),algo.getGraph().getVertex(CaseSuivante));
        }
        System.out.println(retour);
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
        if (this.algo.getPath().isEmpty()){
            retour = Type_Action.sortir;
        }
        else {
            retour = noeudToAction(this.algo.getPath().get(0).getCase());
        }
        turn ++;
        return retour;
    }
    

}
