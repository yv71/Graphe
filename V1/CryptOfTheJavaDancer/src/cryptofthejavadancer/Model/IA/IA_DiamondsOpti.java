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
import cryptofthejavadancer.Model.Carte.Graphes.Vertex;
import cryptofthejavadancer.Model.Carte.Graphes.VertexCouple;
import cryptofthejavadancer.Model.Entites.Entite;
import cryptofthejavadancer.Model.Entites.Type_Entite;
import cryptofthejavadancer.Model.Objet.Objet;
import cryptofthejavadancer.Model.Objet.Type_Objet;
import java.util.ArrayList;

/**
 *
 * @author Beelzed
 */
public class IA_DiamondsOpti extends IA{

    private ArrayList<Objet> diamonds;
    private Boolean firstTurn;
    private ArrayList<Vertex> localPath;
    
    
    public IA_DiamondsOpti(Entite _entite) {
        super(_entite);
        algo = null;
        diamonds = new ArrayList<>();
        firstTurn = true;
        localPath = new ArrayList<>();
        
    }

    public Type_Action action() {
        Type_Action retour = Type_Action.attendre;
        if (firstTurn){
            this.algo = new Dijkstra(this.getMap().getGrapheSimple());
            this.algo.calcul(this.getMap().getDebut(), this.getMap().getFina());
            for (Objet o : this.entite.getMap().getListeObjet()){
                if (o.getType() == Type_Objet.Diamant){
                    this.diamonds.add(o);
                }
            }
            if (this.nearestDiamond()!=null){
               localPath = algo.getPath(getMap().getDebut(),getMap().getGrapheSimple().getVertex(nearestDiamond().getCase()));
               diamonds.remove(nearestDiamond());
           }
           else {
               algo.calcul(getMap().getDebut(),getMap().getFina());
               localPath = this.algo.getPath();
               
           }
           firstTurn = false;
        }
        if (this.entite.getCase().getObjet()!=null){
            if (this.entite.getCase().getObjet().getType() == Type_Objet.Diamant || this.entite.getCase().getObjet().getType()== Type_Objet.Pelle){
                retour = Type_Action.ramasser;
                if (this.nearestDiamond()!=null){
                    localPath = algo.getPath(getMap().getDebut(),getMap().getGrapheSimple().getVertex(nearestDiamond().getCase()));
                    diamonds.remove(nearestDiamond());
                }                
                else {
                    algo.calcul(this.getMap().getGrapheSimple().getVertex(getMap().getJoueur().getCase()), getMap().getFina());
                    localPath = this.algo.getPath();
                }
            }
            else if(this.entite.getCase().getObjet().getType() == Type_Objet.Sortie){
                retour = Type_Action.sortir;
            }
       }
       else {
           retour = noeudToAction(localPath.get(0).getCase());
       }
        return retour;
    } 
    
    public Objet nearestDiamond(){
        Objet diam = null;
        int minDistance = this.algo.getInfini();

        if (!this.diamonds.isEmpty()){             
             for (Objet o : this.diamonds){
                 int dist = this.getPath(this.getGraph().getVertex(this.getMap().getJoueur().getCase()), this.getGraph().getVertex(o.getCase())).size();
                 if (dist < minDistance){
                     diam = o;
                     minDistance = dist;
                 }
             }
        }
       return diam;
    }
    
    public ArrayList<Vertex> getPath(Vertex start, Vertex end){
        return this.algo.getPath(start, end);
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
                            VertexCouple vC = new VertexCouple(v,this.getGraph().getVertex(CaseSuivante));
                            this.getGraph().getLabels().replace(vC,2,1);
                        }
                    }
                Case test = new Case_Sol(CaseSuivante.getLigne(),CaseSuivante.getColonne(),getMap());
                Vertex v = algo.getGraph().getVertex(CaseSuivante);
                v.setCase(test);
                algo.getGraph().getVertices().remove(CaseSuivante,v);
                algo.getGraph().getVertices().put(test,v);
                
             
            
        }
        return retour;
        
    }
    
}
