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
	private JToolBar formes=new JToolBar("Formes");
	private String numFigCourante;

	
	// CONSTRUCTEUR
	/**
	 * Constructeur initialisant le menu
	 */
	public Menu() {
		// Creation des boutons de selection de forme et stockage dans un tableau
		formes.setLayout(new GridLayout(2,4));
		JButton cercle = new JButton("1", new ImageIcon("images/cercle.jpg"));
		JButton rectangle = new JButton("2", new ImageIcon("images/rectangle.jpg"));
		JButton carre = new JButton("3", new ImageIcon("images/carre.jpg"));
		JButton triangle = new JButton("4", new ImageIcon("images/triangle.jpg"));
		JButton ellipse = new JButton("5", new ImageIcon("images/ovale.jpg"));
		JButton polygone = new JButton("6", new ImageIcon("images/polygone.jpg"));
		JButton losange = new JButton("7", new ImageIcon("images/losange.jpg"));
		JButton trait = new JButton("8", new ImageIcon("images/trait.jpg"));
		JButton[] tabBoutonsFormes = {cercle, rectangle, carre, triangle, ellipse, polygone, losange, trait};
		
		// Creation d'un auditeur commun a tous les boutons
		ActionListener selectionForme = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dessiner = true;
				numFigCourante = ((JButton)(e.getSource())).getText();
			}
		};
		
		/* Pour tous les boutons :
		 *  - Affectation de l'auditeur
		 *  - Coloration du background
		 *  - Attachement à la ToolBar
		 */
		for (int i = 0 ; i < tabBoutonsFormes.length ; i++) {
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
