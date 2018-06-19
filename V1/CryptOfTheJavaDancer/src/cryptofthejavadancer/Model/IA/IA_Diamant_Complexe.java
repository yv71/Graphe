/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA;

import cryptofthejavadancer.Model.Carte.Cases.Case;
import cryptofthejavadancer.Model.Carte.Cases.Type_Case;
import cryptofthejavadancer.Model.Carte.Graphes.Algorithmes.AStar;
import cryptofthejavadancer.Model.Carte.Graphes.Algorithmes.Dijkstra2;
import cryptofthejavadancer.Model.Carte.Graphes.Graphe;
import cryptofthejavadancer.Model.Carte.Map;
import cryptofthejavadancer.Model.Entites.Entite;
import cryptofthejavadancer.Model.Objet.Objet;
import cryptofthejavadancer.Model.Objet.Type_Objet;
import java.util.ArrayList;

/**
 *
 * @author yv066840
 */
public class IA_Diamant_Complexe extends IA {

    private Dijkstra2 dijkstraSimple;
    private Dijkstra2 dijkstraComplexe;
    private AStar astar;
    private ArrayList<Objet> diamants;
    private boolean tourUn;
    private Graphe grapheSimple;
    private Graphe grapheComplexe;
    private Map map;
    private boolean hasPelle;
    private Objet pelle;

    public IA_Diamant_Complexe(Entite _entite) {
        super(_entite);
        map = null;
        grapheSimple = null;
        grapheComplexe = null;
        dijkstraComplexe = null;
        diamants = null;
        tourUn = true;
        grapheSimple = null;
        astar = null;
        hasPelle = false;
        pelle = null;
    }

    @Override
    public Type_Action action() {
        Type_Action action = Type_Action.attendre;
        map = this.getEntite().getMap();
        grapheSimple = map.getGrapheSimple();
        grapheComplexe = map.getGraphAvance();
        if (tourUn) {
            //Génération de la liste des diamants
            diamants = new ArrayList<Objet>();
            for (Objet o : map.getListeObjet()) {
                if (o.getType() == Type_Objet.Diamant) {
                    diamants.add(o);
                }
                if (o.getType() == Type_Objet.Pelle) {
                    pelle = o;
                }
            }
            //System.out.println(pelle.getCase());
            //Génération de l'algo
            this.dijkstraSimple = new Dijkstra2(grapheSimple);
            this.dijkstraComplexe = new Dijkstra2(grapheComplexe);
            astar = new AStar(grapheSimple);
            dijkstraSimple.calcul(grapheSimple.getVertex(this.getCase()), grapheSimple.getVertex(map.getCaseFin()));
            //System.out.println(this.dijkstraSimple.getPath());
            dijkstraComplexe.calcul(grapheComplexe.getVertex(this.getCase()), grapheComplexe.getVertex(map.getCaseFin()));
            tourUn = false;
            //System.out.println(this.dijkstraSimple.getPath());
        }
        if (astar.getPath().isEmpty()) {
            //Interaction
            if (this.getCase().getObjet() != null) {
                if (this.getCase().getObjet().getType() == Type_Objet.Diamant) {
                    action = Type_Action.ramasser;
                    diamants.remove(this.getCase().getObjet());
                }
                if (this.getCase().getObjet().getType() == Type_Objet.Pelle && !hasPelle) {
                    action = Type_Action.ramasser;
                    hasPelle = true;
                    astar.setGraph(grapheComplexe);
                    System.out.println("pelle ramassée");
                    //System.out.println(astar.getPath());
                }
                if (this.getCase().getObjet().getType() == Type_Objet.Sortie) {
                    action = Type_Action.sortir;
                }
            }
            //Si il y a un diamant accessible on récupère le plus proche
            this.calculDest();
        } else {
            action = calculAction(astar.getPath().get(0).getCase());
        }
        //System.out.println(astar.getPath());
        return action;
    }

    public Type_Action calculAction(Case CaseSuivante) {
        Case caseCadence = this.getEntite().getCase();
        Type_Action res = Type_Action.attendre;
        int X = caseCadence.getLigne();
        int Y = caseCadence.getColonne();

        //Si la case suivante est une case Sol
        if (CaseSuivante.getType() == Type_Case.Sol) {
            //Si la case est vide
            if (CaseSuivante.getEntite() == null) {
                res = this.directionDeplacement(X, Y, CaseSuivante);
                this.astar.destroyFirst();
            } //Si la case est occupée
            else {
                res = this.directionInteraction(X, Y, CaseSuivante);
            }
        } else if (CaseSuivante.getType() == Type_Case.Mur) {
            res = this.directionInteraction(X, Y, CaseSuivante);
        } else if (CaseSuivante.getType() == Type_Case.MurDur && hasPelle) {
            res = this.directionInteraction(X, Y, CaseSuivante);
        }
        return res;
    }

    public void calculDest() {
        Case dest = this.getCase();
        boolean diamAcc = false;
        for (Objet o : diamants) {
            if (dijkstraComplexe.taillePath(grapheComplexe.getVertex(o.getCase())) < dijkstraComplexe.getInfini()) {
                diamAcc = true;
                System.out.println(diamAcc);
                System.out.println(diamants);
            }
        }
        if (diamAcc) {
            int dist = dijkstraComplexe.getInfini() + 1;
            for (Objet o : diamants) {
                if (hasPelle) {
                    dijkstraComplexe.calcul(grapheComplexe.getVertex(this.getCase()), grapheComplexe.getVertex(map.getCaseFin()));
                    if (dijkstraComplexe.taillePath(grapheComplexe.getVertex(o.getCase())) < dist && dijkstraComplexe.taillePath(grapheComplexe.getVertex(o.getCase())) != 0) {
                        dist = dijkstraComplexe.taillePath(grapheComplexe.getVertex(o.getCase()));
                        dest = o.getCase();
                    }
                } else {
                    int distCadDiam = dijkstraSimple.taillePath(grapheSimple.getVertex(o.getCase()));
                    //System.out.println("distCadDiam "+distCadDiam);
                    AStar astarPelle = new AStar(grapheSimple);
                    astarPelle.calcul(grapheSimple.getVertex(this.getCase()), grapheSimple.getVertex(pelle.getCase()));
                    //System.out.println(astar.getPath());
                    //System.out.println(astar.getDistance());
                    int distCadPelle = astarPelle.getTaillePath(grapheSimple.getVertex(pelle.getCase()));       //J'utilise Astar pour raccourcir le trajet
                    //System.out.println("distCadPelle "+distCadPelle);
                    dijkstraComplexe.calcul(grapheComplexe.getVertex(pelle.getCase()), grapheComplexe.getVertex(o.getCase()));
                    int distPelleDiam = dijkstraComplexe.taillePath(grapheComplexe.getVertex(o.getCase()));
                    //System.out.println("distPelleDiam "+distPelleDiam);
                    if (distCadDiam < distCadPelle + distPelleDiam && distCadDiam != 0 && distCadPelle + distPelleDiam != 0) {
                        if (distCadDiam < dist) {
                            dist = distCadDiam;
                            dest = o.getCase();
                            System.out.println("dest diam");
                        }
                    } else {
                        if (distCadPelle + distPelleDiam < dist) {
                            dist = distCadPelle + distPelleDiam;
                            dest = pelle.getCase();
                            System.out.println("dest pelle");
                        }
                    }
                }

            }
        } else {
            dest = map.getCaseFin();
        }
        if (hasPelle) {
            astar.calcul(grapheComplexe.getVertex(this.getCase()), grapheComplexe.getVertex(dest));
        } else {
            astar.calcul(grapheSimple.getVertex(this.getCase()), grapheSimple.getVertex(dest));
        }
    }

    public Type_Action directionDeplacement(int X, int Y, Case caseSuivante) {
        Type_Action res = Type_Action.attendre;
        if (caseSuivante.getLigne() == (X - 1)) {
            res = Type_Action.deplacement_haut;
        } else if (caseSuivante.getLigne() == (X + 1)) {
            res = Type_Action.deplacement_bas;
        } else if (caseSuivante.getColonne() == (Y - 1)) {
            res = Type_Action.deplacement_gauche;
        } else if (caseSuivante.getColonne() == (Y + 1)) {
            res = Type_Action.deplacement_droite;
        }
        return res;
    }

    public Type_Action directionInteraction(int X, int Y, Case caseSuivante) {
        Type_Action res = Type_Action.attendre;
        if (caseSuivante.getLigne() == (X - 1)) {
            res = Type_Action.interagir_haut;
        } else if (caseSuivante.getLigne() == (X + 1)) {
            res = Type_Action.interagir_bas;
        } else if (caseSuivante.getColonne() == (Y - 1)) {
            res = Type_Action.interagir_gauche;
        } else if (caseSuivante.getColonne() == (Y + 1)) {
            res = Type_Action.interagir_droite;
        }
        return res;
    }
}
