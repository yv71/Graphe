/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.Carte.Graphes;

import cryptofthejavadancer.Model.Carte.Cases.Case;
import java.util.HashMap;
import cryptofthejavadancer.Model.Carte.Graphes.Vertex;
import java.util.ArrayList;
/**
 *
 * @author yv066840*_* 
 */
public class Graphe {
    private HashMap<Case,Vertex> Vertices;
    private HashMap<VertexCouple,Integer> Labels;
    
    
    public Graphe() {
    Vertices = new HashMap<>();
    Labels = new HashMap<>();
}
    public HashMap<Case,Vertex> getHashMap(){
      return  this.Vertices;
}
    public void addVertex(Case _case){
           Vertices.put(_case, new Vertex(this));
    }
    
    public void addEdge(Case _case1, Case _case2){
        
    }
    
    public Vertex getVertex(Case _case){
       return  Vertices.get(_case);
    }
    
    public int getLabel(Case _case1, Case _case2){
        return Labels.get(new VertexCouple(Vertices.get(_case1),Vertices.get(_case2)));
    }
    
    public void setLabel(Case _case1, Case _case2, Integer label){
        Labels.put(new VertexCouple(Vertices.get(_case1),Vertices.get(_case2)), label);
    }
}
