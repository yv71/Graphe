package cryptofthejavadancer.Model.Carte;

import cryptofthejavadancer.Model.Carte.Cases.Case;
import cryptofthejavadancer.Model.Carte.Cases.Type_Case;
import cryptofthejavadancer.Model.Carte.Graphes.Graphe;
import cryptofthejavadancer.Model.Carte.Parseur.Fabrique_Cases;
import cryptofthejavadancer.Model.Carte.Parseur.Parseur;
import cryptofthejavadancer.Model.Entites.Entite;
import cryptofthejavadancer.Model.Entites.Entite_Cadence;
import cryptofthejavadancer.Model.Entites.Type_Entite;
import cryptofthejavadancer.Model.IA.IA_droite;
import cryptofthejavadancer.Model.Objet.Objet;
import cryptofthejavadancer.Model.Objet.Type_Objet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Carte du jeu
 * @author Matthieu
 */
public class Map {

    private final HashMap<Coordonnees,Case> hashMapCases;                       //Stockage par coordonnées des cases
    private final ArrayList<Entite> listeEntite;                                //Liste des entités    
    private final ArrayList<Case> listeCase;                                    //Liste des cases
    private final ArrayList<Objet> listeObjet;                                  //Liste des objets
    private Coordonnees depart;                                                 //Position du point de départ
    private Coordonnees fin;                                                    //Position de la sortie
    
    private Graphe graphe_simple;
    private Entite_Cadence joueur;                                              //Cadence
    
//---------- CONSTRUCTEURS -----------------------------------------------------

    public Map() {
        //Initialisation
        this.hashMapCases = new HashMap<>();
        this.listeCase = new ArrayList<>();
        this.listeEntite = new ArrayList<>();
        this.listeObjet = new ArrayList<>();
        this.joueur = null;
        this.graphe_simple = null;
    }
    
    //créer la map à partir d'un fichier texte
    public void chargerFichier(String adresseFichier) throws IOException {
        Parseur parseur = new Parseur(adresseFichier,this);
        parseur.lecture();
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
        this.genererGrapheSimple();
            //Il est possible de rajouter ICI des choses se réalisant juste après le chargement de la carte...


    }
    
    private void genererGrapheSimple(){
        
    }
//------------------------------------------------------------------------------

//---------- GETEUR/SETEUR -----------------------------------------------------
    public Graphe getGrapheSimple(){
        return this.graphe_simple;
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

}