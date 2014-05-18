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

	// CONSTRUCTEUR
	/**
	 * Constructeur initialisant le menu
	 */
	public Menu() {
		// Creation des boutons de selection de forme et stockage dans un
		// tableau
		formes.setLayout(new GridLayout(2, 4, 2, 2));
		Bouton cercle = new Bouton(1, new ImageIcon("images/cercle.png"));
		Bouton rectangle = new Bouton(2,
				new ImageIcon("images/rectangle.png"));
		Bouton carre = new Bouton(3, new ImageIcon("images/carre.png"));
		Bouton triangle = new Bouton(4, new ImageIcon("images/triangle.png"));
		Bouton ellipse = new Bouton(5, new ImageIcon("images/ovale.png"));
		Bouton polygone = new Bouton(6, new ImageIcon("images/polygone.png"));
		Bouton losange = new Bouton(7, new ImageIcon("images/losange.png"));
		Bouton trait = new Bouton(8, new ImageIcon("images/trait.png"));
		final Bouton[] tabBoutonsFormes = { cercle, rectangle, carre, triangle,
				ellipse, polygone, losange, trait };
		// Creation d'un auditeur commun a tous les boutons
		ActionListener selectionForme = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dessiner = true;
				for (int i = 0; i < tabBoutonsFormes.length; i++) {
					tabBoutonsFormes[i].setSelected(false);
				}
				numFigCourante = ((Bouton) (e.getSource())).getValeur();
				((Bouton) (e.getSource())).setSelected(true);
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

		
		//Boutons de la boîte à outils
		Bouton supprimer = new Bouton(1, new ImageIcon("images/poubelle.png"));
		Bouton selectionner = new Bouton(2, new ImageIcon("images/fleche.png"));
		Bouton remplir = new Bouton(3, new ImageIcon("images/peinture.png"));
		final Bouton[] tabBoutonsOutils = { supprimer,selectionner,remplir };
		for (int i = 0; i < tabBoutonsOutils.length; i++) {
			tabBoutonsOutils[i].setBackground(Color.white);
			outils.add(tabBoutonsOutils[i]);
			tabBoutonsOutils[i].setFocusPainted(false);
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
}
