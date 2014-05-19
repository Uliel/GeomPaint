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
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.jar.Attributes.Name;

import figures.*;
// Rq : laisser l'import de figures.Rectangle qui permet a eclipse de ne pas confondre avec une classe java existante java.awt.rectangle
import figures.Rectangle;

public class Dessin extends JPanel {

	// ATTRIBUTS
	private final static int MAXTAILLE = 100;
	private final int MARGE_SELECTION_POLY = 1;
	private final int MARGE_SELECTION_CERCLE = 8;
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
		this.boutons = new Menu(this);
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
						case (1):
							// Cercle
							nbPoints = 2;
							break;
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
						if (nbClics < nbPoints - 1) {
							listePoints.add(new UnPoint(e.getX(), e.getY()));
							nbClics++;
						}
						// Dernier clic : on instancie la figure, on supprime
						// l'ArrayList et on incrémente le nombre de figures
						else {
							listePoints.add(new UnPoint(e.getX(), e.getY()));
							switch (boutons.getNumFigCourante()) {
							case (1):
								// Cercle
								tabFigures[nbFigures] = new Cercle(listePoints);
								break;
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
							// du premier point, le polygone est considéré
							// comme
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
				
				// Si le bouton selectionner est choisi, on passe en mode selection
				if(boutons.getSelect()) {
					UnPoint ptCourant = new UnPoint(e.getX(), e.getY());
					boolean trouve = false;
					int j = 0;
					// Deselection de toutes les figures
					for(int i = 0 ; i < nbFigures ; i++) {
						tabFigures[i].setSelection(false);
					}
					// Boucle sur toutes les figures pour savoir si l'une d'elle possede un segment a proximite du point courant
					while(j < nbFigures && !trouve) {
						// Si la figure est un polygone
						if(tabFigures[j] instanceof Polygone) {
							int nbSommets;
							nbSommets = tabFigures[j].getNbMemo();
							for(int k = 0 ; k < tabFigures[j].getNbMemo() ; k++) {
								// Si c'est le cas, selection de cette figure
								if(ptCourant.estVoisinSegment(MARGE_SELECTION_POLY, tabFigures[j].getTabMemo()[k], tabFigures[j].getTabMemo()[(k+1)%nbSommets])) {
									tabFigures[j].setSelection(true);
									trouve = true;
								}
							}
						}
						// Si la figure est un cercle
						if(tabFigures[j] instanceof Cercle) {
							int rayonCercle = tabFigures[j].getTabMemo()[0].dist(tabFigures[j].getTabMemo()[1]);
							int rayonPtCourant = ptCourant.dist(tabFigures[j].getTabMemo()[0]);
							if(Math.abs(rayonCercle - rayonPtCourant) < MARGE_SELECTION_CERCLE) {
								tabFigures[j].setSelection(true);
								trouve = true;
							}
						}
						j++;			
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

		addMouseListener(ml);
		// this.addMouseMotionListener(mml);

	}

	// METHODES

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Dessin du tableau de figures
		if (nbFigures > 0) {
			for (int i = 0; i < nbFigures; i++) {
				g.setColor(tabFigures[i].getCouleur());
				UnPoint[] positions = tabFigures[i].getTabSaisie();

				// Cas du cercle
				if (tabFigures[i] instanceof Cercle) {
					// Calcul du rayon et dessin
					int rayon = positions[0].dist(positions[1]);
					g.drawOval(positions[0].x - rayon, positions[0].y - rayon,
							rayon * 2, rayon * 2);
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
			if (boutons.getNumFigCourante() == 6) {
				g.fillRect(listePoints.get(0).x - 3, listePoints.get(0).y - 3,
						6, 6);
				for (int i = 0; i < listePoints.size(); i++)
					g.drawRect(listePoints.get(i).x - 3,
							listePoints.get(i).y - 3, 6, 6);
			} else
				for (int j = 0; j < listePoints.size(); j++) {
					g.drawRect(listePoints.get(j).x - 3,
							listePoints.get(j).y - 3, 6, 6);
				}
		}
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

	// Fonction qui permet d'exporter une image
	public void exporter(String format) {
		JFileChooser filechoose = new JFileChooser();
		filechoose.setCurrentDirectory(new File(".")); /*
														 * ouvrir la boite de
														 * dialogue dans
														 * répertoire courant
														 */
		filechoose.setDialogTitle("Exporter une image"); /*
														 * nom de la boite de
														 * dialogue
														 */

		filechoose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); /*
																		 * pour
																		 * afficher
																		 * seulement
																		 * les
																		 * ré
																		 * pertoires
																		 */

		String approve = new String("Exporter"); /*
												 * Le bouton pour valider
												 */
		int resultatEnregistrer = filechoose.showDialog(filechoose, approve);
		if (resultatEnregistrer == JFileChooser.APPROVE_OPTION) { /*
																 * Si
																 * l’utilisateur
																 * clique sur le
																 * bouton
																 * Exporter
																 */
			String chemin = filechoose.getSelectedFile().getAbsolutePath(); /*
																			 * pour
																			 * avoir
																			 * le
																			 * chemin
																			 * absolu
																			 */
			/*
			 * on enregistre le fichier dans le repertoire desiré avec pour nom
			 * image + date en millisecondes
			 */
			GregorianCalendar intCal = new GregorianCalendar();
			long tmp = intCal.getTimeInMillis();
			boutons.setVisible(false);
			BufferedImage img = new BufferedImage(this.getWidth(),
					this.getHeight(), BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = img.createGraphics();
			this.paint(g2);
			try {
				ImageIO.write(img, format,
						new File(chemin, "Image" + tmp + "."));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			boutons.setVisible(true);
		}

	}
}