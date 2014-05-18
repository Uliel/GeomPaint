package paint;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Menu extends JPanel {

	// ATTRIBUTS
	private boolean dessiner;
	private JToolBar formes = new JToolBar("Formes");
	private JToolBar outils = new JToolBar("Outils");
	private int numFigCourante;
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

	// Boutons de la boîte à outils
	private Bouton supprimer = new Bouton(1, new ImageIcon(
			"images/poubelle.png"));
	private Bouton selectionner = new Bouton(2, new ImageIcon(
			"images/fleche.png"));
	private Bouton remplir = new Bouton(3, new ImageIcon("images/peinture.png"));
	private Bouton[] tabBoutonsOutils = { supprimer, selectionner, remplir };

	// CONSTRUCTEUR
	/**
	 * Constructeur initialisant le menu
	 */
	public Menu() {
		// Creation des boutons de selection de forme et stockage dans un
		// tableau
		formes.setLayout(new GridLayout(2, 4, 2, 2));
		// Creation d'un auditeur commun a tous les boutons de forme
		ActionListener selectionForme = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dessiner = true;
				desactiverOutils();
				numFigCourante = ((Bouton) (e.getSource())).getValeur();
				if (((Bouton) (e.getSource())).isSelected()) {
					desactiverFormes();
					((Bouton) (e.getSource())).setSelected(true);
				}
				else 
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

		ActionListener selectionOutils = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dessiner = true;
				desactiverFormes();
				if (((Bouton) (e.getSource())).isSelected()
						&& ((Bouton) (e.getSource())).getValeur() == 2) {
					((Bouton) (e.getSource())).setSelected(true);
				}
				else {
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

		this.add(outils);
		this.add(formes);
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

	public void desactiverFormes() {
		for (int i = 0; i < tabBoutonsFormes.length; i++) {
			tabBoutonsFormes[i].setSelected(false);
		}
	}

	public void desactiverOutils() {
		for (int i = 0; i < tabBoutonsOutils.length; i++) {
			tabBoutonsOutils[i].setSelected(false);
		}
	}
}
