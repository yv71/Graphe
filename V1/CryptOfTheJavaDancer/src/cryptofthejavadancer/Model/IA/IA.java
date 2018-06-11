package cryptofthejavadancer.Model.IA;

import cryptofthejavadancer.Model.Carte.Cases.Case;
import cryptofthejavadancer.Model.Carte.Graphes.Algorithmes.Dijkstra;
import cryptofthejavadancer.Model.Carte.Graphes.Graphe;
import cryptofthejavadancer.Model.Carte.Map;
import cryptofthejavadancer.Model.Entites.Entite;

/**
 * Classe générique des IA
 * @author Matthieu
 */
public abstract class IA {
    protected int turn;
    protected Entite entite;                                                      //Entité liée à l'IA
    protected Dijkstra algo;
//---------- CONSTRUCTEURS -----------------------------------------------------
    
    public IA(Entite _entite) {
        this.entite = _entite;
        this.turn = 0;
    }
//------------------------------------------------------------------------------

//---------- GETEUR/SETEUR -----------------------------------------------------
    public Graphe getGraph(){
        return this.algo.getGraph();
    }
    //Renvoie l'entité
    public Entite getEntite() {
        return this.entite;
    }
    
    //Renvoie la case de l'entité
    public Case getCase() {
        return this.entite.getCase();
    }
    
    //Renvoie la carte du jeu
    public Map getMap() {
        return this.entite.getMap();
    }
    
    //Méthode appelé à chaque pulsation de la musique et devant renvoyer l'action à réaliser
    public abstract Type_Action action();
//------------------------------------------------------------------------------

}
