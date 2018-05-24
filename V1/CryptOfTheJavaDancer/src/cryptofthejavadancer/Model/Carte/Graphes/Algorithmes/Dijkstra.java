/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.Carte.Graphes.Algorithmes;

import cryptofthejavadancer.Model.Carte.Graphes.Graphe;
import cryptofthejavadancer.Model.Carte.Graphes.Vertex;
import cryptofthejavadancer.Model.Carte.Graphes.VertexCouple;
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
    private Integer infini;
    
    public Dijkstra (Graphe graph){
    this.graph = graph;
    this.distance = new HashMap<Vertex,Integer>();
    this.visited = new HashMap<Vertex,Boolean>();
    this.predecessor = new HashMap<Vertex,Vertex>();
    this.chemin = new ArrayList<Vertex>();
    this.infini = null;
    }
    
    public void initialisation(){
        int max = getInfini();
        for (Vertex v : graph.getVertices().values()){
            distance.put(v,max+1);
            visited.put(v,false);
            predecessor.put(v,null);
        }
        distance.put(debut,0);
    }
    
    public Vertex closestVertex(){
      int min = getInfini();
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
            
            
    public void calcul(Vertex _debug, Vertex _fin){
        this.debut = _debug;
        this.fin = _fin;
        this.initialisation();
        for (int i = 0; i< visited.size(); i++){
            Vertex a = closestVertex();
            visited.put(a,true);
            for (Vertex b : visited.keySet()){
                relaxing(a,b);
            }
        }
    }
            
    public void relaxing(Vertex a, Vertex b){
        if (distance.get(b)> (distance.get(a)+ this.graph.getLabels().get(new VertexCouple(a,b)))){
            predecessor.put(b, a);
        }
    }
    public int getInfini(){
        if(infini == null){
            for (Integer vLabel : graph.getLabels().values()){
                infini = 0;
                infini += vLabel;
            }
        }
        return infini;
    }
    
    
}


