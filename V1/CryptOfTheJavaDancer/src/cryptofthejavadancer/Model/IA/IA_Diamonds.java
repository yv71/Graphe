/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA;

import cryptofthejavadancer.Model.Carte.Cases.Case;
import cryptofthejavadancer.Model.Carte.Cases.Type_Case;
import cryptofthejavadancer.Model.Carte.Graphes.Algorithmes.Dijkstra;
import cryptofthejavadancer.Model.Carte.Graphes.Vertex;
import cryptofthejavadancer.Model.Entites.Entite;
import cryptofthejavadancer.Model.Entites.Type_Entite;
import cryptofthejavadancer.Model.Objet.Objet;
import cryptofthejavadancer.Model.Objet.Objet_Diamant;
import cryptofthejavadancer.Model.Objet.Type_Objet;
import java.util.ArrayList;

/**
 *
 * @author Beelzed
 */
public class IA_Diamonds extends IA{
    private Dijkstra algo;
    private boolean tour;
    private boolean firstTurn;
            
    public IA_Diamonds(Entite _entite) {
        super(_entite);
        this.algo = null;
        this.tour = false;
        this.firstTurn = true;
        this.turn = 0;
    }
    

    @Override
    public Type_Action action() {
       Type_Action retour = Type_Action.attendre;
       if (this.firstTurn){
           this.algo = new Dijkstra(this.getMap().getGrapheSimple());           
           this.firstTurn = false;
           if (this.nearestDiamond()!=null){
               algo.calcul(getMap().getDebut(),getMap().getGrapheSimple().getVertex(nearestDiamond().getCase()));
           }
           else {
               
           }
       }
       if (this.entite.getCase().getObjet()!=null){
            if (this.entite.getCase().getObjet().getType() == Type_Objet.Diamant){
                retour = Type_Action.ramasser;
                if (this.nearestDiamond()!=null){
                    algo.calcul(this.getMap().getGrapheSimple().getVertex(getMap().getJoueur().getCase()),getMap().getGrapheSimple().getVertex(nearestDiamond().getCase()));
                 }
                else {
                    algo.calcul(this.getMap().getGrapheSimple().getVertex(getMap().getJoueur().getCase()), getMap().getFina());
                }
            }
            else if(this.entite.getCase().getObjet().getType() == Type_Objet.Sortie){
                retour = Type_Action.sortir;
            }
       }
       else {
           retour = noeudToAction(this.algo.getPath().get(0).getCase());
       }
       return retour;
    }
    
    public Objet nearestDiamond(){
        Objet diam = null;
        int minDistance = 999999999;
        Vertex depart = this.getMap().getGrapheSimple().getVertex(this.getMap().getJoueur().getCase());
        ArrayList<Objet> lDiamant = new ArrayList<>();
        for (Objet o : this.getMap().getListeObjet()){
            if (o.getType() == Type_Objet.Diamant){
                lDiamant.add(o);
            }
        };
        int dist;
        for (Objet d : lDiamant){
            Vertex diamV = this.getMap().getGrapheSimple().getVertex(d.getCase());
            dist = this.algo.pathLength((Vertex)depart,(Vertex)diamV);
            if (dist < minDistance && dist!=0){
                System.out.println();
                diam = d;
                minDistance = dist;
            }
        }
        
 
        return diam;
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
            if (tour == false){
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
                tour = true;
            }
            else {
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
                tour = false;
            }
            
        }
        System.out.println(retour);
        System.out.println(this.algo.getPath());
        return retour;
        
    }
}


