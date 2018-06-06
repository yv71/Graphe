package cryptofthejavadancer.Model.Carte;

import cryptofthejavadancer.Model.Carte.Cases.Case;
import cryptofthejavadancer.Model.Carte.Cases.Type_Case;
import cryptofthejavadancer.Model.Carte.Graphes.Algorithmes.AStar;
import cryptofthejavadancer.Model.Carte.Graphes.Algorithmes.Dijkstra;
import cryptofthejavadancer.Model.Carte.Graphes.Graphe;
import cryptofthejavadancer.Model.Carte.Graphes.Vertex;
import cryptofthejavadancer.Model.Carte.Parseur.Fabrique_Cases;
import cryptofthejavadancer.Model.Carte.Parseur.Parseur;
import cryptofthejavadancer.Model.Entites.Entite;
import cryptofthejavadancer.Model.Entites.Entite_Cadence;
import cryptofthejavadancer.Model.Entites.Type_Entite;
import static cryptofthejavadancer.Model.Entites.Type_Entite.ChauveSouris;
import cryptofthejavadancer.Model.IA.IA_droite;
import cryptofthejavadancer.Model.Objet.Objet;
import cryptofthejavadancer.Model.Objet.Type_Objet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Math.*;

/**
 * Carte du jeu
 * @author Matthieu
 */
public class Map {

    private final HashMap<Coordonnees,Case> hashMapCases;                       //Stockage par coordonnées des cases
    private final ArrayList<Entite> listeEntite;                                //Liste des entités    
    private final ArrayList<Case> listeCase;                                    //Liste des cases
    private final ArrayList<Objet> listeObjet;   
    //Liste des objets
   // private final ArrayList<Case> CaseVertex;
    private Coordonnees depart;                                                 //Position du point de départ
    private Coordonnees fin;                                                    //Position de la sortie
 //   private Dijkstra algo;
 //   private AStar algo2;
    private Graphe graphe_simple;
    private Graphe graph_avance;
    private Entite_Cadence joueur;                                              //Cadence
    private Vertex debut;
    private Vertex fina;
    
//---------- CONSTRUCTEURS -----------------------------------------------------

    public Map() {
        //Initialisation
        this.hashMapCases = new HashMap<>();
        this.listeCase = new ArrayList<>();
        this.listeEntite = new ArrayList<>();
        this.listeObjet = new ArrayList<>();
        this.joueur = null;
        this.graphe_simple = new Graphe();
        this.graph_avance = new Graphe();
    //    this.algo = new Dijkstra(graphe_simple);
    //    this.algo2 = new AStar(graphe_simple);
       // this.CaseVertex = new ArrayList<>();
       
    }
    
    //créer la map à partir d'un fichier texte
    public void chargerFichier(String adresseFichier) throws IOException {
        Parseur parseur = new Parseur(adresseFichier,this);
        parseur.lecture();
        //this.getInfos();
        this.genererGrapheSimple();
        this.genererGrapheAvance();
        debut = graphe_simple.getVertex(this.getCase(depart.getLigne(), depart.getColonne()));
        fina = graphe_simple.getVertex(this.getCase(fin.getLigne(), fin.getColonne()));
       // this.algo.calcul(debut,fina);
        //this.algo2.calcul(debut, fina);
            //Il est possible de rajouter ICI des choses se réalisant juste après le chargement de la carte...S
    }
    
    public void genererGrapheSimple(){
        // generation des vertices
        for(Case c : listeCase) {
            this.graphe_simple.addVertex(c);
        }
        // generation des neighbours
        for (Case c  : listeCase){
            for (Case c2 : listeCase){
                    int c2Ligne = c2.getLigne();
                    int c2Col = c2.getColonne();
                    int cLigne = c.getLigne();
                    int cCol = c.getColonne();
                    int ligne = Math.abs(c2Ligne-cLigne);
                    int col = Math.abs(c2Col-cCol);
                    // ajout des labels
                    if ((ligne + col)==1){
                    switch(c2.getType()){
                        case Mur : this.graphe_simple.addEdge(c,c2);
                        this.graphe_simple.setLabel(c,c2,2);
                        break;
                        case Sol : this.graphe_simple.addEdge(c, c2);
                        this.graphe_simple.setLabel(c, c2, 1);
                        break;
                        default : ;
                    }
                    }
               
                        
                        
                    }
                }
               // this.graphe_simple.afficheMatriceAdjacence();             
           
        }
    
    
    public void genererGrapheAvance(){
        // generation des vertices
        for(Case c : listeCase) {
            this.graph_avance.addVertex(c);
        }
        // generation des neighbours
        for (Case c  : listeCase){
            for (Case c2 : listeCase){
                    int c2Ligne = c2.getLigne();
                    int c2Col = c2.getColonne();
                    int cLigne = c.getLigne();
                    int cCol = c.getColonne();
                    int ligne = Math.abs(c2Ligne-cLigne);
                    int col = Math.abs(c2Col-cCol);
                    // ajout des labels
                    if ((ligne + col)==1){
                    switch(c2.getType()){
                        case Mur : this.graph_avance.addEdge(c,c2);
                        this.graph_avance.setLabel(c,c2,2);
                        break;
                        case Sol : this.graph_avance.addEdge(c, c2);
                        this.graph_avance.setLabel(c, c2, 1);
                        break;
                        case MurDur : this.graph_avance.addEdge(c, c2);
                        this.graph_avance.setLabel(c, c2, 2);
                        break;
                        default : ;
                    }
                    }
               
                        
                        
                    }
                }
                //this.graphe_simple.afficheMatriceAdjacence();             
           
        }

//------------------------------------------------------------------------------

//---------- GETEUR/SETEUR -----------------------------------------------------

    public Vertex getDebut() {
        return debut;
    }

    public Vertex getFina() {
        return fina;
    }
  
    
    public Graphe getGrapheSimple(){
        return this.graphe_simple;
    }
    
    public Graphe getGraphAvance(){
        return this.graph_avance;
    }
    

    //Renvoie la case présente à ses coordonnées
    public Case getCase(int ligne,int colonne) {
        return this.hashMapCases.get(new Coordonnees(ligne,colonne));
    }
    
    //Change la case à ses coordonnées
    public void setCase(int ligne, int colonne, Case _case) {
        this.hashMapCases.put(new Coordonnees(ligne,colonne), _case);
        this.listeCase.add(_case);
    }
    
    //Change le type de case à ses coordonnées (lors du minage)
    public void changeTypeCase(Case caseInitiale, Type_Case typeNouvelleCase) {
        Case nouvelleCase = Fabrique_Cases.construireCase(typeNouvelleCase, caseInitiale.getLigne(), caseInitiale.getColonne(), this);
        this.setCase(caseInitiale.getLigne(), caseInitiale.getColonne(), nouvelleCase);
        this.listeCase.remove(caseInitiale);
    }
    
    //Ajoute une entité à la position donnée
    public void ajouteEntite(int ligne,int colonne, Entite entite) {
        this.getCase(ligne, colonne).setEntite(entite);
        entite.setCase(this.getCase(ligne, colonne));
        if(entite.getType() != Type_Entite.Cadence) {
            this.listeEntite.add(entite);
        }
    }
    
    //Fixe le joueur
    public void setJoueur(Entite_Cadence cadence) {
        this.joueur = cadence;
    }
    
    //Ajoute un objet à la position donnée
    public void ajouteObjet(int ligne,int colonne, Objet objet) {
        this.getCase(ligne, colonne).setObjet(objet);
        objet.setCase(this.getCase(ligne, colonne));
        this.listeObjet.add(objet);
    }
    
    //Renvoie la hashmap des cases
    public HashMap<Coordonnees,Case> getHashMapCases() {
        return this.hashMapCases;
    }
    
    //Fixe le point de départ
    public void setDepart(int numLigne,int numColonne) {
        this.depart = new Coordonnees(numLigne,numColonne);
    }
    
    //Fixe la position de la sortie
    public void setSortie(int numLigne,int numColonne) {
        this.fin = new Coordonnees(numLigne,numColonne);
    }
    
    //Renvoie la position du point de départ
    public Coordonnees getDepart() {
        return this.depart;
    }
    
    public Objet getPelle(){
        Objet pelle = null;
        for(Objet o : this.listeObjet){
            if (o.getType() == Type_Objet.Pelle){
                pelle = o;
            }
        }
        return pelle;
    }
    
    //Renvoie la position du point de sortie
    public Coordonnees getSortie() {
        return this.fin;
    }
    
    //Renvoie la liste des entités
    public ArrayList<Entite> getListeEntite() {
        return this.listeEntite;
    }
    
    //Renvoie la liste des cases
    public ArrayList<Case> getListeCase() {
        return this.listeCase;
    }
    
    //Renvoie la liste des objets
    public ArrayList<Objet> getListeObjet() {
        return this.listeObjet;
    }
    
    //Renvoie le joueur
    public Entite_Cadence getJoueur() {
        return this.joueur;
    }
    
//------------------------------------------------------------------------------
 public void getInfos(){
       System.out.println("Niveau chargé.");
        System.out.println("Nb squares : " + listeCase.size());
        System.out.println("Nb items : " + listeObjet.size());
        System.out.println("Nb entities : "+ listeEntite.size());
        int nbPelle=0;
        int nbSortie = 0;
        int nbDiamonds=0;        
        for (int i = 0; i < listeObjet.size(); i++){
            switch(listeObjet.get(i).getType()){
                case Diamant:
                    nbDiamonds ++;
                    System.out.println("Diamant");
                    break;
                case Pelle:
                    nbPelle ++;
                    System.out.println("Pelle");
                    break;
                case Sortie:
                    nbSortie ++;
                    System.out.println("Sortie");
                    break;
            }
        }
        
        int nbChauve=0;
        int SlimeB=0;
        int SlimeJ=0;
        int SlimeV=0;
        int Skel=0;
        for (int i =0; i< listeEntite.size();i++){
            switch (listeEntite.get(i).getType()){
                case ChauveSouris:
                nbChauve ++;
                    System.out.println(listeEntite.get(i).getType());
                break;
                case SlimeBleu:
                SlimeB ++;
                System.out.println(listeEntite.get(i).getType());
                break;
                case SlimeJaune:
                SlimeJ ++;
                System.out.println(listeEntite.get(i).getType());
                break;
                case SlimeVert:
                SlimeV ++;
                System.out.println(listeEntite.get(i).getType());
                break;
                case Squelette:
                Skel ++;
                System.out.println(listeEntite.get(i).getType());
                break;
                
            }
    }
        System.out.println("Chauve Souris : "+ nbChauve + "\nSlime Bleu : " + SlimeB + "\nSlime Jaune : "+ SlimeJ + "\nSlimeVert : "+ SlimeV + "\nSquelette : " + Skel);
        System.out.println("Nb diamonds : " + nbDiamonds);
        System.out.println("Coordonnées de la sortie : \nLigne : " + this.getSortie().getLigne()+ " Colonne : " + this.getSortie().getColonne());
        System.out.println("Coordonnées de Cadence : \nLigne : " + joueur.getCase().getLigne() + " Colonne : "+ joueur.getCase().getColonne());
        System.out.println("Type de la case à gauche de Cadence : "+ this.getCase(joueur.getCase().getLigne(), joueur.getCase().getColonne()-1).getType());
   }
}
