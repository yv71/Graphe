/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA;

import cryptofthejavadancer.Model.Carte.Cases.Case;
import cryptofthejavadancer.Model.Carte.Cases.Case_Sol;
import cryptofthejavadancer.Model.Carte.Cases.Type_Case;
import cryptofthejavadancer.Model.Carte.Graphes.Algorithmes.Dijkstra;
import cryptofthejavadancer.Model.Carte.Graphes.Vertex;
import cryptofthejavadancer.Model.Carte.Graphes.VertexCouple;
import cryptofthejavadancer.Model.Carte.Map;
import cryptofthejavadancer.Model.Entites.Entite;
import cryptofthejavadancer.Model.Entites.Type_Entite;
import cryptofthejavadancer.Model.Objet.Objet;
import cryptofthejavadancer.Model.Objet.Type_Objet;
import java.util.ArrayList;

/**
 *
 * @author Beelzed
 */
public class IA_DiamondsOpti_Pelle extends IA {

    private ArrayList<Objet> diamonds;
    private Boolean firstTurn;
    private ArrayList<Vertex> localPath;
    private Dijkstra dij;

    public IA_DiamondsOpti_Pelle(Entite _entite) {
        super(_entite);
        algo = null;
        diamonds = new ArrayList<>();
        firstTurn = true;
        localPath = new ArrayList<>();
        dij = null;
    }

    /**
     * public Type_Action action() { Type_Action action = Type_Action.attendre;
     * Case dest = this.getCase(); if (firstTurn) { Map map =
     * this.getEntite().getMap(); //Génération de la liste des diamants for
     * (Objet o : map.getListeObjet()) { if (o.getType() == Type_Objet.Diamant)
     * { diamonds.add(o); } } //Génération de l'algo algo = new
     * Dijkstra(map.getGrapheSimple()); dij = new
     * Dijkstra(map.getGrapheSimple());
     * algo.calcul(this.getGraph().getVertex(this.getCase()),
     * this.getGraph().getVertex(map.getCaseFin())); firstTurn = false; } if
     * (dij.getPath().isEmpty()) { //Interaction if (this.getCase().getObjet()
     * != null) { if (this.getCase().getObjet().getType() == Type_Objet.Diamant)
     * { action = Type_Action.ramasser;
     * diamonds.remove(this.getCase().getObjet()); } if
     * (this.getCase().getObjet().getType() == Type_Objet.Sortie) { action =
     * Type_Action.sortir; } } //Si il y a un diamant accessible on récupère le
     * plus proche dest = this.calculDest(); } else { action =
     * noeudToAction(dij.getPath().get(0).getCase()); } return action; }*
     */
    public Type_Action actionAmed() {
        //System.out.println(localPath);
        Type_Action retour = Type_Action.attendre;
        if (firstTurn) {
            this.algo = new Dijkstra(this.getMap().getGrapheSimple());
            this.dij = new Dijkstra(this.getMap().getGraphAvance());
            this.algo.calcul(this.getMap().getDebut(), this.getMap().getFina());
            for (Objet o : this.entite.getMap().getListeObjet()) {
                if (o.getType() == Type_Objet.Diamant) {
                    this.diamonds.add(o);
                }
            }
            if (this.nearestDiamond() != null) {
                localPath = algo.getPath(getMap().getGrapheSimple().getVertex(nearestDiamond().getCase()));
                diamonds.remove(nearestDiamond());
            } else {
                algo.calcul(getMap().getDebut(), getMap().getFina());
                localPath = this.algo.getPath();
            }
            firstTurn = false;
        }
        if (!this.getMap().getJoueur().getPelle()) {
            if (this.localPath.isEmpty()) {
                if (this.entite.getCase().getObjet() != null) {
                    this.algo.calcul(this.getGraph().getVertex(this.getEntite().getCase()), this.getMap().getFina());
                    if (this.entite.getCase().getObjet().getType() == Type_Objet.Diamant || this.entite.getCase().getObjet().getType() == Type_Objet.Pelle) {
                        retour = Type_Action.ramasser;
                        if (this.nearestDiamond() != null) {
                            System.out.println("diamantleplusproche : " + this.nearestDiamond());
                            if (this.nearestDiamond().getType() == Type_Objet.Diamant) {
                                localPath = algo.getPath(getMap().getGrapheSimple().getVertex(nearestDiamond().getCase()));
                                diamonds.remove(nearestDiamond());
                            }
                            if (this.nearestDiamond().getType() == Type_Objet.Pelle) {
                                dij.calcul(this.getGraph().getVertex(this.getEntite().getCase()), this.getMap().getFina());
                                this.getMap().removePelle(this.nearestDiamond());
                                localPath = dij.getPath(getMap().getGrapheSimple().getVertex(getMap().getPelle().getCase()));
                            }

                        } else {
                            algo.calcul(this.getMap().getGrapheSimple().getVertex(getMap().getJoueur().getCase()), getMap().getFina());
                            localPath = this.algo.getPath();
                        }
                    } else if (this.entite.getCase().getObjet().getType() == Type_Objet.Sortie) {
                        retour = Type_Action.sortir;
                    }
                }

            } else {
                retour = noeudToAction(localPath.get(0).getCase());
            }
            // System.out.println(this.getGraph().getVertex(this.getEntite().getCase()));

            //System.out.println(retour);
        } else {
            retour = this.actionAdv();
            if (this.localPath.isEmpty()) {
                if (this.entite.getCase().getObjet() != null) {
                    this.dij.calcul(this.getGraph().getVertex(this.getEntite().getCase()), this.getMap().getFina());
                    if (this.entite.getCase().getObjet().getType() == Type_Objet.Diamant || this.entite.getCase().getObjet().getType() == Type_Objet.Pelle) {
                        retour = Type_Action.ramasser;
                        System.out.println("diamantleplusproche : " + this.nearestDiamond());
                        if (this.nearestDiamond() != null) {
                            if (this.nearestDiamond().getType() == Type_Objet.Diamant) {
                                localPath = dij.getPath(getMap().getGrapheSimple().getVertex(nearestDiamond().getCase()));
                                diamonds.remove(nearestDiamond());
                            }
                            if (this.nearestDiamond().getType() == Type_Objet.Pelle) {
                                localPath = dij.getPath(getMap().getGrapheSimple().getVertex(nearestDiamond().getCase()));
  
                            }

                        } else {
                            dij.calcul(this.getMap().getGrapheSimple().getVertex(getMap().getJoueur().getCase()), getMap().getFina());
                            localPath = this.algo.getPath();
                        }
                    } else if (this.entite.getCase().getObjet().getType() == Type_Objet.Sortie) {
                        retour = Type_Action.sortir;
                    }
                }

            } else {
                retour = noeudToAction(localPath.get(0).getCase());
            }
        }

        return retour;
    }

    public Type_Action actionAdv() {
        Type_Action test = Type_Action.attendre;

        return test;
    }

    public Objet nearestDiamond() {
        Objet diam = null;
        int minDistance = this.algo.getInfini() + 1;
        if (!this.getMap().getJoueur().getPelle() && this.getMap().getPelle() != null) {
            for (Objet o : this.diamonds) {
                int SimpleDist = this.algo.getTaillePath(this.getGraph().getVertex(o.getCase()));
                int DistToPelle = this.algo.getTaillePath(this.getGraph().getVertex(this.getMap().getPelle().getCase()));
                dij.calcul(this.getGraph().getVertex(this.getMap().getPelle().getCase()), this.getGraph().getVertex(o.getCase()));
                DistToPelle += dij.getPath().size();
                if (DistToPelle <= SimpleDist) {
                    if (DistToPelle < minDistance) {
                        diam = this.getMap().getPelle();
                        minDistance = DistToPelle;

                    }
                } else {
                    if (SimpleDist < minDistance) {
                        diam = o;
                        minDistance = SimpleDist;
                    }
                }
            }
        } else {
            for (Objet o : this.diamonds) {
                int dist = this.algo.getTaillePath(this.getGraph().getVertex(o.getCase()));
                if (dist < minDistance) {
                    diam = o;
                    minDistance = dist;
                }
            }

        }
        return diam;
    }

    public ArrayList<Vertex> getPath(Vertex end) {
        return this.algo.getPath(end);
    }

    public Type_Action noeudToAction(Case CaseSuivante) {
        Case cC = this.entite.getCase();
        Type_Action retour = Type_Action.attendre;
        int X = cC.getLigne();
        int Y = cC.getColonne();
        if (CaseSuivante.getType() == Type_Case.Sol) {
            if (CaseSuivante.getEntite() == null) {
                if (CaseSuivante.getLigne() == (X - 1)) {
                    retour = Type_Action.deplacement_haut;
                } else if (CaseSuivante.getLigne() == (X + 1)) {
                    retour = Type_Action.deplacement_bas;
                } else if (CaseSuivante.getColonne() == (Y - 1)) {
                    retour = Type_Action.deplacement_gauche;
                } else if (CaseSuivante.getColonne() == (Y + 1)) {
                    retour = Type_Action.deplacement_droite;
                }

                //this.algo.destroyFirst();
                this.localPath.remove(0);
            } else if (CaseSuivante.getEntite().getType() != Type_Entite.Cadence) {
                if (CaseSuivante.getLigne() == (X - 1)) {
                    retour = Type_Action.interagir_haut;
                } else if (CaseSuivante.getLigne() == (X + 1)) {
                    retour = Type_Action.interagir_bas;
                } else if (CaseSuivante.getColonne() == (Y - 1)) {
                    retour = Type_Action.interagir_gauche;
                } else if (CaseSuivante.getColonne() == (Y + 1)) {
                    retour = Type_Action.interagir_droite;
                }
            }
        } else {

            if (CaseSuivante.getLigne() == (X - 1)) {
                retour = Type_Action.interagir_haut;
            } else if (CaseSuivante.getLigne() == (X + 1)) {
                retour = Type_Action.interagir_bas;
            } else if (CaseSuivante.getColonne() == (Y - 1)) {
                retour = Type_Action.interagir_gauche;
            } else if (CaseSuivante.getColonne() == (Y + 1)) {
                retour = Type_Action.interagir_droite;
            }
            for (Vertex v : this.algo.getGraph().getVertices().values()) {
                if (v.getNeighbours().contains(this.getGraph().getVertex(CaseSuivante))) {
                    VertexCouple vC = new VertexCouple(v, this.getGraph().getVertex(CaseSuivante));
                    this.getGraph().getLabels().replace(vC, 2, 1);
                }
            }
            Case test = new Case_Sol(CaseSuivante.getLigne(), CaseSuivante.getColonne(), getMap());
            Vertex v = algo.getGraph().getVertex(CaseSuivante);
            v.setCase(test);
            algo.getGraph().getVertices().remove(CaseSuivante, v);
            algo.getGraph().getVertices().put(test, v);

        }

        return retour;

    }

    public Case calculDest() {
        Case dest = this.getCase();
        Map map = this.getMap();
        boolean diamAcc = false;
        for (Objet o : diamonds) {
            if (algo.getTaillePath(this.getGraph().getVertex(o.getCase())) < algo.getInfini()) {
                diamAcc = true;
            }
        }
        if (diamAcc) {
            int dist = algo.getInfini() + 1;
            for (Objet o : diamonds) {
                if (algo.getTaillePath(this.getGraph().getVertex(o.getCase())) < dist && algo.getTaillePath(this.getGraph().getVertex(o.getCase())) != 0) {
                    dist = algo.getTaillePath(this.getGraph().getVertex(o.getCase()));
                    dest = o.getCase();
                }
            }
        } else {
            dest = map.getCaseFin();
        }
        dij.calcul(this.getGraph().getVertex(this.getCase()), this.getGraph().getVertex(dest));
        return dest;
    }

    public Type_Action action() {
        Type_Action retour = Type_Action.attendre;
        //gestion du premier tour de jeu
        if (firstTurn) {
            System.out.println("AAAH");
            firstTurn = false;
            this.algo = new Dijkstra(this.getMap().getGrapheSimple());
            this.dij = new Dijkstra(this.getMap().getGraphAvance());
            this.algo.calcul(this.getMap().getDebut(), this.getMap().getFina());
            this.dij.calcul(this.getMap().getDebut(), this.getMap().getFina());
           
            for (Objet o : this.entite.getMap().getListeObjet()) {
                if (o.getType() == Type_Objet.Diamant) {
                    this.diamonds.add(o);
                }
            }

            if (this.nearestDiamond() != null) {
                if (nearestDiamond().getType() == Type_Objet.Diamant) {
                    localPath = algo.getPath(getMap().getGrapheSimple().getVertex(nearestDiamond().getCase()));
                }
                if (nearestDiamond().getType() == Type_Objet.Pelle) {
                    localPath = algo.getPath(getMap().getGraphAvance().getVertex(nearestDiamond().getCase()));
                    this.getMap().removePelle(nearestDiamond());
                }

            } else {
                algo.calcul(getMap().getDebut(), getMap().getFina());
                localPath = this.algo.getPath();
            }
        }
        System.out.println("PATTH" + localPath);
        // ------------- FIIIIIIIN --------------//

        //Action si t'as pas la pelle 
        if (!this.getMap().getJoueur().getPelle()) {
            System.out.println("!getPelle");
            //On calcul le chemin le plus court de la case du joueur jusqu'à la sortie (+ mise à jour du graphe par extension)
            this.algo.calcul(this.getGraph().getVertex(this.getEntite().getCase()), this.getMap().getFina());
            //Si la map possède une pelle (une map peut être générée sans pelle)
                System.out.println("test");
                //Si le chemin est vide, l'IA est arrivée à destination (sortie ou diamants, normalement)
                if (this.localPath.isEmpty()) {
                    System.out.println("empty");
                    //S'il y a un objet sur la case de l'entité
                    if (this.entite.getCase().getObjet() != null) {
                        //On récupère le type de l'objet
                        Type_Objet test = this.entite.getCase().getObjet().getType();
                        //On choisit l'action en fonction du type
                        switch (test) {
                            case Diamant:
                                retour = Type_Action.ramasser;
                                break;
                            case Pelle:
                                //Si l'objet est une pelle, alors le nouveau chemin est initialisé
                                dij.calcul(this.getGraph().getVertex(this.getEntite().getCase()), this.getMap().getFina());
                                retour = Type_Action.ramasser;
                                break;
                            case Sortie:
                                retour = Type_Action.sortir;
                                break;
                            default:
                                break;
                       }
                        //S'il reste un diamant / une pelle à aller chercher
                       if (this.nearestDiamond() != null){
                           if (this.nearestDiamond().getType() == Type_Objet.Diamant && this.entite.getCase().getObjet().getType() == Type_Objet.Pelle){
                               localPath = dij.getPath(this.getMap().getGraphAvance().getVertex(nearestDiamond().getCase()));
                           }
                           else if (this.nearestDiamond().getType()== Type_Objet.Diamant || this.nearestDiamond().getType() == Type_Objet.Pelle){
                               localPath = algo.getPath(this.getMap().getGrapheSimple().getVertex(nearestDiamond().getCase()));
                           }
                       }
                       //S'il ne reste plus de diamants, on se dirige vers la sortie
                       else {
                           localPath = algo.getPath();
                       }
                    }
                }
                //si le chemin n'est pas vide, on est en plein déplacement
                else {
                    System.out.println("salut");
                    retour = noeudToAction(localPath.get(0).getCase());
                }
            
            
        }
        //si le joueur a la pelle
        else {
            
        }
        return retour;
    }
}
