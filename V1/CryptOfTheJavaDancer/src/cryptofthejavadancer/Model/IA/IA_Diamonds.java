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
import cryptofthejavadancer.Model.Objet.Objet_Diamant;
import cryptofthejavadancer.Model.Objet.Type_Objet;
import java.util.ArrayList;

/**
 *
 * @author Beelzed
 */
public class IA_Diamonds extends IA{
    private boolean tour;
    private boolean firstTurn;
    private boolean firstTurnAdv;
    private ArrayList<Objet> diamonds;
    
            
    public IA_Diamonds(Entite _entite) {
        super(_entite);
        this.algo = null;
        this.tour = false;
        this.firstTurn = true;
        this.firstTurnAdv = true;
        this.turn = 0;
        this.diamonds= new ArrayList<Objet>();
       
    }
    
    
    @Override
    public Type_Action action() {
       Type_Action retour = Type_Action.attendre;
       if (this.getMap().getJoueur().getPelle()){
           retour = this.actionAdv();
       }
       else {
           retour = this.actionSimple();
       }
       return retour;
    }
    
    public int pathToShovel(){
        int dist;
        Case cJoueur = this.getMap().getJoueur().getCase();
        Vertex vJoueur = this.algo.getGraph().getVertex(cJoueur);
        Case cShovel = this.getMap().getPelle().getCase();
        Vertex vShovel = this.algo.getGraph().getVertex(cShovel);
        dist = this.algo.pathLength(vJoueur, vShovel);
        return dist;
    }
    
    public int pathTo(Objet start,Objet end){
        int dist;
        Dijkstra test = new Dijkstra(getMap().getGraphAvance());
        Case cItem = start.getCase();
        Vertex vItem = test.getGraph().getVertex(cItem);
        Case cEnd = end.getCase();
        Vertex vEnd = test.getGraph().getVertex(cEnd);
        dist = test.pathLength(vItem, vEnd);
        return dist;
    }
    
    public Objet nearestDiamond(){
        Objet diam = diamantLePlusProche();
        if (diam != null){
        Vertex depart = this.getMap().getGrapheSimple().getVertex(this.getMap().getJoueur().getCase()); 
        Vertex diamV = this.getMap().getGrapheSimple().getVertex(diam.getCase());
        int minDistance = this.algo.pathLength((Vertex)depart,(Vertex)diamV);         
        if (!this.getMap().getJoueur().getPelle()&& this.getMap().getPelle()!=null){
                    int distToShovel = pathToShovel();
                    int shovelToDiamond = pathTo(getMap().getPelle(),diam);
                    int distToDiamond = distToShovel + shovelToDiamond;
                    if (minDistance > distToDiamond){
                        diam = (Objet) getMap().getPelle();
                    }
            } 
        }
        return diam;
    }
 
    public Objet diamantLePlusProche(){
        Objet diam = null;
        int minDistance = this.algo.getInfini();
        Vertex depart = this.getMap().getGrapheSimple().getVertex(this.getMap().getJoueur().getCase());       
        int dist;
        for (Objet d : this.diamonds){
            Vertex diamV = this.getMap().getGrapheSimple().getVertex(d.getCase());
            dist = this.algo.pathLength((Vertex)depart,(Vertex)diamV); 
            if (dist < minDistance && dist!=0){
                   diam = d;
                    minDistance = dist;
                }
            }
        return diam;
    }

    
    public Type_Action actionSimple(){
        
       Type_Action retour = Type_Action.attendre;
       if (this.firstTurn ){
           this.algo = new Dijkstra(this.getMap().getGrapheSimple());           
           this.firstTurn = false;           
           if (this.nearestDiamond()!=null){
               algo.calcul(getMap().getDebut(),getMap().getGrapheSimple().getVertex(nearestDiamond().getCase()));
               diamonds.remove(nearestDiamond());
           }
           else {
               algo.calcul(getMap().getDebut(),getMap().getFina());
           }
       }
       System.out.println(algo.getPath().size());
       if (this.entite.getCase().getObjet()!=null){
            if (this.entite.getCase().getObjet().getType() == Type_Objet.Diamant || this.entite.getCase().getObjet().getType()== Type_Objet.Pelle){
                retour = Type_Action.ramasser;
                if (this.nearestDiamond()!=null){
                    algo.calcul(this.getMap().getGrapheSimple().getVertex(getMap().getJoueur().getCase()),getMap().getGrapheSimple().getVertex(nearestDiamond().getCase()));
                    diamonds.remove(nearestDiamond());
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
    
    public Type_Action actionAdv(){
        Type_Action retour = Type_Action.attendre;
       if (this.firstTurnAdv ){
           this.algo = new Dijkstra(this.getMap().getGraphAvance());           
           this.firstTurnAdv = false;
           if (this.nearestDiamond()!=null){
               algo.calcul(getMap().getDebut(),getMap().getGraphAvance().getVertex(nearestDiamond().getCase()));
           }
           else {
               algo.calcul(getMap().getDebut(),getMap().getFina());
           }
       }
       if (this.entite.getCase().getObjet()!=null){
            if (this.entite.getCase().getObjet().getType() == Type_Objet.Diamant){
                retour = Type_Action.ramasser;
                if (this.nearestDiamond()!=null){
                    algo.calcul(this.getMap().getGraphAvance().getVertex(getMap().getJoueur().getCase()),getMap().getGraphAvance().getVertex(nearestDiamond().getCase()));
                 }
                else {
                    algo.calcul(this.getMap().getGraphAvance().getVertex(getMap().getJoueur().getCase()), getMap().getFina());
                }
            }
            else if(this.entite.getCase().getObjet().getType() == Type_Objet.Sortie){
                retour = Type_Action.sortir;
            }
       }
       else {
           if (algo.getPath().size()>0){
               retour = noeudToActionAdv(this.algo.getPath().get(0).getCase());
           }
           
       }
       return retour;
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
    public Type_Action noeudToActionAdv(Case CaseSuivante){
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
        return retour;
    }
}




