package paint;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import figures.Cercle;

/**
 * Classe Menu qui permet de choisir la couleur et la taille du plateau
 * 
 * @author Nicolas Gambarini
 * 
 * esssssaaiiii
 * 
 */
public class Menu extends JToolBar {
	/**
	 * Attribut Dessin auquel est associé le menu
	 */
	private Dessin d;
	private boolean dessiner;

	/**
	 * Constructeur qui permet d'initialiser le plateau de jeu
	 * 
	 * @param pl
	 */
	public Menu() {
		JButton forme = new JButton(new ImageIcon("images/cercle.jpg"));
		forme.setBackground(Color.white);
		ActionListener dessinCercle = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				dessiner = true;
			}
		};
		
		forme.addActionListener(dessinCercle);
		this.add(forme);	}
	
	public boolean getDessiner() {
		return this.dessiner;
	}
	
	public void setDessiner(boolean d) {
		this.dessiner = d;
	}

}
