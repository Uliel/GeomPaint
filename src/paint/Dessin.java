package paint;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import figures.*;
// Rq : laisser l'import de figures.Rectangle qui permet a eclipse de ne pas confondre avec une classe java existante java.awt.rectangle
import figures.Rectangle;

/**
 * 
 * @authors Frederic Euriot, Nicolas Gambarini, Sarah Lequeuvre, Sylvain Riess
 * 
 */
public class Dessin extends JPanel {

	// ATTRIBUTS
	private final static int MAXTAILLE = 1000000;
	private final int MARGE_SELECTION_POLY = 5;
	private final int MARGE_SELECTION_CERCLE = 8;
	private final int MARGE_SELECTION_POINT = 12;
	private final int MARGE_SELECTION_ELLIPSE = 10;
	private final int MARGE_ANNULE = 30;
	private FigureGeom[] tabFigures;
	private BoiteOutils boutons;
	private MenuDeroulant menuD;
	private int nbClics;
	private int nbFigures;
	private int nbPoints;
	private ArrayList<UnPoint> listePoints = new ArrayList<UnPoint>();
	private ArrayList<FigureGeom> listeFigSelectionnees = new ArrayList<FigureGeom>();
	private ArrayList<FigureGeom[]> listeEtats = new ArrayList<FigureGeom[]>();
	private Color couleur = Color.BLACK;
	private int epaisseur = 1;
	private boolean control;
	private UnMenu menu = new UnMenu(this);
	private boolean translation;
	private boolean modifFigure;
	private UnPoint ptSouris = new UnPoint(0, 0);
	private UnPoint ptFigure;
	private FigureGeom figModifiee;
	private boolean annule = true;
	private ArrayList<FigureGeom> listeTampon = new ArrayList<FigureGeom>();
	private int absSouris = -1;
	private int ordSouris = -1;
	private JPanel entete = new JPanel();
	private boolean selectionMultiple = false;
	private UnPoint ptSelectionMultiple;

	// CONSTRUCTEURS
	/**
	 * Constructeur sans paramètres de la classe Dessin initialisation de la
	 * barre d'utils, le menu et le menu déroulant création et ajout des
	 * différents listener
	 */
	public Dessin() {
		menuD = new MenuDeroulant(this);
		this.setFocusable(true);
		this.setComponentPopupMenu(menuD);
		this.setPreferredSize(new Dimension(1000, 600));
		this.setBackground(Color.WHITE);
		this.tabFigures = new FigureGeom[Dessin.MAXTAILLE];
		this.boutons = new BoiteOutils(this);
		this.setLayout(new BorderLayout());
		entete.setLayout(new BorderLayout(2, 2));
		entete.add(boutons, BorderLayout.CENTER);
		entete.add(menu, BorderLayout.NORTH);
		this.add(entete, BorderLayout.NORTH);
		this.nbFigures = 0;
		this.nbClics = 0;
		this.nbPoints = 0;
		this.control = false;

		MouseListener ml = new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				modifFigure = false;
				annule = true;
				// on verifie s'il y a des figures dans le rectangle de rotation
				// si c'est le cas on les ajoute à la liste des figures
				// sélectionnées
				if (selectionMultiple) {
					int maxX = ptSelectionMultiple.x;
					int minX = ptSouris.x;
					int minY = ptSouris.y;
					int maxY = ptSelectionMultiple.y;
					if (ptSouris.x > ptSelectionMultiple.x) {
						minX = ptSelectionMultiple.x;
						maxX = ptSouris.x;
					}
					if (ptSouris.y > ptSelectionMultiple.y) {
						minY = ptSelectionMultiple.y;
						maxY = ptSouris.y;
					}
					for (int i = 0; i < nbFigures; i++) {
						boolean select = true;
						for (int j = 0; j < tabFigures[i].getNbMemo(); j++) {
							if (tabFigures[i].getTabMemo()[j].x > maxX
									|| tabFigures[i].getTabMemo()[j].x < minX
									|| tabFigures[i].getTabMemo()[j].y > maxY
									|| tabFigures[i].getTabMemo()[j].y < minY)
								select = false;
						}
						if (select)
							listeFigSelectionnees.add(tabFigures[i]);
					}
					selectionMultiple = false;
				}
				repaint();

			}

			public void mousePressed(MouseEvent e) {
				// On ne dessine que si un bouton dessin est appuye
				if (boutons.getDessiner()) {
					Graphics g = getGraphics();
					g.setColor(couleur);
					// Si on clique pour la premiere fois :
					if (nbClics == 0) {
						// - Recuperation du nombre de points de selection de la
						// figure desiree
						switch (boutons.getNumFigCourante()) {
						case (1): // Cercle
							nbPoints = 2;
							break;
						case (2): // Rectangle
							nbPoints = 2;
							break;
						case (3): // Carre
							nbPoints = 2;
							break;
						case (4): // Triangle
							nbPoints = 3;
							break;
						case (5): // Ellipse
							nbPoints = 2;
							break;
						case (7): // Losange
							nbPoints = 2;
							break;
						case (8): // Trait
							nbPoints = 2;
							break;
						}
						// tabFigures[nbFigures].setCouleur(g.getColor());

						// - Deselection de la figure precedente
						listeFigSelectionnees.clear();
					}

					// Si on a une figure autre qu'un polygone
					if (boutons.getNumFigCourante() != 6) {
						// Tant que l'on a pas le nombre de points requis pour
						// la figure, on incremente une ArrayList
						if (nbClics < nbPoints - 1) {
							listePoints.add(new UnPoint(e.getX(), e.getY()));
							nbClics++;

						}
						// Dernier clic : on instancie la figure, on supprime
						// l'ArrayList et on incremente le nombre de figures
						else {
							listePoints.add(new UnPoint(e.getX(), e.getY()));
							ajouterEtat();
							switch (boutons.getNumFigCourante()) {
							case (1): // Cercle
								tabFigures[nbFigures] = new Cercle(listePoints);
								break;
							case (2): // Rectangle
								tabFigures[nbFigures] = new Rectangle(
										listePoints);
								break;
							case (3): // Carre
								tabFigures[nbFigures] = new Carre(listePoints);
								break;
							case (4): // Triangle
								tabFigures[nbFigures] = new Triangle(
										listePoints);
								break;
							case (5): // Ellipse
								tabFigures[nbFigures] = new Ellipse(listePoints);
								break;
							// Le cas du Polygone est traite a part
							case (7): // Losange
								tabFigures[nbFigures] = new Losange(listePoints);
								break;
							case (8): // Trait
								tabFigures[nbFigures] = new Polygone(
										listePoints);
								break;
							}
							nbClics = 0;
							tabFigures[nbFigures].setCouleur(couleur);
							tabFigures[nbFigures].setEpaisseur(epaisseur);
							listeFigSelectionnees.add(tabFigures[nbFigures]);
							nbFigures++;
							listePoints.clear();
						}
					}
					// Si on est en presence d'un polygone
					else {
						UnPoint nouveauPoint = new UnPoint(e.getX(), e.getY());
						// Le premier point est automatiquement ajoute a
						// l'ArrayList
						if (nbClics == 0) {
							listePoints.add(nouveauPoint);
							nbClics++;
						}
						// Gestion des points suivants
						else {
							// Si le nouveau point est positionne au voisinage
							// du premier point, le polygone est considere
							// comme fini
							if (estVoisin(20, nouveauPoint, listePoints.get(0))) {
								ajouterEtat();
								tabFigures[nbFigures] = new Polygone(
										listePoints);
								tabFigures[nbFigures].setCouleur(couleur);
								tabFigures[nbFigures].setEpaisseur(epaisseur);
								listePoints.clear();
								listeFigSelectionnees
										.add(tabFigures[nbFigures]);
								nbClics = 0;
								nbFigures++;
							}
							// Sinon, on incremente l'ArrayList
							else {
								listePoints.add(nouveauPoint);
								nbClics++;
							}
						}
					}
				}
				if ((e.getModifiers() & ActionEvent.CTRL_MASK) == ActionEvent.CTRL_MASK) {
					setControl(true);
				}

				// Actions possibles avec le bouton "select" :
				if (boutons.getSelect()) {
					ptSouris.move(e.getX(), e.getY());
					// 1) Selection d'une ou plusieurs figures
					if (!control)
						listeFigSelectionnees.clear();
					FigureGeom fig = figVoisine(ptSouris);
					boolean trouve = false;
					if (fig != null) {
						for (int i = 0; i < listeFigSelectionnees.size()
								&& !trouve; i++) {
							if (fig == listeFigSelectionnees.get(i))
								trouve = true;
						}
						if (!trouve)
							listeFigSelectionnees.add(fig);
						else
							listeFigSelectionnees.remove(fig);
					} else {
						listeFigSelectionnees.clear();
						translation = false;
					}

					// 2) Initialisation d'une modification de figure
					// (couplage avec MouseDragged)
					if (pointVoisin(ptSouris) != null) {
						ptFigure = pointVoisin(ptSouris);
						figModifiee = figVoisine(ptFigure);
						modifFigure = true;
					}
					// 3) Initialisation d'une translation de figure(s)
					// (couplage avec MouseDragged)
					else if (figVoisine(ptSouris) != null) {
						translation = true;
					} else {
						// 4) Selection multiple avec le drag de la souris
						selectionMultiple = true;
						ptSelectionMultiple = new UnPoint(e.getX(), e.getY());
					}
				}
				control = false;
				repaint();
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseClicked(MouseEvent e) {
			}
		};

		// Listener qui permet de modifier le curseur de la souris
		MouseMotionListener apparenceSouris = new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Stub de la methode genere automatiquement
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// Changement de l'apparence de la souris pour deplacer ou pour
				// modifier une figure
				// TODO Stub de la methode genere automatiquement
				if (figVoisine(new UnPoint(e.getX(), e.getY())) != null
						&& boutons.getSelectionner().isSelected()
						&& pointVoisin(new UnPoint(e.getX(), e.getY())) == null) {
					setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
				} else if (pointVoisin(new UnPoint(e.getX(), e.getY())) != null
						&& boutons.getSelectionner().isSelected()) {
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				} else {
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}

				// pour quand on est en train de construire une figure
				ptSouris.x = e.getX();
				ptSouris.y = e.getY();
				if (listePoints.size() > 0) {
					repaint();
				}
			}
		};

		MouseMotionListener mml = new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
				if (modifFigure) {
					if (annule) {
						ajouterEtat();
						annule = false;
					}
					// Si le point appartient a un losange, il faut garder tous
					// les cotes de la meme longueur et les angles deux a deux
					// egaux
					if (figModifiee instanceof Losange) {
						((Losange) figModifiee).modifierTaille(ptFigure,
								e.getX() - ptSouris.x, e.getY() - ptSouris.y);
					}
					// Si le point appartient a un carre, il faut garder tous
					// les cotes de la meme longueur
					else if (figModifiee instanceof Carre) {
						((Carre) figModifiee).modifierTaille(ptFigure, e.getY()
								- ptSouris.y);
					}
					// Si le point appartient a un rectangle, il faut garder la
					// forme rectangulaire
					else if (figModifiee instanceof Rectangle) {
						((Rectangle) figModifiee).modifierTaille(ptFigure,
								e.getX() - ptSouris.x, e.getY() - ptSouris.y);
					}
					// Si le point appartient a une ellipse, il faut garder la
					// forme rectangulaire
					else if (figModifiee instanceof Ellipse) {
						((Ellipse) figModifiee).modifierTaille(ptFigure,
								e.getX() - ptSouris.x, e.getY() - ptSouris.y);
					} else {
						ptFigure.deplacerPt(e.getX() - ptSouris.x, e.getY()
								- ptSouris.y);
					}
				} else if (translation) {
					if (annule) {
						ajouterEtat();
						annule = false;
					}
					for (int i = 0; i < listeFigSelectionnees.size(); i++) {
						listeFigSelectionnees.get(i).translater(
								e.getX() - ptSouris.x, e.getY() - ptSouris.y);
					}
				}
				ptSouris.move(e.getX(), e.getY());
				repaint();
			}

			public void mouseMoved(MouseEvent e) {
				absSouris = e.getX();
				ordSouris = e.getY();
			}

		};

		addMouseListener(ml);
		addMouseMotionListener(mml);
		addMouseMotionListener(apparenceSouris);

	}

	// ACCESSEURS
	public Color getCouleur() {
		return couleur;
	}

	public void setCouleur(Color c) {
		couleur = c;
	}

	public void setControl(boolean control) {
		this.control = control;
	}

	public void setEpaisseur(int e) {
		this.epaisseur = e;
	}

	// AUTRES METHODES
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);

		// Dessin du tableau de figures
		if (nbFigures > 0) {
			for (int i = 0; i < nbFigures; i++) {
				// Modification de l'epaisseur
				g2d.setStroke(new BasicStroke(tabFigures[i].getEpaisseur()));
				g2d.setColor(tabFigures[i].getCouleur());
				UnPoint[] positions = tabFigures[i].getTabMemo();

				// Cas du cercle et de l'ellipse
				if (tabFigures[i] instanceof Cercle) {
					// Cas de l'ellipse
					if (tabFigures[i] instanceof Ellipse) {
						int longueur = Math
								.abs(positions[1].x - positions[0].x);
						int hauteur = Math.abs(positions[1].y - positions[0].y);
						// Si l'ellipse est pleine
						if (tabFigures[i].getPlein())
							if (positions[1].x > positions[0].x) {
								// 1er point en haut a gauche, 2nd en bas a
								// droite
								if (positions[1].y > positions[0].y)
									g2d.fillOval(positions[0].x,
											positions[0].y, longueur, hauteur);
								// 1er point en bas a gauche, 2nd en haut a
								// droite
								else
									g2d.fillOval(positions[0].x,
											positions[1].y, longueur, hauteur);
							} else {
								// 1er point en haut a droite, 2nd en bas a
								// gauche
								if (positions[1].y > positions[0].y)
									g2d.fillOval(positions[1].x,
											positions[0].y, longueur, hauteur);
								// 1er point en bas a droite, 2nd en haut a
								// gauche
								else
									g2d.fillOval(positions[1].x,
											positions[1].y, longueur, hauteur);
							}
						// Si l'ellipse est vide
						else if (positions[1].x > positions[0].x) {
							// 1er point en haut a gauche, 2nd en bas a droite
							if (positions[1].y > positions[0].y)
								g2d.drawOval(positions[0].x, positions[0].y,
										longueur, hauteur);
							// 1er point en bas a gauche, 2nd en haut a droite
							else
								g2d.drawOval(positions[0].x, positions[1].y,
										longueur, hauteur);
						} // 1er point en haut a droite, 2nd en bas a gauche
						else {
							if (positions[1].y > positions[0].y)
								g2d.drawOval(positions[1].x, positions[0].y,
										longueur, hauteur);
							// 1er point en bas a droite, 2nd en haut a gauche
							else
								g2d.drawOval(positions[1].x, positions[1].y,
										longueur, hauteur);
						}
					}
					// Cas du cercle
					else {
						// Calcul du rayon et dessin
						int rayon = positions[0].dist(positions[1]);
						if (tabFigures[i].getPlein())
							g2d.fillOval(positions[0].x - rayon, positions[0].y
									- rayon, rayon * 2, rayon * 2);
						else
							g2d.drawOval(positions[0].x - rayon, positions[0].y
									- rayon, rayon * 2, rayon * 2);
					}
				}

				// Cas des polygones
				if (tabFigures[i] instanceof Polygone) {
					int nbSommets = tabFigures[i].getNbMemo();
					// Cas du trait
					if (nbSommets == 2) {
						g2d.drawLine(positions[0].x, positions[0].y,
								positions[1].x, positions[1].y);
					} else {
						// Cas des polygones remplis
						if (tabFigures[i].getPlein()) {
							// Cas du rectangle
							if (tabFigures[i] instanceof Rectangle) {
								int longueur = Math.abs(positions[2].x
										- positions[0].x);
								int hauteur = Math.abs(positions[2].y
										- positions[0].y);
								if (positions[2].x > positions[0].x) {
									// 1er point de saisie en haut a gauche, 2nd
									// en bas a droite
									if (positions[2].y > positions[0].y)
										g2d.fillRect(positions[0].x,
												positions[0].y, longueur,
												hauteur);
									// 1er point en bas a gauche, 2nd en haut a
									// droite
									else
										g2d.fillRect(positions[0].x,
												positions[2].y, longueur,
												hauteur);
								} // 1er point en haut a droite, 2nd en bas a
									// gauche
								else {
									if (positions[2].y > positions[0].y)
										g2d.fillRect(positions[2].x,
												positions[0].y, longueur,
												hauteur);
									// 1er point en bas a droite, 2nd en haut a
									// gauche
									else
										g2d.fillRect(positions[2].x,
												positions[2].y, longueur,
												hauteur);
								}
							}
							// Cas des autres polygones
							else {
								int[] tabX = new int[nbSommets];
								int[] tabY = new int[nbSommets];
								for (int j = 0; j < nbSommets; j++) {
									tabX[j] = positions[j].x;
									tabY[j] = positions[j].y;
								}
								g2d.fillPolygon(tabX, tabY, nbSommets);
							}
							// Cas des polygones non remplis
						} else {
							for (int j = 0; j < nbSommets; j++) {
								dessinLigne(g2d, positions[j],
										positions[(j + 1) % nbSommets]);
							}
						}
					}
				}
			}

			// Si la figure est selectionnee, on dessine les points de selection
			for (int i = 0; i < listeFigSelectionnees.size(); i++) {
				if (listeFigSelectionnees.get(i).getPlein()
						&& couleur.getRed() < 150 && couleur.getBlue() < 150
						&& couleur.getGreen() < 150)
					g2d.setColor(Color.YELLOW);
				else
					g2d.setColor(Color.BLACK);
				for (int j = 0; j < listeFigSelectionnees.get(i).getNbMemo(); j++) {

					// utilise a des fins de debuggages
					// colore les points de selection des figures
					/*
					 * switch (j) { case 0 : g2d.setColor(Color.black);break;
					 * case 1 : g2d.setColor(Color.red);break; case 2 :
					 * g2d.setColor(Color.blue);break; case 3 :
					 * g2d.setColor(Color.green);break; }
					 */

					// Dessin des carres de selection
					g2d.setStroke(new BasicStroke(1));
					g2d.drawRect(listeFigSelectionnees.get(i).getTabMemo()[j].x
							- 3 - listeFigSelectionnees.get(i).getEpaisseur()
							+ 1, listeFigSelectionnees.get(i).getTabMemo()[j].y
							- 3 - listeFigSelectionnees.get(i).getEpaisseur()
							+ 1, 6 + listeFigSelectionnees.get(i)
							.getEpaisseur() - 1,
							6 + listeFigSelectionnees.get(i).getEpaisseur() - 1);
				}
			}
		}

		// Affichage des points d'une ArrayList si existante
		g2d.setColor(Color.black);
		if (!listePoints.isEmpty()) {
			g2d.setStroke(new BasicStroke(1));
			if (boutons.getNumFigCourante() == 6) {
				g2d.fillRect(listePoints.get(0).x - 3,
						listePoints.get(0).y - 3, 6, 6);
				for (int i = 0; i < listePoints.size(); i++)
					g2d.drawRect(listePoints.get(i).x - 3,
							listePoints.get(i).y - 3, 6, 6);
			} else
				for (int j = 0; j < listePoints.size(); j++) {
					g2d.drawRect(listePoints.get(j).x - 3,
							listePoints.get(j).y - 3, 6, 6);
				}
		}

		// Activation ou non de la fonction supprimer,dupliquer, remplir, ...
		if (!listeFigSelectionnees.isEmpty()) {
			menuD.getSupprimer().setEnabled(true);
			boutons.getSupprimer().setEnabled(true);
			boutons.getRemplir().setEnabled(true);
			menu.getDupliquer().setEnabled(true);
			menu.getSupprimer().setEnabled(true);
			boutons.getRotationDroite().setEnabled(true);
			boutons.getRotationGauche().setEnabled(true);
			menu.getCouper().setEnabled(true);
			menu.getCopier().setEnabled(true);
			menuD.getCouper().setEnabled(true);
			menuD.getCopier().setEnabled(true);
		} else {
			menuD.getSupprimer().setEnabled(false);
			boutons.getSupprimer().setEnabled(false);
			boutons.getRemplir().setEnabled(false);
			menu.getDupliquer().setEnabled(false);
			menu.getSupprimer().setEnabled(false);
			boutons.getRotationDroite().setEnabled(false);
			boutons.getRotationGauche().setEnabled(false);
			menu.getCouper().setEnabled(false);
			menu.getCopier().setEnabled(false);
			menuD.getCouper().setEnabled(false);
			menuD.getCopier().setEnabled(false);
		}

		if (listeTampon.isEmpty()) {
			menu.getColler().setEnabled(false);
			menuD.getColler().setEnabled(false);
		} else {
			menu.getColler().setEnabled(true);
			menuD.getColler().setEnabled(true);
		}

		if (listeEtats.isEmpty()) {
			boutons.getAnnuler().setEnabled(false);
			menuD.getAnnuler().setEnabled(false);
			menu.getAnnuler().setEnabled(false);
		} else {
			boutons.getAnnuler().setEnabled(true);
			menuD.getAnnuler().setEnabled(true);
			menu.getAnnuler().setEnabled(true);
		}

		// si une figure est en cours de construction
		if (listePoints.size() > 0) {
			// recuperation de la couleur active
			g2d.setColor(couleur);

			// recuperation de l'epaisseur de trait active
			g2d.setStroke(new BasicStroke(epaisseur));

			// calcul de variables utiles pour le pre-rendu du carre, du
			// rectangle, de l'ellipse
			int longueur = ptSouris.x - listePoints.get(0).x;
			if (longueur < 0) {
				longueur = -longueur;
			}
			int hauteur = ptSouris.y - listePoints.get(0).y;
			if (hauteur < 0) {
				hauteur = -hauteur;
			}

			switch (boutons.getNumFigCourante()) {
			case 1:// si la figure est un cercle
				g2d.drawOval(
						listePoints.get(0).x
								- listePoints.get(0).dist(
										new UnPoint(ptSouris.x, ptSouris.y)),
						listePoints.get(0).y
								- listePoints.get(0).dist(
										new UnPoint(ptSouris.x, ptSouris.y)),
						listePoints.get(0).dist(
								new UnPoint(ptSouris.x, ptSouris.y)) * 2,
						listePoints.get(0).dist(
								new UnPoint(ptSouris.x, ptSouris.y)) * 2);
				break;

			case 2: // si la figure est un rectangle
				// si le curseur de la souris est en dessous du point d'origine
				if (ptSouris.y > listePoints.get(0).y) {
					// si le curseur de la souris est a droite du point
					// d'origine
					if (ptSouris.x > listePoints.get(0).x)
						g2d.drawRect(listePoints.get(0).x,
								listePoints.get(0).y, longueur, hauteur);
					// si le curseur de la souris est a gauche du point
					// d'origine
					else
						g2d.drawRect(listePoints.get(0).x - longueur,
								listePoints.get(0).y, longueur, hauteur);
				}
				// si le curseur de la souris est au dessus du point d'origine
				else {
					// si le curseur de la souris est a droite du point
					// d'origine
					if (ptSouris.x > listePoints.get(0).x)
						g2d.drawRect(listePoints.get(0).x, listePoints.get(0).y
								- hauteur, longueur, hauteur);
					// si le curseur de la souris est a gauche du point
					// d'origine
					else
						g2d.drawRect(listePoints.get(0).x - longueur,
								listePoints.get(0).y - hauteur, longueur,
								hauteur);
				}
				break;

			case 3: // si la figure est un carre
				// si le curseur de la souris est en dessous du point d'origine
				if (ptSouris.y > listePoints.get(0).y) {
					// si le curseur de la souris est a droite du point
					// d'origine
					if (ptSouris.x > listePoints.get(0).x)
						g2d.drawRect(listePoints.get(0).x,
								listePoints.get(0).y, longueur, longueur);
					// si le curseur de la souris est a gauche du point
					// d'origine
					else
						g2d.drawRect(listePoints.get(0).x - longueur,
								listePoints.get(0).y, longueur, longueur);
				}
				// si le curseur de la souris est au dessus du point d'origine
				else {
					// si le curseur de la souris est a droite du point
					// d'origine
					if (ptSouris.x > listePoints.get(0).x)
						g2d.drawRect(listePoints.get(0).x, listePoints.get(0).y
								- longueur, longueur, longueur);
					// si le curseur de la souris est a gauche du point
					// d'origine
					else
						g2d.drawRect(listePoints.get(0).x - longueur,
								listePoints.get(0).y - longueur, longueur,
								longueur);
				}
				break;

			case 4: // si la figure est un triangle
				if (listePoints.size() == 1) {
					g2d.drawLine(listePoints.get(0).x, listePoints.get(0).y,
							ptSouris.x, ptSouris.y);
				} else {
					g2d.drawLine(listePoints.get(0).x, listePoints.get(0).y,
							listePoints.get(1).x, listePoints.get(1).y);
					g2d.drawLine(listePoints.get(1).x, listePoints.get(1).y,
							ptSouris.x, ptSouris.y);
					g2d.drawLine(listePoints.get(0).x, listePoints.get(0).y,
							ptSouris.x, ptSouris.y);
				}
				break;

			case 5: // si la figure est un ovale
				// si le curseur de la souris est en dessous du point d'origine
				if (ptSouris.y > listePoints.get(0).y) {
					// si le curseur de la souris est a droite du point
					// d'origine
					if (ptSouris.x > listePoints.get(0).x)
						g2d.drawOval(listePoints.get(0).x,
								listePoints.get(0).y, longueur, hauteur);
					// si le curseur de la souris est a gauche du point
					// d'origine
					else
						g2d.drawOval(listePoints.get(0).x - longueur,
								listePoints.get(0).y, longueur, hauteur);
				}
				// si le curseur de la souris est au dessus du point d'origine
				else {
					// si le curseur de la souris est a droite du point
					// d'origine
					if (ptSouris.x > listePoints.get(0).x)
						g2d.drawOval(listePoints.get(0).x, listePoints.get(0).y
								- hauteur, longueur, hauteur);
					// si le curseur de la souris est a gauche du point
					// d'origine
					else
						g2d.drawOval(listePoints.get(0).x - longueur,
								listePoints.get(0).y - hauteur, longueur,
								hauteur);
				}
				break;

			case 6: // si la figure est un polygone
				int i;
				for (i = 0; i < listePoints.size() - 1; i++) {
					g2d.drawLine(listePoints.get(i).x, listePoints.get(i).y,
							listePoints.get(i + 1).x, listePoints.get(i + 1).y);
				}
				g2d.drawLine(listePoints.get(i).x, listePoints.get(i).y,
						ptSouris.x, ptSouris.y);
				break;

			case 7: // si la figure est un losange
				g2d.drawLine(listePoints.get(0).x - longueur,
						listePoints.get(0).y, listePoints.get(0).x,
						listePoints.get(0).y - hauteur);
				g2d.drawLine(listePoints.get(0).x - longueur,
						listePoints.get(0).y, listePoints.get(0).x,
						listePoints.get(0).y + hauteur);
				g2d.drawLine(listePoints.get(0).x + longueur,
						listePoints.get(0).y, listePoints.get(0).x,
						listePoints.get(0).y - hauteur);
				g2d.drawLine(listePoints.get(0).x + longueur,
						listePoints.get(0).y, listePoints.get(0).x,
						listePoints.get(0).y + hauteur);
				break;

			case 8: // si la figure est un trait
				g2d.drawLine(listePoints.get(0).x, listePoints.get(0).y,
						ptSouris.x, ptSouris.y);
				break;
			}
		}

		// si on est en mode selection multiple avec le drag, on dessine un
		// rectangle en pointillés
		if (selectionMultiple) {
			float[] dash = { 5.0f, 4.0f };
			g2d.setColor(Color.GRAY);
			g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_SQUARE,
					BasicStroke.JOIN_MITER, 10.0f, dash, .0f));
			if (ptSelectionMultiple.x < ptSouris.x
					&& ptSelectionMultiple.y < ptSouris.y)
				g2d.drawRect(ptSelectionMultiple.x, ptSelectionMultiple.y,
						ptSouris.x - ptSelectionMultiple.x, ptSouris.y
								- ptSelectionMultiple.y);
			else if (ptSelectionMultiple.x < ptSouris.x
					&& ptSelectionMultiple.y > ptSouris.y) {
				g2d.drawRect(ptSelectionMultiple.x, ptSouris.y, ptSouris.x
						- ptSelectionMultiple.x,
						Math.abs(ptSouris.y - ptSelectionMultiple.y));
			} else if (ptSelectionMultiple.x > ptSouris.x
					&& ptSelectionMultiple.y < ptSouris.y) {
				g2d.drawRect(ptSouris.x, ptSelectionMultiple.y,
						Math.abs(ptSouris.x - ptSelectionMultiple.x),
						Math.abs(ptSouris.y - ptSelectionMultiple.y));
			} else
				g2d.drawRect(ptSouris.x, ptSouris.y,
						Math.abs(ptSouris.x - ptSelectionMultiple.x),
						Math.abs(ptSouris.y - ptSelectionMultiple.y));
		}
	}

	/**
	 * change le booleen rempli de la figure si elle est selectionnee
	 */
	public void remplir() {
		ajouterEtat();
		for (int i = 0; i < listeFigSelectionnees.size(); i++) {
			listeFigSelectionnees.get(i).remplir();
		}
		repaint();
	}

	/**
	 * supprime la figure si elle est selectionnee
	 */
	public void supprimer() {
		ajouterEtat();
		int suppr = 0;
		for (int i = 0; i < listeFigSelectionnees.size(); i++) {
			boolean trouve = false;
			for (int j = 0; j < nbFigures && !trouve; j++) {
				if (tabFigures[j] == listeFigSelectionnees.get(i)) {
					suppr = j;
					trouve = true;
				}
			}
			if (trouve) {
				for (int j = suppr; j < nbFigures - 1; j++) {
					tabFigures[j] = tabFigures[j + 1];
				}
				nbFigures--;
			}
		}
		listeFigSelectionnees.clear();
		repaint();
	}

	// Methode de dessin d'une ligne avec 2 parametres de type UnPoint
	public void dessinLigne(Graphics2D g2d, UnPoint p1, UnPoint p2) {
		g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
	}

	// Methode qui teste si un point se trouve a� moins d'une certaine distance
	// d'un autre point
	public boolean estVoisin(int distance, UnPoint p1, UnPoint p2) {
		boolean res = false;
		if (p1.dist(p2) < distance)
			res = true;
		return res;
	}

	// Fonction qui renvoie la premiere figure situee au voisinage d'un point
	// ou null sinon
	public FigureGeom figVoisine(UnPoint pt) {
		FigureGeom res = null;
		boolean trouve = false;
		int i = 0;

		// Boucle sur toutes les figures pour savoir si l'une d'elle
		// possede un segment a proximite du point courant
		while (i < nbFigures && !trouve) {
			// Si la figure est un polygone
			if (tabFigures[i] instanceof Polygone) {
				int nbSommets;
				nbSommets = tabFigures[i].getNbMemo();
				for (int j = 0; j < tabFigures[i].getNbMemo(); j++) {
					// Si c'est le cas, selection de cette figure
					if (pt.estVoisinSegment(MARGE_SELECTION_POLY,
							tabFigures[i].getTabMemo()[j],
							tabFigures[i].getTabMemo()[(j + 1) % nbSommets])) {
						res = tabFigures[i];
						trouve = true;
					}
				}
			}
			// Si la figure est un cercle
			if (tabFigures[i] instanceof Cercle
					&& !(tabFigures[i] instanceof Ellipse)) {
				int rayonCercle = tabFigures[i].getTabMemo()[0]
						.dist(tabFigures[i].getTabMemo()[1]);
				int rayonPtCourant = pt.dist(tabFigures[i].getTabMemo()[0]);
				if (Math.abs(rayonCercle - rayonPtCourant) < MARGE_SELECTION_CERCLE) {
					res = tabFigures[i];
					trouve = true;
				}
			}

			// Si la figure est une ellipse
			if (tabFigures[i] instanceof Ellipse) {
				// Calcul des dimensions de l'ellipse
				int rayonX = Math.abs(tabFigures[i].getTabMemo()[0].x
						- tabFigures[i].getTabMemo()[1].x) / 2;
				int rayonY = Math.abs(tabFigures[i].getTabMemo()[0].y
						- tabFigures[i].getTabMemo()[1].y) / 2;
				// Calcul des coordonnées du centre
				int centreX = (tabFigures[i].getTabMemo()[0].x + tabFigures[i]
						.getTabMemo()[1].x) / 2;
				int centreY = (tabFigures[i].getTabMemo()[0].y + tabFigures[i]
						.getTabMemo()[1].y) / 2;
				// Calcul des coordonnées des foyers
				int foyer1X;
				int foyer1Y;
				int foyer2X;
				int foyer2Y;
				double c = Math.sqrt(Math
						.abs(rayonX * rayonX - rayonY * rayonY));
				if (rayonX > rayonY) {
					foyer1X = centreX - (int) c;
					foyer1Y = centreY;
					foyer2X = centreX + (int) c;
					foyer2Y = centreY;
				} else {
					foyer1X = centreX;
					foyer1Y = centreY - (int) c;
					foyer2X = centreX;
					foyer2Y = centreY + (int) c;
				}
				// Calcul de la somme des distances du point aux 2 foyers
				double distanceF1 = pt.distance(foyer1X, foyer1Y);
				double distanceF2 = pt.distance(foyer2X, foyer2Y);
				int sommeDist = (int) (distanceF1 + distanceF2);
				// Test de voisinage
				if (rayonX > rayonY) {
					if (Math.abs(sommeDist - rayonX * 2) < MARGE_SELECTION_ELLIPSE) {
						trouve = true;
						res = tabFigures[i];
					}
				} else {
					if (Math.abs(sommeDist - rayonY * 2) < MARGE_SELECTION_ELLIPSE) {
						trouve = true;
						res = tabFigures[i];
					}
				}
			}
			i++;
		}
		return res;
	}

	// Fonction qui renvoie le premier point d'une figure situee au voisinage
	// d'un point, ou null sinon
	public UnPoint pointVoisin(UnPoint pt) {
		UnPoint res = null;
		boolean trouve = false;
		int i = 0;

		// Boucle sur toutes les figures pour savoir si l'une d'elle
		// possede un segment a proximite du point courant
		while (i < nbFigures && !trouve) {
			// Si la figure est un polygone
			if (tabFigures[i] instanceof Polygone
					|| tabFigures[i] instanceof Ellipse) {
				for (int j = 0; j < tabFigures[i].getNbMemo(); j++) {
					// Si c'est le cas, selection de cette figure
					if (estVoisin(MARGE_SELECTION_POINT, pt,
							tabFigures[i].getTabMemo()[j])) {
						res = tabFigures[i].getTabMemo()[j];
						trouve = true;
					}
				}
			}
			// Si la figure est un cercle
			if (tabFigures[i] instanceof Cercle
					&& !(tabFigures[i] instanceof Ellipse)) {
				if (estVoisin(MARGE_SELECTION_POINT, pt,
						tabFigures[i].getTabMemo()[1])) {
					res = tabFigures[i].getTabMemo()[1];
					trouve = true;
				}
			}
			i++;
		}
		return res;
	}

	// Fonction qui permet d'exporter une image
	public void exporter(String format) {
		ArrayList<FigureGeom> tampon = new ArrayList<FigureGeom>();
		tampon = (ArrayList<FigureGeom>) listeFigSelectionnees.clone();
		listeFigSelectionnees.clear();
		JFileChooser filechoose = new JFileChooser();
		/* ouvrir la boite de dialogue dans repertoire courant */
		filechoose.setCurrentDirectory(new File("."));

		/* nom de la boite de dialogue */
		filechoose.setDialogTitle("Exporter une image");

		/* pour afficher seulement les repertoires */
		filechoose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		/* Le bouton pour valider */
		String approve = new String("Exporter");
		int resultatEnregistrer = filechoose.showDialog(filechoose, approve);

		/* Si l'utilisateur clique sur le bouton Exporter */
		if (resultatEnregistrer == JFileChooser.APPROVE_OPTION) {
			/* pour avoir le chemin absolu */
			String chemin = filechoose.getSelectedFile().getAbsolutePath();

			/*
			 * on enregistre le fichier dans le repertoire desire avec pour nom
			 * image + date en millisecondes
			 */
			GregorianCalendar intCal = new GregorianCalendar();
			long tmp = intCal.getTimeInMillis();
			entete.setVisible(false);
			BufferedImage img = new BufferedImage(this.getWidth(),
					this.getHeight(), BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = img.createGraphics();
			this.paint(g2);
			try {
				ImageIO.write(img, format, new File(chemin, "Image" + tmp + "."
						+ format));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			entete.setVisible(true);
			listeFigSelectionnees = (ArrayList<FigureGeom>) tampon.clone();
		}
	}

	/**
	 * Methode permettant de changer la couleur
	 * 
	 * @param c
	 *            la nouvelle couleur
	 */
	public void changeCouleur(Color c) {
		if (!listeFigSelectionnees.isEmpty())
			ajouterEtat();
		couleur = c;
		for (int i = 0; i < listeFigSelectionnees.size(); i++) {
			listeFigSelectionnees.get(i).setCouleur(c);
		}
		repaint();
	}

	/**
	 * Methode permettant de changer l'epaisseur
	 * 
	 * @param e
	 *            la nouvelle epaisseur
	 */
	public void changeEpaisseur(int e) {
		if (!listeFigSelectionnees.isEmpty())
			ajouterEtat();
		epaisseur = e;
		for (int i = 0; i < listeFigSelectionnees.size(); i++) {
			listeFigSelectionnees.get(i).setEpaisseur(e);
		}
		repaint();
	}

	/**
	 * Methode permettant de vider listePoints
	 */
	public void viderPoints() {
		if (nbClics != 0) {
			nbClics = 0;
			nbPoints = 0;
			listePoints.clear();
			repaint();
		}
	}

	/**
	 * Methode permettant de dupliquer une ou plusieurs figures
	 */
	public void dupliquer() {
		if (!listeFigSelectionnees.isEmpty()) {
			ajouterEtat();
			for (int i = 0; i < listeFigSelectionnees.size(); i++) {
				if (listeFigSelectionnees.get(i) instanceof Cercle)
					tabFigures[nbFigures] = new Cercle(
							(Cercle) listeFigSelectionnees.get(i));
				if (listeFigSelectionnees.get(i) instanceof Rectangle)
					tabFigures[nbFigures] = new Rectangle(
							(Rectangle) listeFigSelectionnees.get(i));
				if (listeFigSelectionnees.get(i) instanceof Polygone)
					tabFigures[nbFigures] = new Polygone(
							(Polygone) listeFigSelectionnees.get(i));
				if (listeFigSelectionnees.get(i) instanceof Triangle)
					tabFigures[nbFigures] = new Triangle(
							(Triangle) listeFigSelectionnees.get(i));
				nbFigures++;
			}
		}
		repaint();
	}

	/**
	 * Methode permettant de selectionner toutes les figures
	 */
	public void toutSelectionner() {
		listeFigSelectionnees.clear();
		for (int i = 0; i < nbFigures; i++) {
			listeFigSelectionnees.add(tabFigures[i]);
			repaint();
		}
	}

	/**
	 * Methode permettant d'ajouter un etat a� listeEtats
	 */
	public void ajouterEtat() {
		FigureGeom tmp[] = new FigureGeom[nbFigures];
		for (int i = 0; i < nbFigures; i++) {
			try {
				tmp[i] = tabFigures[i].clone();
			} catch (CloneNotSupportedException e) {
				// TODO Bloc catch genere automatiquement
				e.printStackTrace();
			}
		}
		if (listeEtats.size() >= MARGE_ANNULE) {
			listeEtats.remove(0);
		}
		listeEtats.add(tmp);
	}

	/**
	 * Methode permettant d'annuler une action
	 */
	public void annuler() {
		viderPoints();
		listeFigSelectionnees.clear();
		int n = listeEtats.size() - 1;
		nbFigures = listeEtats.get(n).length;
		for (int i = 0; i < nbFigures; i++) {
			tabFigures[i] = listeEtats.get(n)[i];
		}
		repaint();
		listeEtats.remove(n);
	}

	/**
	 * Methode permettant d'effectuer la rotation d'une ou plusieurs figures
	 * 
	 * @param r
	 *            1 pour la droite, 2 pour la gauche
	 */
	public void rotation(int r) {
		ajouterEtat();
		for (int i = 0; i < listeFigSelectionnees.size(); i++) {
			listeFigSelectionnees.get(i).rotation(r);
		}
		repaint();
	}

	/**
	 * Methode permettant de copier une ou plusieurs figures
	 */
	public void copier() {
		repaint();
		viderPoints();
		listeTampon.clear();
		for (int i = 0; i < listeFigSelectionnees.size(); i++) {
			try {
				listeTampon.add(listeFigSelectionnees.get(i).clone());
			} catch (CloneNotSupportedException e) {
				// TODO Bloc catch genere automatiquement
				e.printStackTrace();
			}
		}
	}

	/**
	 * Methode permettant de couper une ou plusieurs figures
	 */
	public void couper() {
		viderPoints();
		copier();
		supprimer();
	}

	/**
	 * Methode qui permet de coller une figure copiee soit a� l'emplacement de
	 * la souris soit a� proximite de la figure copiee
	 * 
	 * @param a
	 */
	public void coller(int a) {
		viderPoints();
		ajouterEtat();
		int distX = absSouris - listeTampon.get(0).getTabMemo()[0].x;
		int distY = ordSouris - listeTampon.get(0).getTabMemo()[0].y;
		for (int i = 0; i < listeTampon.size(); i++) {
			if (a == 1)
				listeTampon.get(i).translater(distX, distY);
			else
				listeTampon.get(i).translater(10, 10);

			try {
				tabFigures[nbFigures] = listeTampon.get(i).clone();
			} catch (CloneNotSupportedException e) {
				// TODO Bloc catch genere automatiquement
				e.printStackTrace();
			}
			nbFigures++;
		}
		repaint();
	}

	/**
	 * Methode qui permet de reinitiliser un nouveau dessin
	 */
	public void nouveau() {
		tabFigures = new FigureGeom[MAXTAILLE];
		listeFigSelectionnees.clear();
		viderPoints();
		nbFigures = 0;
		listeEtats.clear();
		couleur = Color.black;
		boutons.setCouleurCourante(Color.black);
		epaisseur = 1;
		boutons.recupSize().setIcon(null);
		boutons.recupSize().setText("Taille");
		repaint();
	}
}
