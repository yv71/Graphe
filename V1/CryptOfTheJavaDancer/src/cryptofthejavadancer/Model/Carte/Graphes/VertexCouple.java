/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.Carte.Graphes;

/**
 *
 * @author Beelzed
 */
class VertexCouple {
    private Vertex start;
    private Vertex end;
    
    public VertexCouple (Vertex _start, Vertex _end){
        this.start = _start;
        this.end = _end;
    }
}
