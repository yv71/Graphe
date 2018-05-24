/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.Carte.Graphes;

import java.util.ArrayList;

/**
 *
 * @author Beelzed
 */
public class Vertex {
    private Graphe graph;
    private ArrayList<Vertex> voisins;
    
    public Vertex (Graphe graph){
        this.graph = graph;
        voisins = new ArrayList<Vertex>();
    }
    
    public ArrayList<Vertex> getNeighbours(){
        return this.voisins;
    }
    
    public void addNeighbour(Vertex v){
        voisins.add(v);
    }
    
    public String toString(){
        return this.graph.caseVertex(this);
    }

    
    
 
}
