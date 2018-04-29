/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.Carte.Graphes;

import java.util.HashMap;
import cryptofthejavadancer.Model.Carte.Graphes.Vertex;
/**
 *
 * @author yv066840*_* 
 */
public class Graphe {
    private HashMap<String,Vertex> Vertices;
    private HashMap<VertexCouple,String> Labels;
    
    
    public Graphe() {
    Vertices = new HashMap<>();
    Labels = new HashMap<>();
}
    
    public void addVertex(String name){

    }
}
