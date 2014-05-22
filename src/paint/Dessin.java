/**
 * 

 * @authors Nicolas Gambarini, Sarah Lequeuvre
 *
 */

package paint;

import java.awt.*;
import java.awt.event.ActionEvent;
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

public class Dessin extends JPanel {

	// ATTRIBUTS
	private final static int MAXTAILLE = 1000000;
	private final int MARGE_SELECTION_POLY = 5;
	private final int MARGE_SELECTION_CERCLE = 8;
	private final int MARGE_SELECTION_POINT = 12;
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
	private int departTranslation = 0;
	private boolean control;
	private UnMenu menu = new UnMenu(this);
	private boolean translation;
	private boolean modifFigure;
	private UnPoint ptSouris = new UnPoint(0,0);
	private UnPoint ptFigure;
	private FigureGeom figModifiee;
	private boolean annule=true;
	private ArrayList<FigureGeom> listeTampon = new ArrayList<FigureGeom>(); 
	private int absSouris=-1;
	private int ordSouris=-1;	
	private JPanel entete = new JPanel();

	// CONSTRUCTEURS
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
				annule=true;
				if(SwingUtilities.isRightMouseButton(e)) {
					absSouris=e.getX();
					ordSouris=e.getY();
				}
			}

			public void mousePressed(MouseEvent e) {
				// On ne dessine que si un bouton dessin est appuy�
				if (boutons.getDessiner()) {
					Graphics g = getGraphics();
					g.setColor(couleur);
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
						case(3) :
						// Carre
							nbPoints = 2;
						  break;
						case (4):
							// Triangle
							nbPoints = 3;
							break;
						case (5):
							// Ovale
							//nbPoints = 2;
							//break;
							
							// tabFigures[nbFigures] = new Ovale();
							// break;
							// case(7) :
							// tabFigures[nbFigures] = new Losange();
							// break;
						case(8) :
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
						// la figure, on incrémente une ArrayList
						if (nbClics < nbPoints - 1) {
							listePoints.add(new UnPoint(e.getX(), e.getY()));
							nbClics++;
							
							
						}
						// Dernier clic : on instancie la figure, on supprime
						// l'ArrayList et on incrémente le nombre de figures
						else {
							listePoints.add(new UnPoint(e.getX(), e.getY()));
							ajouterEtat();
							switch (boutons.getNumFigCourante()) {
							case (1):
								// Cercle
								tabFigures[nbFigures] = new Cercle(listePoints);
								break;
							case (2):
								// Rectangle
								tabFigures[nbFigures] = new Rectangle(listePoints);
								break;
							case (3) :
								// Carre
							  tabFigures[nbFigures] = new Carre(listePoints);
							  break;
							case (4):
								// Triangle
								tabFigures[nbFigures] = new Triangle(listePoints);
								break;
							case (5):
								// Ovale
								// tabFigures[nbFigures] = new Ovale(
								//		listePoints);
								//break;
								// case(7) :
								// tabFigures[nbFigures] = new Losange();
								// break;
							case(8) :
								tabFigures[nbFigures] = new Polygone(listePoints);
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
						// Le premier point est automatiquement ajoute à
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
							// Sinon, on incrémente l'ArrayList
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
					}
					else {
						listeFigSelectionnees.clear();
						translation = false;
					}
						
					// 2) Initialisation d'une modification de figure (couplage avec MouseDragged)
					if(pointVoisin(ptSouris) != null) {
						ptFigure = pointVoisin(ptSouris);
						figModifiee = figVoisine(ptFigure);
						modifFigure = true;
					}
					// 3) Initialisation d'une translation de figure(s) (couplage avec MouseDragged)
					else {
						if(figVoisine(ptSouris) != null) {
							translation = true;
							
						}
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
		//Listener qui permet de modifier le curseur de la souris
		
		MouseMotionListener apparenceSouris = new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Stub de la méthode généré automatiquement
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Stub de la méthode généré automatiquement
				if (figVoisine(new UnPoint(e.getX(),e.getY()))!=null&&boutons.getSelectionner().isSelected()
						&&pointVoisin(new UnPoint(e.getX(),e.getY()))==null) {
				    setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR) );	
				}
				else if(pointVoisin(new UnPoint(e.getX(),e.getY()))!=null&&boutons.getSelectionner().isSelected()){
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR) );	
				}
				else {
				    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR) );	
				}
				
			  //quand on est en train de construire une figure !
				ptSouris.x = e.getX();
				ptSouris.y = e.getY();
				if(listePoints.size() > 0)
				{
					repaint();
				}
			}
		};
		MouseMotionListener mml = new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
				if (modifFigure) {
					if (annule) {
						ajouterEtat();
						annule=false;
					}
				// Si le point appartient a un carre, il faut garder tous les cotes de la meme longueur
					if(figModifiee instanceof Carre) {
						((Carre)figModifiee).modifierTaille(ptFigure, e.getY() - ptSouris.y);
					}
				// Si le point appartient à un rectangle, il faut garder la forme rectangulaire
					else if(figModifiee instanceof Rectangle) {
						((Rectangle)figModifiee).modifierTaille(ptFigure, e.getX() - ptSouris.x,
								e.getY() - ptSouris.y);
					}
						else {
							ptFigure.deplacerPt(e.getX() - ptSouris.x,
									e.getY() - ptSouris.y);
							}
				} else {
					if(translation)
						if (annule) {
							ajouterEtat();
							annule=false;
						}
						for (int i = 0; i < listeFigSelectionnees.size(); i++) {
								listeFigSelectionnees.get(i).translater(
									e.getX() - ptSouris.x,
									e.getY() - ptSouris.y);
						}
				}
				ptSouris.move(e.getX(), e.getY());
				repaint();
			}

			public void mouseMoved(MouseEvent e) {
			}

		};

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
		addMouseMotionListener(mml);
		addMouseMotionListener(apparenceSouris);

	}

	// METHODES

	public Color getCouleur() {
		return couleur;
	}

	public void setCouleur(Color c) {
		couleur = c;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		
	  // si une figure est en cours de construction
		if(listePoints.size() > 0)
		{
			//recuperation de la couleur active
			g2d.setColor(couleur);
			
			//recuperation de l'epaisseur de trait active
			g2d.setStroke(new BasicStroke(epaisseur));
			
			//calcul de variables utiles pour le pre-rendu du carre, du rectangle
			int longueur = ptSouris.x - listePoints.get(0).x;
			if(longueur < 0) { longueur = -longueur; }
			int hauteur = ptSouris.y - listePoints.get(0).y;
			if(hauteur < 0) { hauteur = -hauteur; }
			
			switch (boutons.getNumFigCourante()) {
				case 1://si la figure est un cercle
					g2d.drawOval(listePoints.get(0).x - listePoints.get(0).dist(new UnPoint(ptSouris.x, ptSouris.y)), 
											 listePoints.get(0).y - listePoints.get(0).dist(new UnPoint(ptSouris.x, ptSouris.y)), 
											 listePoints.get(0).dist(new UnPoint(ptSouris.x, ptSouris.y))*2,
											 listePoints.get(0).dist(new UnPoint(ptSouris.x, ptSouris.y))*2);
					break;
				
				case 2 : //si la figure est un rectangle
					//si le curseur de la souris est en dessous du point d'origine
					if(ptSouris.y > listePoints.get(0).y)
					{
						//si le curseur de la souris est a droite du point d'origine
						if(ptSouris.x > listePoints.get(0).x)
							g2d.drawRect(listePoints.get(0).x, listePoints.get(0).y, longueur, hauteur);
					  //si le curseur de la souris est a gauche du point d'origine
						else
							g2d.drawRect(listePoints.get(0).x - longueur, listePoints.get(0).y , longueur, hauteur);
					}
				  //si le curseur de la souris est au dessus du point d'origine
					else
					{
					  //si le curseur de la souris est a droite du point d'origine
						if(ptSouris.x > listePoints.get(0).x)
							g2d.drawRect(listePoints.get(0).x , listePoints.get(0).y -hauteur, longueur, hauteur);
					  //si le curseur de la souris est a gauche du point d'origine
						else
							g2d.drawRect(listePoints.get(0).x - longueur,listePoints.get(0).y - hauteur, longueur, hauteur);
					}
					break;
					
				case 3 : //si la figure est un carre					
					//si le curseur de la souris est en dessous du point d'origine
					if(ptSouris.y > listePoints.get(0).y)
					{
						//si le curseur de la souris est a droite du point d'origine
						if(ptSouris.x > listePoints.get(0).x)
							g2d.drawRect(listePoints.get(0).x, listePoints.get(0).y, longueur, longueur);
					  //si le curseur de la souris est a gauche du point d'origine
						else
							g2d.drawRect(listePoints.get(0).x - longueur, listePoints.get(0).y , longueur, longueur);
					}
				  //si le curseur de la souris est au dessus du point d'origine
					else
					{
					  //si le curseur de la souris est a droite du point d'origine
						if(ptSouris.x > listePoints.get(0).x)
							g2d.drawRect(listePoints.get(0).x , listePoints.get(0).y -longueur, longueur, longueur);
					  //si le curseur de la souris est a gauche du point d'origine
						else
							g2d.drawRect(listePoints.get(0).x - longueur,listePoints.get(0).y - longueur, longueur, longueur);
					}
					break;
					
				case 4 : // si la figure est un triangle
					if(listePoints.size() == 1)
					{
						g2d.drawLine(listePoints.get(0).x, listePoints.get(0).y, ptSouris.x, ptSouris.y);
					}
					else
					{
						g2d.drawLine(listePoints.get(0).x, listePoints.get(0).y, listePoints.get(1).x, listePoints.get(1).y);
						g2d.drawLine(listePoints.get(1).x, listePoints.get(1).y, ptSouris.x, ptSouris.y);
						g2d.drawLine(listePoints.get(0).x, listePoints.get(0).y, ptSouris.x, ptSouris.y);
					}
					break;
					
				case 5 : // si la figure est un ovale
					
					break;
					
				case 6 : // si la figure est un polygone
					int i;
					for(i=0; i<listePoints.size()-1; i++)
					{
						g2d.drawLine(listePoints.get(i).x, listePoints.get(i).y,
												 listePoints.get(i+1).x, listePoints.get(i+1).y);
					}
					g2d.drawLine(listePoints.get(i).x, listePoints.get(i).y, ptSouris.x, ptSouris.y);
					break;
					
				case 7 : // si la figure est un losange
					
					break;
					
				case 8 : // si la figure est un trait
					g2d.drawLine(listePoints.get(0).x, listePoints.get(0).y, ptSouris.x, ptSouris.y);
					break;
					
			}
		}
		
		// Dessin du tableau de figures
		if (nbFigures > 0) {
			for (int i = 0; i < nbFigures; i++) {
				g2d.setStroke(new BasicStroke(tabFigures[i].getEpaisseur()));
				g2d.setColor(tabFigures[i].getCouleur());
				UnPoint[] positions = tabFigures[i].getTabMemo();

				// Cas du cercle
				if (tabFigures[i] instanceof Cercle) {
					// Calcul du rayon et dessin
					int rayon = positions[0].dist(positions[1]);
					if (tabFigures[i].getPlein())
						g2d.fillOval(positions[0].x - rayon, positions[0].y
								- rayon, rayon * 2, rayon * 2);
					else
						g2d.drawOval(positions[0].x - rayon, positions[0].y
								- rayon, rayon * 2, rayon * 2);
				}

				// Cas des polygones
				if (tabFigures[i] instanceof Polygone) {
					int nbSommets = tabFigures[i].getNbMemo();
					if(nbSommets == 2)
					{
						g2d.drawLine(positions[0].x, positions[0].y, positions[1].x, positions[1].y);
					}
					else
					{
						if (tabFigures[i].getPlein()) {
							if (tabFigures[i] instanceof Rectangle) {
								g2d.fillRect(positions[0].x, positions[0].y,
										positions[0].dist(positions[1]),
										positions[0].dist(positions[3]));
							} else {
								int[] tabX = new int[nbSommets];
								int[] tabY = new int[nbSommets];
								for (int j = 0; j < nbSommets; j++) {
									tabX[j] = positions[j].x;
									tabY[j] = positions[j].y;
								}
								g2d.fillPolygon(tabX, tabY, nbSommets);
							}
						} else {
							for (int j = 0; j < nbSommets; j++)
								dessinLigne(g2d, positions[j], positions[(j + 1)
										% nbSommets]);
						}
					}
				}
			}
			// Si la figure est selectionnee, on dessine les points de
			// selection (et pas les points de memorisation
			// je pense qu'il y a une faute dans l'enonce, a discuter)
			for (int i = 0; i < listeFigSelectionnees.size(); i++) {
				
				if (listeFigSelectionnees.get(i).getPlein()
						&& couleur.getRed() < 150 && couleur.getBlue() < 150
						&& couleur.getGreen() < 150)
					g2d.setColor(Color.YELLOW);
				else
					g2d.setColor(Color.BLACK);
				for (int j = 0; j < listeFigSelectionnees.get(i).getNbMemo(); j++) {
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

		// activation ou non de la fonction supprimer,dupliquer et remplir
		if (figureSelectionne()) {
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
		}
		
		else {
			menu.getColler().setEnabled(true);
			menuD.getColler().setEnabled(true);
		}
		if (listeEtats.isEmpty()) {
			boutons.getAnnuler().setEnabled(false);
			menuD.getAnnuler().setEnabled(false);
			menu.getAnnuler().setEnabled(false);
		}
		else {
			boutons.getAnnuler().setEnabled(true);
			menuD.getAnnuler().setEnabled(true);
			menu.getAnnuler().setEnabled(true);
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

	// Methode de dessin d'une ligne avec 2 paramètres de type UnPoint
	public void dessinLigne(Graphics2D g2d, UnPoint p1, UnPoint p2) {
		g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
	}

	// Methode qui teste si un point se trouve à moins d'une certaine distance
	// d'un autre point
	public boolean estVoisin(int distance, UnPoint p1, UnPoint p2) {
		boolean res = false;
		if (p1.dist(p2) < distance)
			res = true;
		return res;
	}

	// Fonction qui renvoie la première figure situee au voisinage d'un point
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
			if (tabFigures[i] instanceof Cercle) {
				int rayonCercle = tabFigures[i].getTabMemo()[0]
						.dist(tabFigures[i].getTabMemo()[1]);
				int rayonPtCourant = pt.dist(tabFigures[i].getTabMemo()[0]);
				if (Math.abs(rayonCercle - rayonPtCourant) < MARGE_SELECTION_CERCLE) {
					res = tabFigures[i];
					trouve = true;
				}
			}
			i++;
		}
		return res;
	}

	// Fonction qui renvoie le premier point d'une figure située au voisinage
	// d'un point, ou null sinon
	public UnPoint pointVoisin(UnPoint pt) {
		UnPoint res = null;
		boolean trouve = false;
		int i = 0;

		// Boucle sur toutes les figures pour savoir si l'une d'elle
		// possede un segment a proximite du point courant
		while (i < nbFigures && !trouve) {
			// Si la figure est un polygone
			if (tabFigures[i] instanceof Polygone) {
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
			if (tabFigures[i] instanceof Cercle) {
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
		tampon=(ArrayList<FigureGeom>) listeFigSelectionnees.clone();
		listeFigSelectionnees.clear();
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
			entete.setVisible(false);
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
			entete.setVisible(true);
			listeFigSelectionnees=(ArrayList<FigureGeom>) tampon.clone();

		}
	}

	public void changeCouleur(Color c) {
		if (!listeFigSelectionnees.isEmpty())
			ajouterEtat();
		couleur = c;
		for (int i = 0; i < listeFigSelectionnees.size(); i++) {
			listeFigSelectionnees.get(i).setCouleur(c);
		}
		repaint();
	}

	public void changeEpaisseur(int e) {
		if (!listeFigSelectionnees.isEmpty())
			ajouterEtat();
		epaisseur = e;
		for (int i = 0; i < listeFigSelectionnees.size(); i++) {
			listeFigSelectionnees.get(i).setEpaisseur(e);
		}
		repaint();
	}

	public boolean figureSelectionne() {
		if (listeFigSelectionnees.isEmpty())
			return false;
		else
			return true;
	}

	public void setControl(boolean control) {
		this.control = control;
	}

	public void setEpaisseur(int e) {
		this.epaisseur = e;
	}

	public void viderPoints() {
		if (nbClics != 0) {
			nbClics = 0;
			nbPoints = 0;
			listePoints.clear();
			repaint();
		}
	}

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
	
	public void toutSelectionner() {
		listeFigSelectionnees.clear();
		for (int i = 0; i < nbFigures; i++) {
			listeFigSelectionnees.add(tabFigures[i]);
			repaint();
		}
	}
	
	public void ajouterEtat() {
		FigureGeom tmp[] = new FigureGeom[nbFigures];
		for (int i=0;i<nbFigures;i++) {
			try {
				tmp[i]=tabFigures[i].clone();
			} catch (CloneNotSupportedException e) {
				// TODO Bloc catch généré automatiquement
				e.printStackTrace();
			}
		}
		if (listeEtats.size()>=MARGE_ANNULE) {
			listeEtats.remove(0);
		}
		listeEtats.add(tmp);
	}
	
	public void annuler() {
		viderPoints();
		listeFigSelectionnees.clear();
		int n = listeEtats.size()-1;
		nbFigures=listeEtats.get(n).length;
		for (int i=0;i<nbFigures;i++) {
			tabFigures[i]=listeEtats.get(n)[i];
		}
		repaint();
		listeEtats.remove(n);
		
	}

	public void rotation(int r) {
		ajouterEtat();
		for (int i = 0; i < listeFigSelectionnees.size(); i++) {
			listeFigSelectionnees.get(i).rotation(r);
		}
		repaint();
	}
	
	public void copier() {
		viderPoints();
		listeTampon.clear();
		for (int i = 0; i <listeFigSelectionnees.size();i++) {
			try {
				listeTampon.add(listeFigSelectionnees.get(i).clone());
			} catch (CloneNotSupportedException e) {
				// TODO Bloc catch généré automatiquement
				e.printStackTrace();
			}
		}
	}
	public void couper() {
		viderPoints();
		copier();
		supprimer();
	}
	
	public void coller(int a) {
		viderPoints();
		ajouterEtat();
		int distX= absSouris-listeTampon.get(0).getTabMemo()[0].x;
		int distY= ordSouris-listeTampon.get(0).getTabMemo()[0].y;
		for (int i=0;i<listeTampon.size();i++) {
			if (a==1)
				listeTampon.get(i).translater(distX, distY);
			else 
				listeTampon.get(i).translater(10, 10);

			try {
				tabFigures[nbFigures]=listeTampon.get(i).clone();
			} catch (CloneNotSupportedException e) {
				// TODO Bloc catch généré automatiquement
				e.printStackTrace();
			}
			nbFigures++;
		}
		
		repaint();
	}
	
	public void nouveau() {
		tabFigures=new FigureGeom[MAXTAILLE];
		listeFigSelectionnees.clear();
		viderPoints();
		nbFigures=0;
		listeEtats.clear();
		repaint();
	}
}
