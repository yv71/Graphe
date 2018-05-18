/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.Carte.Graphes.Algorithmes;

import cryptofthejavadancer.Model.Carte.Graphes.Graphe;
import cryptofthejavadancer.Model.Carte.Graphes.Vertex;
import java.util.ArrayList;
import java.util.HashMap;
    
/**
 *
 * @author yv066840
 */
public class Dijkstra {
    private Graphe graph;
    private Vertex debut;
    private Vertex fin;
    private HashMap<Vertex,Integer> distance;
    private HashMap<Vertex,Boolean> visited;
    private HashMap<Vertex,Vertex> predecessor;
    private ArrayList<Vertex> chemin;
    
    public Dijkstra (Graphe graph){
    this.graph = graph;
    this.distance = new HashMap<Vertex,Integer>();
    this.visited = new HashMap<Vertex,Boolean>();
    this.predecessor = new HashMap<Vertex,Vertex>();
    this.chemin = new ArrayList<Vertex>();
    }
    
    public void initialisation(){
        int max = maxWeight();
        for (Vertex v : graph.getVertices().values()){
            distance.put(v,max+1);
            visited.put(v,false);
            predecessor.put(v,null);
        }
        distance.put(debut,0);
    }
    
    public Vertex closestVertex(){
      int min = maxWeight();
      Vertex plusProche = null;
        for (Vertex v : distance.keySet()){
            if (visited.get(v)==false){
                if (distance.get(v)<min){
                    plusProche = v;
                    min = distance.get(v);
                }
            }
        }
        return plusProche;
    }
            
            
            
            
    public int maxWeight(){
        int a = 0;
        for (Integer i : graph.getLabels().values()){
            a += i;
        }
        return a;
    }
}

}
