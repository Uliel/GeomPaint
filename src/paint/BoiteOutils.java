package paint;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.*;

public class BoiteOutils extends JPanel {

	// ATTRIBUTS
	private boolean select;
	private boolean dessiner;
	private Dessin dessin;
	
	private JToolBar formes = new JToolBar("Formes");
	private JToolBar outils = new JToolBar("Outils");
	private JToolBar col = new JToolBar("Couleurs");
	
	private int numFigCourante;
	
	// Creation des boutons de selection de forme et stockage dans un
	// tableau
	private Bouton cercle = new Bouton(1, new ImageIcon("images/cercle.png"));
	private Bouton rectangle = new Bouton(2, new ImageIcon(
			"images/rectangle.png"));
	private Bouton carre = new Bouton(3, new ImageIcon("images/carre.png"));
	private Bouton triangle = new Bouton(4,
			new ImageIcon("images/triangle.png"));
	private Bouton ellipse = new Bouton(5, new ImageIcon("images/ovale.png"));
	private Bouton polygone = new Bouton(6,
			new ImageIcon("images/polygone.png"));
	private Bouton losange = new Bouton(7, new ImageIcon("images/losange.png"));
	private Bouton trait = new Bouton(8, new ImageIcon("images/trait.png"));
	private Bouton[] tabBoutonsFormes = { cercle, rectangle, carre, triangle,
			ellipse, polygone, losange, trait };
	
	// tableau de boutons de couleur
	private Bouton[] couleurs = new Bouton[30];
	int nbCoul = 20;
	private Bouton palette = new Bouton(1,new ImageIcon("images/palette.png"));

	// Boutons de la boîte à outils
	private Bouton supprimer = new Bouton(1, new ImageIcon(
			"images/poubelle.png"));
	private Bouton selectionner = new Bouton(2, new ImageIcon(
			"images/fleche.png"));
	private Bouton remplir = new Bouton(3, new ImageIcon("images/peinture.png"));
	private Bouton exporterJPG = new Bouton(4, new ImageIcon(
			"images/exporter_jpg2.png"));
	private Bouton exporterPNG = new Bouton(5, new ImageIcon(
			"images/exporter_png2.png"));
	private Bouton annuler = new Bouton(6, new ImageIcon(
			"images/annuler.png"));
	private Bouton[] tabBoutonsOutils = { supprimer, selectionner, remplir,
			exporterJPG, exporterPNG,annuler };
	
	//Boutons rotation
	private Bouton rotationDroite = new Bouton(1,new ImageIcon("images/rotation_d.png"));
	private Bouton rotationGauche = new Bouton(2,new ImageIcon("images/rotation_g.png"));
	
	// CONSTRUCTEUR
	/**
	 * Constructeur initialisant le menu
	 */
	public BoiteOutils(Dessin d) {
		
		supprimer.setEnabled(false);
		//JMenuBar contenant la selection de la taille
		JMenu size = new JMenu("Taille");
		JMenuItem[] tailles = new JMenuItem[16];
		JMenuItem image = new JMenuItem(new ImageIcon("images/taille.png"));
		for (int i=10;i<=60;i=i+4) {
			tailles[(i-10)/4] = new JMenuItem(i+"pts ----");
			tailles[(i-10)/4].setFont(new Font("Arial",Font.BOLD,i));
			size.add(tailles[(i-10)/4]);
		}
		JMenuBar tailleBarre= new JMenuBar();
		image.setEnabled(false);
		image.setDisabledIcon(image.getIcon());
		tailleBarre.add(size);
		tailleBarre.add(image);
		
		//JPanel contenant les boutons de rotation
		JPanel rotation = new JPanel();
		rotation.setLayout(new GridLayout(1,2));
		rotationDroite.setFocusPainted(false);
		rotationGauche.setFocusPainted(false);
		
		//ActionListener des boutons de rotation
		ActionListener rotationListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dessiner = false;
				desactiverOutils();
				desactiverFormes();
				desactiverCoul();
				desactiverRotation();
				((Bouton) (e.getSource())).setSelected(true);
			}
		};
		
		rotationDroite.addActionListener(rotationListener);
		rotationGauche.addActionListener(rotationListener);
		rotation.add(rotationDroite);
		rotation.add(rotationGauche);

		
		
	
		//Affichage des bulles d'aide
		supprimer.setToolTipText("Supprimer une figure");
		selectionner.setToolTipText("Selectionner une figure");
		remplir.setToolTipText("Remplir ou vider une figure");
		exporterJPG.setToolTipText("Exporter l'image en .jpg");
		exporterPNG.setToolTipText("Exporter l'image en .png");
		annuler.setToolTipText("Annuler la dernière modification");
		palette.setToolTipText("Sélectionner une couleur dans la palette");
		
		//On empeche les barres d'outils d'être déplacées
		formes.setFloatable(false); 
		outils.setFloatable(false); 
		col.setFloatable(false);
		
		//Definition des layout
		this.setLayout(new BorderLayout(4,4));
		formes.setLayout(new GridLayout(2, 5, 2, 2));
		outils.setLayout(new GridLayout(2, 3, 2, 2));
		col.setLayout(new BorderLayout(2,2));
		
		//Pour pouvoir avoir accès aux attributs et aux methodes de la classe dessin
		dessin = d;
		
		// Creation d'un auditeur commun a tous les boutons de forme
		ActionListener selectionForme = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dessiner = true;
				select = false;
				desactiverOutils();
				desactiverCoul();
				desactiverRotation();
				numFigCourante = ((Bouton) (e.getSource())).getValeur();
				if (((Bouton) (e.getSource())).isSelected()) {
					desactiverFormes();
					((Bouton) (e.getSource())).setSelected(true);
					((Bouton) (e.getSource())).setBorder(BorderFactory.createLineBorder(Color.black, 2, false));
				} else
					desactiverFormes();
			}
		};
		/*
		 * Pour tous les boutons : - Affectation de l'auditeur - Coloration du
		 * background - Attachement à la ToolBar
		 */
		for (int i = 0; i < tabBoutonsFormes.length; i++) {
			tabBoutonsFormes[i].setBackground(Color.white);
			tabBoutonsFormes[i].addActionListener(selectionForme);
			formes.add(tabBoutonsFormes[i]);
			tabBoutonsFormes[i].setFocusPainted(false);
		}
		// Creation d'un auditeur commun a tous les boutons outils

		ActionListener selectionOutils = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dessiner = false;
				select = false;
				desactiverFormes();
				desactiverCoul();
				desactiverRotation();
				desactiverOutils();
				if (((Bouton) (e.getSource())).getValeur() == 2) {
					select = true;
					((Bouton) (e.getSource())).setSelected(true);
					((Bouton) (e.getSource())).setBorder(BorderFactory.createLineBorder(Color.black, 2));

				} else {
					if (((Bouton) (e.getSource())).getValeur() == 4) {
						dessin.exporter("jpg");
						selectionner.doClick();
					}
					else if (((Bouton) (e.getSource())).getValeur() == 5) {
						dessin.exporter("png");
						selectionner.doClick();
					}
					else if (((Bouton) (e.getSource())).getValeur() == 3) {
						dessin.remplir();
						selectionner.doClick();
					}
					else if (((Bouton) (e.getSource())).getValeur() == 1) {
						dessin.supprimer();
						selectionner.doClick();
					}
					((Bouton) (e.getSource())).setSelected(false);
				}
				
			}
		};
		
		//Mise en formes des boutons et ajout du listenner
		for (int i = 0; i < tabBoutonsOutils.length; i++) {
			tabBoutonsOutils[i].setBackground(Color.white);
			outils.add(tabBoutonsOutils[i]);
			tabBoutonsOutils[i].setFocusPainted(false);
			tabBoutonsOutils[i].addActionListener(selectionOutils);
		}
		// Ajout des différentes couleurs

		col.setPreferredSize(new Dimension(230,70));
		final JPanel colors = new JPanel();
		colors.setLayout(new GridLayout(3,10,2,2));
		Color[] tabCouleurs= {Color.black,Color.darkGray,new Color(137,0,21),Color.red,new Color(255,227,39),
				Color.yellow,Color.green,new Color(0,162,232),Color.blue,new Color(163,73,164),Color.white,
				Color.lightGray,new Color(185,122,87),Color.pink,Color.orange,new Color(239,228,176),
				new Color(181,230,29),Color.cyan,new Color(112,146,190),Color.gray
				};
		
		//JLabel qui représente la couleur courante utilisée pour le dessin
		final JLabel couleurCourante = new JLabel("            ");
		couleurCourante.setBackground(Color.black);
		couleurCourante.setOpaque(true);
		
		//ActionListener pour les différentes couleurs
		final ActionListener selectionCouleur = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dessiner = false;
				desactiverOutils();
				desactiverFormes();
				desactiverCoul();
				desactiverRotation();
				((Bouton) (e.getSource())).setSelected(true);
				if (((Bouton) (e.getSource())).getCoul().getBlue()<150&&
						((Bouton) (e.getSource())).getCoul().getRed()<150&&
						((Bouton) (e.getSource())).getCoul().getGreen()<150)
				((Bouton) (e.getSource())).setBorder(BorderFactory.createLineBorder(new Color(255,230,90), 2));
				else 
					((Bouton) (e.getSource())).setBorder(BorderFactory.createLineBorder(Color.black, 2));
				couleurCourante.setBackground(((Bouton) (e.getSource())).getCoul());
				dessin.changeCouleur(((Bouton) (e.getSource())).getCoul());
				
			}
		};

		//Mise en forme des boutons
		for (int i = 0; i < tabCouleurs.length; i++) {
			couleurs[i]=new Bouton(i,tabCouleurs[i]);
			couleurs[i].addActionListener(selectionCouleur);
			colors.add(couleurs[i]);
		}
		for (int i=tabCouleurs.length;i<couleurs.length;i++) {
			couleurs[i]=new Bouton(i,new Color(230,230,230));
			couleurs[i].setEnabled(false);
			colors.add(couleurs[i]);
			
		}
		
		palette.setFocusPainted(false);
		
		//Definition du mouseListener pour l'apparition de la palette de couleurs
		JColorChooser jc=new JColorChooser(Color.black);
		ActionListener paletteListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {	     
	            Color background = JColorChooser.showDialog(null,
	                    "JColorChooser Sample", null);
	            if (background != null) {
	            	couleurCourante.setBackground(background);
	            	dessin.setCouleur(background);
	            	if (nbCoul<30) {
	            		couleurs[nbCoul].setEnabled(true);
	            		couleurs[nbCoul].setBackground(background);
	            		couleurs[nbCoul].addActionListener(selectionCouleur);
	            		couleurs[nbCoul].setCoul(background);
	            		nbCoul++;
	            	}
	            	else {
	            		nbCoul=20;
	            		couleurs[20].setBackground(background);
	            	}     
	            }
	        }
		};
		
		palette.addActionListener(paletteListener);
		
		//Mise en place des JToolBar
		
		col.add(colors,BorderLayout.CENTER);
		col.add(palette,BorderLayout.WEST);
		col.add(couleurCourante,BorderLayout.EAST);
		formes.add(tailleBarre);
		formes.add(rotation);
		formes.setPreferredSize(new Dimension(150,60));
		outils.setPreferredSize(new Dimension(190,60));
		col.setPreferredSize(new Dimension(400,60));
		this.add(col,BorderLayout.EAST);
		this.add(formes,BorderLayout.CENTER);
		this.add(outils,BorderLayout.WEST);
	}

	public Bouton getPalette() {
		return palette;
	}

	public Bouton getExporterJPG() {
		return exporterJPG;
	}

	public Bouton getExporterPNG() {
		return exporterPNG;
	}

	public Bouton getRotationDroite() {
		return rotationDroite;
	}

	public Bouton getRotationGauche() {
		return rotationGauche;
	}

	// ACCESSEURS
	public boolean getDessiner() {
		return this.dessiner;
	}

	public void setDessiner(boolean d) {
		this.dessiner = d;
	}

	public boolean getSelect() {
		return this.select;
	}
	
	public void setSelect(boolean d) {
		this.select = d;
	}
	
	public int getNumFigCourante() {
		return this.numFigCourante;
	}

	// methodes pour deselectionner les boutons
	public void desactiverFormes() {
		for (int i = 0; i < tabBoutonsFormes.length; i++) {
			tabBoutonsFormes[i].setSelected(false);
			tabBoutonsFormes[i].setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
			
		}
	}

	public void desactiverOutils() {
		for (int i = 0; i < tabBoutonsOutils.length; i++) {
			tabBoutonsOutils[i].setSelected(false);
			tabBoutonsOutils[i].setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
		}
	}
	public void desactiverCoul() {
		for (int i = 0; i < couleurs.length; i++) {
			couleurs[i].setSelected(false);
			couleurs[i].setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));

		}
	}
	public void desactiverRotation() {
		rotationDroite.setSelected(false);
		rotationGauche.setSelected(false);
		rotationDroite.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
		rotationGauche.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
	}
	
	public Bouton getSupprimer() {
		return supprimer;
	}

	public Bouton getRemplir() {
		return remplir;
	}

	public Bouton getSelectionner() {
		return selectionner;
	}
	
	
	
	
}
