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
		this.add(boutons, BorderLayout.NORTH);

		this.nbFigures = 0;
		this.nbClics = 0;

		MouseListener ml = new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				Graphics g = getGraphics();
				g.setColor(Color.BLACK);
				//On ne dessine que si un bouton dessin est appuyé
				if (boutons.getDessiner()) {
					//Si on clique pour la première fois, on crée la figure et on déselectionne la précédente
					if (nbClics == 0) {
						tabFigures[nbFigures] = new Cercle();
						tabFigures[nbFigures].setCouleur(g.getColor());
						for (int i = 0; i < nbFigures; i++) {
							if (tabFigures[i].getSelection())
								tabFigures[i].selectionner();
						}
					}
					//On remplit le tableau de points et on incrémente le nombre de clics
					if (nbClics < tabFigures[nbFigures].getTabPoints().length - 1) {
						tabFigures[nbFigures].ajouterPoint(nbClics, e.getX(),
								e.getY());
						nbClics++;
					} 
					//Dernier clic : on réinitialise le nb de clics et on incrémente le nb de figures
					else {
						tabFigures[nbFigures].ajouterPoint(nbClics, e.getX(),
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
					//Tentative pour dessiner avec le mouvement de la souris (loupé)
					Graphics g = getGraphics();
					tabFigures[nbFigures].ajouterPoint(nbClics, e.getX(), e.getY());
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
				Point[] positions = tabFigures[i].getTabPoints();
				// Dessin d'un cercle
				// Calcul de la distance à faire ailleurs
				int diametre = ((int) Math
						.sqrt((positions[0].x - positions[1].x)
								* (positions[0].x - positions[1].x)
								+ (positions[0].y - positions[1].y)
								* (positions[0].y - positions[1].y))) * 2;
				g.drawOval(positions[0].x - diametre / 2, positions[0].y
						- diametre / 2, diametre, diametre);
				//Si la figure est sélectionnée, on dessine les points de sélection
				if (tabFigures[i].getSelection()) {
					g.drawRect(positions[0].x - 3, positions[0].y - 3, 6, 6);
					g.drawRect(positions[0].x + diametre / 2 - 3,
							positions[0].y - 3, 6, 6);
				}
			}
		}
	}

}