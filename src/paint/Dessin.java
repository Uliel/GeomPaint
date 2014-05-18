/**
 * 
 * @authors Nicolas Gambarini, Sarah Lequeuvre
 *
 */

package paint;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

import java.util.ArrayList;

import figures.*;
// Rq : laisser l'import de figures.Rectangle qui permet a eclipse de ne pas confondre avec une classe java existante java.awt.rectangle
import figures.Rectangle;

public class Dessin extends JPanel {

	// ATTRIBUTS
	private final static int MAXTAILLE = 100;
	private FigureGeom[] tabFigures;
	private Menu boutons;
	private int nbClics;
	private int nbFigures;
	private int nbPoints;
	private ArrayList<UnPoint> listePoints = new ArrayList<UnPoint>();

	// CONSTRUCTEURS
	public Dessin() {
		this.setPreferredSize(new Dimension(1000, 600));
		this.setBackground(Color.WHITE);
		this.tabFigures = new FigureGeom[Dessin.MAXTAILLE];
		this.boutons = new Menu();
		this.setLayout(new BorderLayout());
		this.add(boutons, BorderLayout.NORTH);
		this.nbFigures = 0;
		this.nbClics = 0;
		this.nbPoints = 0;

		MouseListener ml = new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				Graphics g = getGraphics();
				g.setColor(Color.BLACK);
				// On ne dessine que si un bouton dessin est appuy�
				if (boutons.getDessiner()) {
					// Si on clique pour la premiere fois :
					if (nbClics == 0) {
						// - Recuperation du nombre de points de selection de la
						// figure desiree
						switch (boutons.getNumFigCourante()) {
						// case(1) :
						// // Cercle
						// nbPoints = 2;
						// break;
						case (2):
							// Rectangle
							nbPoints = 2;
							break;
						// case(3) :
						// tabFigures[nbFigures] = new Carre();
						// break;
						case (4):
							// Triangle
							nbPoints = 3;
							break;
						case (5):
							// tabFigures[nbFigures] = new Ovale();
							// break;
							// case(7) :
							// tabFigures[nbFigures] = new Losange();
							// break;
							// case(8) :
							// tabFigures[nbFigures] = new Trait();
							// break;
						}
						// tabFigures[nbFigures].setCouleur(g.getColor());

						// - Deselection de la figure precedente
						for (int i = 0; i < nbFigures; i++) {
							if (tabFigures[i].getSelection())
								tabFigures[i].selectionner();
						}
					}

					// Si on a une figure autre qu'un polygone
					if (boutons.getNumFigCourante() != 6) {
						// Tant que l'on a pas le nombre de points requis pour
						// la figure, on incrémente une ArrayList
						if (nbClics < nbPoints-1) {
							listePoints.add(new UnPoint(e.getX(), e.getY()));
							nbClics++;
						}
						// Dernier clic : on instancie la figure, on supprime
						// l'ArrayList et on incrémente le nombre de figures
						else {
							listePoints.add(new UnPoint(e.getX(), e.getY()));
							switch (boutons.getNumFigCourante()) {
							// case(1) :
							// // Cercle
							// nbPoints = 2;
							// break;
							case (2):
								// Rectangle
								tabFigures[nbFigures] = new Rectangle(
										listePoints);
								break;
							// case(3) :
							// tabFigures[nbFigures] = new Carre();
							// break;
							case (4):
								// Triangle
								tabFigures[nbFigures] = new Triangle(
										listePoints);
								break;
							case (5):
								// tabFigures[nbFigures] = new Ovale();
								// break;
								// case(7) :
								// tabFigures[nbFigures] = new Losange();
								// break;
								// case(8) :
								// tabFigures[nbFigures] = new Trait();
								// break;
							}
							nbClics = 0;
							nbFigures++;
							listePoints.clear();
						}
					}
					// Si on est en présence d'un polygone
					else {
						UnPoint nouveauPoint = new UnPoint(e.getX(), e.getY());
						// Le premier point est automatiquement ajouté à
						// l'ArrayList
						if (nbClics == 0) {
							listePoints.add(nouveauPoint);
							nbClics++;
						}
						// Gestion des points suivants
						else {
							// Si le nouveau point est positionné au voisinage
							// du premier point, le polygone est considéré comme
							// fini
							if (estVoisin(20, nouveauPoint, listePoints.get(0))) {
								tabFigures[nbFigures] = new Polygone(
										listePoints);
								listePoints.clear();
								nbClics = 0;
								nbFigures++;
							}
							// Sinon, on incrémente l'ArrayList
							else {
								listePoints.add(nouveauPoint);
								nbClics++;
							}
						}
					}
				}
				repaint();
			}

			public void mousePressed(MouseEvent e) {

			}

			public void mouseExited(MouseEvent e) {

			}

			public void mouseEntered(MouseEvent e) {

			}

			public void mouseClicked(MouseEvent e) {

			}
		};

		// MouseMotionListener mml = new MouseMotionListener() {

		// public void mouseMoved(MouseEvent e) {
		// if (boutons.getDessiner() && nbClics > 0) {
		// //Tentative pour dessiner avec le mouvement de la souris (loup�)
		// Graphics g = getGraphics();
		// tabFigures[nbFigures].ajouterSaisie(nbClics, e.getX(), e.getY());
		// Point[] positions = tabFigures[nbFigures].getTabPoints();
		// g.drawRect(positions[0].x - 3, positions[0].y - 3, 6, 6);
		// int diametre = ((int) Math.sqrt((positions[0].x - e.getX())
		// * (positions[0].x - e.getX())
		// + (positions[0].y - e.getY())
		// * (positions[0].y - e.getY()))) * 2;
		// g.drawOval(positions[0].x - diametre / 2, positions[0].y
		// - diametre / 2, diametre, diametre);
		// }
		// repaint();
		// }
		//
		// public void mouseDragged(MouseEvent e) {
		//
		// }
		// };

		this.addMouseListener(ml);
		// this.addMouseMotionListener(mml);

	}

	// METHODES

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Dessin du tableau de figures
		if (nbFigures > 0) {
			for (int i = 0; i < nbFigures; i++) {
				g.setColor(tabFigures[i].getCouleur());
				Point[] positions = tabFigures[i].getTabSaisie();

				// Cas du cercle (ne marche pas)
				if (tabFigures[i] instanceof Cercle) {
					// Recuperation des coordonnees
					int x0 = positions[0].x;
					int y0 = positions[0].y;
					int x1 = positions[1].x;
					int y1 = positions[1].y;

					// Calcul du rayon et dessin
					int rayon = distance(x0, y0, x1, y1);
					g.drawOval(x0 - rayon, y0 - rayon, rayon * 2, rayon * 2);

					// Remplissage du tableau de points de memorisation
					tabFigures[i].ajouterMemo(0, x0, y0);
					tabFigures[i].ajouterMemo(1, x0 + rayon, y0);
				}

				// Cas des polygones
				if (tabFigures[i] instanceof Polygone) {
					int nbSommets = tabFigures[i].getNbMemo();
					for (int j = 0; j < nbSommets; j++)
						dessinLigne(g, tabFigures[i].getTabMemo()[j],
								tabFigures[i].getTabMemo()[(j + 1) % nbSommets]);
				}

				// Si la figure est selectionnee, on dessine les points de
				// selection (et pas les points de memorisation
				// je pense qu'il y a une faute dans l'enonce, a discuter)
				if (tabFigures[i].getSelection()) {
					for (int j = 0; j < positions.length; j++)
						g.drawRect(positions[j].x - 3, positions[j].y - 3, 6, 6);
				}
			}
		}
		// Affichage des points d'une ArrayList si existante
		g.setColor(Color.black);
		if (!listePoints.isEmpty()) {
			for (int j = 0; j < listePoints.size(); j++) {
				g.drawRect(listePoints.get(j).x - 3, listePoints.get(j).y - 3,
						6, 6);
			}
		}
	}

	// Methode de calcul de la distance entre 2 points
	public int distance(int x1, int y1, int x2, int y2) {
		return (int) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}

	// Methode de dessin d'une ligne avec 2 paramètres de type UnPoint
	public void dessinLigne(Graphics g, UnPoint p1, UnPoint p2) {
		g.drawLine(p1.x, p1.y, p2.x, p2.y);
	}

	// Methode qui teste si un point se trouve à moins d'une certaine distance
	// d'un autre point
	public boolean estVoisin(int distance, UnPoint p1, UnPoint p2) {
		boolean res = false;
		if (p1.distance(p2) < distance)
			res = true;
		return res;
	}
}