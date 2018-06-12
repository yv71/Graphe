/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.Carte.Graphes.Algorithmes;

import cryptofthejavadancer.Model.Carte.Cases.Case;
import cryptofthejavadancer.Model.Carte.Graphes.Graphe;
import cryptofthejavadancer.Model.Carte.Graphes.Vertex;
import cryptofthejavadancer.Model.Carte.Graphes.VertexCouple;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author yv066840
 */
public class AStar {
    private Graphe graph;
    private Vertex debut;
    private Vertex fin;
    private HashMap<Vertex,Integer> distance;
    private HashMap<Vertex,Boolean> visited;
    private HashMap<Vertex,Vertex> predecessor;
    private ArrayList<Vertex> path;
    private Integer infini;
    
    public AStar (Graphe graph){
    this.graph = graph;
    this.distance = new HashMap<Vertex,Integer>();
    this.visited = new HashMap<Vertex,Boolean>();
    this.predecessor = new HashMap<Vertex,Vertex>();
    this.path = new ArrayList<Vertex>();
    this.infini = null;
    }
 
  /**  public void initialisation(){
        int max = getInfini();
        for (Vertex v : graph.getVertices().values()){
            distance.put(v,max);
            visited.put(v,false);
            predecessor.put(v,null);
        }
        distance.put(debut,0);
    }
    
    public Vertex closestVertex(){
      int min = getInfini()+1;
      Vertex plusProche = null;
        for (Vertex v : distance.keySet()){
            if (visited.get(v)==false){
                if (heuristic(v)<min){
                    plusProche = v;
                    min = heuristic(v);
                }
            }
        }
        return plusProche;
    }
            
            
    public void calcul(Vertex _debug, Vertex _fin){
        debut = _debug;
        fin = _fin;
        this.initialisation();
        
        for (int i = 0; i< visited.size(); i++){
            Vertex a = closestVertex();
            visited.put(a,true);
            for (Vertex b : visited.keySet()){
                relaxing(a,b);
            }
        }
        Vertex v = fin;
        while (v !=null){
            path.add(0,v);
            v = predecessor.get(v);
        }
        System.out.println(path);
        
        
    }
            
    public void relaxing(Vertex a, Vertex b){
        if(this.graph.getLabels().get(new VertexCouple(a,b)) != null) {
            if (distance.get(b)> (distance.get(a)+ this.graph.getLabels().get(new VertexCouple(a,b)))){
                distance.put(b, (distance.get(a)+ this.graph.getLabels().get(new VertexCouple(a,b))));
                predecessor.put(b, a);
            }
        }
    }
    public int getInfini(){
        if(infini == null){
            infini = 0;
            for (Integer vLabel : graph.getLabels().values()){
                infini += vLabel;
            }
            infini++;
        }
        return infini;
    }
    
    public int heuristic(Vertex v){
        return distance.get(v) + euclidian_distance(v,fin);
    }
    
    public int euclidian_distance(Vertex v, Vertex end){
        Case cv = this.graph.getCoord(v);
        Case cend = this.graph.getCoord(end);
        int x = (int)(Math.pow((cend.getLigne()-cv.getLigne()),2));
        int y = (int)(Math.pow((cend.getColonne()-cv.getColonne()),2));
        int max = (int)(Math.sqrt(x+y));
        return max;
    }
    * */
}
