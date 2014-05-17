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

import figures.*;
// Rq : laisser l'import de figures.Rectangle qui permet a eclipse de ne pas confondre avec une classe java existante java.awt.rectangle
import figures.Rectangle;

public class Dessin extends JPanel {

	private final static int MAXTAILLE = 100;
	private FigureGeom[] tabFigures;
	private Menu boutons;
	private int nbClics;
	private int nbFigures;

	public Dessin() {
		this.setPreferredSize(new Dimension(1000, 600));
		this.setBackground(Color.WHITE);
		this.tabFigures = new FigureGeom[Dessin.MAXTAILLE];
		this.boutons = new Menu();
		this.setLayout(new BorderLayout());
		this.add(boutons,BorderLayout.NORTH);
		this.nbFigures = 0;
		this.nbClics = 0;

		MouseListener ml = new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				Graphics g = getGraphics();
				g.setColor(Color.BLACK);
				//On ne dessine que si un bouton dessin est appuy�
				if (boutons.getDessiner()) {
					//Si on clique pour la premiere fois :
					if (nbClics == 0) {
						// - Creation de la figure selectionnee dans le menu
						switch(boutons.getNumFigCourante()) {
						case(1) :
							tabFigures[nbFigures] = new Cercle();
							break;
						case(2) :
							tabFigures[nbFigures] = new Rectangle();
							break;
						case(3) :
							tabFigures[nbFigures] = new Carre();
							break;
						case(4) :
							tabFigures[nbFigures] = new Triangle();
							break;
						case(5) :
							tabFigures[nbFigures] = new Ovale();
							break;
						case(6) :
							tabFigures[nbFigures] = new Polygone();
							break;
						case(7) :
							tabFigures[nbFigures] = new Losange();
							break;	
//						case(8) :
//							tabFigures[nbFigures] = new Trait();
//							break;
						}
						tabFigures[nbFigures].setCouleur(g.getColor());
						
						// - Deselection de la figure precedente
						for (int i = 0; i < nbFigures; i++) {
							if (tabFigures[i].getSelection())
								tabFigures[i].selectionner();
						}
					}
					
					//On remplit le tableau de points et on incr�mente le nombre de clics
					if (nbClics < tabFigures[nbFigures].getTabSaisie().length - 1) {
						tabFigures[nbFigures].ajouterSaisie(nbClics, e.getX(),
								e.getY());
						nbClics++;
					} 
					//Dernier clic : on r�initialise le nb de clics et on incr�mente le nb de figures
					else {
						tabFigures[nbFigures].ajouterSaisie(nbClics, e.getX(),
								e.getY());
						nbClics = 0;
						nbFigures++;
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

		MouseMotionListener mml = new MouseMotionListener() {

			public void mouseMoved(MouseEvent e) {
				if (boutons.getDessiner() && nbClics > 0) {
					//Tentative pour dessiner avec le mouvement de la souris (loup�)
					Graphics g = getGraphics();
					tabFigures[nbFigures].ajouterSaisie(nbClics, e.getX(), e.getY());
//					Point[] positions = tabFigures[nbFigures].getTabPoints();
//					g.drawRect(positions[0].x - 3, positions[0].y - 3, 6, 6);
//					int diametre = ((int) Math.sqrt((positions[0].x - e.getX())
//							* (positions[0].x - e.getX())
//							+ (positions[0].y - e.getY())
//							* (positions[0].y - e.getY()))) * 2;
//					g.drawOval(positions[0].x - diametre / 2, positions[0].y
//							- diametre / 2, diametre, diametre);
				}
				repaint();
			}

			public void mouseDragged(MouseEvent e) {

			}
		};

		this.addMouseListener(ml);
		this.addMouseMotionListener(mml);

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//Dessin du tableau de figures
		if (nbFigures > 0) {
			for (int i = 0; i < nbFigures; i++) {
				g.setColor(tabFigures[i].getCouleur());
				Point[] positions = tabFigures[i].getTabSaisie();
				
				// Cas du cercle
				if(tabFigures[i] instanceof Cercle) {
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
				
				
				// Cas du triangle
				if(tabFigures[i] instanceof Triangle) {
					// Recuperation des coordonnees
					int x0 = positions[0].x;
					int y0 = positions[0].y;
					int x1 = positions[1].x;
					int y1 = positions[1].y;
					int x2 = positions[2].x;
					int y2 = positions[2].y;
										
					// Remplissage du tableau de points de memorisation
					tabFigures[i].ajouterMemo(0, x0, y0);
					tabFigures[i].ajouterMemo(1, x1, y1);
					tabFigures[i].ajouterMemo(2, x2, y2);
				
					// Dessin
					g.drawLine(x0, y0, x1, y1);
					g.drawLine(x1, y1, x2, y2);
					g.drawLine(x0, y0, x2, y2);
				}
				
				
				// Cas du rectangle
				if(tabFigures[i] instanceof Rectangle) {
					// Recuperation des coordonnees
					int x0 = positions[0].x;
					int y0 = positions[0].y;
					int x1 = positions[1].x;
					int y1 = positions[1].y;
					
					// Remplissage du tableau de points de memorisation
					tabFigures[i].ajouterMemo(0, x0, y0);
					tabFigures[i].ajouterMemo(1, x0, y1);
					tabFigures[i].ajouterMemo(2, x1, y1);
					tabFigures[i].ajouterMemo(3, x1, y0);
					
					// Dessin
					g.drawLine(x0, y0, x0, y1);
					g.drawLine(x0, y1, x1, y1);
					g.drawLine(x1, y1, x1, y0);
					g.drawLine(x1, y0, x0, y0);					
				}
						
				// Testtttttt
				
				//Si la figure est selectionnee, on dessine les points de selection (et pas les points de memorisation
				// je pense qu'il y a une faute dans l'enonce, a discuter)
				if (tabFigures[i].getSelection()) {
					for (int j = 0 ; j < positions.length ; j++)
						g.drawRect(positions[j].x - 3, positions[j].y - 3, 6, 6);
				}
			}
		}
	}
	
	
	// Methode de calcul de la distance entre 2 points
	public int distance(int x1, int y1, int x2, int y2) {
		return (int)Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	}
		
	

}