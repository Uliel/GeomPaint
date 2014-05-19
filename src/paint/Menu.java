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

public class Menu extends JPanel {

	// ATTRIBUTS
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
	private Bouton[] tabBoutonsOutils = { supprimer, selectionner, remplir,
			exporterJPG, exporterPNG };

	// CONSTRUCTEUR
	/**
	 * Constructeur initialisant le menu
	 */
	public Menu(Dessin d) {
		this.setLayout(new BorderLayout(4,4));
		formes.setLayout(new GridLayout(2, 4, 2, 2));
		outils.setLayout(new GridLayout(2, 3, 2, 2));
		col.setLayout(new BorderLayout(2,2));
		dessin = d;
		// Creation d'un auditeur commun a tous les boutons de forme
		ActionListener selectionForme = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dessiner = true;
				desactiverOutils();
				desactiverCoul();
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
				desactiverFormes();
				desactiverCoul();
				if (((Bouton) (e.getSource())).isSelected()
						&& ((Bouton) (e.getSource())).getValeur() == 2) {
					((Bouton) (e.getSource())).setSelected(true);
					((Bouton) (e.getSource())).setBorder(BorderFactory.createLineBorder(Color.black, 2));


				} else {
					if (((Bouton) (e.getSource())).getValeur() == 4)
						dessin.exporter("jpg");
					else if (((Bouton) (e.getSource())).getValeur() == 5)
						dessin.exporter("png");
					((Bouton) (e.getSource())).setSelected(false);
				}

			}
		};

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
				((Bouton) (e.getSource())).setSelected(true);
				((Bouton) (e.getSource())).setBorder(BorderFactory.createLineBorder(Color.black, 2));
				couleurCourante.setBackground(((Bouton) (e.getSource())).getCoul());
			}
		};

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
		
		JColorChooser jc=new JColorChooser(Color.black);
		MouseAdapter paletteListener = new java.awt.event.MouseAdapter() {
	        public void mouseClicked(java.awt.event.MouseEvent evt) {
	            Color background = JColorChooser.showDialog(null,
	                    "JColorChooser Sample", null);
	            if (background != null) {
	            	couleurCourante.setBackground(background);
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
		
		palette.addMouseListener(paletteListener);
		
		col.add(colors,BorderLayout.CENTER);
		col.add(palette,BorderLayout.WEST);
		col.add(couleurCourante,BorderLayout.EAST);
		formes.setPreferredSize(new Dimension(150,60));
		outils.setPreferredSize(new Dimension(190,60));
		col.setPreferredSize(new Dimension(400,60));
		this.add(col,BorderLayout.EAST);
		this.add(formes,BorderLayout.CENTER);
		this.add(outils,BorderLayout.WEST);

	}

	// ACCESSEURS
	public boolean getDessiner() {
		return this.dessiner;
	}

	public void setDessiner(boolean d) {
		this.dessiner = d;
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
}
