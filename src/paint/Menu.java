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
	private String numFigCourante;

	// CONSTRUCTEUR
	/**
	 * Constructeur initialisant le menu
	 */
	public Menu() {
		// Creation des boutons de selection de forme et stockage dans un
		// tableau
		formes.setLayout(new GridLayout(2, 4, 2, 2));
		Bouton cercle = new Bouton("1", new ImageIcon("images/cercle.jpg"));
		Bouton rectangle = new Bouton("2",
				new ImageIcon("images/rectangle.jpg"));
		Bouton carre = new Bouton("3", new ImageIcon("images/carre.jpg"));
		Bouton triangle = new Bouton("4", new ImageIcon("images/triangle.jpg"));
		Bouton ellipse = new Bouton("5", new ImageIcon("images/ovale.jpg"));
		Bouton polygone = new Bouton("6", new ImageIcon("images/polygone.jpg"));
		Bouton losange = new Bouton("7", new ImageIcon("images/losange.jpg"));
		Bouton trait = new Bouton("8", new ImageIcon("images/trait.jpg"));
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
		}

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
		return Integer.parseInt(this.numFigCourante);
	}
}
